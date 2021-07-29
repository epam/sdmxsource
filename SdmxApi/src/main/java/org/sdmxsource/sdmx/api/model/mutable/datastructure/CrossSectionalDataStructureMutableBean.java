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
package org.sdmxsource.sdmx.api.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.List;
import java.util.Map;


/**
 * The interface Cross sectional data structure mutable bean.
 */
public interface CrossSectionalDataStructureMutableBean extends DataStructureMutableBean {

    /**
     * Gets cross sectional measures.
     *
     * @return the cross sectional measures
     */
    List<CrossSectionalMeasureMutableBean> getCrossSectionalMeasures();

    /**
     * Sets cross sectional measures.
     *
     * @param crossSectionalMeasure the cross sectional measure
     */
    void setCrossSectionalMeasures(List<CrossSectionalMeasureMutableBean> crossSectionalMeasure);

    /**
     * Gets a map of attribute id, mapping to the cross sectional measure ids it is attached to.
     *
     * @return the attribute to measure map
     */
    Map<String, List<String>> getAttributeToMeasureMap();

    /**
     * Set a map of attribute id, mapping to the cross sectional measure ids it is attached to.
     *
     * @param attributeToMeasure the attribute to measure
     */
    void setAttributeToMeasureMap(Map<String, List<String>> attributeToMeasure);

    /**
     * Gets measure dimension codelist mapping.
     *
     * @return the measure dimension codelist mapping
     */
    Map<String, StructureReferenceBean> getMeasureDimensionCodelistMapping();

    /**
     * Sets measure dimension codelist mapping.
     *
     * @param mapping the mapping
     */
    void setMeasureDimensionCodelistMapping(Map<String, StructureReferenceBean> mapping);

    /**
     * Gets cross sectional attach data set.
     *
     * @return the cross sectional attach data set
     */
    List<String> getCrossSectionalAttachDataSet();

    /**
     * Sets cross sectional attach data set.
     *
     * @param dimensionReferences the dimension references
     */
    void setCrossSectionalAttachDataSet(List<String> dimensionReferences);

    /**
     * Gets cross sectional attach group.
     *
     * @return the cross sectional attach group
     */
    List<String> getCrossSectionalAttachGroup();

    /**
     * Sets cross sectional attach group.
     *
     * @param dimensionReferences the dimension references
     */
    void setCrossSectionalAttachGroup(List<String> dimensionReferences);

    /**
     * Gets cross sectional attach section.
     *
     * @return the cross sectional attach section
     */
    List<String> getCrossSectionalAttachSection();

    /**
     * Sets cross sectional attach section.
     *
     * @param dimensionReferences the dimension references
     */
    void setCrossSectionalAttachSection(List<String> dimensionReferences);

    /**
     * Gets cross sectional attach observation.
     *
     * @return the cross sectional attach observation
     */
    List<String> getCrossSectionalAttachObservation();

    /**
     * Sets cross sectional attach observation.
     *
     * @param dimensionReferences the dimension references
     */
    void setCrossSectionalAttachObservation(List<String> dimensionReferences);

    /**
     * Add cross sectional measures.
     *
     * @param crossSectionalMeasure the cross sectional measure
     */
    void addCrossSectionalMeasures(CrossSectionalMeasureMutableBean crossSectionalMeasure);

    /**
     * Add cross sectional attach data set.
     *
     * @param dimensionReference the dimension reference
     */
    void addCrossSectionalAttachDataSet(String dimensionReference);

    /**
     * Add cross sectional attach group.
     *
     * @param dimensionReference the dimension reference
     */
    void addCrossSectionalAttachGroup(String dimensionReference);

    /**
     * Add cross sectional attach section.
     *
     * @param dimensionReference the dimension reference
     */
    void addCrossSectionalAttachSection(String dimensionReference);

    /**
     * Add cross sectional attach observation.
     *
     * @param dimensionReference the dimension reference
     */
    void addCrossSectionalAttachObservation(String dimensionReference);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the immutable instance
     */
    @Override
    CrossSectionalDataStructureBean getImmutableInstance();
}
