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
package org.sdmxsource.sdmx.api.model.metadata;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean.TARGET_TYPE;

import java.util.List;
import java.util.Set;


/**
 * The interface Metadata report bean.
 */
public interface MetadataReportBean extends SDMXBean {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Returns the target, defining what this metadata report attaches to.  The same information can be found
     * using the getTarget...() methods on this interface.
     *
     * @return target
     */
    TargetBean getTarget();

    /**
     * Returns a copy of the list of the ReportedAttributes
     *
     * @return reported attributes
     */
    List<ReportedAttributeBean> getReportedAttributes();

    /**
     * Returns the targets that exist in the targetBean
     *
     * @return targets
     */
    Set<TARGET_TYPE> getTargets();

    /**
     * This will search the Target.ReportedAttributeBeans for any that contain a datasetid, and will return null if none do.
     * <p>
     * Returns the id of the dataset this bean is referencing, returns null if this is not a dataset reference.
     * <p>
     * <b>NOTE : </b> This is to be used in conjunction with the getIdentifiableReference() which will return the data provider reference
     *
     * @return target dataset id
     */
    String getTargetDatasetId();

    /**
     * This will search the Target.ReportedAttributeBeans for any that contain a reportPeriod, and will return null if none do.
     * <p>
     * Returns the date for which this report is relevant
     *
     * @return target report period
     */
    SdmxDate getTargetReportPeriod();

    /**
     * This will search the Target.ReportedAttributeBeans for any that contain a identifiable refernce, and will return null if none do.
     * <p>
     * Returns null if there is no reference to an identifiable structure
     *
     * @return target identifiable reference
     */
    CrossReferenceBean getTargetIdentifiableReference();

    /**
     * This will search the Target.ReportedAttributeBeans for any that contain a constraint reference, and will return null if none do.
     * <p>
     * Returns the reference to the content constraint, if there is one
     *
     * @return target content constraint reference
     */
    CrossReferenceBean getTargetContentConstraintReference();

    /**
     * This will search the Target.ReportedAttributeBeans for any that contain data keys, and will return null if none do.
     * <p>
     * Returns a list of data keys, will return an empty list if isDatasetReference() is false
     *
     * @return target data keys
     */
    List<DataKeyBean> getTargetDataKeys();
}
