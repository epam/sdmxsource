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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.base.ConstrainableBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;

import java.util.Date;
import java.util.Set;


/**
 * The DataConstraintRetrievalManager manages the retrieval of data constraints for a given constrainable artefact
 *
 * @author Matt Nelson
 */
public interface DataConstraintRetrievalManager {

    /**
     * Returns true if there are constraints available for this constrainable bean
     *
     * @param constrainable the constrainable
     * @return boolean
     */
    boolean hasConstraint(ConstrainableBean constrainable);

    /**
     * The cube region takes into account whether data exists for a concept/code combination based
     * on the selected codes in the data query, the constraint codes are determined from what has been attached to the
     * constrainable artifact if it is given, if the constrainable artifact is null, then the key family from the data query
     * will be used as the constrainable artifact.
     * <p>
     * A set of valid keys is returned based on the data query passed in, which contains concept/code selections,
     * in this way the result is `what is still a valid selection based on the selections that have already been made`
     *
     * @param dataQuery the data query
     * @return the set
     */
    Set<KeyValue> filterKeysUsingCubeRegion(DataQuery dataQuery);

    /**
     * Returns all the valid key values for this constrainable bean
     *
     * @param constrainable the constrainable
     * @return all valid key values
     */
    Set<KeyValue> getAllValidKeyValues(ConstrainableBean constrainable);

    /**
     * Returns the start date for the constrainable bean
     *
     * @param constrainable the constrainable
     * @return data start date
     */
    Date getDataStartDate(ConstrainableBean constrainable);

    /**
     * Returns the end date for the constrainable bean
     *
     * @param constrainable the constrainable
     * @return data end date
     */
    Date getDataEndDate(ConstrainableBean constrainable);

}
