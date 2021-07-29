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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalDataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalMeasureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AgencySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataConsumerSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataProviderSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.ReportingTaxonomyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.HierarchicalCodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.CrossSectionalDataStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping.StructureSetBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataStructureDefinitionBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.process.ProcessBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Sdmx beans v 2 struc doc builder.
 */
public class SdmxBeansV2StrucDocBuilder extends AbstractSdmxBeansV2Builder implements Builder<SdmxBeans, StructureDocument> {

    @Override
    public SdmxBeans build(StructureDocument structuresDoc) throws SdmxException {
        Set<String> urns = new HashSet<String>();
        StructureType structures = structuresDoc.getStructure();

        SdmxBeansImpl beans = new SdmxBeansImpl(new HeaderBeanImpl(structures.getHeader()));

        //CATEGORY SCHEMES
        processCategorySchemes(structures.getCategorySchemes(), beans);

        if (structures.getCodeLists() != null && structures.getCodeLists().getCodeListList() != null) {
            for (CodeListType currentType : structures.getCodeLists().getCodeListList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new CodelistBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CODE_LIST, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
        if (structures.getConcepts() != null && structures.getConcepts().getConceptSchemeList() != null) {
            for (ConceptSchemeType currentType : structures.getConcepts().getConceptSchemeList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ConceptSchemeBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
        //CONCEPTS
        Map<String, List<ConceptType>> conceptAgencyMap = new HashMap<String, List<ConceptType>>();
        if (structures.getConcepts() != null && structures.getConcepts().getConceptList() != null) {
            for (ConceptType currentType : structures.getConcepts().getConceptList()) {
                List<ConceptType> concepts;
                if (conceptAgencyMap.containsKey(currentType.getAgencyID())) {
                    concepts = conceptAgencyMap.get(currentType.getAgencyID());
                } else {
                    concepts = new ArrayList<ConceptType>();
                    conceptAgencyMap.put(currentType.getAgencyID(), concepts);
                }
                concepts.add(currentType);
            }
        }
        for (String currentConceptAgency : conceptAgencyMap.keySet()) {
            try {
                addIfNotDuplicateURN(beans, urns, new ConceptSchemeBeanImpl(conceptAgencyMap.get(currentConceptAgency), currentConceptAgency));
            } catch (Throwable th) {
                throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, currentConceptAgency, ConceptSchemeBean.DEFAULT_SCHEME_ID, ConceptSchemeBean.DEFAULT_SCHEME_VERSION);
            }
        }

        //DATAFLOWS
        processDataflows(structures.getDataflows(), beans);

        if (structures.getHierarchicalCodelists() != null && structures.getHierarchicalCodelists().getHierarchicalCodelistList() != null) {
            for (HierarchicalCodelistType currentType : structures.getHierarchicalCodelists().getHierarchicalCodelistList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new HierarchicalCodelistBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
        if (structures.getKeyFamilies() != null && structures.getKeyFamilies().getKeyFamilyList() != null) {
            for (KeyFamilyType currentType : structures.getKeyFamilies().getKeyFamilyList()) {
                try {
                    if (isXsDataStructure(currentType)) {
                        //HACK HORRIBLE HACK
                        CrossSectionalDataStructureBean xsdBean = new CrossSectionalDataStructureBeanImpl(currentType);
                        if (!xsdBean.getDimensions(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION).isEmpty()) {
                            CrossSectionalDataStructureMutableBean mutable = xsdBean.getMutableInstance();
                            CrossSectionalMeasureMutableBean xsMutable = mutable.getCrossSectionalMeasures().get(0);
                            StructureReferenceBean sRef = xsMutable.getConceptRef();

                            Map<String, StructureReferenceBean> mapping = new HashMap<String, StructureReferenceBean>();
                            StructureReferenceBean cocneptSchemeRef = new StructureReferenceBeanImpl(sRef.getMaintainableReference(), SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
                            for (DimensionMutableBean dim : mutable.getDimensions()) {
                                if (dim.isMeasureDimension()) {
                                    mapping.put(dim.getId(), dim.getRepresentation().getRepresentation());
                                    dim.getRepresentation().setRepresentation(cocneptSchemeRef);
                                }
                            }
                            mutable.setMeasureDimensionCodelistMapping(mapping);
                            xsdBean = mutable.getImmutableInstance();
                        }
                        addIfNotDuplicateURN(beans, urns, xsdBean);
                    } else {
                        addIfNotDuplicateURN(beans, urns, new DataStructureBeanImpl(currentType));
                    }
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DSD, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }

        //METADATA FLOWS
        processMetadataFlows(structures.getMetadataflows(), beans);

        //METADATA STRUCTURES
        if (structures.getMetadataStructureDefinitions() != null && structures.getMetadataStructureDefinitions().getMetadataStructureDefinitionList() != null) {
            for (MetadataStructureDefinitionType currentType : structures.getMetadataStructureDefinitions().getMetadataStructureDefinitionList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new MetadataStructureDefinitionBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.MSD, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
            //throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Metadata Structure Definition at SMDX v2.0 - please use SDMX v2.1");
        }


        //ORGANISATION SCHEMES
        if (structures.getOrganisationSchemes() != null) {
            for (OrganisationSchemeType orgSchemeType : structures.getOrganisationSchemes().getOrganisationSchemeList()) {

                if (ObjectUtil.validCollection(orgSchemeType.getAgenciesList())) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new AgencySchemeBeanImpl(orgSchemeType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, orgSchemeType.getAgencyID(), orgSchemeType.getId(), orgSchemeType.getVersion());
                    }
                }
                if (ObjectUtil.validCollection(orgSchemeType.getDataConsumersList())) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new DataConsumerSchemeBeanImpl(orgSchemeType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME, orgSchemeType.getAgencyID(), orgSchemeType.getId(), orgSchemeType.getVersion());
                    }
                }
                if (ObjectUtil.validCollection(orgSchemeType.getDataProvidersList())) {
                    try {
                        addIfNotDuplicateURN(beans, urns, new DataProviderSchemeBeanImpl(orgSchemeType));
                    } catch (Throwable th) {
                        throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME, orgSchemeType.getAgencyID(), orgSchemeType.getId(), orgSchemeType.getVersion());
                    }
                }

                // If the organisation scheme contains no elements, then this is an error
                if (orgSchemeType.getAgenciesList().isEmpty() &&
                        orgSchemeType.getDataConsumersList().isEmpty() &&
                        orgSchemeType.getDataProvidersList().isEmpty()) {
                    throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_INVALID_ORGANISATION_SCHEME_NO_CONTENT, orgSchemeType.getAgencyID(), orgSchemeType.getId());
                }
            }
        }

        //PROCESSES
        if (structures.getProcesses() != null && structures.getProcesses().getProcessList() != null) {
            for (ProcessType currentType : structures.getProcesses().getProcessList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ProcessBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.PROCESS, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }

        //REPORTING TAXONOMIES
        if (structures.getReportingTaxonomies() != null && structures.getReportingTaxonomies().getReportingTaxonomyList() != null) {
            for (ReportingTaxonomyType currentType : structures.getReportingTaxonomies().getReportingTaxonomyList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new ReportingTaxonomyBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }

        //STRUCTURE SETS
        if (structures.getStructureSets() != null && structures.getStructureSets().getStructureSetList() != null) {
            for (StructureSetType currentType : structures.getStructureSets().getStructureSetList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new StructureSetBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.STRUCTURE_SET, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
        return beans;
    }

    /**
     * Checks if the given Keyfamily is CrossSectional.
     * However This doesn't guarantee that is a Valid CrossSectional Data Structure.
     *
     * @param keyFamilyType
     * @return true if it is an CrossSectional Data Structure
     */
    private boolean isXsDataStructure(KeyFamilyType keyFamilyType) {
        ComponentsType components = keyFamilyType.getComponents();
        if (components != null) {
            if (ObjectUtil.validCollection(components.getDimensionList())) {
                for (DimensionType dim : components.getDimensionList()) {
                    if (dim.getCrossSectionalAttachDataSet() || dim.getCrossSectionalAttachGroup()
                            || dim.getCrossSectionalAttachSection() || dim.getCrossSectionalAttachObservation()) {
                        return true;
                    }
                }
            }
            if (ObjectUtil.validCollection(components.getAttributeList())) {
                for (AttributeType att : components.getAttributeList()) {
                    if (att.getCrossSectionalAttachDataSet() || att.getCrossSectionalAttachGroup()
                            || att.getCrossSectionalAttachSection() || att.getCrossSectionalAttachObservation()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
