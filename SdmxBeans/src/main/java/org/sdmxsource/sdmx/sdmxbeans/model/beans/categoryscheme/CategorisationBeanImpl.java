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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataflowRefType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.CategorisationType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorisationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorisationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Categorisation bean.
 */
public class CategorisationBeanImpl extends MaintainableBeanImpl implements CategorisationBean {
    private static final long serialVersionUID = 1L;
    private CrossReferenceBean categoryReference;
    private CrossReferenceBean structureReference;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private CategorisationBeanImpl(CategorisationBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Categorisation bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN              //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorisationBeanImpl(CategorisationMutableBean bean) {
        super(bean);
        try {
            if (bean.getCategoryReference() != null) {
                this.categoryReference = new CrossReferenceBeanImpl(this, bean.getCategoryReference());
            }
            if (bean.getStructureReference() != null) {
                this.structureReference = new CrossReferenceBeanImpl(this, bean.getStructureReference());
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Categorisation bean.
     *
     * @param cs the cs
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CategorisationBeanImpl(CategorisationType cs) {
        super(cs, SDMX_STRUCTURE_TYPE.CATEGORISATION);
        if (cs.getSource() != null) {
            structureReference = RefUtil.createReference(this, cs.getSource());
        }
        if (cs.getTarget() != null) {
            categoryReference = RefUtil.createReference(this, cs.getTarget());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Constructs a categorisation from a category that contains a metadataflow ref
     *
     * @param category the category
     * @param mdf      the mdf
     */
    public CategorisationBeanImpl(CategoryBean category, MetadataflowRefType mdf) {
        super(null,
                SDMX_STRUCTURE_TYPE.CATEGORISATION,
                null,
                null,
                null,
                null,
                category.getMaintainableParent().getAgencyId(),
                null,
                null,
                null,
                null,
                null,
                null);
        try {
            this.name = category.getNames();
            if (mdf.getURN() != null) {
                this.structureReference = new CrossReferenceBeanImpl(this, mdf.getURN());
            } else {
                this.structureReference = new CrossReferenceBeanImpl(this, mdf.getAgencyID(), mdf.getMetadataflowID(), mdf.getVersion(), SDMX_STRUCTURE_TYPE.METADATA_FLOW);
            }
            this.categoryReference = new CrossReferenceBeanImpl(this, category.getUrn());
            generateId();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Constructs a categorisation from a category that contains a dataflow ref
     *
     * @param category the category
     * @param df       the df
     */
    public CategorisationBeanImpl(CategoryBean category, DataflowRefType df) {
        super(null,
                SDMX_STRUCTURE_TYPE.CATEGORISATION,
                null,
                null,
                null,
                null,
                category.getMaintainableParent().getAgencyId(),
                null,
                null,
                null,
                null,
                null,
                null);
        try {
            this.name = category.getNames();
            if (df.getURN() != null) {
                this.structureReference = new CrossReferenceBeanImpl(this, df.getURN());
            } else {
                this.structureReference = new CrossReferenceBeanImpl(this, df.getAgencyID(), df.getDataflowID(), df.getVersion(), SDMX_STRUCTURE_TYPE.DATAFLOW);
            }
            this.categoryReference = new CrossReferenceBeanImpl(this, category.getUrn());
            generateId();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Constructs a categorisation from a dataflow that contains a category ref
     *
     * @param referencedFrom the referenced from
     * @param currentRef     the current ref
     */
    public CategorisationBeanImpl(MaintainableBean referencedFrom, CategoryRefType currentRef) {
        super(null,
                SDMX_STRUCTURE_TYPE.CATEGORISATION,
                null,
                null,
                null,
                null,
                referencedFrom.getAgencyId(),
                null,
                null,
                null,
                null,
                null,
                null);
        try {
            this.name = referencedFrom.getNames();
            this.structureReference = new CrossReferenceBeanImpl(this, referencedFrom.getUrn());
            this.categoryReference = RefUtil.createCategoryRef(this, currentRef);
            generateId();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    private void generateId() {
        StringBuilder sb = new StringBuilder();
        sb.append(categoryReference.hashCode() + "_" + structureReference.hashCode());
        setId(sb.toString());
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            CategorisationBean that = (CategorisationBean) bean;
            if (!super.equivalent(categoryReference, that.getCategoryReference())) {
                return false;
            }
            if (!super.equivalent(structureReference, that.getStructureReference())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (!isExternalReference.isTrue()) {
            if (this.structureReference == null) {
                throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "StructureReference");
            }
            if (this.categoryReference == null) {
                throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "CategoryReference");
            }
        }
        super.validateId(true);
        super.validateNameableAttributes();
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Do nothing yet, not yet fully built
    }


    @Override
    protected void validateNameableAttributes() throws SdmxSemmanticException {
        //Do nothing yet, not yet fully built
    }


    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> crossReferences = new HashSet<CrossReferenceBean>();
        crossReferences.add(categoryReference);
        crossReferences.add(structureReference);
        return crossReferences;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS  							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CategorisationBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new CategorisationBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public CategorisationMutableBean getMutableInstance() {
        return new CategorisationMutableBeanImpl(this);
    }

    @Override
    public CrossReferenceBean getCategoryReference() {
        return categoryReference;
    }

    @Override
    public CrossReferenceBean getStructureReference() {
        return structureReference;
    }
}
