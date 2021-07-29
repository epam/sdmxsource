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
package org.sdmxsource.sdmx.dataparser.engine.reader;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;

import java.io.OutputStream;
import java.util.List;

/**
 * Acts a a direct wrapper on top of a {@link DataReaderEngine}.  The intention is to override this class if any specific methods are to be overridden.
 */
public class AbstractWrapperedDataReaderEngine implements DataReaderEngine {

    /**
     * The Data reader engine.
     */
    protected DataReaderEngine dataReaderEngine;

    /**
     * Instantiates a new Abstract wrappered data reader engine.
     *
     * @param dataReaderEngine the data reader engine
     */
    public AbstractWrapperedDataReaderEngine(DataReaderEngine dataReaderEngine) {
        this.dataReaderEngine = dataReaderEngine;
        if (dataReaderEngine == null) {
            throw new IllegalArgumentException("Can not create AbstractWrapperedDataReaderEngine: DataReaderEngine can not be null");
        }
    }

    @Override
    public HeaderBean getHeader() {
        return dataReaderEngine.getHeader();
    }

    @Override
    public DataReaderEngine createCopy() {
        return dataReaderEngine.createCopy();
    }

    @Override
    public DataStructureBean getDataStructure() {
        return dataReaderEngine.getDataStructure();
    }

    @Override
    public DataflowBean getDataFlow() {
        return dataReaderEngine.getDataFlow();
    }

    @Override
    public ProvisionAgreementBean getProvisionAgreement() {
        return dataReaderEngine.getProvisionAgreement();
    }

    @Override
    public int getDatasetPosition() {
        return dataReaderEngine.getDatasetPosition();
    }

    @Override
    public int getKeyablePosition() {
        return dataReaderEngine.getKeyablePosition();
    }

    @Override
    public int getSeriesPosition() {
        return dataReaderEngine.getSeriesPosition();
    }

    @Override
    public int getObsPosition() {
        return dataReaderEngine.getObsPosition();
    }

    @Override
    public DatasetHeaderBean getCurrentDatasetHeaderBean() {
        return dataReaderEngine.getCurrentDatasetHeaderBean();
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return dataReaderEngine.getDatasetAttributes();
    }

    @Override
    public Keyable getCurrentKey() {
        return dataReaderEngine.getCurrentKey();
    }

    @Override
    public Observation getCurrentObservation() {
        return dataReaderEngine.getCurrentObservation();
    }

    @Override
    public boolean moveNextObservation() {
        return dataReaderEngine.moveNextObservation();
    }

    @Override
    public boolean moveNextKeyable() {
        return dataReaderEngine.moveNextKeyable();
    }

    @Override
    public boolean moveNextDataset() {
        return dataReaderEngine.moveNextDataset();
    }

    @Override
    public void reset() {
        dataReaderEngine.reset();
    }

    @Override
    public void copyToOutputStream(OutputStream outputStream) {
        dataReaderEngine.copyToOutputStream(outputStream);
    }

    @Override
    public void close() {
        dataReaderEngine.close();
    }
}
