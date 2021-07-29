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
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;

/**
 * The constraint retrieval manager is used to retrieve constraints for the purpose of building a data query.
 */
public interface ConstraintRetrievalManager {

    /**
     * Returns a ContentConstraintBean defining the data present for the ConstrainableBean.
     * Returns null if no constraint exists.
     *
     * @param constrainable the constrainable
     * @return content constraint defining data present
     */
    ContentConstraintBean getContentConstraintDefiningDataPresent(ConstrainableBean constrainable);

    /**
     * Returns a ContentConstraintBean defining the data that is allowed for the ConstrainableBean.
     * Will merge constraints attached to the child constrainable structures of the input constrainable.
     *
     * @param constrainable the constrainable
     * @return constraint defining allowed data
     */
    ContentConstraintBean getConstraintDefiningAllowedData(ConstrainableBean constrainable);
}
