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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.process;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.process.ComputationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ComputationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AnnotableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Computation mutable bean.
 */
public class ComputationMutableBeanImpl extends AnnotableMutableBeanImpl implements ComputationMutableBean {
    private static final long serialVersionUID = 1L;

    private String localId;
    private String softwarePackage;
    private String softwareLanguage;
    private String softwareVersion;
    private List<TextTypeWrapperMutableBean> descriptions = new ArrayList<TextTypeWrapperMutableBean>();

    /**
     * Instantiates a new Computation mutable bean.
     */
    public ComputationMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.COMPUTATION);
    }

    /**
     * Instantiates a new Computation mutable bean.
     *
     * @param bean the bean
     */
    public ComputationMutableBeanImpl(ComputationBean bean) {
        super(bean);
        this.localId = bean.getLocalId();
        this.softwareLanguage = bean.getSoftwareLanguage();
        this.softwarePackage = bean.getSoftwarePackage();
        this.softwareVersion = bean.getSoftwareVersion();
        if (bean.getDescription() != null) {
            descriptions = new ArrayList<TextTypeWrapperMutableBean>();
            for (TextTypeWrapper currentTextType : bean.getDescription()) {
                descriptions.add(new TextTypeWrapperMutableBeanImpl(currentTextType));
            }
        }
    }

    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public void setLocalId(String localId) {
        this.localId = localId;
    }

    @Override
    public String getSoftwarePackage() {
        return softwarePackage;
    }

    @Override
    public void setSoftwarePackage(String softwarePackage) {
        this.softwarePackage = softwarePackage;
    }

    @Override
    public String getSoftwareLanguage() {
        return softwareLanguage;
    }

    @Override
    public void setSoftwareLanguage(String softwareLanguage) {
        this.softwareLanguage = softwareLanguage;
    }

    @Override
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @Override
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getDescriptions() {
        return descriptions;
    }

    @Override
    public void setDescriptions(List<TextTypeWrapperMutableBean> descriptions) {
        this.descriptions = descriptions;
    }
}
