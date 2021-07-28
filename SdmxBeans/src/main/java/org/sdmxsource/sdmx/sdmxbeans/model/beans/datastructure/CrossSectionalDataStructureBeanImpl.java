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

import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalMeasureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalDataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalMeasureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.CrossSectionalDataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Cross sectional data structure bean.
 */
public class CrossSectionalDataStructureBeanImpl extends DataStructureBeanImpl implements CrossSectionalDataStructureBean {
    private static final long serialVersionUID = 6854980059046880882L;
    private List<CrossSectionalMeasureBean> crossSectionalMeasures = new ArrayList<CrossSectionalMeasureBean>();
    private List<ComponentBean> crossSectionalAttachDataSet = new ArrayList<ComponentBean>();
    private List<ComponentBean> crossSectionalAttachGroup = new ArrayList<ComponentBean>();
    private List<ComponentBean> crossSectionalAttachSection = new ArrayList<ComponentBean>();
    private List<ComponentBean> crossSectionalAttachObservation = new ArrayList<ComponentBean>();
    private Map<String, List<CrossSectionalMeasureBean>> attributeToMeasuresMap = new HashMap<String, List<CrossSectionalMeasureBean>>();
    private List<String> measureDimensions = new ArrayList<String>();
    private Map<String, CrossReferenceBean> codelistMap = new HashMap<String, CrossReferenceBean>();


