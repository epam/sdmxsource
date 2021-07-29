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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.LevelMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.NameableMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;


/**
 * The type Level mutable bean.
 */
public class LevelMutableBeanImpl extends NameableMutableBeanImpl implements LevelMutableBean {
    private static final long serialVersionUID = 1L;

    private LevelMutableBean childLevel;
    private TextFormatMutableBean codingFormat;

    /**
     * Instantiates a new Level mutable bean.
     */
    public LevelMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.LEVEL);
    }

    /**
     * Instantiates a new Level mutable bean.
     *
     * @param level the level
     */
    public LevelMutableBeanImpl(LevelBean level) {
        super(level);
        if (level.hasChild()) {
            this.childLevel = new LevelMutableBeanImpl(level.getChildLevel());
        }
        if (level.getCodingFormat() != null) {
            this.codingFormat = new TextFormatMutableBeanImpl(level.getCodingFormat());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public LevelMutableBean getChildLevel() {
        return childLevel;
    }

    @Override
    public void setChildLevel(LevelMutableBean childLevel) {
        this.childLevel = childLevel;
    }

    @Override
    public TextFormatMutableBean getCodingFormat() {
        return codingFormat;
    }

    @Override
    public void setCodingFormat(TextFormatMutableBean codingFormat) {
        this.codingFormat = codingFormat;
    }
}
