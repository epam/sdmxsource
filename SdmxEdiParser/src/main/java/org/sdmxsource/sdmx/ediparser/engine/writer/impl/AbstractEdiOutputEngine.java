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
package org.sdmxsource.sdmx.ediparser.engine.writer.impl;

import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.constants.MESSSAGE_FUNCTION;
import org.sdmxsource.sdmx.ediparser.model.impl.InterchangeHeader;
import org.sdmxsource.sdmx.ediparser.model.impl.MessageIdentification;

import java.io.*;


/**
 * The type Abstract edi output engine.
 */
public abstract class AbstractEdiOutputEngine {

    /**
     * The type Inner engine.
     */
    protected class InnerEngine {
        private OutputStreamWriter writer;
        private int numLines;
        private int numMessages;
        private InterchangeHeader header;
        private MessageIdentification messageId;

        /**
         * Instantiates a new Inner engine.
         *
         * @param out the out
         */
        public InnerEngine(OutputStream out) {
            try {
                writer = new OutputStreamWriter(new BufferedOutputStream(out), EDI_CONSTANTS.CHARSET_ENCODING);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unable to open writer!", e);
            }
        }

        /**
         * Write interchange administration.
         *
         * @param header the header
         * @throws IOException the io exception
         */
        protected void writeInterchangeAdministration(InterchangeHeader header) throws IOException {
            this.header = header;

            write(EDI_PREFIX.MESSAGE_START + EDI_CONSTANTS.END_TAG);
            write(header.toString());
        }

        /**
         * Write message identification.
         *
         * @param messageId the message id
         * @throws IOException the io exception
         */
        protected void writeMessageIdentification(MessageIdentification messageId) throws IOException {
            this.messageId = messageId;
            numLines = 1;
            numMessages++;
            writeSegment(messageId.toString());
        }

        /**
         * Write message function.
         *
         * @param messagefunctionEnum the messagefunction enum
         * @throws IOException the io exception
         */
        protected void writeMessageFunction(MESSSAGE_FUNCTION messagefunctionEnum) throws IOException {
            writeSegment(EDI_PREFIX.MESSAGE_FUNCTION.toString() + messagefunctionEnum.getEDIString() + "'");
        }

        /**
         * Write structure maint agency.
         *
         * @param agencyId the agency id
         * @throws IOException the io exception
         */
        protected void writeStructureMaintAgency(String agencyId) throws IOException {
            writeSegment(EDI_PREFIX.MESSAGE_AGENCY.toString() + agencyId + "'");
        }

        /**
         * Write recieving agency.
         *
         * @param agencyId the agency id
         * @throws IOException the io exception
         */
        protected void writeRecievingAgency(String agencyId) throws IOException {
            writeSegment(EDI_PREFIX.RECIEVING_AGENCY.toString() + agencyId + "'");
        }

        /**
         * Write sending agency.
         *
         * @param agencyId the agency id
         * @throws IOException the io exception
         */
        protected void writeSendingAgency(String agencyId) throws IOException {
            writeSegment(EDI_PREFIX.SENDING_AGENCY.toString() + agencyId + "'");
        }

        /**
         * Write end message administration.
         */
        protected void writeEndMessageAdministration() {
            writeSegment(EDI_PREFIX.END_MESSAGE_ADMINISTRATION.toString() + numLines + "+" + messageId.getMessageRefNum() + "'");
        }

        /**
         * Write end message.
         */
        protected void writeEndMessage() {
            write(EDI_PREFIX.END_MESSAGE.toString() + numMessages + "+" + header.getInterchangeRef() + "'");
        }

        /**
         * Write segment.
         *
         * @param str the str
         */
        protected void writeSegment(String str) {
            write(str);
            numLines++;
        }

        /**
         * Close resources.
         */
        public void closeResources() {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close: ", e);
            }
        }

        private void write(String value) {
            try {
                writer.write(value);
            } catch (IOException e) {
                throw new RuntimeException("Unable to write the string: " + value, e);
            }
            try {
                writer.write(System.getProperty("line.separator"));
            } catch (IOException e) {
                throw new RuntimeException("Unable to write!", e);
            }
        }
    }
}
