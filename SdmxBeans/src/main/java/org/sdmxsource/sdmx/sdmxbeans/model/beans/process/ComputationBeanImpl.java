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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.process;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ComputationType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.process.ComputationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ComputationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AnnotableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Computation bean.
 */
public class ComputationBeanImpl extends AnnotableBeanImpl implements ComputationBean {
    private static final long serialVersionUID = 2467028947066566585L;
    private String localId;
    private String softwarePackage;
    private String softwareLanguage;
    private String softwareVersion;
    private List<TextTypeWrapper> description = new ArrayList<TextTypeWrapper>();

    /**
     * Instantiates a new Computation bean.
     *
     * @param parent      the parent
     * @param mutableBean the mutable bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ComputationBeanImpl(IdentifiableBean parent, ComputationMutableBean mutableBean) {
        super(mutableBean, parent);
        this.localId = mutableBean.getLocalId();
        this.softwareLanguage = mutableBean.getSoftwareLanguage();
        this.softwarePackage = mutableBean.getSoftwarePackage();
        this.softwareVersion = mutableBean.getSoftwareVersion();
        if (mutableBean.getDescriptions() != null) {
            for (TextTypeWrapperMutableBean currentTT : mutableBean.getDescriptions()) {
                if (ObjectUtil.validString(currentTT.getValue())) {
                    description.add(new TextTypeWrapperImpl(currentTT, this));
                }
            }
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
    }

    /**
     * Instantiates a new Computation bean.
     *
     * @param parent  the parent
     * @param xmlType the xml type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ComputationBeanImpl(IdentifiableBean parent, ComputationType xmlType) {
        super(xmlType, SDMX_STRUCTURE_TYPE.COMPUTATION, parent);
        this.localId = xmlType.getLocalID();
        this.softwareLanguage = xmlType.getSoftwareLanguage();
        this.softwarePackage = xmlType.getSoftwarePackage();
        this.softwareVersion = xmlType.getSoftwareVersion();
        if (xmlType.getDescriptionList() != null) {
            this.description = TextTypeUtil.wrapTextTypeV21(xmlType.getDescriptionList(), this);
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
    }

    /**
     * Instantiates a new Computation bean.
     *
     * @param parent      the parent
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.0 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ComputationBeanImpl(IdentifiableBean parent, ProcessStepType processBean) {
        super(null, SDMX_STRUCTURE_TYPE.COMPUTATION, parent);  //HACK this is using the 2.1 constructor
        if (processBean.getComputationList() != null) {
            this.description = TextTypeUtil.wrapTextTypeV2(processBean.getComputationList(), this);
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
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
            ComputationBean that = (ComputationBean) bean;
            if (!super.equivalent(description, that.getDescription(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalent(localId, that.getLocalId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(softwarePackage, that.getSoftwarePackage())) {
                return false;
            }
            if (!ObjectUtil.equivalent(softwareLanguage, that.getSoftwareLanguage())) {
                return false;
            }
            if (!ObjectUtil.equivalent(softwareVersion, that.getSoftwareVersion())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public String getSoftwarePackage() {
        return softwarePackage;
    }

    @Override
    public String getSoftwareLanguage() {
        return softwareLanguage;
    }

    @Override
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @Override
    public List<TextTypeWrapper> getDescription() {
        return new ArrayList<TextTypeWrapper>(description);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (this.description == null) {
            throw new SdmxSemmanticException("Computation missing mandatory field 'description'");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(description, composites);
        return composites;
    }
}
