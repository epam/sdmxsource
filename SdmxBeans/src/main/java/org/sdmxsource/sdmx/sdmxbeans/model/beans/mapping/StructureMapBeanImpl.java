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
import org.sdmx.resources.sdmxml.schemas.v20.structure.StructureMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ComponentMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.base.StructureMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ComponentMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Structure map bean.
 */
public class StructureMapBeanImpl extends SchemeMapBeanImpl implements StructureMapBean {
    private static final long serialVersionUID = 1L;
    private List<ComponentMapBean> components = new ArrayList<ComponentMapBean>();
    private boolean extension;


    /**
     * Instantiates a new Structure map bean.
     *
     * @param structMapType the struct map type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureMapBeanImpl(StructureMapMutableBean structMapType, StructureSetBean parent) {
        super(structMapType, parent);
        extension = structMapType.isExtension();
        if (structMapType.getComponents() != null) {
            for (ComponentMapMutableBean mutable : structMapType.getComponents()) {
                components.add(new ComponentMapBeanImpl(mutable, this));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Structure map bean.
     *
     * @param structMapType the struct map type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.StructureMapType structMapType, StructureSetBean parent) {
        super(structMapType, SDMX_STRUCTURE_TYPE.STRUCTURE_MAP, parent);
        this.sourceRef = RefUtil.createReference(this, structMapType.getSource());
        this.targetRef = RefUtil.createReference(this, structMapType.getTarget());
        if (ObjectUtil.validCollection(structMapType.getComponentMapList())) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ComponentMapType cmType : structMapType.getComponentMapList()) {
                this.components.add(new ComponentMapBeanImpl(cmType, this));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Structure map bean.
     *
     * @param structMapType the struct map type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureMapBeanImpl(StructureMapType structMapType, StructureSetBean parent) {
        super(structMapType, SDMX_STRUCTURE_TYPE.STRUCTURE_MAP,
                structMapType.getId(), null, structMapType.getNameList(),
                structMapType.getDescriptionList(), structMapType.getAnnotations(), parent);

        extension = structMapType.getIsExtension();

        if (structMapType.getKeyFamilyRef() != null) {
            if (ObjectUtil.validString(structMapType.getKeyFamilyRef().getURN())) {
                sourceRef = new CrossReferenceBeanImpl(this, structMapType.getKeyFamilyRef().getURN());
            } else {
                String agencyId = structMapType.getKeyFamilyRef().getKeyFamilyAgencyID();
                if (!ObjectUtil.validString(agencyId)) {
                    agencyId = this.getMaintainableParent().getAgencyId();
                }
                sourceRef = new CrossReferenceBeanImpl(this, agencyId,
                        structMapType.getKeyFamilyRef().getKeyFamilyID(),
                        structMapType.getKeyFamilyRef().getVersion(), SDMX_STRUCTURE_TYPE.DSD);
            }
        } else if (structMapType.getMetadataStructureRef() != null) {
            if (ObjectUtil.validString(structMapType.getMetadataStructureRef().getURN())) {
                sourceRef = new CrossReferenceBeanImpl(this, structMapType.getMetadataStructureRef().getURN());
            } else {
                String agencyId = structMapType.getKeyFamilyRef().getKeyFamilyAgencyID();
                if (!ObjectUtil.validString(agencyId)) {
                    agencyId = this.getMaintainableParent().getAgencyId();
                }
                sourceRef = new CrossReferenceBeanImpl(this, agencyId,
                        structMapType.getMetadataStructureRef().getMetadataStructureID(),
                        structMapType.getMetadataStructureRef().getVersion(), SDMX_STRUCTURE_TYPE.MSD);
            }
        }

        // target ref can be one of two types so get one which isn't null
        if (structMapType.getTargetKeyFamilyRef() != null) {
            if (ObjectUtil.validString(structMapType.getTargetKeyFamilyRef().getURN())) {
                targetRef = new CrossReferenceBeanImpl(this, structMapType.getTargetKeyFamilyRef().getURN());
            } else {
                String agencyId = structMapType.getTargetKeyFamilyRef().getKeyFamilyAgencyID();
                if (!ObjectUtil.validString(agencyId)) {
                    agencyId = this.getMaintainableParent().getAgencyId();
                }
                targetRef = new CrossReferenceBeanImpl(this, agencyId,
                        structMapType.getTargetKeyFamilyRef().getKeyFamilyID(),
                        structMapType.getTargetKeyFamilyRef().getVersion(), SDMX_STRUCTURE_TYPE.DSD);
            }
        } else if (structMapType.getTargetMetadataStructureRef() != null) {
            if (ObjectUtil.validString(structMapType.getTargetMetadataStructureRef().getURN())) {
                targetRef = new CrossReferenceBeanImpl(this, structMapType.getTargetMetadataStructureRef().getURN());
            } else {
                String agencyId = structMapType.getTargetMetadataStructureRef().getMetadataStructureAgencyID();
                if (!ObjectUtil.validString(agencyId)) {
                    agencyId = this.getMaintainableParent().getAgencyId();
                }
                targetRef = new CrossReferenceBeanImpl(this, agencyId,
                        structMapType.getTargetMetadataStructureRef().getMetadataStructureID(),
                        structMapType.getTargetMetadataStructureRef().getVersion(), SDMX_STRUCTURE_TYPE.MSD);
            }

        }

        // get list of component maps
        if (structMapType.getComponentMapList() != null) {
            for (ComponentMapType compMap : structMapType.getComponentMapList()) {
                ComponentMapBeanImpl cRef = new ComponentMapBeanImpl(compMap, this);
                components.add(cRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
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
            StructureMapBean that = (StructureMapBean) bean;
            if (!super.equivalent(components, that.getComponents(), includeFinalProperties)) {
                return false;
            }
            if (isExtension() != that.isExtension()) {
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
        if (sourceRef == null) {
            throw new SdmxSemmanticException("Structure Map missing source component");
        }

        if (targetRef == null) {
            throw new SdmxSemmanticException("Structure Map missing target component");
        }
        Set<SDMX_STRUCTURE_TYPE> allowedTypes = new HashSet<SDMX_STRUCTURE_TYPE>();
        allowedTypes.add(SDMX_STRUCTURE_TYPE.DATAFLOW);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.DSD);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.METADATA_FLOW);
        allowedTypes.add(SDMX_STRUCTURE_TYPE.MSD);

        verifyTypes(allowedTypes, sourceRef.getTargetReference());
        verifyTypes(allowedTypes, targetRef.getTargetReference());

    }

    private void verifyTypes(Set<SDMX_STRUCTURE_TYPE> allowedTypes, SDMX_STRUCTURE_TYPE actualType) {
        if (!allowedTypes.contains(actualType)) {
            String allowedTypesStr = "";

            for (SDMX_STRUCTURE_TYPE currentType : allowedTypes) {
                allowedTypesStr += currentType + ",";
            }
            allowedTypesStr = allowedTypesStr.substring(0, allowedTypesStr.length() - 2);

            throw new SdmxSemmanticException("Disallowed concept map type '" + actualType + "' allowed types are '" + allowedTypesStr + "'");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ComponentMapBean> getComponents() {
        return new ArrayList<ComponentMapBean>(components);
    }

    @Override
    public boolean isExtension() {
        return extension;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(components, composites);
        return composites;
    }
}
