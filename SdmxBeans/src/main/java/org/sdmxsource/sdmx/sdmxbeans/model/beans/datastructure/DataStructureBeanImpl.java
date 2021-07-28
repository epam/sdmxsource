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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataStructureType;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.GroupMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.*;

/**
 * The type Data structure bean.
 */
public class DataStructureBeanImpl extends MaintainableBeanImpl implements DataStructureBean {
    private static final long serialVersionUID = 5566227534589657654L;
    private static Logger LOG = Logger.getLogger(DataStructureBeanImpl.class);
    private List<GroupBean> groups = new ArrayList<GroupBean>();
    private DimensionListBean dimensionList;
    private AttributeListBean attributeList;
    private MeasureListBean measureList;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private DataStructureBeanImpl(DataStructureBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub DataStructureBean Built");
    }

    /**
     * Instantiates a new Data structure bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataStructureBeanImpl(DataStructureMutableBean bean) {
        super(bean);
        LOG.debug("Building DataStructureBean from Mutable Bean");
        try {
            if (bean.getGroups() != null) {
                for (GroupMutableBean mutable : bean.getGroups()) {
                    this.groups.add(new GroupBeanImpl(mutable, this));
                }
            }
            if (bean.getMeasureList() != null) {
                this.measureList = new MeasureListBeanImpl(bean.getMeasureList(), this);
            }
            if (bean.getDimensionList() != null) {
                this.dimensionList = new DimensionListBeanImpl(bean.getDimensionList(), this);
            }
            if (bean.getAttributeList() != null) {
                this.attributeList = new AttributeListBeanImpl(bean.getAttributeList(), this);
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataStructureBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Data structure bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataStructureBeanImpl(DataStructureType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DSD);
        LOG.debug("Building DataStructureBean from 2.1 SDMX");
        DataStructureComponentsType components = bean.getDataStructureComponents();
        try {
            if (components != null) {
                for (org.sdmx.resources.sdmxml.schemas.v21.structure.GroupType currentGroup : components.getGroupList()) {
                    groups.add(new GroupBeanImpl(currentGroup, this));
                }
                if (components.getDimensionList() != null) {
                    this.dimensionList = new DimensionListBeanImpl(components.getDimensionList(), this);
                }
                if (components.getAttributeList() != null) {
                    this.attributeList = new AttributeListBeanImpl(components.getAttributeList(), this);
                }
                if (components.getMeasureList() != null) {
                    this.measureList = new MeasureListBeanImpl(components.getMeasureList(), this);
                }
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataStructureBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Data structure bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataStructureBeanImpl(KeyFamilyType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DSD,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building DataStructureBean from 2.0 SDMX");
        ComponentsType components = bean.getComponents();
        try {
            if (components != null) {
                for (GroupType currentGroup : components.getGroupList()) {
                    groups.add(new GroupBeanImpl(currentGroup, this));
                }
                if (components.getDimensionList() != null) {
                    this.dimensionList = new DimensionListBeanImpl(bean, this);
                }
                if (components.getPrimaryMeasure() != null) {
                    this.measureList = new MeasureListBeanImpl(components.getPrimaryMeasure(), this);
                }
                if (components.getAttributeList() != null) {
                    this.attributeList = new AttributeListBeanImpl(bean, this);
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }

        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataStructureBean Built " + this.getUrn());
        }
    }

    /**
     * Instantiates a new Data structure bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataStructureBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamilyType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.DSD,
                bean.getVersion(),
                bean.getAgency(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                null,
                bean.getAnnotations());
        LOG.debug("Building DataStructureBean from 1.0 SDMX");
        org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ComponentsType components = bean.getComponents();
        if (components != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.GroupType currentGroup : components.getGroupList()) {
                groups.add(new GroupBeanImpl(currentGroup, this));
            }
            if (components.getDimensionList() != null) {
                this.dimensionList = new DimensionListBeanImpl(bean, this);
            }
            if (components.getPrimaryMeasure() != null) {
                this.measureList = new MeasureListBeanImpl(components.getPrimaryMeasure(), this);
            }
            if (components.getAttributeList() != null) {
                this.attributeList = new AttributeListBeanImpl(bean, this);
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DataStructureBean Built " + this.getUrn());
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
            DataStructureBean that = (DataStructureBean) bean;
            if (!super.equivalent(groups, that.getGroups(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(dimensionList, that.getDimensionList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(attributeList, that.getAttributeList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(measureList, that.getMeasureList(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate() throws SdmxSemmanticException {
        if (!isExternalReference.isTrue()) {
            Map<String, DimensionBean> dimensionMap = new HashMap<String, DimensionBean>();

            Set<String> conceptIds = new HashSet<String>();
            Set<Integer> dimPos = new HashSet<Integer>();
            //VALIDATE DIMENSIONS
            if (!ObjectUtil.validCollection(getDimensions())) {
                throw new SdmxSemmanticException("DSD must have at least one dimension");
            }
            for (DimensionBean dimension : getDimensions()) {
                if (dimPos.contains(dimension.getPosition())) {
                    throw new SdmxSemmanticException("Two dimensions can not share the same dimension position : " + dimension.getPosition());
                }
                dimPos.add(dimension.getPosition());
                String conceptId = dimension.getId();
                validateUniqueComponentBean(conceptIds, dimension);
                dimensionMap.put(conceptId, dimension);
            }

            //VALIDATE ONLY ONE FREQUENCY DIMENSION
            boolean foundFreq = false;
            for (DimensionBean dimension : getDimensions()) {
                if (dimension.isFrequencyDimension()) {
                    if (foundFreq) {
                        throw new SdmxSemmanticException("DataStructure can not have more then one frequency dimension");
                    }
                    foundFreq = true;
                }
            }

            //VALIDATE GROUPS
            Set<String> groupIds = new HashSet<String>();
            if (groups != null) {
                for (GroupBean group : groups) {
                    for (String dimensionRef : group.getDimensionRefs()) {
                        if (!dimensionMap.containsKey(dimensionRef)) {
                            DimensionBean timeDimensionBean = getTimeDimension();
                            if (timeDimensionBean != null) {
                                if (timeDimensionBean.getId().equals(dimensionRef)) {
                                    throw new SdmxSemmanticException(ExceptionCode.GROUP_CANNOT_REFERENCE_TIME_DIMENSION, group.getId());
                                }
                            }
                            throw new SdmxSemmanticException(ExceptionCode.REFERENCE_ERROR, SDMX_STRUCTURE_TYPE.DIMENSION + " " + dimensionRef, SDMX_STRUCTURE_TYPE.GROUP, group.toString());
                        }
                    }
                    if (groupIds.contains(group.getId())) {
                        throw new SdmxSemmanticException(ExceptionCode.KEY_FAMILY_DUPLICATE_GROUP_ID, group.getId());
                    }
                    groupIds.add(group.getId());
                }
            }

            //VALIDATE PRIMARY MEASURE
            if (getPrimaryMeasure() != null) {
                validateUniqueComponentBean(conceptIds, getPrimaryMeasure());
            } else if (!isExternalReference.isTrue()) {
                throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "PrimaryMeasure");
            }

            //VALIDATE ATTRIBUTES
            for (AttributeBean currentBean : getAttributes()) {
                validateUniqueComponentBean(conceptIds, currentBean);
                if (currentBean.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.GROUP) {
                    if (currentBean.getAttachmentGroup() == null) {
                        throw new SdmxSemmanticException(ExceptionCode.KEY_FAMILY_GROUP_ATTRIBUTE_MISSING_GROUPID, currentBean.toString());
                    }
                    if (!groupIds.contains(currentBean.getAttachmentGroup())) {
                        throw new SdmxSemmanticException(ExceptionCode.REFERENCE_ERROR, SDMX_STRUCTURE_TYPE.GROUP, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, currentBean.toString());
                    }
                }
                for (String currentDimRef : currentBean.getDimensionReferences()) {
                    if (!dimensionMap.containsKey(currentDimRef)) {
                        throw new SdmxSemmanticException("Attribute relationship references Dimension '" + currentDimRef + "' which is not defined in the Data Structure '" + getId() + "'");
                    }
                }
            }
        }
    }

    @Override
    public boolean isCompatible(SDMX_SCHEMA schemaVersion) {
        switch (schemaVersion) {
            case VERSION_ONE:
                for (ComponentBean currentComponent : getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                    if (currentComponent.getRepresentation() == null || currentComponent.getRepresentation().getRepresentation() == null) {
                        return false;
                    }
                }
                //Intentionally no break, as version 2.0 compatibility checks also apply to version 1.0
            case VERSION_TWO:
                Set<String> componentIds = new HashSet<String>();
                for (ComponentBean currentComponent : getComponents()) {
                    if (currentComponent.getConceptRef().getTargetReference() == SDMX_STRUCTURE_TYPE.CONCEPT) {
                        String conceptId = currentComponent.getConceptRef().getFullId();
                        if (componentIds.contains(conceptId)) {
                            return false;
                        }
                        componentIds.add(conceptId);
                    }
                }
                break;
            case EDI:
                break;
        }
        return true;
    }

    private void validateUniqueComponentBean(Set<String> conceptIds, ComponentBean componentBean) {
        String conceptId = componentBean.getId();
        if (conceptIds.contains(conceptId)) {
            throw new SdmxSemmanticException("Duplicate Data Structure Component Id : " + conceptId);
        }
        conceptIds.add(conceptId);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public DataStructureBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new DataStructureBeanImpl(this, actualLocation, isServiceUrl);
    }


    @Override
    public List<CrossReferenceBean> getCrossReferencedConstrainables() {
        return new ArrayList<CrossReferenceBean>();
    }


    @Override
    public DataStructureMutableBean getMutableInstance() {
        return new DataStructureMutableBeanImpl(this);
    }

    @Override
    public DimensionListBean getDimensionList() {
        return dimensionList;
    }

    @Override
    public AttributeListBean getAttributeList() {
        return attributeList;
    }

    @Override
    public MeasureListBean getMeasureList() {
        return measureList;
    }


    @Override
    public List<ComponentBean> getComponents() {
        List<ComponentBean> returnList = new ArrayList<ComponentBean>();
        returnList.addAll(getDimensionList().getDimensions());
        returnList.addAll(getAttributes());
        returnList.add(getPrimaryMeasure());
        return returnList;
    }

    @Override
    public AttributeBean getAttribute(String id) {
        if (ObjectUtil.validString(id)) {
            for (AttributeBean currentAttribute : getAttributes()) {
                if (currentAttribute.getId().equals(id)) {
                    return currentAttribute;
                }
            }
        }
        return null;
    }


    @Override
    public List<AttributeBean> getSeriesAttributes(String crossSectionalConcept) {
        if (crossSectionalConcept == null) {
            return getDimensionGroupAttributes();
        }
        List<AttributeBean> returnList = new ArrayList<AttributeBean>();
        for (AttributeBean att : this.getDimensionGroupAttributes()) {
            if (!att.getDimensionReferences().contains(crossSectionalConcept)) {
                returnList.add(att);
            }
        }
        return returnList;
    }


    @Override
    public List<AttributeBean> getObservationAttributes(String crossSectionalConcept) {
        if (crossSectionalConcept == null) {
            return getObservationAttributes();
        }
        List<AttributeBean> returnList = new ArrayList<AttributeBean>();
        for (AttributeBean att : this.getDimensionGroupAttributes()) {
            if (att.getDimensionReferences().contains(crossSectionalConcept)) {
                returnList.add(att);
            }
        }
        returnList.addAll(getObservationAttributes());
        return returnList;
    }


    @Override
    public List<DimensionBean> getDimensions(SDMX_STRUCTURE_TYPE... includeTypes) {
        if (dimensionList != null) {
            List<DimensionBean> returnList = new ArrayList<DimensionBean>();
            for (DimensionBean dim : dimensionList.getDimensions()) {
                if (includeTypes != null && includeTypes.length > 0) {
                    for (SDMX_STRUCTURE_TYPE currentType : includeTypes) {
                        if (currentType == dim.getStructureType()) {
                            returnList.add(dim);
                        }
                    }
                } else {
                    returnList.add(dim);
                }
            }
            return returnList;
        }
        return new ArrayList<DimensionBean>();
    }

    @Override
    public DimensionBean getDimension(String conceptId) {
        return (DimensionBean) getComponent(getDimensions(), conceptId);
    }

    @Override
    public DimensionBean getFrequencyDimension() {
        for (DimensionBean currentDimension : getDimensions()) {
            if (currentDimension.isFrequencyDimension()) {
                return currentDimension;
            }
        }
        return null;
    }

    @Override
    public boolean hasFrequencyDimension() {
        for (DimensionBean currentDimension : getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            if (currentDimension.isFrequencyDimension()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ComponentBean getComponent(String conceptId) {
        ComponentBean component = null;
        component = getComponent(getDimensions(), conceptId);
        if (component != null) {
            return component;
        }
        component = getComponent(getAttributes(), conceptId);
        if (component != null) {
            return component;
        }
        if (measureList != null && measureList.getPrimaryMeasure().getId().equals(conceptId)) {
            return measureList.getPrimaryMeasure();
        }
        return null;
    }

    private ComponentBean getComponent(List<? extends ComponentBean> components, String conceptId) {
        for (ComponentBean currentcomponent : components) {
            if (currentcomponent.getId().equals(conceptId)) {
                return currentcomponent;
            }
        }
        return null;
    }

    @Override
    public List<GroupBean> getGroups() {
        return new ArrayList<GroupBean>(groups);
    }

    @Override
    public GroupBean getGroup(String groupId) {
        for (GroupBean currentGroup : groups) {
            if (currentGroup.getId().equals(groupId)) {
                return currentGroup;
            }
        }
        return null;
    }

    @Override
    public DimensionBean getTimeDimension() {
        for (DimensionBean currentDimension : getDimensions()) {
            if (currentDimension.isTimeDimension()) {
                return currentDimension;
            }
        }
        return null;
    }

    @Override
    public PrimaryMeasureBean getPrimaryMeasure() {
        if (measureList != null) {
            return measureList.getPrimaryMeasure();
        }
        return null;
    }


    @Override
    public List<AttributeBean> getAttributes() {
        if (attributeList != null) {
            return attributeList.getAttributes();
        }
        return new ArrayList<AttributeBean>();
    }

    @Override
    public List<AttributeBean> getDatasetAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET);
    }

    @Override
    public List<AttributeBean> getGroupAttributes(String groupId, boolean includeDimensionGroups) {
        GroupBean group = getGroup(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Group not found on Data Structure '" + getUrn() + "' with id: " + groupId);
        }
        List<AttributeBean> allGroupAttributes = getGroupAttributes();
        List<AttributeBean> returnList = new ArrayList<AttributeBean>();

        for (AttributeBean currentAttribute : allGroupAttributes) {
            if (currentAttribute.getAttachmentGroup() != null) {
                if (currentAttribute.getAttachmentGroup().equals(groupId)) {
                    returnList.add(currentAttribute);
                }
            }
        }

        for (AttributeBean cuAttributeBean : getDimensionGroupAttributes()) {
            List<String> attrDimRefs = cuAttributeBean.getDimensionReferences();
            List<String> grpDimRefs = group.getDimensionRefs();
            if (attrDimRefs.containsAll(grpDimRefs) && grpDimRefs.containsAll(attrDimRefs)) {
                returnList.add(cuAttributeBean);
            }
        }
        return returnList;
    }

    @Override
    public List<AttributeBean> getGroupAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.GROUP);
    }

    @Override
    public List<AttributeBean> getDimensionGroupAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP);
    }

    @Override
    public List<AttributeBean> getObservationAttributes() {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION);
    }

    @Override
    public AttributeBean getGroupAttribute(String conceptId) {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.GROUP, conceptId);
    }

    @Override
    public AttributeBean getDimensionGroupAttribute(String conceptId) {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP, conceptId);
    }

    @Override
    public AttributeBean getObservationAttribute(String conceptId) {
        return getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION, conceptId);
    }

    private List<AttributeBean> getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL type) {
        List<AttributeBean> returnList = new ArrayList<AttributeBean>();
        for (AttributeBean currentAttribute : getAttributes()) {
            if (currentAttribute.getAttachmentLevel() == type) {
                returnList.add(currentAttribute);
            }
        }
        return returnList;
    }

    private AttributeBean getAttribute(ATTRIBUTE_ATTACHMENT_LEVEL type, String conceptId) {
        for (AttributeBean currentAttribute : getAttribute(type)) {
            if (currentAttribute.getId().equals(conceptId)) {
                return currentAttribute;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				             //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(groups, composites);
        super.addToCompositeSet(dimensionList, composites);
        super.addToCompositeSet(attributeList, composites);
        super.addToCompositeSet(measureList, composites);
        return composites;
    }
}
