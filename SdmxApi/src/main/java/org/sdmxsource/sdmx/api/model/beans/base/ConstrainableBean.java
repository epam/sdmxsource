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
package org.sdmxsource.sdmx.api.model.beans.base;

import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.util.List;


/**
 * A constrainable bean is one which can have constraints associated with it.
 * <p>
 * Constraints are not `by composition` but `by reference` therefore a constraint can only reference a constrainable artifact,
 * a constrainable artifact does not contain the constraint.
 *
 * @author Matt Nelson
 */
public interface ConstrainableBean extends IdentifiableBean {

    /**
     * Returns a reference to any referenced structures that can also be constrained, examples are:
     * <ul>
     *  <li>Registration would return reference to a provision agreement</li>
     *  <li>Provision Agreement would return a reference to a dataflow and a Data Provider</li>
     *  <li>Dataflow would return a reference to a data structure</li>
     *  <li>Data Structure would return null</li>
     * </ul>
     * <p>
     *  (example a provision agreement would return a reference to a dataflow)
     *
     * @return cross referenced constrainables
     */
    List<CrossReferenceBean> getCrossReferencedConstrainables();

}
