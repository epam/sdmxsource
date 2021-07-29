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
package org.sdmxsource.sdmx.ediparser.model.reader;

import java.io.OutputStream;


/**
 * The interface File reader.
 */
public interface FileReader {

    /**
     * Moves the reader back to the start of the document.
     */
    void resetReader();

    /**
     * Moves the file pointer to the next line.
     *
     * @return false if there is no next line
     */
    boolean moveNext();

    /**
     * Reads the current line - minus the prefix, if the prefix is unknown then an exception is thrown
     *
     * @return the current line
     */
    String getCurrentLine();

    /**
     * Move the file pointer to the next line and returns that line.
     * Null is returned if there is no next line.
     * This method is almost the same as calling moveNext() followed by getCurrentLine() the difference is if there is no next line,
     * the call to getCurrentLine() will the same result as it did prior to this call
     *
     * @return the next line
     */
    String getNextLine();

    /**
     * Returns the line number of the current line, the first line being '1'
     *
     * @return line number
     */
    int getLineNumber();

    /**
     * Returns true is the reader has flagged this to move back a line, as moving back a line does not actually change the current line
     * details
     *
     * @return true is the reader has flagged this to move back a line, as moving back a line does not actually change the current line details
     */
    boolean isBackLine();

    /**
     * Moves the reader back a single line.  This can not be called to iterate backwards - it will only move back one line
     */
    void moveBackLine();

    /**
     * Copies the EDI file to the specified OutputStream
     *
     * @param out the out
     */
    void copyToStream(OutputStream out);

    /**
     * Close the reader and any resources associated with the reader
     */
    void close();
}
