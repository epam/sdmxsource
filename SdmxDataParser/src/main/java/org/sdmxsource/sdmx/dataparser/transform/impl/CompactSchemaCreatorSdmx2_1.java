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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;

import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * Generates a Compact Schema for SDMX 2.1
 */
public class CompactSchemaCreatorSdmx2_1 extends CompactSchemaCreator {

    private static Logger LOG = LogManager.getLogger(CompactSchemaCreatorSdmx2_1.class);

    /**
     * Instantiates a new Compact schema creator sdmx 2 1.
     *
     * @param out                 the out
     * @param targetNamespace     the target namespace
     * @param targetSchemaVersion the target schema version
     * @param keyFamily           the key family
     * @param schemaLocation      the schema location
     * @param validCodes          the valid codes
     */
    public CompactSchemaCreatorSdmx2_1(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String schemaLocation, Map<String, Set<String>> validCodes) {
        super(out, targetNamespace, targetSchemaVersion, keyFamily, schemaLocation, validCodes);
    }

    /**
     * Instantiates a new Compact schema creator sdmx 2 1.
     *
     * @param out                     the out
     * @param targetNamespace         the target namespace
     * @param targetSchemaVersion     the target schema version
     * @param keyFamily               the key family
     * @param crossSectionDimensionId the cross section dimension id
     * @param schemaLocation          the schema location
     * @param validCodes              the valid codes
     */
    public CompactSchemaCreatorSdmx2_1(OutputStream out, String targetNamespace, SDMX_SCHEMA targetSchemaVersion, DataStructureSuperBean keyFamily, String crossSectionDimensionId, String schemaLocation, Map<String, Set<String>> validCodes) {
        super(out, targetNamespace, targetSchemaVersion, keyFamily, crossSectionDimensionId, schemaLocation, validCodes);
    }

    @Override
    public void createSchema() {
        LOG.info("Generating Schema: SDMX 2.1 " + (generatingCrossSectional ? " - cross sectional" : ""));
        super.createSchema();
    }

    @Override
    protected void writeSchemaInformation() {
        String namespaceId;
        if (generatingAllDimensions) {
            namespaceId = "AllDimensions";
        } else if (generatingCrossSectional) {
            // The targetNamespace will be the URN of the cross-sectional dimension
            // FIX SLi According to the standard this should be keyFamily urn followed by the ObsLevelDim and cross dimension id.
            namespaceId = crossSectionDimension.getId();
        } else {
            DimensionSuperBean timeDimensionBean = keyFamily.getTimeDimension();
            if (timeDimensionBean == null) {
                namespaceId = keyFamily.getPrimaryMeasure().getId();
            } else {
                namespaceId = timeDimensionBean.getId();
            }
        }
        targetNamespace = keyFamily.getUrn() + ":ObsLevelDim:" + namespaceId;
        init(out, COMPACT, targetNamespace, targetSchemaVersion, keyFamily);
    }

    @Override
    protected void writeDataset() throws Exception {
        addElement("DataSet", "DataSetType", "compact:DataSet");

        if (generatingAllDimensions || generatingCrossSectional) {
            addComplexContentRoot("DataSetType", "DataSetType");
        } else {
            addComplexContentRoot("DataSetType", "TimeSeriesDataSetType");
        }

        writer.writeStartElement(SCHEMA_NS, "sequence");
        addAnnotations();

        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", "DataProvider");
        writer.writeAttribute("type", "common:DataProviderReferenceType");
        writer.writeAttribute("form", "unqualified");
        writer.writeAttribute("minOccurs", "0");
        writer.writeEndElement();  // End DataProvider

        // Add each group
        for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
            // Write a group element
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("name", "Group");
            writer.writeAttribute("type", currentGroup.getId() + "Type");
            writer.writeAttribute("form", "unqualified");
            writer.writeAttribute("minoocurs", "0");
            writer.writeEndElement();
        }

        // Add a choice with only a MinOccurs
        writer.writeStartElement(SCHEMA_NS, "choice");
        writer.writeAttribute("minOccurs", "0");

        if (generatingAllDimensions) {
            // Write an Observation element
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("name", "Obs");
            writer.writeAttribute("type", "ObsType");
            writer.writeAttribute("form", "unqualified");
            writer.writeAttribute("maxOccurs", "unbounded");
            writer.writeEndElement();
        } else {
            // Write a Series element
            writer.writeStartElement(SCHEMA_NS, "element");
            writer.writeAttribute("name", "Series");
            writer.writeAttribute("type", "SeriesType");
            writer.writeAttribute("form", "unqualified");
            writer.writeAttribute("maxOccurs", "unbounded");
            writer.writeEndElement();
        }

        writer.writeEndElement();  // End Choice
        writer.writeEndElement();  // End Sequence

        for (AttributeSuperBean bean : keyFamily.getDatasetAttributes()) {
            addAttributeBean(bean);
        }

