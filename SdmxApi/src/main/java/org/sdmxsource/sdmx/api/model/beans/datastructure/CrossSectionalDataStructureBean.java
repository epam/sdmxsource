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
package org.sdmxsource.sdmx.api.model.beans.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalDataStructureMutableBean;

import java.util.List;


/**
 * A Cross Sectional Data Structure extends DataStructure by adding cross sectional information as it was modelled in SDMX Version 2.0
 *
 * @author Matt Nelson
 */
public interface CrossSectionalDataStructureBean extends DataStructureBean {

    /**
     * Returns the cross sectional measure with the given id.
     * <p>
     * Returns null if no measure is found with the id.
     *
     * @param id the id
     * @return cross sectional measure
     */
    CrossSectionalMeasureBean getCrossSectionalMeasure(String id);

    /**
     * Returns true if the given dimension is to be treated as the measure dimension
     *
     * @param dim the dim
     * @return boolean
     */
    boolean isMeasureDimension(DimensionBean dim);

    /**
     * Returns a list of the cross sectional measures
     *
     * @return cross sectional measures
     */
    List<CrossSectionalMeasureBean> getCrossSectionalMeasures();

    /**
     * Returns the codelist reference for the dimension with the given id
     *
     * @param dimensionId the dimension id
     * @return codelist for measure dimension
     */
    CrossReferenceBean getCodelistForMeasureDimension(String dimensionId);

    /**
     * Returns a list of components that are cross sectional attach dataset.
     * <p>
     * Returns an empty list if no dimensions's are marked as cross sectional attach dataset.
     *
     * @param returnOnlyIfLowestLevel if true only return the components that do not have an attachment to a lower level (i.e observation)
     * @param returnTypes             optional filter on return types example SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, SDMX_STRUCTURE_TYPE.GROUP, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)
     * @return cross sectional attach data set
     */
    List<ComponentBean> getCrossSectionalAttachDataSet(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes);

    /**
     * Returns a list of components that are cross sectional attach group.
     * <p>
     * Returns an empty list if no dimensions's are marked as cross sectional attach group.
     *
     * @param returnOnlyIfLowestLevel if true only return the components that do not have an attachment to a lower level (i.e observation)
     * @param returnTypes             optional filter on return types example SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, SDMX_STRUCTURE_TYPE.GROUP, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)
     * @return cross sectional attach group
     */
    List<ComponentBean> getCrossSectionalAttachGroup(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes);

    /**
     * Returns a list of components that are cross sectional attach section.
     * <p>
     * Returns an empty list if no dimensions's are marked as cross sectional attach section.
     *
     * @param returnOnlyIfLowestLevel if true only return the components that do not have an attachment to a lower level (i.e observation)
     * @param returnTypes             optional filter on return types example SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, SDMX_STRUCTURE_TYPE.GROUP, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)
     * @return cross sectional attach section
     */
    List<ComponentBean> getCrossSectionalAttachSection(boolean returnOnlyIfLowestLevel, SDMX_STRUCTURE_TYPE... returnTypes);

    /**
     * Returns a list of components that are cross sectional attach group.
     * <p>
     * Returns an empty list if no dimensions's are marked as cross sectional attach group.
     *
     * @param returnTypes optional filter on return types example SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE, SDMX_STRUCTURE_TYPE.GROUP, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)
     * @return cross sectional attach observation
     */
    List<ComponentBean> getCrossSectionalAttachObservation(SDMX_STRUCTURE_TYPE... returnTypes);

    /**
     * Get the cross sectional measures that the attribute is linked to, returns an empty list if there is no cross sectional measures
     * defined by the attribute.
     *
     * @param attribute the attribute
     * @return attachment measures
     */
    List<CrossSectionalMeasureBean> getAttachmentMeasures(AttributeBean attribute);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    CrossSectionalDataStructureMutableBean getMutableInstance();

}
