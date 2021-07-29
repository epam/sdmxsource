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
package org.sdmxsource.sdmx.ediparser.model.reader.impl;

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIReader;
import org.sdmxsource.sdmx.ediparser.model.reader.FileReader;
import org.sdmxsource.sdmx.ediparser.util.EDIUtil;
import org.sdmxsource.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * The type Edi reader.
 */
public class EDIReaderImpl extends FileReaderImpl implements EDIReader, FileReader {
    private static Pattern unreservedPlus = Pattern.compile("(?<!\\?)\\+");
    private static Pattern unreservedDash = Pattern.compile(EDI_CONSTANTS.END_OF_LINE_REG_EX);
    private static Pattern unreservedQuestionMark = Pattern.compile("(?<!\\?)\\?(?![\\+\\.'\\?:])");
    /**
     * The End of file reached.
     */
    public boolean endOfFileReached;
    private EDI_PREFIX lineType;
    private List<String> tmpStore;

    /**
     * Instantiates a new Edi reader.
     *
     * @param dataFile the data file
     */
    public EDIReaderImpl(ReadableDataLocation dataFile) {
        //End of line terminator is any ' char that is not immediately preceded by a ?
        super(dataFile, EDI_CONSTANTS.END_OF_LINE_REG_EX, EDI_CONSTANTS.CHARSET_ENCODING);
    }

    /**
     * Instantiates a new Edi reader.
     *
     * @param dataFile   the data file
     * @param startindex the startindex
     * @param endIndex   the end index
     */
    public EDIReaderImpl(ReadableDataLocation dataFile, int startindex, int endIndex) {
        super(dataFile, EDI_CONSTANTS.END_OF_LINE_REG_EX, startindex, endIndex, EDI_CONSTANTS.CHARSET_ENCODING);
    }

    @Override
    public EDI_PREFIX getLineType() {
        return lineType;
    }

    @Override
    public boolean moveNext() {
        if (tmpStore != null) {
            // If there are entries in the temporaryStore, pop the first entry, make it the currentLine
            // and remember to increment the filePosition counter of the superclass, otherwise we'll end up
            // with a mismatch of actual to expected lines.
            this.currentLine = tmpStore.remove(0);
            filePosition++;
            if (tmpStore.isEmpty()) {
                tmpStore = null;
            }

            this.lineType = EDI_PREFIX.parseString(currentLine);
            String linePrefix = lineType.getPrefix();
            this.currentLine = getCurrentLine().substring(linePrefix.length());

            return true;
        }

        if (super.backLine) {
            backLine = false;
            return true;
        }

        boolean nextLine = super.moveNext();
        if (!nextLine) {
            return false;
        }

        // We cannot use the String method trim() here, since it is important to trim ONLY the leading
        // whitespace. There could be trailing whitespace which has meaning in EDI. At this point the
        // variable currentLine does not contain the end-marker character.
        // So the EDI "UNA:+.? '" would now be in currentLine as "UNA:+.? ".
        // The trailing whitespace is VITAL in this case, so only trim the leading whitespace
        // otherwise we may produce errors.
        currentLine = StringUtil.trimLeadingWhitespace(currentLine);

        if (endOfFileReached) {
            if (currentLine.trim().equals("")) {
                // There is whitespace after the end of the file. Just simply ignore it.
                currentLine = "";
                return true;
            }
        }

        // Does this line contain a ' character? If so, this line needs splitting
        int idx = this.currentLine.indexOf("'");
        if (idx != -1) {
            // Reset the temporaryStore and evaluate the entire "current line"
            tmpStore = new ArrayList<String>();
            String evaluationString = this.currentLine;

            while (idx != -1) {
                // Count the number of sequential ? characters at the end of the evaluation string
                int questionMarkCount = 0;
                for (int j = idx - 1; j >= 0; j--) {
                    if (evaluationString.charAt(j) == '?') {
                        questionMarkCount++;
                    } else {
                        break;
                    }
                }

                if (questionMarkCount == 0 || questionMarkCount % 2 == 0) {
                    // This should have been a split but wasn't.
                    // Put the 1st part of the evaluation string into the temporary store, and the process everything after the 1st ' character
                    // So if the evaluation string is currently: foo??'bar
                    //   foo?? goes into temporary store
                    //   bar becomes the next bit to evaluate
                    tmpStore.add(evaluationString.substring(0, idx));
                    evaluationString = evaluationString.substring(idx + 1);   // The +1 here ensures that the ' character is not included
                    idx = evaluationString.indexOf("'");
                } else {
                    // This was an escaped ' character so hunt for the next one
                    idx = evaluationString.indexOf("'", idx + 1);
                }

                if (idx == -1) {
                    // There are no more ' characters so put whatever remains into the temporary store
                    tmpStore.add(evaluationString);
                }
            }

            // Pop the first item out of the temporary store
            // Note: there is no need to increment the filePosition counter of the superclass since it was
            // done in the prior call:  super.moveNext();
            this.currentLine = tmpStore.remove(0);
            if (tmpStore.isEmpty()) {
                tmpStore = null;
            }
        }

        this.lineType = EDI_PREFIX.parseString(currentLine);
        String linePrefix = lineType.getPrefix();
        if (linePrefix.equals("UNZ+")) {
            endOfFileReached = true;
        }
        this.currentLine = getCurrentLine().substring(linePrefix.length());
        return true;
    }

    @Override
    public String parseTextString() {
        String inputString = currentLine;
        if (unreservedPlus.matcher(inputString).find()) {
            throw new IllegalArgumentException("Error processing line '" + currentLine + "' Reserved character '+' must be escaped by escape character '?'");
        }
        if (unreservedDash.matcher(inputString).find()) {
            throw new IllegalArgumentException("Reserved character ''' must be escaped by escape character '?'");
        }
        if (unreservedQuestionMark.matcher(inputString).find()) {
            throw new IllegalArgumentException("Reserved character '?' must be escaped by escape character '?'");
        }

        return EDIUtil.ediToString(inputString);
    }
}
