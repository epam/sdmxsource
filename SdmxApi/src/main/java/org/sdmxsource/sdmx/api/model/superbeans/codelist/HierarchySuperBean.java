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
package org.sdmxsource.sdmx.api.model.superbeans.codelist;

import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.NameableSuperBean;

import java.util.List;

/**
 * Wraps a hierarchy bean, has all referenced structures by composition
 *
 * @param <T> the type parameter
 */
public interface HierarchySuperBean<T extends MaintainableSuperBean> extends NameableSuperBean {

    /**
     * Returns the codes in this codelist
     *
     * @return codes
     */
    List<HierarchicalCodeSuperBean> getCodes();

    /**
     * Gets level.
     *
     * @return the level for this hierarchy, returns null if there is no level
     */
    LevelBean getLevel();

    /**
     * Returns the LevelBean at the position indicated, by recursing the LevelBean hierarchy of this Heirarchy bean, returns null if there is no level
     *
     * @param levelPos the level pos
     * @return the level at the given hierarchical position (0 indexed) - returns null if there is no level at that position
     */
    LevelBean getLevelAtPosition(int levelPos);

    /**
     * If true this indicates that the hierarchy has formal levels. In this case, every code should have a level associated with it.
     * <p>
     * If false this does not have formal levels.  This hierarchy may still have levels, getLevel() may still return a value, the levels are not formal and the call to a code for its level may return null.
     *
     * @return the boolean
     */
    boolean hasFormalLevels();
}
