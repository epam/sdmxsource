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
package org.sdmxsource.sdmx.ediparser.engine.reader.impl;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.*;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.engine.reader.EDIStructureReaderEngine;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIStructureReader;
import org.sdmxsource.sdmx.ediparser.util.EDIUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.*;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;


/**
 * The type Edi structure reader engine.
 */
public class EDIStructureReaderEngineImpl implements EDIStructureReaderEngine {

    @Override
    public SdmxBeans createSdmxBeans(EDIStructureReader reader) {
        InnerEngine engine = new InnerEngine(reader);
        return engine.processMessage();
    }

    private class InnerEngine {
        //The current line being processed
        private String currentLine;
        private String currentLineCopy;

        //Agency Id, determined from header
        private SdmxBeans beans;

        private EDIStructureReader ediReader;

        private String agencyId;

        private InnerEngine(EDIStructureReader reader) {
            this.ediReader = reader;
            agencyId = ediReader.getMessageAgency();
        }

        private SdmxBeans processMessage() {
            ConceptSchemeMutableBean defaultConceptScheme = new ConceptSchemeMutableBeanImpl();
            defaultConceptScheme.setAgencyId(agencyId);
            defaultConceptScheme.setVersion(ConceptSchemeBean.DEFAULT_SCHEME_VERSION);
            defaultConceptScheme.setId(ConceptSchemeBean.DEFAULT_SCHEME_ID);
            defaultConceptScheme.addName("en", ConceptSchemeBean.DEFAULT_SCHEME_NAME);
            try {
                String groupString;
                beans = new SdmxBeansImpl(getHeader());

                while (readNextLine()) {
                    if (ediReader.getLineType().isCodelistSegment()) {
                        groupString = currentLine;
                        try {
                            processCodelist();
                        } catch (Throwable th) {
                            throw new IllegalArgumentException("Error while attempting to process codelist group: " + groupString, th);
                        }
                    } else if (ediReader.getLineType().isConceptSegment()) {
                        groupString = currentLine;
                        try {
                            processConcept(defaultConceptScheme);
                        } catch (Throwable th) {
                            throw new IllegalArgumentException("Error while attempting to process statistical concept group: " + groupString, th);
                        }
                    } else if (ediReader.getLineType().isDSDSegment()) {
                        groupString = currentLine;
                        try {
                            processDataStructure();
                        } catch (Throwable th) {
                            throw new IllegalArgumentException("Error while attempting to process data structure definition group: " + groupString, th);
                        }
                    }
                }
            } catch (Throwable th) {
                throw new RuntimeException("Error while processing segment #" + ediReader.getLineNumber() + " - " + ediReader.getLineType() + currentLineCopy, th);
            }
            if (defaultConceptScheme.getItems().size() > 0) {
                beans.addConceptScheme(defaultConceptScheme.getImmutableInstance());
            }
            return beans;
        }

        /**
         * Gets header.
         *
         * @return the header
         */
        public HeaderBean getHeader() {
            Map<String, String> additionalAttributes = new HashMap<String, String>();
            StructureReferenceBean dataProviderReference = null;
            List<DatasetStructureReferenceBean> structure = new ArrayList<DatasetStructureReferenceBean>();
            String datasetId = null;

            PartyBean sender = ediReader.getSendingAgency();
            String recievingAgency = ediReader.getRecievingAgency();

            String id = ediReader.getEdiDocumentMetadata().getInterchangeReference();
            Date prepared = ediReader.getPreparationDate();

            List<PartyBean> receiver = new ArrayList<PartyBean>();
            if (ObjectUtil.validString(recievingAgency)) {
                receiver.add(new PartyBeanImpl(null, recievingAgency, null, null));
            }
            DATASET_ACTION datasetAction = null;
            Date embargoDate = null;
            Date extracted = null;
            Date reportingBegin = null;
            Date reportingEnd = null;

            List<TextTypeWrapper> name = null;
            List<TextTypeWrapper> source = null;

            boolean test = ediReader.getEdiDocumentMetadata().isTest();

            HeaderBean header = new HeaderBeanImpl(additionalAttributes,
                    structure,
                    dataProviderReference,
                    datasetAction,
                    id,
                    datasetId,
                    embargoDate,
                    extracted,
                    prepared,
                    reportingBegin,
                    reportingEnd,
                    name,
                    source,
                    receiver,
                    sender,
                    test);
            if (ediReader.getEdiDocumentMetadata().getMessageName() != null) {
                header.addName(new TextTypeWrapperImpl("en", ediReader.getEdiDocumentMetadata().getMessageName(), null));
            }
            return header;
        }