    /**
     * Instantiates a new Cross sectional data structure bean.
     *
     * @param bean the bean
     */
    public CrossSectionalDataStructureBeanImpl(CrossSectionalDataStructureMutableBean bean) {
        super(bean);
        if (bean.getCrossSectionalMeasures() != null) {
            for (CrossSectionalMeasureMutableBean currentMeasure : bean.getCrossSectionalMeasures()) {
                crossSectionalMeasures.add(new CrossSectionalMeasureBeanImpl(currentMeasure, this));
            }
        }
        if (bean.getMeasureDimensionCodelistMapping() != null) {
            for (String currentCodelist : bean.getMeasureDimensionCodelistMapping().keySet()) {
                codelistMap.put(currentCodelist, new CrossReferenceBeanImpl(this, bean.getMeasureDimensionCodelistMapping().get(currentCodelist)));
            }
        }
        for (DimensionMutableBean dim : bean.getDimensions()) {
            if (dim.isMeasureDimension()) {
                measureDimensions.add(dim.getConceptRef().getIdentifiableIds()[0]);
            }
        }
        if (bean.getCrossSectionalAttachDataSet() != null) {
            for (String componentId : bean.getCrossSectionalAttachDataSet()) {
                addComponentToList(componentId, crossSectionalAttachDataSet);
            }
        }
        if (bean.getCrossSectionalAttachGroup() != null) {
            for (String componentId : bean.getCrossSectionalAttachGroup()) {
                addComponentToList(componentId, crossSectionalAttachGroup);
            }
        }
        if (bean.getCrossSectionalAttachSection() != null) {
            for (String componentId : bean.getCrossSectionalAttachSection()) {
                addComponentToList(componentId, crossSectionalAttachSection);
            }
        }
        if (bean.getCrossSectionalAttachObservation() != null) {
            for (String componentId : bean.getCrossSectionalAttachObservation()) {
                addComponentToList(componentId, crossSectionalAttachObservation);
            }
        }
        if (bean.getAttributeToMeasureMap() != null) {
            for (String attributeId : bean.getAttributeToMeasureMap().keySet()) {
                setAttributeMeasures(attributeId, bean.getAttributeToMeasureMap().get(attributeId));
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Cross sectional data structure bean.
     *
     * @param bean the bean
     */
    public CrossSectionalDataStructureBeanImpl(KeyFamilyType bean) {
        super(bean);
//		if(bean.getComponents() == null || !ObjectUtil.validCollection(bean.getComponents().getCrossSectionalMeasureList())) {
//			throw new SdmxSemmanticException("Can not create CrossSectionalDataStructureBean as there are no CrossSectional Measures defined");
//		}
        for (CrossSectionalMeasureType xsMeasureType : bean.getComponents().getCrossSectionalMeasureList()) {
            crossSectionalMeasures.add(new CrossSectionalMeasureBeanImpl(xsMeasureType, this));
        }
        for (DimensionType currentComponent : bean.getComponents().getDimensionList()) {
            String componentId = currentComponent.getConceptRef();
            if (currentComponent.getIsMeasureDimension()) {
                measureDimensions.add(componentId);
                String codelistAgency = currentComponent.getCodelistAgency();
                if (!ObjectUtil.validString(codelistAgency)) {
                    codelistAgency = this.getAgencyId();
                }
                CrossReferenceBean codelistRef = new CrossReferenceBeanImpl(this, codelistAgency, currentComponent.getCodelist(), currentComponent.getCodelistVersion(), SDMX_STRUCTURE_TYPE.CODE_LIST);
                codelistMap.put(componentId, codelistRef);
            }
            if (currentComponent.getCrossSectionalAttachDataSet()) {
                addComponentToList(componentId, crossSectionalAttachDataSet);
            }
            if (currentComponent.getCrossSectionalAttachGroup()) {
                addComponentToList(componentId, crossSectionalAttachGroup);
            }
            if (currentComponent.getCrossSectionalAttachObservation()) {
                addComponentToList(componentId, crossSectionalAttachObservation);
            }
            if (currentComponent.getCrossSectionalAttachSection()) {
                addComponentToList(componentId, crossSectionalAttachSection);
            }
        }
        for (AttributeType currentComponent : bean.getComponents().getAttributeList()) {
            String componentId = currentComponent.getConceptRef();
            if (currentComponent.getCrossSectionalAttachDataSet()) {
                addComponentToList(componentId, crossSectionalAttachDataSet);
            }
            if (currentComponent.getCrossSectionalAttachGroup()) {
                addComponentToList(componentId, crossSectionalAttachGroup);
            }
            if (currentComponent.getCrossSectionalAttachObservation()) {
                addComponentToList(componentId, crossSectionalAttachObservation);
            }
            if (currentComponent.getCrossSectionalAttachSection()) {
                addComponentToList(componentId, crossSectionalAttachSection);
            }
            setAttributeMeasures(componentId, currentComponent.getAttachmentMeasureList());
        }
        if (bean.getComponents().getTimeDimension() != null) {
            TimeDimensionType currentComponent = bean.getComponents().getTimeDimension();
            if (currentComponent.getCrossSectionalAttachDataSet()) {
                addComponentToList(DimensionBean.TIME_DIMENSION_FIXED_ID, crossSectionalAttachDataSet);
            }
            if (currentComponent.getCrossSectionalAttachGroup()) {
                addComponentToList(DimensionBean.TIME_DIMENSION_FIXED_ID, crossSectionalAttachGroup);
            }
            if (currentComponent.getCrossSectionalAttachObservation()) {
                addComponentToList(DimensionBean.TIME_DIMENSION_FIXED_ID, crossSectionalAttachObservation);
            }
            if (currentComponent.getCrossSectionalAttachSection()) {
                addComponentToList(DimensionBean.TIME_DIMENSION_FIXED_ID, crossSectionalAttachSection);
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    @Override
    public CrossReferenceBean getCodelistForMeasureDimension(String dimensionId) {
        return codelistMap.get(dimensionId);
    }

    @Override
    public boolean isMeasureDimension(DimensionBean dim) {
        return measureDimensions.contains(dim.getId());
    }

    //	public List<DimensionBean> getDimensions(SDMX_STRUCTURE_TYPE... includeTypes) {
    //		List<DimensionBean> reutrnList = super.getDimensions(includeTypes);
    //
    //		for(DimensionBean dim : dimensionList.getDimensions()) {
    //			if(includeTypes != null && includeTypes.length > 0) {
    //				for(SDMX_STRUCTURE_TYPE currentType : includeTypes) {
    //					if(currentType == dim.getStructureType()) {
    //						returnList.add(dim);
    //					}
    //				}
    //			} else {
    //				returnList.add(dim);
    //			}
    //		}
    //		return returnList;
    //		return new ArrayList<DimensionBean>();
    //	}

    //	private void addDimensionToList(String componentId, List<DimensionBean> listToAddTo) {
    //		ComponentBean component = getComponent(componentId);
    //		if(component == null) {
    //			throw new SdmxSemmanticException("Can not find referenced component with id " + componentId);
    //		}
    //		if(component instanceof DimensionBean) {
    //			listToAddTo.add((DimensionBean)component);
    //		} else {
    //			throw new SdmxSemmanticException("Referneced component is not a dimension " + componentId);
    //		}
    //	}

    @Override
    public List<ComponentBean> getComponents() {
        List<ComponentBean> components = super.getComponents();
        components.addAll(crossSectionalAttachDataSet);
        components.addAll(crossSectionalAttachGroup);
        components.addAll(crossSectionalAttachObservation);
        components.addAll(crossSectionalAttachSection);
        components.addAll(crossSectionalMeasures);
        return components;
    }

    private void addComponentToList(String componentId, List<ComponentBean> listToAddTo) {
        ComponentBean component = getComponent(componentId);
        if (component == null) {
            throw new SdmxSemmanticException("Can not find referenced component with id " + componentId);
        }
        listToAddTo.add(component);
    }

    private void setAttributeMeasures(String attributeId, List<String> measureIds) {
        if (measureIds == null || measureIds.isEmpty()) {
            return;
        }
        try {
            ComponentBean component = getComponent(attributeId);
            if (component == null) {
                throw new SdmxSemmanticException("Could not resolve reference to attribute with id '" + attributeId + "' " +
                        "referenced from cross sectional data structure");
            }
            if (component instanceof AttributeBean) {
                AttributeBean att = (AttributeBean) component;
                if (att.getAttachmentLevel() != ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION) {
                    throw new SdmxSemmanticException("Attribute '" + attributeId + "' is referencing cross sectional measure, the attribute " +
                            "must have an attachment level of Observation, it is currently set to '" + att.getAttachmentLevel() + "'");
                }
            } else {
                throw new SdmxSemmanticException("Cross Sectional Measure attribute reference id '" + attributeId + "' " +
                        "is referencing structure of type '" + component.getStructureType().getType() + "'");
            }
            List<CrossSectionalMeasureBean> measureList = new ArrayList<CrossSectionalMeasureBean>();
            for (String measureId : measureIds) {
                if (measureId == null) {
                    continue;
                }
                CrossSectionalMeasureBean crossSectionalMeasure = getCrossSectionalMeasure(measureId);
                if (crossSectionalMeasure == null) {
                    throw new SdmxSemmanticException("Could not resolve reference to cross sectional measure with id '" + measureId + "' " +
                            "referenced from attribute '" + attributeId + "'");
                }
                measureList.add(crossSectionalMeasure);
            }
            attributeToMeasuresMap.put(attributeId, measureList);
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE 							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        for (DimensionBean dim : getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            if (!dim.isFrequencyDimension()) {
                if (!crossSectionalAttachDataSet.contains(dim) &&
                        !crossSectionalAttachGroup.contains(dim) &&
                        !crossSectionalAttachSection.contains(dim) &&
                        !crossSectionalAttachObservation.contains(dim)) {
                    String[] identifiableIds = dim.getConceptRef().getIdentifiableIds();
                    String conceptId = "";
                    if (identifiableIds != null && identifiableIds.length >= 1) {
                        conceptId = identifiableIds[0];
                    }
                    throw new SdmxSemmanticException("Can not create Cross Sectional Data Structure, dimension '" + conceptId + "' doesn't have cross sectional attachment level.");
                }
            }
        }
        for (AttributeBean att : getAttributes()) {
            if (!crossSectionalAttachDataSet.contains(att) &&
                    !crossSectionalAttachGroup.contains(att) &&
                    !crossSectionalAttachSection.contains(att) &&
                    !crossSectionalAttachObservation.contains(att)) {
                String[] identifiableIds = att.getConceptRef().getIdentifiableIds();
                String conceptId = "";
                if (identifiableIds != null && identifiableIds.length >= 1) {
                    conceptId = identifiableIds[0];
                }
                throw new SdmxSemmanticException("Can not create Cross Sectional Data Structure, attribute '" + conceptId + "' doesn't have cross sectional attachment level.");
            }
        }

    }


    @Override
    public List<CrossSectionalMeasureBean> getAttachmentMeasures(AttributeBean attribute) {
        if (attributeToMeasuresMap.containsKey(attribute.getId())) {
            return attributeToMeasuresMap.get(attribute.getId());
        }
        return new ArrayList<CrossSectionalMeasureBean>();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(crossSectionalAttachDataSet, composites);
        super.addToCompositeSet(crossSectionalAttachGroup, composites);
        super.addToCompositeSet(crossSectionalAttachSection, composites);
        super.addToCompositeSet(crossSectionalAttachObservation, composites);
        super.addToCompositeSet(crossSectionalMeasures, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CrossSectionalDataStructureMutableBean getMutableInstance() {
        return new CrossSectionalDataStructureMutableBeanImpl(this);
    }

    @Override
    public CrossSectionalMeasureBean getCrossSectionalMeasure(String id) {
        for (CrossSectionalMeasureBean currentMeasure : crossSectionalMeasures) {
            if (currentMeasure.getId().equals(id)) {
                return currentMeasure;
            }
        }
        return null;
    }


    @Override
    public List<CrossSectionalMeasureBean> getCrossSectionalMeasures() {
        return new ArrayList<CrossSectionalMeasureBean>(crossSectionalMeasures);
    }


    @Override
    public List<ComponentBean> getCrossSectionalAttachDataSet(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes) {
        List<ComponentBean> returnList = getComponets(crossSectionalAttachDataSet, returnTypes);
        if (returnOnlyIfLowestLevel) {
            returnList.removeAll(crossSectionalAttachGroup);
            returnList.removeAll(crossSectionalAttachSection);
            returnList.removeAll(crossSectionalAttachObservation);
        }
        return returnList;
    }

    @Override
    public List<ComponentBean> getCrossSectionalAttachGroup(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes) {
        List<ComponentBean> returnList = getComponets(crossSectionalAttachGroup, returnTypes);
        if (returnOnlyIfLowestLevel) {
            returnList.removeAll(crossSectionalAttachSection);
            returnList.removeAll(crossSectionalAttachObservation);
        }
        return returnList;
    }

    @Override
    public List<ComponentBean> getCrossSectionalAttachSection(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes) {
        List<ComponentBean> returnList = getComponets(crossSectionalAttachSection, returnTypes);
        if (returnOnlyIfLowestLevel) {
            returnList.removeAll(crossSectionalAttachObservation);
        }
        return returnList;
    }

    @Override
    public List<ComponentBean> getCrossSectionalAttachObservation(SDMX_STRUCTURE_TYPE... returnTypes) {
        return getComponets(crossSectionalAttachObservation, returnTypes);
    }

    private List<ComponentBean> getComponets(List<ComponentBean> listToGetFrom, SDMX_STRUCTURE_TYPE... returnTypes) {
        List<ComponentBean> returnList = new ArrayList<ComponentBean>();
        for (ComponentBean currentComponentBean : listToGetFrom) {
            if (isValidReturnType(currentComponentBean, returnTypes)) {
                returnList.add(currentComponentBean);
            }
        }
        return returnList;
    }

    private boolean isValidReturnType(ComponentBean component, SDMX_STRUCTURE_TYPE... returnTypes) {
        if (returnTypes == null || returnTypes.length == 0) {
            return true;
        }
        for (SDMX_STRUCTURE_TYPE currentRetrunType : returnTypes) {
            if (component.getStructureType() == currentRetrunType) {
                return true;
            }
        }
        return false;
    }
}
