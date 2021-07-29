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
package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ObjectIDType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.RepresentationTypeType;
import org.sdmx.resources.sdmxml.schemas.v21.common.MaintainableReferenceBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ObjectTypeCodelistType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;


/**
 * The type Xml beans enum util.
 */
public class XmlBeansEnumUtil {

    /**
     * Gets sdmx structure type.
     *
     * @param ref the ref
     * @return the sdmx structure type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONVERT FROM V2.1 SCHEMA			///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static SDMX_STRUCTURE_TYPE getSdmxStructureType(MaintainableReferenceBaseType ref) {
        return getSdmxStructureType(ref.getRef().getClass1());
    }

    /**
     * Build object type codelist type . enum.
     *
     * @param buildFrom the build from
     * @return the object type codelist type . enum
     */
    public static ObjectTypeCodelistType.Enum build(SDMX_STRUCTURE_TYPE buildFrom) {
        if (buildFrom == null) {
            return ObjectTypeCodelistType.ANY;
        }
        switch (buildFrom) {
            case ANY:
                return ObjectTypeCodelistType.IDENTIFIABLE_OBJECT_TARGET;
            case IDENTIFIABLE_OBJECT_TARGET:
                return ObjectTypeCodelistType.IDENTIFIABLE_OBJECT_TARGET;
            case AGENCY:
                return ObjectTypeCodelistType.AGENCY;
            case AGENCY_SCHEME:
                return ObjectTypeCodelistType.AGENCY_SCHEME;
            case ATTACHMENT_CONSTRAINT:
                return ObjectTypeCodelistType.ATTACHMENT_CONSTRAINT;
            case DATA_ATTRIBUTE:
                return ObjectTypeCodelistType.ATTRIBUTE;
            case ATTRIBUTE_DESCRIPTOR:
                return ObjectTypeCodelistType.ATTRIBUTE_DESCRIPTOR;
            case CATEGORISATION:
                return ObjectTypeCodelistType.CATEGORISATION;
            case CATEGORY:
                return ObjectTypeCodelistType.CATEGORY;
            case CATEGORY_SCHEME:
                return ObjectTypeCodelistType.CATEGORY_SCHEME;
            case CATEGORY_SCHEME_MAP:
                return ObjectTypeCodelistType.CATEGORY_SCHEME_MAP;
            case CODE:
                return ObjectTypeCodelistType.CODE;
            case CODE_MAP:
                return ObjectTypeCodelistType.CODE_MAP;
            case CODE_LIST:
                return ObjectTypeCodelistType.CODELIST;
            case CODE_LIST_MAP:
                return ObjectTypeCodelistType.CODELIST_MAP;
            case COMPONENT_MAP:
                return ObjectTypeCodelistType.COMPONENT_MAP;
            case CONCEPT:
                return ObjectTypeCodelistType.CONCEPT;
            case CONCEPT_MAP:
                return ObjectTypeCodelistType.CONCEPT_MAP;
            case CONCEPT_SCHEME:
                return ObjectTypeCodelistType.CONCEPT_SCHEME;
            case CONCEPT_SCHEME_MAP:
                return ObjectTypeCodelistType.CONCEPT_SCHEME_MAP;
//			case C: return ObjectTypeCodelistType.CONSTRAINT;  //IMPORTANT CONSTRAINT NOT SUPPORTED
            case CONSTRAINT_CONTENT_TARGET:
                return ObjectTypeCodelistType.CONSTRAINT_TARGET;
            case CONTENT_CONSTRAINT:
                return ObjectTypeCodelistType.CONTENT_CONSTRAINT;
            case DATA_CONSUMER:
                return ObjectTypeCodelistType.DATA_CONSUMER;
            case DATA_CONSUMER_SCHEME:
                return ObjectTypeCodelistType.DATA_CONSUMER_SCHEME;
            case DATA_PROVIDER:
                return ObjectTypeCodelistType.DATA_PROVIDER;
            case DATA_PROVIDER_SCHEME:
                return ObjectTypeCodelistType.DATA_PROVIDER_SCHEME;
            case DATASET_TARGET:
                return ObjectTypeCodelistType.DATA_SET_TARGET;
            case DSD:
                return ObjectTypeCodelistType.DATA_STRUCTURE;
            case DATAFLOW:
                return ObjectTypeCodelistType.DATAFLOW;
            case DIMENSION:
                return ObjectTypeCodelistType.DIMENSION;
            case DIMENSION_DESCRIPTOR:
                return ObjectTypeCodelistType.DIMENSION_DESCRIPTOR;
            case DIMENSION_DESCRIPTOR_VALUES_TARGET:
                return ObjectTypeCodelistType.DIMENSION_DESCRIPTOR_VALUES_TARGET;
            case GROUP:
                return ObjectTypeCodelistType.GROUP_DIMENSION_DESCRIPTOR;
            case HIERARCHICAL_CODE:
                return ObjectTypeCodelistType.HIERARCHICAL_CODE;
            case HIERARCHICAL_CODELIST:
                return ObjectTypeCodelistType.HIERARCHICAL_CODELIST;
            case HIERARCHY:
                return ObjectTypeCodelistType.HIERARCHY;
            case HYBRID_CODE:
                return ObjectTypeCodelistType.HYBRID_CODE_MAP;
            case HYBRID_CODELIST_MAP:
                return ObjectTypeCodelistType.HYBRID_CODELIST_MAP;
            case LEVEL:
                return ObjectTypeCodelistType.LEVEL;
            case MEASURE_DESCRIPTOR:
                return ObjectTypeCodelistType.MEASURE_DESCRIPTOR;
            case MEASURE_DIMENSION:
                return ObjectTypeCodelistType.MEASURE_DIMENSION;
            case METADATA_ATTRIBUTE:
                return ObjectTypeCodelistType.METADATA_ATTRIBUTE;
            case METADATA_SET:
                return ObjectTypeCodelistType.METADATA_SET;
            case MSD:
                return ObjectTypeCodelistType.METADATA_STRUCTURE;
            case METADATA_TARGET:
                return ObjectTypeCodelistType.METADATA_TARGET;
            case METADATA_FLOW:
                return ObjectTypeCodelistType.METADATAFLOW;
//			case ORG: return ObjectTypeCodelistType.ORGANISATION;  //IMPORTANT ORGANISATION NOT SUPPORTED
//			case ORG: return ObjectTypeCodelistType.ORGANISATION_SCHEME;  //IMPORTANT ORGANISATION_SCHEME NOT SUPPORTED
            case ORGANISATION_MAP:
                return ObjectTypeCodelistType.ORGANISATION_MAP;
            case ORGANISATION_SCHEME_MAP:
                return ObjectTypeCodelistType.ORGANISATION_SCHEME_MAP;
            case ORGANISATION_UNIT:
                return ObjectTypeCodelistType.ORGANISATION_UNIT;
            case ORGANISATION_UNIT_SCHEME:
                return ObjectTypeCodelistType.ORGANISATION_UNIT_SCHEME;
            case PRIMARY_MEASURE:
                return ObjectTypeCodelistType.PRIMARY_MEASURE;
            case PROCESS:
                return ObjectTypeCodelistType.PROCESS;
            case PROCESS_STEP:
                return ObjectTypeCodelistType.PROCESS_STEP;
            case PROVISION_AGREEMENT:
                return ObjectTypeCodelistType.PROVISION_AGREEMENT;
            case REPORT_PERIOD_TARGET:
                return ObjectTypeCodelistType.REPORT_PERIOD_TARGET;
            case REPORT_STRUCTURE:
                return ObjectTypeCodelistType.REPORT_STRUCTURE;
            case REPORTING_CATEGORY:
                return ObjectTypeCodelistType.REPORTING_CATEGORY;
            case CATEGORY_MAP:
                return ObjectTypeCodelistType.REPORTING_CATEGORY_MAP;
            case REPORTING_TAXONOMY:
                return ObjectTypeCodelistType.REPORTING_TAXONOMY;
            case REPORTING_TAXONOMY_MAP:
                return ObjectTypeCodelistType.REPORTING_TAXONOMY_MAP;
            case STRUCTURE_MAP:
                return ObjectTypeCodelistType.STRUCTURE_MAP;
            case STRUCTURE_SET:
                return ObjectTypeCodelistType.STRUCTURE_SET;
            case TIME_DIMENSION:
                return ObjectTypeCodelistType.TIME_DIMENSION;
            case TRANSITION:
                return ObjectTypeCodelistType.TRANSITION;
        }
        throw new SdmxNotImplementedException("Object Type : " + buildFrom);
    }

