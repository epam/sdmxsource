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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference;

import org.sdmxsource.sdmx.api.manager.retrieval.ServiceRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferencingTree;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CrossReferenceTreeMutable;

import java.util.ArrayList;
import java.util.List;


/**
 * Used to send to external applications that require a default constructor
 */
public class CrossReferenceTreeMutableImpl implements CrossReferenceTreeMutable {
    private MaintainableMutableBean maintianableBean;
    private List<CrossReferenceTreeMutable> referencingBeans = new ArrayList<CrossReferenceTreeMutable>();


    /**
     * Instantiates a new Cross reference tree mutable.
     */
    public CrossReferenceTreeMutableImpl() {
    }

    /**
     * Instantiates a new Cross reference tree mutable.
     *
     * @param crossReferencingTree the cross referencing tree
     */
    public CrossReferenceTreeMutableImpl(CrossReferencingTree crossReferencingTree) {
        this(crossReferencingTree, null);
    }

    /**
     * Instantiates a new Cross reference tree mutable.
     *
     * @param crossReferencingTree    the cross referencing tree
     * @param serviceRetrievalManager the service retrieval manager
     */
    public CrossReferenceTreeMutableImpl(CrossReferencingTree crossReferencingTree, ServiceRetrievalManager serviceRetrievalManager) {
        if (serviceRetrievalManager != null) {
            this.maintianableBean = serviceRetrievalManager.createStub(crossReferencingTree.getMaintainable()).getMutableInstance();
        } else {
            this.maintianableBean = crossReferencingTree.getMaintainable().getMutableInstance();
        }
        for (CrossReferencingTree currentChildReference : crossReferencingTree.getReferencingStructures()) {
            this.referencingBeans.add(new CrossReferenceTreeMutableImpl(currentChildReference));
        }
    }

    @Override
    public MaintainableMutableBean getMaintianableBean() {
        return maintianableBean;
    }

    @Override
    public void setMaintianableBean(MaintainableMutableBean maintianableBean) {
        this.maintianableBean = maintianableBean;
    }

    @Override
    public List<CrossReferenceTreeMutable> getReferencingBeans() {
        return referencingBeans;
    }

    @Override
    public void setReferencingBeans(List<CrossReferenceTreeMutable> referencingBeans) {
        this.referencingBeans = referencingBeans;
    }
}
