/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.conceptscheme;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.conceptscheme.ConceptMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.NameableMutableSuperBeanImpl;


/**
 * The type Concept mutable super bean.
 *
 * @author Matt Nelson
 */
public class ConceptMutableSuperBeanImpl extends NameableMutableSuperBeanImpl implements ConceptMutableSuperBean {

    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new Concept mutable super bean.
     *
     * @param concept the concept
     */
    public ConceptMutableSuperBeanImpl(ConceptSuperBean concept) {
        super(concept);

    }

    /**
     * Instantiates a new Concept mutable super bean.
     */
    public ConceptMutableSuperBeanImpl() {

    }
}
