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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans;

import org.sdmxsource.sdmx.api.manager.retrieval.HeaderRetrievalManager;
import org.sdmxsource.sdmx.structureretrieval.manager.HeaderRetrievalManagerImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Header helper.
 */
public class HeaderHelper {
    /**
     * The constant INSTANCE.
     */
    public static HeaderHelper INSTANCE = new HeaderHelper();
    private final HeaderRetrievalManager headerManager = new HeaderRetrievalManagerImpl();

    private HeaderHelper() {
    }

    /**
     * Returns the sender Id for this message
     *
     * @return sender id
     */
    public String getSenderId() {
        String senderId = "Unknown";
        if (headerManager != null && headerManager.getHeader() != null) {
            String retVal = headerManager.getHeader().getSender().getId();
            if (retVal != null) {
                senderId = retVal;
            }
        }
        return senderId;
    }

    /**
     * Returns the receiver Id for this message
     *
     * @return receiver id
     */
    public String getReceiverId() {
        String receiverId = "Unknown";
        if (headerManager != null && headerManager.getHeader() != null && ObjectUtil.validCollection(headerManager.getHeader().getReceiver())) {
            return headerManager.getHeader().getReceiver().get(0).getId();
        }
        return receiverId;
    }
}
