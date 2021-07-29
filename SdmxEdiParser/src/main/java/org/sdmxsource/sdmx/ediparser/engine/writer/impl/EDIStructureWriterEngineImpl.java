/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.ediparser.engine.writer.impl;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.MESSSAGE_FUNCTION;
import org.sdmxsource.sdmx.ediparser.constants.SDMX_EDI_ATTRIBUTES;
import org.sdmxsource.sdmx.ediparser.engine.writer.EDIStructureWriterEngine;
import org.sdmxsource.sdmx.ediparser.model.impl.InterchangeHeader;
import org.sdmxsource.sdmx.ediparser.model.impl.MessageIdentification;
import org.sdmxsource.sdmx.ediparser.util.EDIStructureWriterUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.util.sort.IdentifiableComparator;
import org.sdmxsource.sdmx.util.sort.NameableComparator;
import org.sdmxsource.util.thread.ThreadLocalUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


/**
 * The type Edi structure writer engine.
 */
public class EDIStructureWriterEngineImpl extends AbstractEdiOutputEngine implements EDIStructureWriterEngine {
    private static String TIME_FORMAT = "TIME_FORMAT";
    //Set these to Version 1, but depending on the DSD they may be updated to version 2 concept ids
    private String obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V1;
    private String obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1;

    @Override
    public void writeToEDI(SdmxBeans beans, OutputStream out) {
        InnerEngine engine = new InnerEngine(beans, out);
        try {
            engine.writeToEDI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            engine.closeResources();
        }
    }

    private class InnerEngine extends AbstractEdiOutputEngine.InnerEngine {
        private Set<DataStructureBean> keyFamilies;

        // Preserve the order of concepts, so use a List rather than a Set
        private List<ConceptBean> concepts = new ArrayList<ConceptBean>();

        private Set<CodelistBean> codelists;
        private Set<String> agencies = new TreeSet<String>();

        private String processingAgency;

