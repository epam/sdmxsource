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

import org.sdmx.resources.sdmxml.schemas.v20.structure.RepresentationMapRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationMapType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ToValueTypeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ValueMappingType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TO_VALUE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.RepresentationMapRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RepresentationMapRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextFormatBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Representation map ref bean.
 */
public class RepresentationMapRefBeanImpl extends SdmxStructureBeanImpl implements RepresentationMapRefBean {
    private static final long serialVersionUID = 1L;

    private CrossReferenceBean codelistMap;
    private TextFormatBean toTextFormat;
    private TO_VALUE toValueType;
    private Map<String, Set<String>> valueMappings = new HashMap<String, Set<String>>();

    /**
     * Instantiates a new Representation map ref bean.
     *
     * @param ref    the ref
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected RepresentationMapRefBeanImpl(RepresentationMapRefMutableBean ref, SdmxStructureBean parent) {
        super(ref, parent);
        if (ref.getCodelistMap() != null) {
            this.codelistMap = new CrossReferenceBeanImpl(this, ref.getCodelistMap());
        }
        if (ref.getToTextFormat() != null) {
            this.toTextFormat = new TextFormatBeanImpl(ref.getToTextFormat(), this);
        }
        if (ref.getValueMappings() != null) {
            this.valueMappings = new HashMap<String, Set<String>>(ref.getValueMappings());
        }
        toValueType = ref.getToValueType();
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
    }

    /**
     * Instantiates a new Representation map ref bean.
     *
     * @param refBean the ref bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V21 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RepresentationMapRefBeanImpl(RepresentationMapType refBean, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.REPRESENTATION_MAP, parent);

        if (refBean.getCodelistMap() != null) {
            //Local Reference
            String agencyId = this.getMaintainableParent().getAgencyId();
            String maintainableId = this.getMaintainableParent().getId();
            String version = this.getMaintainableParent().getVersion();
            this.codelistMap = new CrossReferenceBeanImpl(this, agencyId, maintainableId, version, SDMX_STRUCTURE_TYPE.CODE_LIST_MAP, refBean.getCodelistMap().getRef().getId());
        }
        if (refBean.getToTextFormat() != null) {
            this.toTextFormat = new TextFormatBeanImpl(refBean.getToTextFormat(), this);
        }
        if (refBean.getToValueType() != null) {
            switch (refBean.getToValueType().intValue()) {
                case ToValueTypeType.INT_DESCRIPTION:
                    toValueType = TO_VALUE.DESCRIPTION;
                    break;
                case ToValueTypeType.INT_NAME:
                    toValueType = TO_VALUE.NAME;
                    break;
                case ToValueTypeType.INT_VALUE:
                    toValueType = TO_VALUE.VALUE;
                    break;
            }
        }
        if (refBean.getValueMap() != null) {
            for (ValueMappingType vmt : refBean.getValueMap().getValueMappingList()) {
                Set<String> mappings = valueMappings.get(vmt.getSource());
                if (mappings == null) {
                    mappings = new HashSet<String>();
                    valueMappings.put(vmt.getSource(), mappings);
                }
                mappings.add(vmt.getTarget());
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
    }

    /**
     * Instantiates a new Representation map ref bean.
     *
     * @param textFormatType the text format type
     * @param toValueType    the to value type
     * @param parent         the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RepresentationMapRefBeanImpl(TextFormatType textFormatType, org.sdmx.resources.sdmxml.schemas.v20.structure.ToValueTypeType.Enum toValueType, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.REPRESENTATION_MAP, parent);
        this.toTextFormat = new TextFormatBeanImpl(textFormatType, this);
        if (toValueType != null) {
            switch (toValueType.intValue()) {
                case ToValueTypeType.INT_DESCRIPTION:
                    this.toValueType = TO_VALUE.DESCRIPTION;
                    break;
                case ToValueTypeType.INT_NAME:
                    this.toValueType = TO_VALUE.NAME;
                    break;
                case ToValueTypeType.INT_VALUE:
                    this.toValueType = TO_VALUE.VALUE;
                    break;
            }
        }
        validate();
    }

    /**
     * Instantiates a new Representation map ref bean.
     *
     * @param refBean the ref bean
     * @param parent  the parent
     */
    public RepresentationMapRefBeanImpl(RepresentationMapRefType refBean, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.REPRESENTATION_MAP, parent);

        this.codelistMap = new CrossReferenceBeanImpl(this, refBean.getRepresentationMapAgencyID(), refBean.getRepresentationMapID(), MaintainableBean.DEFAULT_VERSION, SDMX_STRUCTURE_TYPE.CODE_LIST);
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            RepresentationMapRefBean that = (RepresentationMapRefBean) bean;
            if (!super.equivalent(codelistMap, that.getCodelistMap())) {
                return false;
            }
            if (!ObjectUtil.equivalent(toValueType, that.getToValueType())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(valueMappings.keySet(), that.getValueMappings().keySet())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(valueMappings.values(), that.getValueMappings().values())) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (codelistMap == null && valueMappings.size() == 0 && toTextFormat == null) {
            throw new SdmxSemmanticException("RepresentationMapping requires either a codelistMap, ToTextFormat or ValueMap");
        }
        if (toTextFormat != null) {
            if (toValueType == null) {
                throw new SdmxSemmanticException("For RepresentationMapping, if using ToTextFormat ToValueType is also required");
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (codelistMap != null) {
            references.add(codelistMap);
        }
        return references;
    }

    @Override
    public CrossReferenceBean getCodelistMap() {
        return codelistMap;
    }

    @Override
    public TextFormatBean getToTextFormat() {
        return toTextFormat;
    }

    @Override
    public TO_VALUE getToValueType() {
        return toValueType;
    }

    @Override
    public Map<String, Set<String>> getValueMappings() {
        return new HashMap<String, Set<String>>(valueMappings);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(toTextFormat, composites);
        return composites;
    }
}
