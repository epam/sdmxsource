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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;


/**
 * The type Concept mutable bean.
 */
public class ConceptMutableBeanImpl extends ItemMutableBeanImpl implements ConceptMutableBean {
    private static final long serialVersionUID = 1L;

    private String parent;
    private String parentAgency;
    private RepresentationMutableBean coreRepresentation;
    private StructureReferenceBean isoConceptReference;

    /**
     * Instantiates a new Concept mutable bean.
     */
    public ConceptMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONCEPT);
    }

    /**
     * Instantiates a new Concept mutable bean.
     *
     * @param bean the bean
     */
    public ConceptMutableBeanImpl(ConceptBean bean) {
        super(bean);
        if (bean.getCoreRepresentation() != null) {
            this.coreRepresentation = new RepresentationMutableBeanImpl(bean.getCoreRepresentation());
        }
        if (bean.getIsoConceptReference() != null) {
            this.isoConceptReference = bean.getIsoConceptReference().createMutableInstance();
        }
        this.parent = bean.getParentConcept();
        this.parentAgency = bean.getParentAgency();
    }

    /**
     * Creates a new instance with an id and an english name
     *
     * @param id   the id
     * @param name the name
     * @return instance instance
     */
    public static ConceptMutableBean getInstance(String id, String name) {
        ConceptMutableBean newInstance = new ConceptMutableBeanImpl();
        newInstance.setId(id);
        newInstance.addName("en", name);
        return newInstance;
    }

    @Override
    public boolean isStandAloneConcept() {
        return false;
    }

    @Override
    public RepresentationMutableBean getCoreRepresentation() {
        return coreRepresentation;
    }

    @Override
    public void setCoreRepresentation(RepresentationMutableBean coreRepresentation) {
        this.coreRepresentation = coreRepresentation;
    }

    @Override
    public StructureReferenceBean getIsoConceptReference() {
        return isoConceptReference;
    }

    @Override
    public void setIsoConceptReference(StructureReferenceBean isoConceptReference) {
        this.isoConceptReference = isoConceptReference;
    }

    @Override
    public String getParentConcept() {
        return parent;
    }

    @Override
    public void setParentConcept(String parent) {
        this.parent = parent;
    }

    @Override
    public String getParentAgency() {
        return parentAgency;
    }

    @Override
    public void setParentAgency(String parentAgency) {
        this.parentAgency = parentAgency;
    }
}
