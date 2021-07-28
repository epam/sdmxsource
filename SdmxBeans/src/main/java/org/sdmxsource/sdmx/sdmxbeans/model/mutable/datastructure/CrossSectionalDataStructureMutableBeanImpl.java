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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalMeasureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalDataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalMeasureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.CrossSectionalDataStructureBeanImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Cross sectional data structure mutable bean.
 */
public class CrossSectionalDataStructureMutableBeanImpl extends DataStructureMutableBeanImpl implements CrossSectionalDataStructureMutableBean {
    private static final long serialVersionUID = -2013720166151052536L;
    private List<CrossSectionalMeasureMutableBean> crossSectionalMeasures = new ArrayList<CrossSectionalMeasureMutableBean>();
    private List<String> crossSectionalAttachDataSet = new ArrayList<String>();
    private List<String> crossSectionalAttachGroup = new ArrayList<String>();
    private List<String> crossSectionalAttachSection = new ArrayList<String>();
    private List<String> crossSectionalAttachObservation = new ArrayList<String>();
    private Map<String, List<String>> attributeToMeasureMap = new HashMap<String, List<String>>();
    private Map<String, StructureReferenceBean> measureCodelistMapping;

    /**
     * Instantiates a new Cross sectional data structure mutable bean.
     */
    public CrossSectionalDataStructureMutableBeanImpl() {
    }

    /**
     * Instantiates a new Cross sectional data structure mutable bean.
     *
     * @param bean the bean
     */
    public CrossSectionalDataStructureMutableBeanImpl(CrossSectionalDataStructureBean bean) {
        super(bean);
        populateList(this.crossSectionalAttachDataSet, bean.getCrossSectionalAttachDataSet(false));
        populateList(this.crossSectionalAttachGroup, bean.getCrossSectionalAttachGroup(false));
        populateList(this.crossSectionalAttachObservation, bean.getCrossSectionalAttachObservation());
        populateList(this.crossSectionalAttachSection, bean.getCrossSectionalAttachSection(false));

        for (AttributeBean attribute : bean.getAttributes()) {
            List<String> measureIds = new ArrayList<String>();
            for (CrossSectionalMeasureBean xsMeasure : bean.getAttachmentMeasures(attribute)) {
                measureIds.add(xsMeasure.getId());
            }
            attributeToMeasureMap.put(attribute.getId(), measureIds);
        }
        Map<String, StructureReferenceBean> codelistMap = new HashMap<String, StructureReferenceBean>();

        for (DimensionBean dimension : bean.getDimensions(SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
            String id = dimension.getId();
            CrossReferenceBean codelistForMeasureDimension = bean.getCodelistForMeasureDimension(id);
            if (codelistForMeasureDimension != null) {
                codelistMap.put(id, codelistForMeasureDimension.createMutableInstance());
            }
        }

        if (codelistMap.size() > 0) {
            this.measureCodelistMapping = codelistMap;
        }

        for (CrossSectionalMeasureBean measure : bean.getCrossSectionalMeasures()) {
            crossSectionalMeasures.add(new CrossSectionalMeasureMutableBeanImpl(measure));
        }
    }

    @Override
    public Map<String, StructureReferenceBean> getMeasureDimensionCodelistMapping() {
        return measureCodelistMapping;
    }

    @Override
    public void setMeasureDimensionCodelistMapping(Map<String, StructureReferenceBean> mapping) {
        this.measureCodelistMapping = mapping;
    }

    private void populateList(List<String> toPopulateList, List<ComponentBean> components) {
        for (ComponentBean currentComponent : components) {
            toPopulateList.add(currentComponent.getId());
        }
    }

    @Override
    public Map<String, List<String>> getAttributeToMeasureMap() {
        return attributeToMeasureMap;
    }

    @Override
    public void setAttributeToMeasureMap(Map<String, List<String>> attributeToMeasureMap) {
        this.attributeToMeasureMap = attributeToMeasureMap;
    }

    @Override
    public List<CrossSectionalMeasureMutableBean> getCrossSectionalMeasures() {
        return crossSectionalMeasures;
    }

    @Override
    public void setCrossSectionalMeasures(List<CrossSectionalMeasureMutableBean> crossSectionalMeasure) {
        this.crossSectionalMeasures = crossSectionalMeasure;
    }

    @Override
    public List<String> getCrossSectionalAttachDataSet() {
        return crossSectionalAttachDataSet;
    }

    @Override
    public void setCrossSectionalAttachDataSet(List<String> dimensionReferences) {
        this.crossSectionalAttachDataSet = dimensionReferences;
    }

    @Override
    public List<String> getCrossSectionalAttachGroup() {
        return crossSectionalAttachGroup;
    }

    @Override
    public void setCrossSectionalAttachGroup(List<String> dimensionReferences) {
        this.crossSectionalAttachGroup = dimensionReferences;
    }

    @Override
    public List<String> getCrossSectionalAttachSection() {
        return crossSectionalAttachSection;
    }

    @Override
    public void setCrossSectionalAttachSection(List<String> dimensionReferences) {
        this.crossSectionalAttachSection = dimensionReferences;
    }

    @Override
    public List<String> getCrossSectionalAttachObservation() {
        return crossSectionalAttachObservation;
    }

    @Override
    public void setCrossSectionalAttachObservation(List<String> dimensionReferences) {
        this.crossSectionalAttachObservation = dimensionReferences;
    }

    @Override
    public void addCrossSectionalMeasures(CrossSectionalMeasureMutableBean crossSectionalMeasure) {
        if (crossSectionalMeasures == null) {
            crossSectionalMeasures = new ArrayList<CrossSectionalMeasureMutableBean>();
        }
        crossSectionalMeasures.add(crossSectionalMeasure);
    }

    @Override
    public void addCrossSectionalAttachDataSet(String dimensionReference) {
        if (crossSectionalAttachDataSet == null) {
            crossSectionalAttachDataSet = new ArrayList<String>();
        }
        crossSectionalAttachDataSet.add(dimensionReference);
    }

    @Override
    public void addCrossSectionalAttachGroup(String dimensionReference) {
        if (crossSectionalAttachGroup == null) {
            crossSectionalAttachGroup = new ArrayList<String>();
        }
        crossSectionalAttachGroup.add(dimensionReference);
    }

    @Override
    public void addCrossSectionalAttachSection(String dimensionReference) {
        if (crossSectionalAttachSection == null) {
            crossSectionalAttachSection = new ArrayList<String>();
        }
        crossSectionalAttachSection.add(dimensionReference);
    }

    @Override
    public void addCrossSectionalAttachObservation(String dimensionReference) {
        if (crossSectionalAttachObservation == null) {
            crossSectionalAttachObservation = new ArrayList<String>();
        }
        crossSectionalAttachObservation.add(dimensionReference);
    }

    @Override
    public CrossSectionalDataStructureBean getImmutableInstance() {
        return new CrossSectionalDataStructureBeanImpl(this);
    }
}
