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
package org.sdmxsource.sdmx.dataparser.transform.impl;

import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;
import org.sdmxsource.sdmx.dataparser.transform.SchemaGenerator;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * The type Schema generator utility.
 */
public class SchemaGeneratorUtility extends BaseSchemaGenerator implements SchemaGenerator {


    /**
     * Instantiates a new Schema generator utility.
     */
    public SchemaGeneratorUtility() {
        super(null);
    }

    @Override
    public void transform(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, Map<String, Set<String>> validCodes) {
        this.validCodeMap = validCodes;
        if (out == null) {
            throw new IllegalArgumentException("No output stream provided");
        }

        try {
            // write out the initial xml schema information common to all xsd types
            // this includes the CODELISTS
            targetNamespace = keyFamily.getUrn() + ":utility";
            init(out, UTILITY, targetNamespace, targetSchemaVersion, keyFamily);

            //////////////////DATASET////////////////////////////////////////////////////////////////////////////////
            //ADD ELEMENT
            // only changing compact to utility in 3rd arg
            addElement("DataSet", "DataSetType", "utility:DataSet");

            // again just change compact to utility
            addComplexType("DataSetType", "utility:DataSetType");

            //START SEQUENCE
            writer.writeStartElement(SCHEMA_NS, "sequence");

            //START CHOICE
            //addChoice("unbounded");
            addChoice(UTILITY, "", "unbounded");

            //ADD GROUPS
            for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
                //START ELEMENT
                writer.writeStartElement(SCHEMA_NS, "element");
                writer.writeAttribute("ref", currentGroup.getId());

                //END ELEMENT
                writer.writeEndElement();
            }
            //START ELEMENT
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("ref", "Series");
            writer.writeEndElement();

            //END CHOICE ELEMENT
            writer.writeEndElement();

            addAnnotations();

            //END SEQUENCE
            writer.writeEndElement();

            for (AttributeSuperBean bean : keyFamily.getDatasetAttributes()) {
                addComponentBean(bean);
            }

            // END DATASET
            endComplexType(writer);

            for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
                //////////////////GROUP////////////////////////////////////////////////////////////////////////////////

                addElement(currentGroup.getId(), currentGroup.getId() + "Type", "utility:Group");

                addComplexType(currentGroup.getId() + "Type", "utility:GroupType");

                //START SEQUENCE
                writer.writeStartElement(SCHEMA_NS, "sequence");

                //START ELEMENT
                writer.writeStartElement(SCHEMA_NS, "element");
                writer.writeAttribute("ref", "Series");
                writer.writeAttribute("maxOccurs", "unbounded");
                //END ELEMENT
                writer.writeEndElement();

                addAnnotations();
                //END SEQUENCE
                writer.writeEndElement();

                writeGroupAttributes(currentGroup);

                endComplexType(writer);
            }

            //////////////////SERIES////////////////////////////////////////////////////////////////////////////////
            addElement("Series", "SeriesType", "utility:Series");

            addComplexType("SeriesType", "utility:SeriesType");

            //START SEQUENCE
            writer.writeStartElement(SCHEMA_NS, "sequence");

            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("ref", "Key");
            writer.writeEndElement();
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("ref", "Obs");
            writer.writeAttribute("maxOccurs", "unbounded");
            writer.writeEndElement();

            addAnnotations();

            //END SEQUENCE
            writer.writeEndElement();

            for (AttributeSuperBean bean : keyFamily.getSeriesAttributes()) {
                addComponentBean(bean);
            }
            endComplexType(writer);

            //////////////////KEY////////////////////////////////////////////////////////////////
            addElement("Key", "KeyType", "utility:Key");

            addComplexType("KeyType", "utility:KeyType");

            //START SEQUENCE
            writer.writeStartElement(SCHEMA_NS, "sequence");

            for (DimensionSuperBean bean : keyFamily.getDimensions()) {
                addElement(bean);
            }

            //END SEQUENCE
            writer.writeEndElement();

            endComplexType(writer);

            //////////////////OBSERVATION////////////////////////////////////////////////////////////////////////////////
            addElement("Obs", "ObsType", "utility:Obs");

            addComplexType("ObsType", "utility:ObsType");

            //START SEQUENCE
            writer.writeStartElement(SCHEMA_NS, "sequence");

            if (keyFamily.getTimeDimension() != null) {
                addElement(keyFamily.getTimeDimension());
            }

            if (keyFamily.getPrimaryMeasure() != null) {
                addElement(keyFamily.getPrimaryMeasure());
            }

            addAnnotations();

            //END SEQUENCE
            writer.writeEndElement();

            for (AttributeSuperBean bean : keyFamily.getObservationAttributes()) {
                addComponentBean(bean);
            }

            endComplexType(writer);
            //END documentation
            writer.writeEndElement();

        } catch (Throwable th) {
            throw new RuntimeException(th);
        } finally {
            close();
        }
    }

    @Override
    public void transformCrossSectional(OutputStream out,
                                        String targetNamespace, SDMX_SCHEMA targetSchemaVersion,
                                        DataStructureSuperBean keyFamily, String crossSectionDimensionId,
                                        Map<String, Set<String>> validCodes) {
        throw new SdmxNotImplementedException("Utility Cross Sectional Schema Generation");
    }
}