        writeReportingYearStartDay();
        endComplexType(writer);
    }

    @Override
    protected void writeGroups() throws Exception {
        for (GroupSuperBean currentGroup : keyFamily.getGroups()) {
            addComplexTypeForRestriction(currentGroup.getId() + "Type", "dsd:GroupType");

            writer.writeStartElement(SCHEMA_NS, "sequence");  // Start Sequence
            addAnnotations();
            writer.writeEndElement();                          // End Sequence

            writeGroupAttributes(currentGroup);
            writeReportingYearStartDay();
            endComplexType(writer);
        }
    }

    @Override
    protected void writeSeries() throws Exception {
        if (generatingAllDimensions) {
            //All Dimensions are at the observation level
            return;
        }

        addComplexContentRoot("SeriesType", "TimeSeriesType");

        writer.writeStartElement(SCHEMA_NS, "sequence");     // Start Sequence

        addAnnotations();
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("name", "Obs");
        writer.writeAttribute("type", "ObsType");
        writer.writeAttribute("form", "unqualified");
        writer.writeAttribute("minOccurs", "0");
        writer.writeAttribute("maxOccurs", "unbounded");
        writer.writeEndElement();

        writer.writeEndElement();                            // End Sequence

        if (generatingCrossSectional) {
            for (DimensionSuperBean bean : keyFamily.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.TIME_DIMENSION)) {
                if (!bean.getId().equals(crossSectionDimension.getId())) {
                    addRequiredDimensionBean(bean);
                }
            }
        } else {
            for (DimensionSuperBean bean : keyFamily.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                addRequiredDimensionBean(bean);
            }
        }

        for (AttributeSuperBean bean : keyFamily.getSeriesAttributes()) {
            addComponentBean(bean);
            writer.writeAttribute("use", "optional");
            writer.writeEndElement();
        }
        writeReportingYearStartDay();

        endComplexType(writer);
    }

    @Override
    protected void writeObservations() throws Exception {
        if (generatingAllDimensions || generatingCrossSectional) {
            addComplexContentRoot("ObsType", "ObsType");
        } else {
            addComplexContentRoot("ObsType", "TimeSeriesObsType");
        }

        writer.writeStartElement(SCHEMA_NS, "sequence");   // Start Sequence
        addAnnotations();
        writer.writeEndElement();                           // End Sequence

        // If Cross-Sectional add the dimension now
        if (generatingCrossSectional) {
            addRequiredDimensionBean(crossSectionDimension);
        } else if (generatingAllDimensions) {
            for (DimensionSuperBean bean : keyFamily.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                addRequiredDimensionBean(bean);
            }
        }

        if (keyFamily.getPrimaryMeasure() != null) {
            addOptionalDimensionBean(keyFamily.getPrimaryMeasure());
        }

        for (AttributeSuperBean bean : keyFamily.getObservationAttributes()) {
            addComponentBean(bean);
            writer.writeAttribute("use", "optional");
            writer.writeEndElement();
        }

        // If Cross-Sectional add the time dimension
        if (generatingCrossSectional) {
            if (keyFamily.getTimeDimension() != null) {
                addProhibitedDimensionBean(keyFamily.getTimeDimension());
            }
        }

        writeReportingYearStartDay();

        endComplexType(writer);
    }

    @Override
    protected void addComplexContentRoot(String type, String type_2_1) throws Exception {
        String complexType;
        if (generatingCrossSectional) {
            complexType = type;
        } else {
            complexType = type_2_1;
        }
        addComplexTypeForRestriction(type, "dsd:" + complexType);
    }

    @Override
    void addElement(String name, String type, String substitutionGroup) throws XMLStreamException {
        super.addElement(name, type, null);
    }

    @Override
    void addComponentBean(ComponentSuperBean componentBean) throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "attribute");
        writer.writeAttribute("name", componentBean.getId());
        if (componentBean.getBuiltFrom().getStructureType() == SDMX_STRUCTURE_TYPE.TIME_DIMENSION) {
            writer.writeAttribute("type", "common:ObservationalTimePeriodType");
        } else if (componentBean.getCodelist(true) == null) {
            TextFormatBean textFormat = componentBean.getTextFormat();
            if (textFormat == null) {
                writer.writeAttribute("type", "xs:string");
            } else if (textFormat.hasRestrictions()) {
                writer.writeAttribute("type", componentBean.getId());
            } else {
                TEXT_TYPE textType = textFormat.getTextType();
                writer.writeAttribute("type", determineSchemaType(textType));
            }
        } else {
            writer.writeAttribute("type", getCodelistReference(componentBean.getCodelist(true)));
        }
    }

    @Override
    void addAnnotations() throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "element");
        writer.writeAttribute("ref", "common:Annotations");
        writer.writeAttribute("minOccurs", "0");
        writer.writeEndElement();
    }

    private void writeReportingYearStartDay() throws XMLStreamException {
        writer.writeStartElement(SCHEMA_NS, "attribute");
        writer.writeAttribute("name", "REPORTING_YEAR_START_DAY");
        writer.writeAttribute("type", "xs:gMonthDay");
        writer.writeAttribute("use", "prohibited");
        writer.writeEndElement();
    }
}