        /**
         * Marks move back line to true - this does not alter the current line, this means the next call to readNextLine() will not
         * actually do anything
         */
        private void goBackLine() {
            ediReader.moveBackLine();
        }

        /**
         * Reads the next line of the message, returns true if there was a next line
         */
        private boolean readNextLine() {
            currentLine = ediReader.getNextLine();
            currentLineCopy = currentLine;

            return currentLine != null;
        }


        private void processConcept(ConceptSchemeMutableBean defaultConceptScheme) throws IOException {
            String conceptId = currentLine;
            EDIUtil.parseId(conceptId);
            if (!ObjectUtil.validString(conceptId)) {
                throw new SdmxSyntaxException(EDI_PREFIX.CONCEPT + " is empty.  Concept must have an Id");
            }
            readNextLine();
            String conceptName;

            try {
                conceptName = ediReader.parseTextString();
            } catch (Throwable th) {
                throw new SdmxException(th, "Error while attempting to process concept name for concept '" + conceptId + "'");
            }
            if (!ObjectUtil.validString(conceptName)) {
                throw new SdmxSyntaxException(EDI_PREFIX.STRING + " segment for Concept '" + conceptId + "' is empty.  Concept must have a Name");
            }
            ConceptMutableBean conceptMutableBean = new ConceptMutableBeanImpl();
            conceptMutableBean.setId(conceptId);
            conceptMutableBean.addName("en", conceptName);
            defaultConceptScheme.addItem(conceptMutableBean);
        }

        /**
         * Creates the codelist, and stores it on the heap
         *
         * @throws IOException
         */
        private void processCodelist() throws IOException {
            String[] split = currentLine.split("\\+");

            String codelistId = split[0];
            String codelistName = split[3];
            EDIUtil.parseId(codelistId);
            CodelistMutableBean codelist = new CodelistMutableBeanImpl();
            codelist.setId(codelistId);
            codelist.setAgencyId(ediReader.getMessageAgency());
            codelist.addName("en", codelistName);
            try {
                while (processCode(codelist)) {
                }
            } catch (Throwable th) {
                throw new SdmxException(th, "Error while attempting to process codelist:  agency id='" + agencyId + "' id='" + codelistId + "'");
            }
            beans.addCodelist(codelist.getImmutableInstance());
        }

        /**
         * Processes a code line, and adds the code to the codelist on the heap
         *
         * @throws IOException
         */
        private boolean processCode(CodelistMutableBean codelist) throws IOException {
            readNextLine();
            if (ediReader.getLineType() == EDI_PREFIX.CODE_VALUE) {
                String codeValue = currentLine;
                readNextLine();
                String codeName;
                try {
                    codeName = ediReader.parseTextString();
                } catch (Throwable th) {
                    throw new SdmxException(th, "Error while attempting to process code name for code '" + codeValue + "'");
                }
                if (!ObjectUtil.validString(codeValue)) {
                    throw new SdmxSyntaxException(EDI_PREFIX.CODE_VALUE + " segment is empty, Code must have an id");
                }
                if (!ObjectUtil.validString(codeName)) {
                    throw new SdmxSyntaxException(EDI_PREFIX.STRING + " segment for Code '" + codeValue + "' is empty, Code must have a Name");
                }

                CodeMutableBean codeMutable = new CodeMutableBeanImpl();
                codeMutable.setId(codeValue);
                EDIUtil.parseId(codeValue);
                codeMutable.addName("en", codeName);
                codelist.addItem(codeMutable);
                return true;
            }

            goBackLine();
            return false;
        }

        /**
         * Processes the Data Structure line, and stores the DataStructure on the heap
         *
         * @throws IOException
         */
        private void processDataStructure() throws IOException {
            String dataStructureId = currentLine;

            //The next line should be a EDI String, representing the Key Family Name
            readNextLine();

            String dataStructureName = ediReader.parseTextString();

            DataStructureMutableBean dataStructure = new DataStructureMutableBeanImpl();
            dataStructure.setId(dataStructureId);
            EDIUtil.parseId(dataStructureId);
            dataStructure.addName("en", dataStructureName);
            dataStructure.setAgencyId(agencyId);
            try {
                processDataStructureComponents(dataStructure);
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process key family: agency id='" + agencyId + "' id='" + dataStructureId + "'", th);
            }
            beans.addDataStructure(dataStructure.getImmutableInstance());
        }


