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
import org.sdmxsource.sdmx.ediparser.model.reader.FileReader;
import org.sdmxsource.util.io.StreamUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * FR-983: Previous implementations of this class used a Scanner rather than a BufferedReader.
 * Due to various issues, this class must use a BufferedReader.
 */
public class FileReaderImpl implements FileReader {
    /**
     * The Data file.
     */
    protected ReadableDataLocation dataFile;
    /**
     * The Current line.
     */
    protected String currentLine;
    /**
     * The Back line.
     */
    protected boolean backLine;
    /**
     * The File position.
     */
    protected int filePosition;
    private String endOfLineTag;
    private BufferedReader bufferedReader;
    private int startIndex = -1;
    private int endIndex = -1;
    private String charset;


    /**
     * Instantiates a new File reader.
     *
     * @param dataFile     the data file
     * @param endOfLineTag the end of line tag
     */
    public FileReaderImpl(ReadableDataLocation dataFile, String endOfLineTag) {
        this.dataFile = dataFile;
        this.endOfLineTag = endOfLineTag;
        resetReader();
    }

    /**
     * Instantiates a new File reader.
     *
     * @param dataFile     the data file
     * @param endOfLineTag the end of line tag
     * @param charset      the charset
     */
    public FileReaderImpl(ReadableDataLocation dataFile, String endOfLineTag, String charset) {
        this.dataFile = dataFile;
        this.endOfLineTag = endOfLineTag;
        this.charset = charset;
        resetReader();
    }

    /**
     * Instantiates a new File reader.
     *
     * @param dataFile     the data file
     * @param endOfLineTag the end of line tag
     * @param startindex   the startindex
     * @param endIndex     the end index
     */
    public FileReaderImpl(ReadableDataLocation dataFile, String endOfLineTag, int startindex, int endIndex) {
        this.dataFile = dataFile;
        this.endOfLineTag = endOfLineTag;
        this.startIndex = startindex;
        this.endIndex = endIndex;
        resetReader();
    }

    /**
     * Instantiates a new File reader.
     *
     * @param dataFile     the data file
     * @param endOfLineTag the end of line tag
     * @param startindex   the startindex
     * @param endIndex     the end index
     * @param charset      the charset
     */
    public FileReaderImpl(ReadableDataLocation dataFile, String endOfLineTag, int startindex, int endIndex, String charset) {
        this.dataFile = dataFile;
        this.endOfLineTag = endOfLineTag;
        this.startIndex = startindex;
        this.endIndex = endIndex;
        this.charset = charset;
        resetReader();
    }

    /**
     * Resets the reader to the start of the file
     */
    @Override
    public void resetReader() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (charset != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(dataFile.getInputStream(), Charset.forName(charset)));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(dataFile.getInputStream()));
        }

        filePosition = 0;
        if (startIndex > 0) {
            while (filePosition != startIndex) {
                moveNext();
            }
        }
    }

    @Override
    public boolean moveNext() {
        if (filePosition == endIndex) {
            currentLine = null;
            return false;
        }

        StringBuilder sb = new StringBuilder();

        boolean hasNext = false;
        int currentChar = -1;
        int previousChar = -1;
        int prevQuestionMarkCount = 0;
        while (true) {
            try {
                // ASCII value of ' is 39
                // ASCII value of ? is 63

                previousChar = currentChar;
                if (previousChar == 63) {
                    prevQuestionMarkCount++;
                } else {
                    prevQuestionMarkCount = 0;
                }
                currentChar = bufferedReader.read();
                if (currentChar == -1) {
                    break;
                }

                if (currentChar == 39) {
                    if (previousChar != 63) {
                        break;
                    }
                    if (prevQuestionMarkCount % 2 == 0) {
                        break;
                    }
                }
                hasNext = true;
                sb.append((char) currentChar);
            } catch (IOException e) {
                break;
            }
        }

        if (hasNext) {
            filePosition++;
            currentLine = sb.toString();
            //LOG.debug("Move Next : "+ currentLine);
        } else {
            currentLine = null;
        }

        cleanLine();
        return currentLine != null && currentLine.length() > 0;
    }

    @Override
    public String getNextLine() {
        if (backLine) {
            backLine = false;
            return currentLine;
        }
        if (moveNext()) {
            return getCurrentLine();
        }
        return null;
    }

    public boolean isBackLine() {
        return backLine;
    }

    @Override
    public String getCurrentLine() {
        return currentLine;
    }

    private void cleanLine() {
        if (currentLine != null) {
            currentLine = currentLine.replaceAll("\\u000A", "");    // Removes all new-line characters 
            currentLine = currentLine.replaceAll("\\u000D", "");    // Removes all carriage-return characters
            //          currentLine = currentLine.replaceAll(lineSeperator, "");
            //          currentLine = currentLine.trim();
        }
    }

    @Override
    public int getLineNumber() {
        return filePosition;
    }

    @Override
    public void moveBackLine() {
        backLine = true;
    }


    @Override
    public void copyToStream(OutputStream out) {
        StreamUtil.copyStream(dataFile.getInputStream(), out);
    }

    @Override
    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
