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

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * Generates a Compact Schema for SDMX 1.0 and SDMX 2.0
 */
public class CompactSchemaCreatorSdmx extends CompactSchemaCreator {

    private static Logger LOG = Logger.getLogger(CompactSchemaCreatorSdmx.class);

    /**
     * Instantiates a new Compact schema creator sdmx.
     *
     * @param out                 the out
     * @param targetNamespace     the target namespace
     * @param targetSchemaVersion the target schema version
     * @param keyFamily           the key family
     * @param schemaLocation      the schema location
     * @param validCodes          the valid codes
     */
    public CompactSchemaCreatorSdmx(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String schemaLocation, Map<String, Set<String>> validCodes) {
        super(out, targetNamespace, targetSchemaVersion, keyFamily, schemaLocation, validCodes);
    }

    /**
     * Instantiates a new Compact schema creator sdmx.
     *
     * @param out                     the out
     * @param targetNamespace         the target namespace
     * @param targetSchemaVersion     the target schema version
     * @param keyFamily               the key family
     * @param crossSectionDimensionId the cross section dimension id
     * @param schemaLocation          the schema location
     * @param validCodes              the valid codes
     */
    public CompactSchemaCreatorSdmx(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String crossSectionDimensionId, String schemaLocation, Map<String, Set<String>> validCodes) {
        super(out, targetNamespace, targetSchemaVersion, keyFamily, crossSectionDimensionId, schemaLocation, validCodes);
    }

    @Override
    public void createSchema() {
        LOG.info("Generating Schema: " + targetSchemaVersion + (generatingCrossSectional ? " - cross sectional" : ""));
        super.createSchema();
    }

    @Override
    protected void writeSchemaInformation() {
        targetNamespace = keyFamily.getUrn() + ":compact";
        init(out, COMPACT, targetNamespace, targetSchemaVersion, keyFamily);
    }

    @Override
    protected void writeDataset() throws Exception {
        addElement("DataSet", "DataSetType", "compact:DataSet");

        addComplexContentRoot("DataSetType", "TimeSeriesDataSetType");

        addChoice(COMPACT, "0", "unbounded");    // Start Choice

        // Add each group
        for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
            // Write a group element
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("ref", currentGroup.getId());
            writer.writeEndElement();
        }

        // Write a series element
        writer.writeStartElement(SCHEMA_NS, "element");    // Start Element
        writer.writeAttribute("ref", "Series");
        writer.writeEndElement();                           // End Element

        addAnnotations();

        writer.writeEndElement();               // End Choice

        for (DimensionSuperBean bean : keyFamily.getDimensions()) {
            addOptionalDimensionBean(bean);
        }
        for (AttributeSuperBean bean : keyFamily.getDatasetAttributes()) {
            addAttributeBean(bean);
        }

        endComplexType(writer);
    }

    @Override
    protected void writeGroups() throws Exception {
        for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
            addElement(currentGroup.getId(), currentGroup.getId() + "Type", "compact:Group");

            addComplexType(currentGroup.getId() + "Type", "compact:GroupType");

            writer.writeStartElement(SCHEMA_NS, "sequence");   // Start Sequence
            addAnnotations();
            writer.writeEndElement();                           // End Sequence

            writeGroupAttributes(currentGroup);

            endComplexType(writer);
        }
    }

    @Override
    protected void writeSeries() throws Exception {
        addElement("Series", "SeriesType", "compact:Series");

        addComplexContentRoot("SeriesType", "TimeSeriesType");

        writer.writeStartElement(SCHEMA_NS, "sequence");   // Start Sequence

        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("ref", "Obs");
        writer.writeAttribute("minOccurs", "0");
        writer.writeAttribute("maxOccurs", "unbounded");
        writer.writeEndElement();
        addAnnotations();

        writer.writeEndElement();                          // End Sequence

        for (DimensionSuperBean bean : keyFamily.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)) {
            addOptionalDimensionBean(bean);
        }

        for (AttributeSuperBean attr : keyFamily.getSeriesAttributes()) {
            //For version 1.0 and 2.0 messages, dimension group attribtues are attached to the group
            if (!skipAttribute(attr.getBuiltFrom())) {
                addComponentBean(attr);
                writer.writeAttribute("use", "optional");
                writer.writeEndElement();
            }
        }

        endComplexType(writer);
    }

    @Override
    protected void writeObservations() throws Exception {
        addElement("Obs", "ObsType", "compact:Obs");

        addComplexContentRoot("ObsType", "TimeSeriesObsType");

        writer.writeStartElement(SCHEMA_NS, "sequence");   // Start Sequence
        addAnnotations();
        writer.writeEndElement();                           // End Sequence

        // If Cross-Sectional add the cross-sectional dimension now
        if (generatingCrossSectional) {
            addRequiredDimensionBean(crossSectionDimension);
        }

        if (keyFamily.getPrimaryMeasure() != null) {
            addOptionalDimensionBean(keyFamily.getPrimaryMeasure());
        }

        // Add the time dimension
        if (keyFamily.getTimeDimension() != null) {
            addOptionalDimensionBean(keyFamily.getTimeDimension());
        }

        for (AttributeSuperBean bean : keyFamily.getObservationAttributes()) {
            addComponentBean(bean);
            writer.writeAttribute("use", "optional");
            writer.writeEndElement();
        }

        endComplexType(writer);
    }

    @Override
    protected void addComplexContentRoot(String type, String type_2_1) throws Exception {
        addComplexType(type, "compact:" + type);
    }
}
