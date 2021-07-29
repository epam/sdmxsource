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
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TransitionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Transition mutable bean.
 */
public class TransitionMutableBeanImpl extends IdentifiableMutableBeanImpl implements TransitionMutableBean {
    private static final long serialVersionUID = 1L;

    private String targetStep;
    private List<TextTypeWrapperMutableBean> conditions = new ArrayList<TextTypeWrapperMutableBean>();
    private String localId;

    /**
     * Instantiates a new Transition mutable bean.
     */
    public TransitionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.TRANSITION);
    }

    /**
     * Instantiates a new Transition mutable bean.
     *
     * @param transitionTypeBean the transition type bean
     */
    public TransitionMutableBeanImpl(TransitionBean transitionTypeBean) {
        super(transitionTypeBean);
        this.targetStep = transitionTypeBean.getTargetStep().getId();
        if (transitionTypeBean.getCondition() != null) {
            for (TextTypeWrapper currentTT : transitionTypeBean.getCondition()) {
                this.conditions.add(new TextTypeWrapperMutableBeanImpl(currentTT));
            }
        }
        this.localId = transitionTypeBean.getLocalId();
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
    public String getTargetStep() {
        return targetStep;
    }

    @Override
    public void setTargetStep(String targetStep) {
        this.targetStep = targetStep;
    }

    @Override
    public List<TextTypeWrapperMutableBean> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(List<TextTypeWrapperMutableBean> conditions) {
        this.conditions = conditions;
    }


}
