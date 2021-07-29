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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ComponentMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.RepresentationMapRefBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ComponentMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AnnotableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Component map bean.
 */
public class ComponentMapBeanImpl extends AnnotableBeanImpl implements ComponentMapBean {
    private static final long serialVersionUID = 1L;

    private String mapConceptRef;
    private String mapTargetConceptRef;
    private RepresentationMapRefBean repMapRef;


    /**
     * Instantiates a new Component map bean.
     *
     * @param compMap the comp map
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentMapBeanImpl(ComponentMapMutableBean compMap, SdmxStructureBean parent) {
        super(compMap, parent);
        if (compMap.getMapConceptRef() != null) {
            mapConceptRef = compMap.getMapConceptRef();
        }
        if (compMap.getMapTargetConceptRef() != null) {
            mapTargetConceptRef = compMap.getMapTargetConceptRef();
        }
        if (compMap.getRepMapRef() != null) {
            repMapRef = new RepresentationMapRefBeanImpl(compMap.getRepMapRef(), this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Component map bean.
     *
     * @param compMap the comp map
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ComponentMapType compMap, StructureMapBean parent) {
        super(compMap, SDMX_STRUCTURE_TYPE.COMPONENT_MAP, parent);

        this.mapConceptRef = compMap.getSource().getRef().getId();
        this.mapTargetConceptRef = compMap.getTarget().getRef().getId();
        if (compMap.getRepresentationMapping() != null) {
            this.repMapRef = new RepresentationMapRefBeanImpl(compMap.getRepresentationMapping(), this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

//	private CrossReferenceBean getCrossReferenceFromRef(CrossReferenceBean dsdRef, RefBaseType ref) {
//		SDMX_STRUCTURE_TYPE structureType = SDMX_STRUCTURE_TYPE.parseClass(dsdRef.get().toString());
//		String agencyId = dsdRef.getMaintainableReference().getAgencyId();
//		String id = dsdRef.getMaintainableReference().getMaintainableId();
//		String version = dsdRef.getMaintainableReference().getVersion();
//		String componentId = ref.getId();
//		return  new CrossReferenceBeanImpl(this, agencyId, id, version, structureType, componentId);
//	}

    /**
     * Instantiates a new Component map bean.
     *
     * @param compMap the comp map
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected ComponentMapBeanImpl(ComponentMapType compMap, StructureMapBean parent) {
        super(null, SDMX_STRUCTURE_TYPE.CATEGORY_MAP, parent);
        throw new SdmxNotImplementedException("ComponentMapBeanImpl at version 2.0");
//		super(null, SDMX_STRUCTURE_TYPE.CATEGORY_MAP, parent);
//
//		this.mapConceptRef = getCrossReferenceFromV2Ref(parent.getSourceRef(), compMap.getMapConceptRef());
//		this.mapTargetConceptRef = getCrossReferenceFromV2Ref(parent.getTargetRef(), compMap.getMapTargetConceptRef());
//
//		if (compMap.getRepresentationMapRef() != null) {
//			this.repMapRef =  new RepresentationMapRefBeanImpl(compMap.getRepresentationMapRef(), this);
//		}
//		if(compMap.getToTextFormat() != null) {
//			this.repMapRef = new RepresentationMapRefBeanImpl(compMap.getToTextFormat(), compMap.getToValueType(), this);
//		}
//		try {
//			validate();
//		} catch(ValidationException e) {
//			throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
//		}
    }
//
//	//Dimension@MT@DSD_IN@1_0@A
//	private CrossReferenceBean getCrossReferenceFromV2Ref(CrossReferenceBean dsdRef, String ref) {
//		String[] split = ref.split("@");
//		if(split.length != 2) {
//			throw new SdmxSemmanticException("Version 2.0 ComponentMap expecting conceptRef to be a reference to the component in the format 'Class@ComponentId'   Exmaple:Dimension@FREQ");
//		}
//		SDMX_STRUCTURE_TYPE structureType = SDMX_STRUCTURE_TYPE.parseClass(split[0]);
//		String agencyId = dsdRef.getMaintainableReference().getAgencyId();
//		String id = dsdRef.getMaintainableReference().getMaintainableId();
//		String version = dsdRef.getMaintainableReference().getVersion();
//		String componentId = split[1];
//		return  new CrossReferenceBeanImpl(this, agencyId, id, version, structureType, componentId);
//	}


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            ComponentMapBean that = (ComponentMapBean) bean;
            if (!ObjectUtil.equivalent(mapConceptRef, that.getMapConceptRef())) {
                return false;
            }
            if (!ObjectUtil.equivalent(mapTargetConceptRef, that.getMapTargetConceptRef())) {
                return false;
            }
            if (!super.equivalent(repMapRef, that.getRepMapRef(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (!ObjectUtil.validObject(mapConceptRef)) {
            throw new SdmxSemmanticException("Component Map missing source component");
        }

        if (!ObjectUtil.validObject(mapTargetConceptRef)) {
            throw new SdmxSemmanticException("Component Map missing target component");
        }
        Set<SDMX_STRUCTURE_TYPE> allowedTypes = new HashSet<SDMX_STRUCTURE_TYPE>();
        allowedTypes.add(SDMX_STRUCTURE_TYPE.ATTRIBUTE_DESCRIPTOR);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.CONSTRAINT_CONTENT_TARGET);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.DATASET_TARGET);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.DIMENSION);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR_VALUES_TARGET);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.IDENTIFIABLE_OBJECT_TARGET);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.PRIMARY_MEASURE);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.TIME_DIMENSION);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.REPORT_PERIOD_TARGET);

//		verifyTypes(allowedTypes, mapConceptRef.getTargetReference());
//		verifyTypes(allowedTypes, mapTargetConceptRef.getTargetReference());
    }

//	private void verifyTypes(Set<SDMX_STRUCTURE_TYPE> allowedTypes, SDMX_STRUCTURE_TYPE actualType) {
//		if(!allowedTypes.contains(actualType)) {
//			String allowedTypesStr = "";
//			
//			for(SDMX_STRUCTURE_TYPE currentType : allowedTypes) {
//				allowedTypesStr+=currentType +",";
//			}
//			allowedTypesStr = allowedTypesStr.substring(0, allowedTypesStr.length()-2);
//			
//			throw new SdmxSemmanticException("Disallowed concept map type '"+actualType+"' allowed types are '"+allowedTypesStr+"'");
//		}
//	}

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getMapConceptRef() {
        return mapConceptRef;
    }

    @Override
    public String getMapTargetConceptRef() {
        return mapTargetConceptRef;
    }

    @Override
    public RepresentationMapRefBean getRepMapRef() {
        return repMapRef;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(repMapRef, composites);
        return composites;
    }
}
