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
package org.sdmxsource.sdmx.api.model.mutable.base;

import org.sdmxsource.sdmx.api.model.mutable.codelist.LevelMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;

import java.util.List;


/**
 * The interface Hierarchy mutable bean.
 */
public interface HierarchyMutableBean extends NameableMutableBean {

    /**
     * Gets hierarchical code beans.
     *
     * @return the hierarchical code beans
     */
    List<CodeRefMutableBean> getHierarchicalCodeBeans();

    /**
     * Sets hierarchical code beans.
     *
     * @param codeRefs the code refs
     */
    void setHierarchicalCodeBeans(List<CodeRefMutableBean> codeRefs);

    /**
     * Add hierarchical code bean.
     *
     * @param codeRef the code ref
     */
    void addHierarchicalCodeBean(CodeRefMutableBean codeRef);

    /**
     * Gets child level.
     *
     * @return the child level
     */
    LevelMutableBean getChildLevel();

    /**
     * Sets child level.
     *
     * @param level the level
     */
    void setChildLevel(LevelMutableBean level);

    /**
     * If true this indicates that the hierarchy has formal levels. In this case, every code should have a level associated with it.
     * <p>
     * If false this does not have formal levels.  This hierarchy may still have levels, getLevel() may still return a value, the levels are not formal and the call to a code for its level may return null.
     *
     * @return the boolean
     */
    boolean isFormalLevels();


    /**
     * Sets formal levels.
     *
     * @param bool the bool
     */
    void setFormalLevels(boolean bool);

}