        private void processDataStructureComponents(DataStructureMutableBean dataStructure) throws IOException {
            //Create a new map for the dimensions and attributes
            Map<Integer, DimensionMutableBean> dimensions = new TreeMap<Integer, DimensionMutableBean>();

            //Process DSD Dimensions, expecting 3 segments
            //1 = SCD+ defining dimension id, type (frequency or normal) and position (Required)
            //2 = ATT+3+5+::: defining field length (Required)
            //3 = IDE+1+ defining codelist reference (Required)
            boolean usingStatedPositions = false;
            int i = 1;
            List<String> dimensionGroup = new ArrayList<String>();
            try {
                while (true) {
                    readNextLine();
                    if (currentLine.startsWith("1+")) {
                        //Break as we are no longer processing 'basic' dimensions (1 is the time dimension)
                        int checkPos = 1;
                        for (Integer pos : dimensions.keySet()) {
                            if (pos.equals(checkPos)) {
                                checkPos++;
                                dataStructure.addDimension(dimensions.get(pos));
                            } else {
                                throw new IllegalArgumentException("Dimension position '" + checkPos + "' is missing, please ensure dimension positions are sequential starting from 1");
                            }
                        }
                        break;
                    }
                    Object[] scdSegment = processSCDSegment();

                    Integer dimPos = (Integer) scdSegment[2];
                    if (dimPos != null) {
                        if (i > 1) {
                            throw new IllegalArgumentException("Dimension positions specified for some dimensions but not others.  Please aither specify dimension positions for all dimensions, or do not specify positions.  Unspecified positions will be processed in file order.");
                        }
                        usingStatedPositions = true;
                    } else {
                        if (usingStatedPositions) {
                            throw new IllegalArgumentException("Dimension positions specified for some dimensions but not others.  Please aither specify dimension positions for all dimensions, or do not specify positions.  Unspecified positions will be processed in file order.");
                        }
                        dimPos = i;
                        i++;
                    }
                    if (dimensions.containsKey(dimPos)) {
                        throw new IllegalArgumentException("Duplicate dimension position : " + dimPos);
                    }
                    if (dimPos <= 0) {
                        throw new IllegalArgumentException("Dimension position must be a positive integer, can not process given position '" + dimPos + "'");
                    }
                    DimensionMutableBean dim = new DimensionMutableBeanImpl();
                    if (scdSegment[0].equals(13)) {
                        dim.setFrequencyDimension(true);
                        if (!dimPos.equals(1)) {
                            throw new IllegalArgumentException("Frequency dimension must be the first dimension, but has been given as dimension position : " + dimPos);
                        }
                    } else if (scdSegment[0].equals(4)) {
                        //dim.setFrequencyDimension(true);


                    } else {
                        throwSCDSegmentException("either", (Integer) scdSegment[0], 14, 13);
                    }

                    StructureReferenceBean conceptRef = getComponentConceptReference((String) scdSegment[1]);
                    dim.setConceptRef(conceptRef);
                    dimensionGroup.add(conceptRef.getIdentifiableIds()[0]);
                    readNextLine();
                    RepresentationMutableBean rep = new RepresentationMutableBeanImpl();
                    dim.setRepresentation(rep);

                    TextFormatMutableBean textFormat = processFieldLength();
                    rep.setTextFormat(textFormat);

                    readNextLine();
                    rep.setRepresentation(processCodelistReference());

                    dimensions.put(dimPos, dim);
                }
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process key family dimension", th);
            }
            //Process Time Dimension, expecting 2 segments
            //1 = SCD+ defining dimension id, type (time 1) and position (Required)
            //2 = ATT+3+5+::: defining field length (Required)
            //The next line (SCD+1) should already have been read from the above loop
            try {
                Object[] scdSegment = processSCDSegment();
                readNextLine();
                RepresentationMutableBean tdRepresentation = new RepresentationMutableBeanImpl();
                TextFormatMutableBean textFormat = processFieldLength();
                textFormat.setTextType(TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD);  //Override Time format to become OBSERVATIONAL TIME PERIOD
                tdRepresentation.setTextFormat(textFormat);
                DimensionMutableBean timeDimension = new DimensionMutableBeanImpl();
                timeDimension.setTimeDimension(true);
                timeDimension.setRepresentation(tdRepresentation);
                timeDimension.setConceptRef(getComponentConceptReference((String) scdSegment[1]));
                dataStructure.addDimension(timeDimension);
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process time dimension", th);
            }

            readNextLine();
            try {
                //Process Time Format, expecting 3 segments
                //1 = SCD+ defining dimension id (TIME_FORMAT), type (time format 1) and position (Required)
                //2 = ATT+3+5+::: defining field length (Required)
                //3 = IDE+1+ defining codelist reference (Optional)
                //In reality this does not map to an SDMX Structure, so we can ignore it..
                AttributeMutableBean timeFormatAttribute = new AttributeMutableBeanImpl();
                Object[] scdSegment = processSCDSegment();
                if (!scdSegment[0].equals(1)) {
                    throwSCDSegmentException("time format", (Integer) scdSegment[0], 1);
                }
                timeFormatAttribute.setConceptRef(getComponentConceptReference((String) scdSegment[1]));
                timeFormatAttribute.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP);
                timeFormatAttribute.setAssignmentStatus("Mandatory");
                timeFormatAttribute.setDimensionReferences(dimensionGroup);
                readNextLine();
                RepresentationMutableBean tfRepresentation = new RepresentationMutableBeanImpl();
                timeFormatAttribute.setRepresentation(tfRepresentation);
                TextFormatMutableBean textFormat = processFieldLength();
                tfRepresentation.setTextFormat(textFormat);
                readNextLine();
                if (ediReader.getLineType().isCodelistReference()) {
                    tfRepresentation.setRepresentation(processCodelistReference());
                } else {
                    goBackLine();
                }
                dataStructure.addAttribute(timeFormatAttribute);
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process time format", th);
            }
            //Process Primary Measure, expecting 2 segments
            //1 = SCD+ defining dimension id , type (3) and position (Required)
            //2 = ATT+3+5+::: defining field length (Required)
            readNextLine();
            try {
                Object[] scdSegment = processSCDSegment();
                if (!scdSegment[0].equals(3)) {
                    throwSCDSegmentException("primary measure", (Integer) scdSegment[0], 3);
                }
                readNextLine();
                TextFormatMutableBean textFormat = processFieldLength();
                PrimaryMeasureMutableBean primaryMeasure = new PrimaryMeasureMutableBeanImpl();
                RepresentationMutableBean pmRepresentation = new RepresentationMutableBeanImpl();
                pmRepresentation.setTextFormat(textFormat);
                primaryMeasure.setRepresentation(pmRepresentation);
                primaryMeasure.setConceptRef(getComponentConceptReference((String) scdSegment[1]));

                dataStructure.setPrimaryMeasure(primaryMeasure);
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process primary measure", th);
            }
            //Process Observation Attributes, of which there are 1-3 each of which has 4-5 segments
            //1 = SCD+ defining dimension id , type (3) and position (Required)
            //2 = ATT+3+5+::: defining field length (Required)
            //3 = ATT+3+35+::: defining useage status (1=Optional, 2=Mandatory) (Required)
            //4 = ATT+3+32+::: defining attribute attachment level - in this case we are expecting observation (Required)
            //5 = IDE+1+ defining codelist reference (Optional)
            readNextLine();
            try {
                Object[] scdSegment = processSCDSegment();
                if (!scdSegment[0].equals(3)) {
                    throwSCDSegmentException("observation attribute", (Integer) scdSegment[0], 3);
                }
                AttributeMutableBean obsAttribute = new AttributeMutableBeanImpl();
                processAttribute(obsAttribute, (String) scdSegment[1]);
                dataStructure.addAttribute(obsAttribute);


                readNextLine();
                if (currentLine.startsWith("3+")) {
                    scdSegment = processSCDSegment();
                    if (!scdSegment[0].equals(3)) {
                        throwSCDSegmentException("Observation attribute", (Integer) scdSegment[0], 3);
                    }
                    obsAttribute = new AttributeMutableBeanImpl();
                    processAttribute(obsAttribute, (String) scdSegment[1]);
                    dataStructure.addAttribute(obsAttribute);


                    readNextLine();
                }

                if (currentLine.startsWith("3+")) {
                    scdSegment = processSCDSegment();
                    if (!scdSegment[0].equals(3)) {
                        throwSCDSegmentException("Observation attribute", (Integer) scdSegment[0], 3);
                    }
                    obsAttribute = new AttributeMutableBeanImpl();
                    processAttribute(obsAttribute, (String) scdSegment[1]);
                    dataStructure.addAttribute(obsAttribute);
                    readNextLine();
                }
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process observation attributes, expecting observation status, followed by observation confidentiality, followed by observation pre-break", th);
            }


