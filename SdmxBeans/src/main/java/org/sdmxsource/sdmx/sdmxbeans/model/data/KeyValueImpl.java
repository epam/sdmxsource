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

import org.sdmxsource.sdmx.api.model.data.KeyValue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The type Key value.
 */
public class KeyValueImpl implements KeyValue, Externalizable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String concept;


    /**
     * Instantiates a new Key value.
     */
    public KeyValueImpl() {
    }

    /**
     * Instantiates a new Key value.
     *
     * @param code    the code
     * @param concept the concept
     */
    public KeyValueImpl(String code, String concept) {
        this.code = code;
        this.concept = concept;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getConcept() {
        return concept;
    }


    @Override
    public int compareTo(KeyValue compareTo) {
        if (concept.equals(compareTo.getConcept())) {
            return code.compareTo(compareTo.getCode());
        }
        return concept.compareTo(compareTo.getConcept());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KeyValue) {
            KeyValue that = (KeyValue) obj;
            return this.getConcept().equals(that.getConcept()) && this.getCode().equals(that.getCode());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (concept + code).hashCode();
    }

    @Override
    public String toString() {
        return concept + ":" + code;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(concept);
        out.writeObject(code);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        concept = (String) in.readObject();
        code = (String) in.readObject();
    }
}
