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
package org.sdmxsource.sdmx.ediparser.engine.impl;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ContactMutableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.constants.EDI_TIME_FORMAT;
import org.sdmxsource.sdmx.ediparser.constants.MESSSAGE_FUNCTION;
import org.sdmxsource.sdmx.ediparser.engine.EdiParseEngine;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.document.impl.EDIDocumentPositionImpl;
import org.sdmxsource.sdmx.ediparser.model.document.impl.EDIMetadataImpl;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIReader;
import org.sdmxsource.sdmx.ediparser.model.reader.impl.EDIReaderImpl;
import org.sdmxsource.sdmx.ediparser.util.EDIUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ContactBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ContactMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Validates the EDI at a high level and returns metadata about the file.
 */
public class EdiParseEngineImpl implements EdiParseEngine {

    @Override
    public EDIMetadata parseEDIMessage(ReadableDataLocation ediMessageLocation) {
        try {
            EDIStructureParserEngine parserEngine = new EDIStructureParserEngine(ediMessageLocation);
            return parserEngine.processMessage();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while trying to read URI:" + ediMessageLocation);
        }
    }


    private class EDIStructureParserEngine {
        //The current line being processed
        private String currentLine;
        private EDIReader ediReader;

        //Data Related
        private String datasetId;
        private String messageName;
        private String keyFamilyIdentifier;
        private DATASET_ACTION datasetAction;
        private String missingValue;
        private Date datasetPreperation;
        private Date reportingPeriod;

        private Date reportingBegin;
        private Date reportingEnd;


        /**
         * Instantiates a new Edi structure parser engine.
         *
         * @param sourceData the source data
         */
        public EDIStructureParserEngine(ReadableDataLocation sourceData) {
            ediReader = new EDIReaderImpl(sourceData);
        }

        private EDIMetadata processMessage() throws IOException {
            try {
                return validateMessage();
            } finally {
                ediReader.close();
            }
        }

        private EDIMetadata validateMessage() {
            //First line in the message start prefix
            readNextLine();

            if (!ediReader.getLineType().isMessageStart()) {
                throw new SdmxSyntaxException("EDI Message expected first line UNA:+.? '");
            }

            //Second line is the interchange header
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.INTERCHANGE_HEADER, true);

            //Current line something like:  UNOC:3+5B0+AT2+120614:2345+IREF003063++GESMES/TS'
            String[] interchangeHeaderSplitOnPlus = EDIUtil.splitOnPlus(currentLine);

            boolean isTest;
            if (interchangeHeaderSplitOnPlus.length == 7) {
                isTest = false;
            } else if (interchangeHeaderSplitOnPlus.length == 11) {
                if (!interchangeHeaderSplitOnPlus[10].equals("1")) {
                    throw new IllegalArgumentException("The Interchange Header test indicator must have the value of 1");
                }
                isTest = true;
            } else {
                throw new IllegalArgumentException("Unexpected number of '+' characters in interchange header. Expected either 7 or 11 but the actual was: '" + interchangeHeaderSplitOnPlus.length + "'");
            }

            //Split on plus: UNOC:3, 5B0, AT2, 120614:2345, IREF003063, +GESMES/TS'

            //Split on colon: UNB+UNOC, 3+5B0+AT2+120614, 2345+IREF003063++GESMES/TS
            if (!interchangeHeaderSplitOnPlus[0].equals("UNOC:3")) {
                throw new SdmxSyntaxException("Character set UNOC:3 expected, but was '" + interchangeHeaderSplitOnPlus[0] + "'");
            }


            //			String[] interchangeRefSplit = interchangeHeaderSplitOnPlus[2].split("(?<!\\?)\\+");
            String revieverIdentification = interchangeHeaderSplitOnPlus[2];
            String preperationDateTime = interchangeHeaderSplitOnPlus[3];  //In format yyMMdd:HHmm example 120605:1802
            String interchangeMessageRef = interchangeHeaderSplitOnPlus[4];
            String appReference = interchangeHeaderSplitOnPlus[6];

            if (preperationDateTime.length() != 11) {
                throw new SdmxSyntaxException("Data preperation date expected in format yyMMdd:HHmm - provided date time was:" + preperationDateTime);
            }

