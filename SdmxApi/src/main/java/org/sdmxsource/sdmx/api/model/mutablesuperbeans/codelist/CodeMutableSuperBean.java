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
package org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.IdentifiableMutableSuperBean;

import java.util.List;


/**
 * @author Matt Nelson
 *
 */
public interface CodeMutableSuperBean extends IdentifiableMutableSuperBean {

    public List<CodeMutableSuperBean> getChildren();

    public void setChildren(List<CodeMutableSuperBean> children);

    public CodeMutableSuperBean getParent();

    public void setParent(CodeMutableSuperBean parent);

    public String getValue();

    public void setValue(String value);
}
