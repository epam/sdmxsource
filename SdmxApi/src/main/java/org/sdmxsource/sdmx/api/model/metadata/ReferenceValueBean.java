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

import java.util.List;


/**
 * ReferenceValue contains a value for a target reference object reference.
 * <p>
 * When this is taken with its sibling elements, they identify the object or objects to which the reported metadata apply.
 * <p>
 * The content of this will either be a reference to an identifiable object, a data key, a reference to a data set, or a report period.
 *
 * @author Matt Nelson
 */
public interface ReferenceValueBean extends SDMXBean {

    /**
     * Returns the id of this reference value
     *
     * @return id
     */
    String getId();

    /**
     * Returns the id of the dataset this bean is referencing, returns null if this is not a dataset reference.
     * <p>
     * <b>NOTE : </b> This is to be used in conjunction with the getIdentifiableReference() which will return the data provider reference
     *
     * @return dataset id
     */
    String getDatasetId();

    /**
     * Returns the date for which this report is relevant
     *
     * @return report period
     */
    SdmxDate getReportPeriod();

    /**
     * Returns a reference to any identifiable structure, if this is a dataset reference this will return a reference to a data provider.
     * <p>
     * Returns null if there is no reference to an identifiable structure
     *
     * @return identifiable reference
     */
    CrossReferenceBean getIdentifiableReference();

    /**
     * Returns the reference to the content constraint, if there is one
     *
     * @return content constraint reference
     */
    CrossReferenceBean getContentConstraintReference();

    /**
     * Returns a list of data keys, will return an empty list if isDatasetReference() is false
     *
     * @return data keys
     */
    List<DataKeyBean> getDataKeys();

    /**
     * Returns an enum defining what this reference value is referencing
     *
     * @return target type
     */
    TARGET_TYPE getTargetType();

    /**
     * Returns true if this is a dataset reference, if true getIdentifiableReference() AND getDatasetId() will NOT be null
     *
     * @return dataset reference
     */
    boolean isDatasetReference();

    /**
     * Returns true if this is an identifiable structure reference, if true getIdentifiableReference() will NOT be null
     *
     * @return identifiable reference
     */
    boolean isIdentifiableReference();

    /**
     * Returns true if this is a datakey reference, if true getDataKeys() will return 1 or more items
     *
     * @return datakey reference
     */
    boolean isDatakeyReference();

    /**
     * Returns true if this is a content constraint reference, if true getContentConstraintReference() will return a not null value
     *
     * @return content constriant reference
     */
    boolean isContentConstriantReference();

    /**
     * The enum Target type.
     */
    enum TARGET_TYPE {
        /**
         * Dataset target type.
         */
        DATASET("DataSet"),
        /**
         * The Report period.
         */
        REPORT_PERIOD("Report Period"),
        /**
         * Identifiable target type.
         */
        IDENTIFIABLE("Identifiable"),
        /**
         * The Constraint.
         */
        CONSTRAINT("Content Constraint"),
        /**
         * The Data key.
         */
        DATA_KEY("Data Key");

        private String type;

        private TARGET_TYPE(String type) {
            this.type = type;
        }

        /**
         * Gets type.
         *
         * @return the type
         */
        public String getType() {
            return type;
        }
    }

}