        /**
         * Instantiates a new Inner engine.
         *
         * @param beans the beans
         * @param out   the out
         */
        public InnerEngine(SdmxBeans beans, OutputStream out) {
            super(out);

            keyFamilies = new TreeSet<DataStructureBean>(NameableComparator.INSTANCE);
            keyFamilies.addAll(beans.getDataStructures());

            codelists = new TreeSet<CodelistBean>(NameableComparator.INSTANCE);
            codelists.addAll(beans.getCodelists());


            agencies = new TreeSet<String>();
            extractMaintenanceAgencies(keyFamilies);
            extractMaintenanceAgencies(codelists);
            extractMaintenanceAgencies(beans.getConceptSchemes());

            Set<String> agenciesRequiringTimeFormatConcept = new HashSet<String>();


            for (DataStructureBean dsd : keyFamilies) {
                boolean found = false;
                for (AttributeBean attributeBean : dsd.getAttributes()) {
                    if (attributeBean.isTimeFormat()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    //Time format not found
                    agenciesRequiringTimeFormatConcept.add(dsd.getAgencyId());
                }
            }

            for (ConceptSchemeBean conceptScheme : beans.getConceptSchemes()) {
                concepts.addAll(conceptScheme.getItems());
            }

            //If there is a DSD that does not have a time format attribute, then it will be created.  However we need to also output the concept TIME_FORMAT, so first check to
            //see if we have one of these, if not create one and add it to the list of concepts
            if (agenciesRequiringTimeFormatConcept.size() > 0) {
                for (String processingAgency : agenciesRequiringTimeFormatConcept) {
                    boolean hasTimeFormatConcept = false;
                    for (ConceptBean concept : concepts) {
                        if (concept.getMaintainableParent().getAgencyId().equals(processingAgency) && concept.getId().equals(TIME_FORMAT)) {
                            hasTimeFormatConcept = true;
                            break;
                        }
                    }
                    if (!hasTimeFormatConcept) {
                        ConceptSchemeMutableBean timeFormatConceptScheme = new ConceptSchemeMutableBeanImpl();
                        timeFormatConceptScheme.setAgencyId(processingAgency);
                        timeFormatConceptScheme.setId(TIME_FORMAT);
                        timeFormatConceptScheme.addName("en", TIME_FORMAT);
                        ConceptMutableBean concept = new ConceptMutableBeanImpl();
                        concept.setId(TIME_FORMAT);
                        concept.addName("en", "Time Format");
                        timeFormatConceptScheme.addItem(concept);
                        concepts.addAll(timeFormatConceptScheme.getImmutableInstance().getItems());
                    }
                }
            }
        }

        //WRITE OUT ALL STRUCTURES ////////////////////////////////
        private void writeToEDI() throws IOException {
            InterchangeHeader header = new InterchangeHeader("MTRegistry", "RegistryClient", new Date(), 1, null);
            writeInterchangeAdministration(header);

            int messsageRef = 1;
            //Loop through each agency reference contained with the document and output a message
            for (String agencyId : agencies) {
                processingAgency = agencyId;

                //WRITE HEADER INFO
                MessageIdentification identification = new MessageIdentification(messsageRef);
                writeMessageIdentification(identification);
                writeMessageFunction(MESSSAGE_FUNCTION.STATISTICAL_DEFINITIONS);
                writeStructureMaintAgency(agencyId);
                writeRecievingAgency("RecAgency");
                writeSendingAgency("SendingAgency");

                //OUTPUT STRUCTURES
                writeCodelists();
                writeConcepts();
                writeDataStructures();

                writeEndMessageAdministration();
                messsageRef++;
            }
            writeEndMessage();
        }


        //WRITE OUT KEY CODELISTS ////////////////////////////////
        private void writeCodelists() {
            for (CodelistBean currentCodelist : codelists) {
                if (currentCodelist.getAgencyId().equals(processingAgency)) {
                    try {
                        writeCodelist(currentCodelist);
                    } catch (Throwable th) {
                        throw new RuntimeException("Error parsing Codelist to EDI : " + currentCodelist.getUrn(), th);
                    }
                }
            }
        }

        private void writeCodelist(CodelistBean codelist) {
            writeSegment(EDIStructureWriterUtil.parseCodelistIdentifier(codelist));
            for (CodeBean code : codelist.getItems()) {
                try {
                    writeCode(code);
                } catch (Throwable th) {
                    throw new RuntimeException("Error parsing Code to EDI : " + code.getUrn(), th);
                }
            }
        }

        private void writeCode(CodeBean code) {
            writeSegment(EDIStructureWriterUtil.parseCodeId(code));
            writeSegment(EDIStructureWriterUtil.parseCodeName(code));
        }

        //WRITE OUT KEY CONCEPTS ////////////////////////////////
        private void writeConcepts() {
            List<String> processedConceptIds = new ArrayList<String>();

            for (ConceptBean currentConcept : concepts) {
                if (currentConcept.getMaintainableParent().getAgencyId().equals(processingAgency)) {
                    if (processedConceptIds.contains(currentConcept.getId())) {
                        throw new IllegalArgumentException("Duplicate concept Id found while processing concept: " + currentConcept.getUrn());
                    }
                    try {
                        writeConcept(currentConcept);
                    } catch (Throwable th) {
                        throw new RuntimeException("Error parsing Concept to EDI : " + currentConcept.getUrn(), th);
                    }
                    processedConceptIds.add(currentConcept.getId());
                }
            }
        }

        private void writeConcept(ConceptBean concept) {
            writeSegment(EDIStructureWriterUtil.parseConceptIdentifier(concept));
            writeSegment(EDIStructureWriterUtil.parseConceptName(concept));
        }


        //WRITE OUT KEY FAMILIES ////////////////////////////////
        private void writeDataStructures() {
            for (DataStructureBean currentKf : keyFamilies) {
                if (currentKf.getAgencyId().equals(processingAgency)) {
                    try {
                        writeDataStructure(currentKf);
                    } catch (Throwable th) {
                        throw new SdmxException(th, "Error parsing DSD to EDI : " + currentKf.getUrn());
                    }
                }
            }
        }

        private void writeDataStructure(DataStructureBean dsd) {
            if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V1) == null) {
                if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V2) != null) {
                    obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V2;
                }
            }

            if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1) == null) {
                if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2) != null) {
                    obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2;
                }
            }

            writeSegment(EDIStructureWriterUtil.parseDataStructureIdentifier(dsd));
            writeSegment(EDIStructureWriterUtil.parseDataStructureName(dsd));

            if (dsd.getGroups() != null && dsd.getGroups().size() > 1) {
                throw new IllegalArgumentException("DSD can not have more then one group in EDI"); //IMPORTANT Exception
            }
            validateDataStructureComponents(dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION), true);
            validateDataStructureComponents(dsd.getAttributes(), false);

            int i = 1;
            for (DimensionBean dim : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                writeDimension(dim, i);
                i++;
            }
            //WRITE TIME DIMENISON
            writeTimeDimension(dsd.getTimeDimension(), i);

            //WRITE TIME FORMAT
            boolean processedTimeFormatattribute = false;
            boolean defaultedTimeFormatattribute = false;
            for (AttributeBean attribute : dsd.getAttributes()) {
                if (attribute.isTimeFormat()) {
                    processedTimeFormatattribute = true;
                    writeTimeFormat(attribute, ++i);
                }
            }
            if (!processedTimeFormatattribute) {
                ComponentBean timeFormatComponent = dsd.getComponent("TIME_FORMAT");
                if (timeFormatComponent != null) {
                    if (!(timeFormatComponent instanceof AttributeBean)) {
                        throw new IllegalArgumentException("Could not output Key Family, no attributes contain isTimeFormat=true, and the default 'TIME_FORMAT' id has been taken");
                    }
                    defaultedTimeFormatattribute = true;
                    writeTimeFormat((AttributeBean) timeFormatComponent, ++i);
                } else {
                    //Write the time format out (just create one);
                    writeTimeFormat(++i);
                }
            }

            //WRITE PRIMARY MEASURE
            writePrimaryMeasure(dsd.getPrimaryMeasure(), ++i);

            writeObservationAttributes(dsd, ++i);

            List<AttributeBean> sortedAttributes = new ArrayList<AttributeBean>(dsd.getAttributes());
            Collections.sort(sortedAttributes, IdentifiableComparator.INSTANCE);

            for (AttributeBean attribute : sortedAttributes) {
                if (isObservationAttribute(attribute.getId())) {
                    //ENSURE IT IS AT THE OBSERVATION LEVEL
                    if (attribute.getAttachmentLevel() != ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION) {
                        throw new SdmxException("Can not output EDI, attribute '" + attribute.getId() + "' expected to be attached to the observation, actual attachment was: " + attribute.getAttachmentLevel());
                    }
                } else if (!attribute.isTimeFormat()) {
                    if (defaultedTimeFormatattribute && attribute.getId().equals(TIME_FORMAT)) {
                        continue;
                    }
                    writeAttribute(attribute);
                }
            }
        }

        private boolean isObservationAttribute(String id) {
            return id.equals(SDMX_EDI_ATTRIBUTES.OBS_STATUS) || id.equals(obsConf) || id.equals(obsPreBreak);
        }

        private void validateDataStructureComponents(List<? extends ComponentBean> componentBeans, boolean mandatoryCodelist) {
            for (ComponentBean componentBean : componentBeans) {
                CrossReferenceBean codelistRef = null;
                if (componentBean.getRepresentation() != null) {
                    codelistRef = componentBean.getRepresentation().getRepresentation();
                }
                if (codelistRef == null) {
                    if (mandatoryCodelist) {
                        throw new IllegalArgumentException("Codelist Reference is missing"); //IMPORTANT Exception
                    }
                } else {
                    if (!ThreadLocalUtil.contains(EDI_CONSTANTS.EDI_LENIENT_MODE)) {
                        MaintainableRefBean codelistMaintRef = codelistRef.getMaintainableReference();
                        //CHECK THAT THE CROSS REFERENCED COMPONENTS ARE OF THE SAME AGENCY AS THE ONE WE ARE PROCESSING
                        if (!codelistMaintRef.getAgencyId().equals(processingAgency)) {
                            throw new IllegalArgumentException("Agency of Codelist Reference '" + codelistMaintRef.getAgencyId() + "' differs from DSD agency `" + processingAgency + "'"); //IMPORTANT Exception
                        }
                    }
                }

                if (!ThreadLocalUtil.contains(EDI_CONSTANTS.EDI_LENIENT_MODE)) {
                    MaintainableRefBean conceptMaintRef = componentBean.getConceptRef().getMaintainableReference();
                    if (!conceptMaintRef.getAgencyId().equals(processingAgency)) {
                        throw new IllegalArgumentException("Agency of Concept Reference '" + conceptMaintRef.getAgencyId() + "'  differs from DSD agency '" + processingAgency + "'"); //IMPORTANT Exception
                    }
                }
            }
        }

        private void writeObservationAttributes(DataStructureBean kf, int position) {
            AttributeBean obsStatus = kf.getObservationAttribute("OBS_STATUS");
            if (obsStatus == null) {
                throw new IllegalArgumentException("Could not output Data Structure, missing observation attribute 'OBS_STATUS'");
            }
            if (!obsStatus.isMandatory()) {
                throw new IllegalArgumentException("Could not output Data Structure, 'OBS_STATUS' attribute must be mandatory");
            }
            writeObservationAttribute(obsStatus, position);
            AttributeBean obsConfAttr = kf.getObservationAttribute(obsConf);
            if (obsConfAttr != null) {
                writeObservationAttribute(obsConfAttr, ++position);
            }
            AttributeBean obsPreBreakAttr = kf.getObservationAttribute(obsPreBreak);
            if (obsPreBreakAttr != null) {
                writeObservationAttribute(obsPreBreakAttr, ++position);
            }
        }

        private void writeDimension(DimensionBean dim, int position) {
            writeSegment(EDIStructureWriterUtil.parseDimensionIdentification(dim, position));
            writeSegment(EDIStructureWriterUtil.parseFieldLength(dim, true));
            writeSegment(EDIStructureWriterUtil.parseCodelistReference(dim));
        }

        private void writeTimeDimension(DimensionBean dim, int position) {
            writeSegment(EDIStructureWriterUtil.parseDimensionIdentification(dim, position));
            writeSegment(EDIStructureWriterUtil.parseFieldLength(dim, true));
        }

        private void writeTimeFormat(int position) {
            writeSegment(EDIStructureWriterUtil.parseTimeFormat(position, TIME_FORMAT));
            writeSegment(EDIStructureWriterUtil.parseTimeFormatFieldLength());
        }

        private void writeTimeFormat(AttributeBean attribute, int position) {
            if (attribute.getConceptRef().getTargetReference().isMaintainable()) {
                writeSegment(EDIStructureWriterUtil.parseTimeFormat(position, attribute.getConceptRef().getMaintainableReference().getMaintainableId()));
            } else {
                writeSegment(EDIStructureWriterUtil.parseTimeFormat(position, attribute.getConceptRef().getChildReference().getId()));
            }
            writeSegment(EDIStructureWriterUtil.parseTimeFormatFieldLength());
            if (attribute.getRepresentation() != null && attribute.getRepresentation().getRepresentation() != null) {
                writeSegment(EDIStructureWriterUtil.parseCodelistReference(attribute));
            }
        }

        private void writePrimaryMeasure(PrimaryMeasureBean primaryMeasure, int position) {
            writeSegment(EDIStructureWriterUtil.parsePrimaryMeasureIdentification(primaryMeasure, position));
            writeSegment(EDIStructureWriterUtil.parseFieldLength(primaryMeasure, true));
        }

        private void writeObservationAttribute(AttributeBean attribute, int pos) {
            writeSegment(EDIStructureWriterUtil.parseObservationAttribute(attribute, pos));
            writeSegment(EDIStructureWriterUtil.parseFieldLength(attribute, false));
            writeSegment(EDIStructureWriterUtil.parseUseageStatus(attribute));
            writeSegment(EDIStructureWriterUtil.parseDimensionAttributeAttachmentLevel(attribute));
            if (attribute.hasCodedRepresentation()) {
                writeSegment(EDIStructureWriterUtil.parseCodelistReference(attribute));
            }
        }

        private void writeAttribute(AttributeBean attribute) {
            writeSegment(EDIStructureWriterUtil.parseDimensionAttribute(attribute));
            writeSegment(EDIStructureWriterUtil.parseFieldLength(attribute, false));
            writeSegment(EDIStructureWriterUtil.parseUseageStatus(attribute));
            writeSegment(EDIStructureWriterUtil.parseDimensionAttributeAttachmentLevel(attribute));
            if (attribute.hasCodedRepresentation()) {
                writeSegment(EDIStructureWriterUtil.parseCodelistReference(attribute));
            }
        }

        //private void extractMaintenanceAgencies(Set<? extends MaintainableBean> maintSet) {
        private void extractMaintenanceAgencies(Collection<? extends MaintainableBean> maintColl) {
            if (maintColl == null) {
                return;
            }
            for (MaintainableBean maint : maintColl) {
                agencies.add(maint.getAgencyId());
            }
        }

        @Override
        public void closeResources() {
            super.closeResources();
        }
    }
}