    /**
     * Gets sdmx structure type.
     *
     * @param enumType the enum type
     * @return the sdmx structure type
     */
    public static SDMX_STRUCTURE_TYPE getSdmxStructureType(ObjectTypeCodelistType.Enum enumType) {
        switch (enumType.intValue()) {
            case ObjectTypeCodelistType.INT_AGENCY:
                return SDMX_STRUCTURE_TYPE.AGENCY;
            case ObjectTypeCodelistType.INT_AGENCY_SCHEME:
                return SDMX_STRUCTURE_TYPE.AGENCY_SCHEME;
            case ObjectTypeCodelistType.INT_ANY:
                return SDMX_STRUCTURE_TYPE.ANY;
            case ObjectTypeCodelistType.INT_ATTACHMENT_CONSTRAINT:
                return SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT;
            case ObjectTypeCodelistType.INT_ATTRIBUTE:
                return SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE;
            case ObjectTypeCodelistType.INT_ATTRIBUTE_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR;
            case ObjectTypeCodelistType.INT_CATEGORISATION:
                return SDMX_STRUCTURE_TYPE.CATEGORISATION;
            case ObjectTypeCodelistType.INT_CATEGORY:
                return SDMX_STRUCTURE_TYPE.CATEGORY;
            case ObjectTypeCodelistType.INT_CATEGORY_SCHEME:
                return SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME;
            case ObjectTypeCodelistType.INT_CATEGORY_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME_MAP;
            case ObjectTypeCodelistType.INT_CODE:
                return SDMX_STRUCTURE_TYPE.CODE;
            case ObjectTypeCodelistType.INT_CODE_MAP:
                return SDMX_STRUCTURE_TYPE.CODE_MAP;
            case ObjectTypeCodelistType.INT_CODELIST:
                return SDMX_STRUCTURE_TYPE.CODE_LIST;
            case ObjectTypeCodelistType.INT_CODELIST_MAP:
                return SDMX_STRUCTURE_TYPE.CODE_LIST_MAP;
            case ObjectTypeCodelistType.INT_COMPONENT_MAP:
                return SDMX_STRUCTURE_TYPE.COMPONENT_MAP;
            case ObjectTypeCodelistType.INT_CONCEPT:
                return SDMX_STRUCTURE_TYPE.CONCEPT;
            case ObjectTypeCodelistType.INT_CONCEPT_MAP:
                return SDMX_STRUCTURE_TYPE.CONCEPT_MAP;
            case ObjectTypeCodelistType.INT_CONCEPT_SCHEME:
                return SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME;
            case ObjectTypeCodelistType.INT_CONCEPT_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME_MAP;
            case ObjectTypeCodelistType.INT_CONSTRAINT_TARGET:
                return SDMX_STRUCTURE_TYPE.CONSTRAINT_CONTENT_TARGET;
            case ObjectTypeCodelistType.INT_CONTENT_CONSTRAINT:
                return SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT;
            case ObjectTypeCodelistType.INT_DATA_CONSUMER:
                return SDMX_STRUCTURE_TYPE.DATA_CONSUMER;
            case ObjectTypeCodelistType.INT_DATA_CONSUMER_SCHEME:
                return SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME;
            case ObjectTypeCodelistType.INT_DATA_PROVIDER:
                return SDMX_STRUCTURE_TYPE.DATA_PROVIDER;
            case ObjectTypeCodelistType.INT_DATA_PROVIDER_SCHEME:
                return SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME;
            case ObjectTypeCodelistType.INT_DATA_SET_TARGET:
                return SDMX_STRUCTURE_TYPE.DATASET_TARGET;
            case ObjectTypeCodelistType.INT_DATA_STRUCTURE:
                return SDMX_STRUCTURE_TYPE.DSD;
            case ObjectTypeCodelistType.INT_DATAFLOW:
                return SDMX_STRUCTURE_TYPE.DATAFLOW;
            case ObjectTypeCodelistType.INT_DIMENSION:
                return SDMX_STRUCTURE_TYPE.DIMENSION;
            case ObjectTypeCodelistType.INT_DIMENSION_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR;
            case ObjectTypeCodelistType.INT_DIMENSION_DESCRIPTOR_VALUES_TARGET:
                return SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR_VALUES_TARGET;
            case ObjectTypeCodelistType.INT_GROUP_DIMENSION_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.GROUP;
            case ObjectTypeCodelistType.INT_HIERARCHICAL_CODE:
                return SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODE;
            case ObjectTypeCodelistType.INT_HIERARCHICAL_CODELIST:
                return SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST;
            case ObjectTypeCodelistType.INT_HIERARCHY:
                return SDMX_STRUCTURE_TYPE.HIERARCHY;
            case ObjectTypeCodelistType.INT_HYBRID_CODE_MAP:
                return SDMX_STRUCTURE_TYPE.HYBRID_CODE;
            case ObjectTypeCodelistType.INT_HYBRID_CODELIST_MAP:
                return SDMX_STRUCTURE_TYPE.HYBRID_CODELIST_MAP;
            case ObjectTypeCodelistType.INT_IDENTIFIABLE_OBJECT_TARGET:
                return SDMX_STRUCTURE_TYPE.IDENTIFIABLE_OBJECT_TARGET;
            case ObjectTypeCodelistType.INT_LEVEL:
                return SDMX_STRUCTURE_TYPE.LEVEL;
            case ObjectTypeCodelistType.INT_MEASURE_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR;
            case ObjectTypeCodelistType.INT_MEASURE_DIMENSION:
                return SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION;
            case ObjectTypeCodelistType.INT_METADATA_ATTRIBUTE:
                return SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE;
            case ObjectTypeCodelistType.INT_METADATA_SET:
                return SDMX_STRUCTURE_TYPE.METADATA_SET;
            case ObjectTypeCodelistType.INT_METADATA_STRUCTURE:
                return SDMX_STRUCTURE_TYPE.MSD;
            case ObjectTypeCodelistType.INT_METADATA_TARGET:
                return SDMX_STRUCTURE_TYPE.METADATA_TARGET;
            case ObjectTypeCodelistType.INT_METADATAFLOW:
                return SDMX_STRUCTURE_TYPE.METADATA_FLOW;
//			case ObjectTypeCodelistType.INT_ORGANISATION : return SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP; 
            case ObjectTypeCodelistType.INT_ORGANISATION_MAP:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_MAP;
//			case ObjectTypeCodelistType.INT_ORGANISATION_SCHEME : return SDMX_STRUCTURE_TYPE.OR; 
            case ObjectTypeCodelistType.INT_ORGANISATION_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP;
            case ObjectTypeCodelistType.INT_ORGANISATION_UNIT:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT;
            case ObjectTypeCodelistType.INT_ORGANISATION_UNIT_SCHEME:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME;
            case ObjectTypeCodelistType.INT_PRIMARY_MEASURE:
                return SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE;
            case ObjectTypeCodelistType.INT_PROCESS:
                return SDMX_STRUCTURE_TYPE.PROCESS;
            case ObjectTypeCodelistType.INT_PROCESS_STEP:
                return SDMX_STRUCTURE_TYPE.PROCESS_STEP;
            case ObjectTypeCodelistType.INT_PROVISION_AGREEMENT:
                return SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT;
            case ObjectTypeCodelistType.INT_REPORT_PERIOD_TARGET:
                return SDMX_STRUCTURE_TYPE.REPORT_PERIOD_TARGET;
            case ObjectTypeCodelistType.INT_REPORT_STRUCTURE:
                return SDMX_STRUCTURE_TYPE.REPORT_STRUCTURE;
            case ObjectTypeCodelistType.INT_REPORTING_CATEGORY:
                return SDMX_STRUCTURE_TYPE.REPORTING_CATEGORY;
            case ObjectTypeCodelistType.INT_REPORTING_CATEGORY_MAP:
                return SDMX_STRUCTURE_TYPE.CATEGORY_MAP;
            case ObjectTypeCodelistType.INT_REPORTING_TAXONOMY:
                return SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY;
            case ObjectTypeCodelistType.INT_REPORTING_TAXONOMY_MAP:
                return SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY_MAP;
//			case ObjectTypeCodelistType.INT_REPORTING_YEAR_START_DAY : return SDMX_STRUCTURE_TYPE.R; 
            case ObjectTypeCodelistType.INT_STRUCTURE_MAP:
                return SDMX_STRUCTURE_TYPE.STRUCTURE_MAP;
            case ObjectTypeCodelistType.INT_STRUCTURE_SET:
                return SDMX_STRUCTURE_TYPE.STRUCTURE_SET;
            case ObjectTypeCodelistType.INT_TIME_DIMENSION:
                return SDMX_STRUCTURE_TYPE.TIME_DIMENSION;
            case ObjectTypeCodelistType.INT_TRANSITION:
                return SDMX_STRUCTURE_TYPE.TRANSITION;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, enumType);
        }
    }

    /**
     * Gets sdmx structure type.
     *
     * @param enumType the enum type
     * @return the sdmx structure type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONVERT FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static SDMX_STRUCTURE_TYPE getSdmxStructureType(RepresentationTypeType.Enum enumType) {
        switch (enumType.intValue()) {
            case RepresentationTypeType.INT_CATEGORY_SCHEME:
                return SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME;
            case RepresentationTypeType.INT_CODELIST:
                return SDMX_STRUCTURE_TYPE.CODE_LIST;
            case RepresentationTypeType.INT_ORGANISATION_SCHEME:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, enumType);
        }
    }

    /**
     * Gets sdmx representation type.
     *
     * @param structureType the structure type
     * @return the sdmx representation type
     */
    public static RepresentationTypeType.Enum getSdmxRepresentationType(SDMX_STRUCTURE_TYPE structureType) {
        switch (structureType) {
            case CATEGORY_SCHEME:
                return RepresentationTypeType.CATEGORY_SCHEME;
            case CODE_LIST:
                return RepresentationTypeType.CODELIST;
            case ORGANISATION_UNIT_SCHEME:
                return RepresentationTypeType.ORGANISATION_SCHEME;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType);
        }
    }

    /**
     * Gets sdmx structure type.
     *
     * @param enumType the enum type
     * @return the sdmx structure type
     */
    public static SDMX_STRUCTURE_TYPE getSdmxStructureType(ObjectIDType.Enum enumType) {
        switch (enumType.intValue()) {
            case ObjectIDType.INT_AGENCY:
                return SDMX_STRUCTURE_TYPE.AGENCY;
            case ObjectIDType.INT_ATTACHMENT_CONSTRAINT:
                return SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT;
            case ObjectIDType.INT_ATTRIBUTE:
                return SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE;
            case ObjectIDType.INT_ATTRIBUTE_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR;
            case ObjectIDType.INT_CATEGORY:
                return SDMX_STRUCTURE_TYPE.CATEGORY;
            case ObjectIDType.INT_CATEGORY_MAP:
                return SDMX_STRUCTURE_TYPE.CATEGORY_MAP;
            case ObjectIDType.INT_CATEGORY_SCHEME:
                return SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME;
            case ObjectIDType.INT_CATEGORY_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME_MAP;
            case ObjectIDType.INT_CODE:
                return SDMX_STRUCTURE_TYPE.CODE;
            case ObjectIDType.INT_CODE_MAP:
                return SDMX_STRUCTURE_TYPE.CODE_MAP;
            case ObjectIDType.INT_CODELIST:
                return SDMX_STRUCTURE_TYPE.CODE_LIST;
            case ObjectIDType.INT_CODELIST_MAP:
                return SDMX_STRUCTURE_TYPE.CODE_LIST_MAP;
            case ObjectIDType.INT_COMPONENT:
                return SDMX_STRUCTURE_TYPE.COMPONENT;
            case ObjectIDType.INT_COMPONENT_MAP:
                return SDMX_STRUCTURE_TYPE.COMPONENT_MAP;
            case ObjectIDType.INT_CONCEPT:
                return SDMX_STRUCTURE_TYPE.CONCEPT;
            case ObjectIDType.INT_CONCEPT_MAP:
                return SDMX_STRUCTURE_TYPE.CONCEPT_MAP;
            case ObjectIDType.INT_CONCEPT_SCHEME:
                return SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME;
            case ObjectIDType.INT_CONCEPT_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME_MAP;
            case ObjectIDType.INT_CONTENT_CONSTRAINT:
                return SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT;
            case ObjectIDType.INT_DATA_FLOW:
                return SDMX_STRUCTURE_TYPE.DATAFLOW;
            case ObjectIDType.INT_DATA_PROVIDER:
                return SDMX_STRUCTURE_TYPE.DATA_PROVIDER;
            case ObjectIDType.INT_DATA_SET:
                return SDMX_STRUCTURE_TYPE.DATASET;
            case ObjectIDType.INT_DIMENSION:
                return SDMX_STRUCTURE_TYPE.DIMENSION;
            case ObjectIDType.INT_GROUP_KEY_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.GROUP;
            case ObjectIDType.INT_HIERARCHICAL_CODELIST:
                return SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST;
            case ObjectIDType.INT_HIERARCHY:
                return SDMX_STRUCTURE_TYPE.HIERARCHY;
            case ObjectIDType.INT_KEY_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR;
            case ObjectIDType.INT_KEY_FAMILY:
                return SDMX_STRUCTURE_TYPE.DSD;
            case ObjectIDType.INT_MEASURE:
                return SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION;
            case ObjectIDType.INT_MEASURE_DESCRIPTOR:
                return SDMX_STRUCTURE_TYPE.MEASURE_DESCRIPTOR;
            case ObjectIDType.INT_METADATA_ATTRIBUTE:
                return SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE;
            case ObjectIDType.INT_METADATA_FLOW:
                return SDMX_STRUCTURE_TYPE.METADATA_FLOW;
            case ObjectIDType.INT_METADATA_SET:
                return SDMX_STRUCTURE_TYPE.METADATA_SET;
            case ObjectIDType.INT_METADATA_STRUCTURE:
                return SDMX_STRUCTURE_TYPE.MSD;
            case ObjectIDType.INT_ORGANISATION_SCHEME:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME;
            case ObjectIDType.INT_ORGANISATION_SCHEME_MAP:
                return SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP;
            case ObjectIDType.INT_PROCESS:
                return SDMX_STRUCTURE_TYPE.PROCESS;
            case ObjectIDType.INT_PROCESS_STEP:
                return SDMX_STRUCTURE_TYPE.PROCESS_STEP;
            case ObjectIDType.INT_PROVISION_AGREEMENT:
                return SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT;
            case ObjectIDType.INT_REPORTING_TAXONOMY:
                return SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY;
            case ObjectIDType.INT_STRUCTURE_MAP:
                return SDMX_STRUCTURE_TYPE.STRUCTURE_MAP;
            case ObjectIDType.INT_STRUCTURE_SET:
                return SDMX_STRUCTURE_TYPE.STRUCTURE_SET;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, enumType);
        }
    }

    /**
     * Gets sdmx object id type.
     *
     * @param structureType the structure type
     * @return the sdmx object id type
     */
    public static ObjectIDType.Enum getSdmxObjectIdType(SDMX_STRUCTURE_TYPE structureType) {
        switch (structureType) {
            case AGENCY:
                return ObjectIDType.AGENCY;
            case ATTACHMENT_CONSTRAINT:
                return ObjectIDType.ATTACHMENT_CONSTRAINT;
            case DATA_ATTRIBUTE:
                return ObjectIDType.ATTRIBUTE;
            case ATTRIBUTE_DESCRIPTOR:
                return ObjectIDType.ATTRIBUTE_DESCRIPTOR;
            case CATEGORY:
                return ObjectIDType.CATEGORY;
            case CATEGORY_MAP:
                return ObjectIDType.CATEGORY_MAP;
            case CATEGORY_SCHEME:
                return ObjectIDType.CATEGORY_SCHEME;
            case CATEGORY_SCHEME_MAP:
                return ObjectIDType.CATEGORY_SCHEME_MAP;
            case CODE:
                return ObjectIDType.CODE;
            case CODE_MAP:
                return ObjectIDType.CODE_MAP;
            case CODE_LIST:
                return ObjectIDType.CODELIST;
            case CODE_LIST_MAP:
                return ObjectIDType.CODELIST_MAP;
            case COMPONENT:
                return ObjectIDType.COMPONENT;
            case COMPONENT_MAP:
                return ObjectIDType.COMPONENT_MAP;
            case CONCEPT:
                return ObjectIDType.CONCEPT;
            case CONCEPT_MAP:
                return ObjectIDType.CONCEPT_MAP;
            case CONCEPT_SCHEME:
                return ObjectIDType.CONCEPT_SCHEME;
            case CONCEPT_SCHEME_MAP:
                return ObjectIDType.CONCEPT_SCHEME_MAP;
            case CONTENT_CONSTRAINT:
                return ObjectIDType.CONTENT_CONSTRAINT;
            case DATAFLOW:
                return ObjectIDType.DATA_FLOW;
            case DATA_PROVIDER:
                return ObjectIDType.DATA_PROVIDER;
            case DATASET:
                return ObjectIDType.DATA_SET;
            case DIMENSION:
                return ObjectIDType.DIMENSION;
            case GROUP:
                return ObjectIDType.GROUP_KEY_DESCRIPTOR;
            case HIERARCHICAL_CODELIST:
                return ObjectIDType.HIERARCHICAL_CODELIST;
            case HIERARCHY:
                return ObjectIDType.HIERARCHY;
            case DIMENSION_DESCRIPTOR:
                return ObjectIDType.KEY_DESCRIPTOR;
            case DSD:
                return ObjectIDType.KEY_FAMILY;
            case MEASURE_DIMENSION:
                return ObjectIDType.MEASURE;
            case MEASURE_DESCRIPTOR:
                return ObjectIDType.MEASURE_DESCRIPTOR;
            case METADATA_ATTRIBUTE:
                return ObjectIDType.METADATA_ATTRIBUTE;
            case METADATA_FLOW:
                return ObjectIDType.METADATA_FLOW;
            case METADATA_SET:
                return ObjectIDType.METADATA_SET;
            case MSD:
                return ObjectIDType.METADATA_STRUCTURE;
            case ORGANISATION_UNIT_SCHEME:
                return ObjectIDType.ORGANISATION_SCHEME;
            case ORGANISATION_SCHEME_MAP:
                return ObjectIDType.ORGANISATION_SCHEME_MAP;
            case PROCESS:
                return ObjectIDType.PROCESS;
            case PROCESS_STEP:
                return ObjectIDType.PROCESS_STEP;
            case PROVISION_AGREEMENT:
                return ObjectIDType.PROVISION_AGREEMENT;
            case REPORTING_TAXONOMY:
                return ObjectIDType.REPORTING_TAXONOMY;
            case STRUCTURE_MAP:
                return ObjectIDType.STRUCTURE_MAP;
            case STRUCTURE_SET:
                return ObjectIDType.STRUCTURE_SET;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, structureType);
        }
    }
}
