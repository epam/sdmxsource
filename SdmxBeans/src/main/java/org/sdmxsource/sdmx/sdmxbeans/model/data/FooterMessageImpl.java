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
package org.sdmxsource.sdmx.sdmxbeans.model.data;

import org.sdmxsource.sdmx.api.engine.DataWriterEngine.FooterMessage;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Footer message.
 */
public class FooterMessageImpl implements FooterMessage {
    private String code;
    private SEVERITY severity;
    private List<TextTypeWrapper> textType;


    /**
     * Instantiates a new Footer message.
     *
     * @param code     the code
     * @param severity the severity
     * @param textType the text type
     */
    public FooterMessageImpl(String code, SEVERITY severity, TextTypeWrapper textType) {
        this.code = code;
        this.severity = severity;
        if (code == null) {
            throw new IllegalArgumentException("FooterMessage - Code is mandatory");
        }
        if (textType == null) {
            throw new IllegalArgumentException("FooterMessage - At least on e text is required");
        }
        this.textType = new ArrayList<TextTypeWrapper>();
        this.textType.add(textType);
    }

    /**
     * Instantiates a new Footer message.
     *
     * @param code     the code
     * @param severity the severity
     * @param textType the text type
     */
    public FooterMessageImpl(String code, SEVERITY severity, List<TextTypeWrapper> textType) {
        this.code = code;
        this.severity = severity;
        this.textType = textType;
        if (code == null) {
            throw new IllegalArgumentException("FooterMessage - Code is mandatory");
        }
        if (textType == null || textType.size() == 0) {
            throw new IllegalArgumentException("FooterMessage - At least on e text is required");
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public SEVERITY getSeverity() {
        return severity;
    }

    @Override
    public List<TextTypeWrapper> getFooterText() {
        return textType;
    }

}
