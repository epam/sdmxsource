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
package org.sdmxsource.sdmx.ediparser.model.impl;

import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;

/**
 * The type Message identification.
 */
public class MessageIdentification extends AbstractEdiModel {

    private String messageRefNum;
    private String messageType = "GESMES";
    private String messageTypeVersion = "2";
    private String messageTypeRelease = "1";
    private String controllingAgency = "E6";

    /**
     * Instantiates a new Message identification.
     *
     * @param messageRef the message ref
     */
    public MessageIdentification(int messageRef) {
        this.messageRefNum = "MREF" + prependZeros(messageRef, 6);
    }

    /**
     * Gets message ref num.
     *
     * @return the message ref num
     */
    public String getMessageRefNum() {
        return messageRefNum;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.MESSAGE_IDENTIFICATION);
        sb.append(messageRefNum);
        sb.append(PLUS);
        sb.append(messageType);
        sb.append(COLON);
        sb.append(messageTypeVersion);
        sb.append(COLON);
        sb.append(messageTypeRelease);
        sb.append(COLON);
        sb.append(controllingAgency);
        sb.append(END_TAG);
        return sb.toString();
    }
}