            //Process Other attributes
            //1 = SCD+Z09 defining attribute id (Required)
            //2 = ATT+3+5+::: defining field length (Required)
            //3 = ATT+3+35+::: defining useage status (1=Optional, 2=Mandatory) (Required)
            //4 = ATT+3+32+::: defining attribute attachment level - in this case we are expecting observation (Required)
            //5 = IDE+1+ defining codelist reference (Optional)

            //Can only have one group level attribute, so make sure we do not process 2
            boolean processedGroupAttribute = false;
            try {
                while (ediReader.getLineType().isAttribute()) {
                    AttributeMutableBean attribute = new AttributeMutableBeanImpl();
                    processAttribute(attribute, currentLine);
                    if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.GROUP) {
                        if (!processedGroupAttribute) {
                            GroupMutableBean siblingGroup = new GroupMutableBeanImpl();
                            siblingGroup.setId(EDIUtil.getSiblingGroupId());
                            dataStructure.addGroup(siblingGroup);
                            List<String> dimensionRef = new ArrayList<String>();
                            for (DimensionMutableBean currentDim : dataStructure.getDimensions()) {
                                if (!currentDim.isFrequencyDimension() && !currentDim.isTimeDimension()) {
                                    dimensionRef.add(currentDim.getConceptRef().getChildReference().getId());
                                }
                            }
                            siblingGroup.setDimensionRef(dimensionRef);
                        }
                        attribute.setAttachmentGroup(EDIUtil.getSiblingGroupId());
                        processedGroupAttribute = true;
                    } else if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
                        attribute.setDimensionReferences(dimensionGroup);
                    }
                    dataStructure.addAttribute(attribute);
                    readNextLine();
                }
            } catch (Throwable th) {
                throw new IllegalArgumentException("Error while attempting to process key family attribute", th);
            }
            //Go back line, as we have gone past the end of the key famliy
            goBackLine();
        }

        private void throwSCDSegmentException(String expecting, int actual, int... expected) {
            StringBuilder sb = new StringBuilder();
            String concat = "";
            sb.append("Expecting " + expecting + ", SCD segment ");
            for (int currentExpected : expected) {
                sb.append(concat + "type '" + currentExpected + "'");
                sb.append(" (" + getSCDSegmentTypeAsString(currentExpected) + ")");
                concat = " or ";
            }
            sb.append(" but got SCD segment " + getSCDSegmentTypeAsString(actual));
            throw new IllegalArgumentException(sb.toString());
        }

        private String getSCDSegmentTypeAsString(int type) {
            switch (type) {
                case 1:
                    return "time";
                case 3:
                    return "array cell";
                case 13:
                    return "frequency dimension";
                case 4:
                    return "dimension";
                default:
                    return "unknown";
            }
        }

        /**
         * Returns an Object array containing the dimension type, the id, and the position
         *
         * @throws IOException
         */
        private Object[] processSCDSegment() throws IOException {

            //CURRENT LINE SOMETHING LIKE:
            //SCD+4+CONCEPT_ID++++:3'   - 4=Normal Dimension, 3=position in key
            //SCD+4+CONCEPT_ID++++:'    - Position in Key Not given

            //Top and Tail removes SCD+ leaving 4+CONCEPT_ID++++:3 or 4+CONCEPT_ID++++:
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DIMENSION, true);

            //Split the line on the ':', split 1 contains the dimension type and concept id, split 2 - if present - contains the dimension position
            String splitOnColon[] = currentLine.split(":");

            currentLine = splitOnColon[0];
            Integer keyPosition = null;
            if (splitOnColon.length > 1 && splitOnColon[1] != null) {
                keyPosition = Integer.parseInt(splitOnColon[1]);
            }

            String splitOnPlus[] = currentLine.split("\\+");
            int dimensionType;
            try {
                dimensionType = Integer.parseInt(splitOnPlus[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error while processing SCD segment, unexpected: '" + splitOnPlus[0] + "': was expecting an integer defining the concept role");
            }

            String conceptId = splitOnPlus[1];

            Object[] returnObj = new Object[3];
            returnObj[0] = dimensionType;
            returnObj[1] = conceptId;
            returnObj[2] = keyPosition;
            return returnObj;
        }


        private void processAttribute(AttributeMutableBean attribute, String conceptId) throws IOException {
            attribute.setConceptRef(getComponentConceptReference(conceptId));

            //Process the text format
            readNextLine();
            RepresentationMutableBean representation = new RepresentationMutableBeanImpl();
            representation.setTextFormat(processFieldLength());
            attribute.setRepresentation(representation);

            //Process the usage status - Mandatory (2) or Conditional (1)
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.USEAGE_STATUS, true);
            int useageStatus = Integer.parseInt(currentLine.substring(0, 1));
            String useageStatsStr;
            switch (useageStatus) {
                case 1:
                    useageStatsStr = "Conditional";
                    break;
                case 2:
                    useageStatsStr = "Mandatory";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Useage Status " + useageStatus + ". Expecting 1 or 2");
            }
            attribute.setAssignmentStatus(useageStatsStr);

            //Process the Attachment Level Dataset (1) or Series (4) or Sibling Group (9) or Obs (5)
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.ATTRIBUTE_ATTACHMENT_VALUE, true);
            int attachmentLevel = Integer.parseInt(currentLine.substring(0, 1));
            ATTRIBUTE_ATTACHMENT_LEVEL attachement;
            switch (attachmentLevel) {
                case 1:
                    attachement = ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET;
                    break;
                case 4:
                    attachement = ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
                    break;
                case 5:
                    attachement = ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION;
                    break;
                case 9:
                    attachement = ATTRIBUTE_ATTACHMENT_LEVEL.GROUP;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Attribute attachment level " + attachmentLevel + ". Expecting 1, 4, 5 or 9");
            }
            attribute.setAttachmentLevel(attachement);
            readNextLine();
            if (ediReader.getLineType().isCodelistReference()) {
                representation.setRepresentation(processCodelistReference());
            } else {
                goBackLine();
            }
        }

        private StructureReferenceBean getComponentConceptReference(String conceptId) {
            StructureReferenceBean sRef = new StructureReferenceBeanImpl(agencyId, ConceptSchemeBean.DEFAULT_SCHEME_ID,
                    ConceptSchemeBean.DEFAULT_SCHEME_VERSION, SDMX_STRUCTURE_TYPE.CONCEPT, conceptId);
            return sRef;
        }

        private TextFormatMutableBean processFieldLength() throws IOException {
            //CURRENT LINE SOMETHING LIKE:
            //ATT+3+5+:::AN..35   - alpha numeric, up to 35 characters long
            //ATT+3+5+:::AN3      - alpha numeric, 3 characters long exactly
            //ATT+3+5+:::N..3     - numeric up to 3 characters long

            //Top and Tail removes ATT+3+5+:::
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.FIELD_LENGTH, true);

            boolean upToLength = false;
            if (currentLine.contains("..")) {
                upToLength = true;
                currentLine = currentLine.replace("..", "");
            }
            boolean isString = false;
            if (currentLine.contains("A")) {
                isString = true;
                currentLine = currentLine.replace("A", "");
            }
            currentLine = currentLine.replace("N", "");

            BigInteger fieldLength;
            try {
                fieldLength = BigInteger.valueOf(Long.parseLong(currentLine));
            } catch (Throwable th) {
                throw new SdmxSemmanticException("Could not process segment " + EDI_PREFIX.FIELD_LENGTH + currentLine + " used to describe field length. Was expecting field type [A (alpha), AN (alpha numeric), or N (numeric)] followed by a field length [fixed length or '..' to indicate a variable length up to the given number].  Examples; AN1 or A1, or AN..3");
            }

            TextFormatMutableBean textFormat = new TextFormatMutableBeanImpl();
            textFormat.setMaxLength(fieldLength);
            if (upToLength) {
                textFormat.setMinLength(BigInteger.ONE);
            } else {
                textFormat.setMinLength(fieldLength);
            }
            if (isString) {
                textFormat.setTextType(TEXT_TYPE.STRING);
            } else {
                textFormat.setTextType(TEXT_TYPE.BIG_INTEGER);
            }
            return textFormat;
        }

        private StructureReferenceBean processCodelistReference() throws IOException {
            //IDE+1+CODELIST_ID
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.CODELIST_REFERENCE, true);
            StructureReferenceBean sRef = new StructureReferenceBeanImpl(agencyId, currentLine, "1.0", SDMX_STRUCTURE_TYPE.CODE_LIST);
            return sRef;
        }
    }
}
