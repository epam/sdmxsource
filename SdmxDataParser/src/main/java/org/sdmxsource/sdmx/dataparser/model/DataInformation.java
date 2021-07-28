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
package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Data information.
 */
public class DataInformation implements Serializable {
    private static final long serialVersionUID = 4279234605066137963L;

    private int datasets = 0;

    private int[] groups = new int[0];
    private int[] keys = new int[0];
    private int[] observations = new int[0];

    //Array mapping to dataset index
    private DatasetHeaderBean[] headers;
    private ProvisionAgreementBean[] provision;
    private DataflowBean[] flow;
    private DataStructureBean[] dsd;

    /**
     * Instantiates a new Data information.
     *
     * @param dre the dre
     */
    public DataInformation(DataReaderEngine dre) {
        if (dre == null) {
            throw new IllegalArgumentException("No DataReaderEngine specified.");
        }

        dre.reset();
        Set<String> keySet = new HashSet<String>();
        Set<String> groupSet = new HashSet<String>();

        List<DatasetHeaderBean> headers = new ArrayList<DatasetHeaderBean>();
        List<ProvisionAgreementBean> provision = new ArrayList<ProvisionAgreementBean>();
        List<DataflowBean> flow = new ArrayList<DataflowBean>();
        List<DataStructureBean> dsd = new ArrayList<DataStructureBean>();


        while (dre.moveNextDataset()) {
            DatasetHeaderBean currentDatasetHeaderBean = dre.getCurrentDatasetHeaderBean();
            headers.add(currentDatasetHeaderBean);

            provision.add(dre.getProvisionAgreement());
            flow.add(dre.getDataFlow());
            dsd.add(dre.getDataStructure());

            int obsCount = 0;
            while (dre.moveNextKeyable()) {
                Keyable currentKey = dre.getCurrentKey();
                if (currentKey.isSeries()) {
                    keySet.add(currentKey.getShortCode());
                } else {
                    groupSet.add(currentKey.getShortCode());
                }
                while (dre.moveNextObservation()) {
                    obsCount++;
                }
            }
            groups = addToArray(groups, groupSet.size());
            keys = addToArray(keys, keySet.size());
            observations = addToArray(observations, obsCount);
            datasets++;
        }
        this.headers = new DatasetHeaderBean[headers.size()];
        headers.toArray(this.headers);

        this.provision = new ProvisionAgreementBean[provision.size()];
        provision.toArray(this.provision);

        this.flow = new DataflowBean[flow.size()];
        flow.toArray(this.flow);

        this.dsd = new DataStructureBean[dsd.size()];
        dsd.toArray(this.dsd);
    }

    private int[] addToArray(int[] arrIn, int i) {
        int[] tmp = new int[arrIn.length + 1];
        System.arraycopy(arrIn, 0, tmp, 0, arrIn.length);
        tmp[arrIn.length] = i;
        return tmp;
    }

    /**
     * Gets datasets.
     *
     * @return the datasets
     */
    public int getDatasets() {
        return datasets;
    }

    /**
     * Returns the number of series keys for the dataset at the given index
     *
     * @param idx the dataset index (zero indexed)
     * @return number of series keys
     */
    public int getNumberOfSeriesKeys(int idx) {
        return keys[idx];
    }

    /**
     * Returns the number of groups for the dataset at the given index
     *
     * @param idx the dataset index (zero indexed)
     * @return groups groups
     */
    public int getGroups(int idx) {
        return groups[idx];
    }

    /**
     * Returns the number of observations for the dataset at the given index
     *
     * @param idx the dataset index (zero indexed)
     * @return number of observations
     */
    public int getNumberOfObservations(int idx) {
        return observations[idx];
    }

    /**
     * Returns the  dataset header for the dataset at the given index
     *
     * @param idx the dataset index (zero indexed)
     * @return header header
     */
    public DatasetHeaderBean getHeader(int idx) {
        return headers[idx];
    }

    /**
     * Returns the provision agreement for the dataset at the given index.  This may be null if the dataset did not
     * reference a provision agreement.
     *
     * @param idx the dataset index (zero indexed)
     * @return provision provision
     */
    public ProvisionAgreementBean getProvision(int idx) {
        return provision[idx];
    }

    /**
     * Returns the dataflow for the dataset at the given index.  This may be null if the dataset did not
     * reference a dataflow.
     *
     * @param idx the dataset index (zero indexed)
     * @return flow flow
     */
    public DataflowBean getFlow(int idx) {
        return flow[idx];
    }

    /**
     * Returns the dsd for the dataset at the given index. This will not be null.
     *
     * @param idx the dataset index (zero indexed)
     * @return dsd dsd
     */
    public DataStructureBean getDsd(int idx) {
        return dsd[idx];
    }
}