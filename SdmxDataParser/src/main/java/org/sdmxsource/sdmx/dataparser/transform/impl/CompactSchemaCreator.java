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
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;

import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;


/**
 * The type Compact schema creator.
 */
abstract public class CompactSchemaCreator extends BaseSchemaGenerator {

    /**
     * The Out.
     */
    protected OutputStream out;
    /**
     * The Target namespace.
     */
    protected String targetNamespace;
    /**
     * The Target schema version.
     */
    protected SDMX_SCHEMA targetSchemaVersion;
    /**
     * The Key family.
     */
    protected DataStructureSuperBean keyFamily;
    /**
     * The Cross section dimension.
     */
    protected DimensionSuperBean crossSectionDimension;
    /**
     * The Generating cross sectional.
     */
    protected boolean generatingCrossSectional;
    /**
     * The Generating all dimensions.
     */
    protected boolean generatingAllDimensions;

    /**
     * Instantiates a new Compact schema creator.
     *
     * @param out                 the out
     * @param targetNamespace     the target namespace
     * @param targetSchemaVersion the target schema version
     * @param keyFamily           the key family
     * @param schemaLocation      the schema location
     * @param validCodes          the valid codes
     */
    public CompactSchemaCreator(OutputStream out,
                                String targetNamespace,
                                SDMX_SCHEMA targetSchemaVersion,
                                DataStructureSuperBean keyFamily,
                                String schemaLocation,
                                Map<String, Set<String>> validCodes) {
        super(validCodes);
        this.out = out;
        if (out == null) {
            throw new IllegalArgumentException("No output stream provided");
        }
        this.targetNamespace = targetNamespace;
        this.targetSchemaVersion = targetSchemaVersion;
        this.keyFamily = keyFamily;
        this.schemaLocation = schemaLocation;
    }

    /**
     * Instantiates a new Compact schema creator.
     *
     * @param out                     the out
     * @param targetNamespace         the target namespace
     * @param targetSchemaVersion     the target schema version
     * @param keyFamily               the key family
     * @param crossSectionDimensionId the cross section dimension id
     * @param schemaLocation          the schema location
     * @param validCodes              the valid codes
     */
    public CompactSchemaCreator(OutputStream out,
                                String targetNamespace,
                                SDMX_SCHEMA targetSchemaVersion,
                                DataStructureSuperBean keyFamily,
                                String crossSectionDimensionId,
                                String schemaLocation,
                                Map<String, Set<String>> validCodes) {
        this(out, targetNamespace, targetSchemaVersion, keyFamily, schemaLocation, validCodes);

        if (crossSectionDimensionId != null && !crossSectionDimensionId.equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
            if (crossSectionDimensionId.equals("AllDimensions")) {
                this.generatingAllDimensions = true;
            } else {
                // Check that the specified dimension id actually exists within the Key Family
                this.crossSectionDimension = keyFamily.getDimensionById(crossSectionDimensionId);
                if (crossSectionDimension == null) {
                    throw new IllegalArgumentException("The specified cross-section dimension: " + crossSectionDimensionId + " does not exist within the Data Structure Definition: " + keyFamily);
                }
                generatingCrossSectional = true;
            }
        }
    }

    /**
     * Create schema.
     */
    public void createSchema() {
        try {
            writeSchemaInformation();
            writeDataset();
            writeGroups();
            writeSeries();
            writeObservations();
            close();
        } catch (Exception th) {
            throw new RuntimeException(th);
        }
    }

    /**
     * Add complex content root.
     *
     * @param type     the type
     * @param type_2_1 the type 2 1
     * @throws Exception the exception
     */
    abstract protected void addComplexContentRoot(String type, String type_2_1) throws Exception;

    /**
     * Write schema information.
     */
    abstract protected void writeSchemaInformation();

    /**
     * Write dataset.
     *
     * @throws Exception the exception
     */
    abstract protected void writeDataset() throws Exception;

    /**
     * Write groups.
     *
     * @throws Exception the exception
     */
    abstract protected void writeGroups() throws Exception;

    /**
     * Write series.
     *
     * @throws Exception the exception
     */
    abstract protected void writeSeries() throws Exception;

    /**
     * Write observations.
     *
     * @throws Exception the exception
     */
    abstract protected void writeObservations() throws Exception;

    /**
     * Add optional dimension bean.
     *
     * @param dimensionBean the dimension bean
     * @throws XMLStreamException the xml stream exception
     */
    protected void addOptionalDimensionBean(ComponentSuperBean dimensionBean) throws XMLStreamException {
        addDimensionBean(dimensionBean, "optional");
    }

    /**
     * Add required dimension bean.
     *
     * @param dimensionBean the dimension bean
     * @throws XMLStreamException the xml stream exception
     */
    protected void addRequiredDimensionBean(ComponentSuperBean dimensionBean) throws XMLStreamException {
        addDimensionBean(dimensionBean, "required");
    }

    /**
     * Add prohibited dimension bean.
     *
     * @param dimensionBean the dimension bean
     * @throws XMLStreamException the xml stream exception
     */
    protected void addProhibitedDimensionBean(ComponentSuperBean dimensionBean) throws XMLStreamException {
        addDimensionBean(dimensionBean, "prohibited");
    }

    private void addDimensionBean(ComponentSuperBean dimensionBean, String useageType) throws XMLStreamException {
        addComponentBean(dimensionBean);
        writer.writeAttribute("use", useageType);
        writer.writeEndElement();
    }

    @Override
    void addAttributeBean(AttributeSuperBean attributeBean) throws XMLStreamException {
        addComponentBean(attributeBean);
        writer.writeAttribute("use", "optional");  // This is always OPTIONAL
        writer.writeEndElement();
    }
}
