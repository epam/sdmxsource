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
package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;

import java.util.Iterator;
import java.util.List;


/**
 * Utility methods for dealing with CodeLists.
 */
public class CodeListUtil {

    /**
     * Returns the size of the longest code contained in the CodeList.
     * Zero will be returned if there are no codes in the CodeList.
     *
     * @param clSuperBean The codelist super bean
     * @return The size of the longest code or zero.
     */
    public static int determineMaxCodeLength(CodelistSuperBean clSuperBean) {
        List<CodeSuperBean> codes = clSuperBean.getCodes();
        int longestCodeLength = 0;
        for (Iterator<CodeSuperBean> iterator = codes.iterator(); iterator.hasNext(); ) {
            CodeSuperBean codeSuperBean = iterator.next();
            longestCodeLength = Math.max(determineMaxCodeLength(codeSuperBean), longestCodeLength);
        }
        return longestCodeLength;
    }

    private static int determineMaxCodeLength(CodeSuperBean codeSuperBean) {
        int longestCodeLength = codeSuperBean.getId().length();
        if (codeSuperBean.hasChildren()) {
            for (CodeSuperBean child : codeSuperBean.getChildren()) {
                longestCodeLength = Math.max(determineMaxCodeLength(child), longestCodeLength);
            }
        }
        return longestCodeLength;
    }
}