            DateFormat df = DateUtil.getDateFormatter("yyMMdd:HHmm");
            Date prepDate;
            try {
                prepDate = df.parse(preperationDateTime);
            } catch (ParseException e) {
                throw new SdmxSyntaxException("Data preperation date expected in format yyMMdd:HHmm - provided date time was:" + preperationDateTime);
            }


            if (!appReference.equals("SDMX-EDI") && !appReference.equals("GESMES/TS")) {
                throw new SdmxSyntaxException("Application reference expected to be SDMX-EDI or GESMES/TS but was '" + appReference + "'");
            }


            //Third line is the message identification
            readNextLine();


            EDIMetadata metadata = new EDIMetadataImpl(revieverIdentification, prepDate, interchangeMessageRef, appReference, reportingBegin, reportingEnd, isTest);
            int messageIdentifications = validateMessageInterchangeHeader(metadata, 1);
            metadata.setMessageName(messageName);

            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.END_MESSAGE, true);

            String[] endMessageSplit = EDIUtil.splitOnPlus(currentLine, 2);
            int numLines = EDIUtil.parseStringAsInt(endMessageSplit[0]);
            if (messageIdentifications != numLines) {
                throw new SdmxSemmanticException("Number of message identification segments expected to be '" + numLines + "' but was '" + messageIdentifications + "'");
            }
            String messageReference = endMessageSplit[1];
            if (!messageReference.equals(interchangeMessageRef)) {
                throw new SdmxSemmanticException("End of message reference expected to be '" + interchangeMessageRef + "' but was '" + messageReference + "'");
            }
            return metadata;
        }

        /**
         * Recursively called for each message (structure or data) in an EDI Document. Each time this is called a EDIDocumentPosition is stored in the EDIMetadata
         *
         * @param metadata
         * @param currentnum
         * @return
         */
        private int validateMessageInterchangeHeader(EDIMetadata metadata, int currentnum) {
            int segmentStart = ediReader.getLineNumber();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.MESSAGE_IDENTIFICATION, true);
            String[] splitOnPlus = EDIUtil.splitOnPlus(currentLine, 2);
            String messageIdentification = splitOnPlus[0];

            if (!splitOnPlus[1].equals("GESMES:2:1:E6")) {
                throw new IllegalArgumentException("Expecting GESMES:2:1:E6 but was " + splitOnPlus[1]);
            }

            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.MESSAGE_FUNCTION, true);
            MESSSAGE_FUNCTION messageFunction = MESSSAGE_FUNCTION.getFromEdiStr(currentLine);

            //Fifth Line is the message agency
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.MESSAGE_AGENCY, true);
            String messageAgency = currentLine;


            //Sixth Line is the receiving agency
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.RECIEVING_AGENCY, true);
            String recievingAgency = currentLine;

            PartyBean sendingAgency = processMessageSender();

            int documentStart = ediReader.getLineNumber();
            if (messageFunction.isData()) {
                determineDatasetMetadata();
                metadata.setReportingBegin(reportingBegin);
                metadata.setReportingEnd(reportingEnd);
            }

            boolean inRecursive = false;
            List<KeyValue> datasetAttributes = new ArrayList<KeyValue>();
            while (currentLine != null) {
                try {
                    readNextLine();
                    if (!messageFunction.isData()) {
                        if (ediReader.getLineType().isDataSegment()) {
                            documentStart = ediReader.getLineNumber() + 1;
                            throw new IllegalArgumentException("Message function is " + messageFunction.getEDIString() + " but mesage contains a data segment");
                        }
                    } else if (!messageFunction.isStructure()) {
                        if (ediReader.getLineType().isStructureSegment()) {
                            throw new IllegalArgumentException("Message function is " + messageFunction.getEDIString() + " but mesage contains a structure segment");
                        }
                    }

                    //CHECK FOR ANY DATASET ATTRIBUTES AND STORE THEM
                    if (messageFunction.isData()) {
                        if (ediReader.getLineType() == EDI_PREFIX.DATASET_ATTRIBUTE_SCOPE) {
                            int scope = Integer.parseInt(ediReader.getCurrentLine());
                            //1 = dataset, 4=mix of dimensions, 5=observation
                            if (scope == 1) {
                                readNextLine();
                                EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_DATAATTRIBUTE, true);
                                while (true) {
                                    readNextLine();
                                    if (ediReader.getLineType() != EDI_PREFIX.DATASET_ATTRIBUTE_CODED
                                            && ediReader.getLineType() != EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED) {
                                        ediReader.moveBackLine();
                                        break;
                                    }
                                    String attributeConceptId = ediReader.getCurrentLine();
                                    String attributeValue = null;
                                    if (ediReader.getLineType() == EDI_PREFIX.DATASET_ATTRIBUTE_CODED) {
                                        //Move to the code value Line
                                        assertMoveNext();
                                        //If the current line is the attribute value then store it, otherwise
                                        if (EDIUtil.assertPrefix(ediReader, EDI_PREFIX.CODE_VALUE, false)) {
                                            attributeValue = ediReader.getCurrentLine();
                                        } else {
                                            ediReader.moveBackLine();
                                        }
                                    } else if (ediReader.getLineType() == EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED) {
                                        String compositeValue = "";
                                        while (true) {
                                            // Move to the next line and see if it is FREE TEXT
                                            assertMoveNext();
                                            if (EDIUtil.assertPrefix(ediReader, EDI_PREFIX.STRING, false)) {
                                                compositeValue += ediReader.parseTextString();
                                            } else {
                                                break;
                                            }
                                        }
                                        attributeValue = compositeValue;
                                        ediReader.moveBackLine();
                                    }
                                    datasetAttributes.add(new KeyValueImpl(attributeValue, attributeConceptId));
                                }
                            }
                        }
                    }


                    if (ediReader.getLineType().isEndMessageAdministration()) {
                        if (ediReader.isBackLine()) {
                            ediReader.moveNext();
                        }

                        String[] splitMessAdminOnPlus = EDIUtil.splitOnPlus(currentLine, 2);
                        String numLinesString = splitMessAdminOnPlus[0];
                        int numLines = EDIUtil.parseStringAsInt(numLinesString);
                        int segmentCount = ediReader.getLineNumber() - segmentStart + 1;
                        if (segmentCount != numLines) {
                            throw new SdmxSemmanticException("Expected segment count '" + numLines + "' does not match actual segment count '" + segmentCount + "'");
                        }
                        EDIDocumentPosition documentPosition = new EDIDocumentPositionImpl(documentStart,
                                ediReader.getLineNumber(),
                                messageFunction.isStructure(),
                                datasetId,
                                messageAgency,
                                sendingAgency,
                                recievingAgency,
                                this.datasetAction,
                                this.keyFamilyIdentifier,
                                this.missingValue,
                                this.datasetPreperation,
                                this.reportingPeriod,
                                datasetAttributes);
                        metadata.addDocumentIndex(documentPosition);

                        String messageRef = splitMessAdminOnPlus[1];
                        if (!messageIdentification.equals(messageRef)) {
                            throw new SdmxSemmanticException("Message ref expected to be '" + messageIdentification + "' but was '" + messageRef + "'");
                        }
                        //Either we have another message identification or an end message
                        readNextLine();
                        if (ediReader.getLineType().isMessageIdentification()) {
                            inRecursive = true;
                            return validateMessageInterchangeHeader(metadata, ++currentnum);
                        }
                        return currentnum;
                    }
                } catch (SdmxException th) {
                    if (inRecursive) {
                        throw th;
                    }
                    throw new SdmxException(th, "Error while trying to validate EDI Message:" + messageIdentification);
                }
            }
            throw new SdmxSyntaxException("Message identification" + EDI_PREFIX.MESSAGE_IDENTIFICATION + " is not terminated with an end identification " + EDI_PREFIX.END_MESSAGE_ADMINISTRATION);
        }

        private PartyBean processMessageSender() {
            //Seventh Line is the sending agency
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.SENDING_AGENCY, true);
            String sendingAgency = currentLine;

            int contactInfoCount = 0;
            int communicationChannellInfoCount = 0;

            List<ContactBean> contacts = new ArrayList<ContactBean>();

            ContactMutableBean contactMutable = null;
            while (ediReader.moveNext()) {
                if (ediReader.getLineType() == EDI_PREFIX.MESSAGE_ID_PROVIDED_BY_SENDER) {
                    //We do not expect to IDE+10 after contact details have been processed
                    if (contactInfoCount > 0) {
                        throw new SdmxSyntaxException("Unexpected " + ediReader.getLineType() + ", this segment must come before contact segment: " + EDI_PREFIX.CONTACT_INFORMATION);
                    }
                    if (messageName != null) {
                        throw new SdmxSyntaxException("Multiple " + EDI_PREFIX.MESSAGE_ID_PROVIDED_BY_SENDER + "  segments found");
                    }
                    messageName = ediReader.getCurrentLine();
                } else if (ediReader.getLineType() == EDI_PREFIX.CONTACT_INFORMATION) {
                    contactInfoCount++;
                    if (contactInfoCount > 3) {
                        throw new SdmxSyntaxException("EDI Message can not contain more then 3 CTA+ segments (contact information)");
                    }
                    if (contactMutable != null) {
                        //This is the second contact info
                        contacts.add(new ContactBeanImpl(contactMutable));
                    }
                    contactMutable = new ContactMutableBeanImpl();

                    communicationChannellInfoCount = 0;  //reset the communication channel count

                    String currentLine = ediReader.getCurrentLine();
                    String[] contactInfoSplit = EDIUtil.splitOnPlus(currentLine, 2);


                    String contactRoleTxt = contactInfoSplit[0];
                    if (!contactRoleTxt.equals("CC") && !contactRoleTxt.equals("CP") && !contactRoleTxt.equals("CF") && !contactRoleTxt.equals("CE")) {
                        throw new SdmxSyntaxException(EDI_PREFIX.CONTACT_INFORMATION + " segment must have a contact function which must be either: CC, CP, CF, or CE - contact function provided was: " + contactRoleTxt);
                    }

                    String[] contactInfoReSplit = EDIUtil.splitOnColon(contactInfoSplit[1]);
                    String contactNameTxt;
                    if (contactInfoReSplit.length == 1) {
                        contactNameTxt = contactInfoReSplit[0];
                    } else if (contactInfoReSplit.length == 2) {
                        contactMutable.setId(contactInfoReSplit[0]);
                        contactNameTxt = contactInfoReSplit[1];
                    } else {
                        throw new SdmxSyntaxException("Unexpected ':' in string, expected only one colon to deliminate between the contact id and the contact name: " + currentLine);
                    }
                    contactMutable.addName(new TextTypeWrapperMutableBeanImpl(Locale.ENGLISH.getLanguage(), contactNameTxt));
                    contactMutable.addRole(new TextTypeWrapperMutableBeanImpl(Locale.ENGLISH.getLanguage(), contactRoleTxt));

                } else if (ediReader.getLineType() == EDI_PREFIX.COMMUNICATION_NUMBER) {
                    //We do not expect to see contact phone numbers before we have hit the contact information segment defining who the contact is
                    if (contactMutable == null) {
                        throw new SdmxSyntaxException("Unexpected " + EDI_PREFIX.COMMUNICATION_NUMBER + ", this segment must come after a contact information segment: " + EDI_PREFIX.CONTACT_INFORMATION);
                    }

                    communicationChannellInfoCount++;
                    if (communicationChannellInfoCount > 5) {
                        throw new SdmxSyntaxException("EDI Message can not contain more then 5 COM+ segments per CTA+ segment (communication number)");
                    }

                    String currentLine = ediReader.getCurrentLine();

                    String[] numberChannelSplit = EDIUtil.splitOnColon(currentLine, 2);
                    String communicationNumber = numberChannelSplit[0];
                    String communicationChannel = numberChannelSplit[1];
                    if (communicationNumber.length() > 512) {
                        throw new SdmxSyntaxException(EDI_PREFIX.COMMUNICATION_NUMBER + " contains a communication number which is longer then the maximum length of 512 characters.  Actual length : " + communicationNumber.length());
                    }

                    if (communicationChannel.equals("EM")) {
                        contactMutable.addEmail(communicationNumber);
                    } else if (communicationChannel.equals("TE")) {
                        contactMutable.addTelephone(communicationNumber);
                    } else if (communicationChannel.equals("FX")) {
                        contactMutable.addFax(communicationNumber);
                    } else if (communicationChannel.equals("XF")) {
                        contactMutable.addX400(communicationNumber);
                    } else {
                        throw new SdmxSyntaxException(EDI_PREFIX.COMMUNICATION_NUMBER + " contains an invalid comminication channel of '" + communicationChannel + "' - allowed channels are EM, TE, FX, or XF");
                    }
                } else {
                    if (contactMutable != null) {
                        //We have a contact to add
                        contacts.add(new ContactBeanImpl(contactMutable));
                    }
                    ediReader.moveBackLine();
                    return new PartyBeanImpl(null, sendingAgency, contacts, null);
                }
            }
            return new PartyBeanImpl(null, sendingAgency, contacts, null);
        }

        private boolean readNextLine() {
            currentLine = ediReader.getNextLine();
            return currentLine != null;
        }

        private void assertMoveNext() {
            if (!ediReader.moveNext()) {
                throw new SdmxSyntaxException("Unexpected end of file" + ediReader.getCurrentLine());
            }
        }

        /**
         * Determines the dataset metadata, such as the missing value string, in the EDI message
         */
        private void determineDatasetMetadata() {
            readNextLine();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATA_START, true);
            datasetId = ediReader.getCurrentLine();

            assertMoveNext();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_ACTION, true);
            if (ediReader.getCurrentLine().equals("6")) {
                datasetAction = DATASET_ACTION.DELETE;
            } else if (ediReader.getCurrentLine().equals("7")) {
                datasetAction = DATASET_ACTION.REPLACE;
            } else {
                throw new SdmxSyntaxException("Unknown EDI-TS Dataset Action value : " + ediReader.getCurrentLine());
            }

            assertMoveNext();

            // Sort out the dates
            reportingBegin = null;
            reportingEnd = null;

            DateFormat df = new SimpleDateFormat("yyyyMMddHHmm:ssS");//.SSSz

            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_DATETIME, true);
            String currentLine = ediReader.getCurrentLine();
            if (!currentLine.startsWith("242:")) {
                throw new SdmxSyntaxException("Could not parse dataset preperation date '" + currentLine + "' expecting '" + EDI_PREFIX.DATASET_DATETIME + "242:' but did not find '242:'");
            }
            if (ediReader.getCurrentLine().length() <= 4) {
                throw new SdmxSyntaxException("Could not parse dataset preperation date '" + currentLine + "' expecting '242:' to be followed by a date in the following format 'yyyyMMddHHmm:SSS' example : 19811807810530:000 (18th July 1981, 05:30am)");
            }
            String datePart = currentLine.substring(4, currentLine.length());
            try {
                datasetPreperation = df.parse(datePart);
            } catch (ParseException e) {
                throw new SdmxSyntaxException("Could not parse dataset preperation date '" + datePart + "', please ensure format is yyyyMMddHHmm:SSS example : 19811807810530:000 (18th July 1981, 05:30am)");
            }
            assertMoveNext();
            if (EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_DATETIME, false)) {

                currentLine = ediReader.getCurrentLine();
                if (!currentLine.startsWith("Z02:")) {
                    throw new SdmxSyntaxException("Could not parse dataset preperation date '" + currentLine + "' expecting '" + EDI_PREFIX.DATASET_DATETIME + "242:' but did not find 'Z02:'");
                }
                if (ediReader.getCurrentLine().length() <= 4) {
                    throw new SdmxSyntaxException("Could not parse dataset preperation date '" + currentLine + "' expecting 'Z02:' to be followed by a date in the following format 'yyyyMMddHHmm:SSS' example : 19811807810530:000 (18th July 1981, 05:30am)");
                }

                // Evaluate the suffix to determine the reporting period format
                int dateFormatStart = currentLine.lastIndexOf(":");
                String timeFormatString = currentLine.substring(dateFormatStart + 1);  // Add 1 since we don't want the colon character
                EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.parseString(timeFormatString);
                datePart = currentLine.substring(4, dateFormatStart);

                reportingBegin = timeFormat.parseDate(datePart);
                if (timeFormat.isRange()) {
                    reportingEnd = timeFormat.parseEndDate(datePart);
                } else {
                    reportingEnd = reportingBegin;
                }
                reportingPeriod = reportingBegin;
                reportingEnd = DateUtil.moveToEndofPeriod(reportingEnd, TIME_FORMAT.DATE_TIME);
            } else {
                ediReader.moveBackLine();
            }
            assertMoveNext();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DSD_REFERENCE, true);
            keyFamilyIdentifier = ediReader.getCurrentLine();

            assertMoveNext();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_SEND_METHOD, true);

            assertMoveNext();
            EDIUtil.assertPrefix(ediReader, EDI_PREFIX.DATASET_MISSING_VALUE_SYMBOL, true);
            missingValue = ediReader.getCurrentLine();
        }
    }
}
