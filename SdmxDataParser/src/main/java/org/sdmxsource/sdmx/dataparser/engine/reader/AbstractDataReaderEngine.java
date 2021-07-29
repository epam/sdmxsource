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

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DATASET_POSITION;
import org.sdmxsource.sdmx.api.constants.DIMENSION_AT_OBSERVATION;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.DataSetStructureReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.util.io.StreamUtil;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;


/**
 * The type Abstract data reader engine.
 */
public abstract class AbstractDataReaderEngine implements DataReaderEngine, Serializable {
    private static final long serialVersionUID = 3424676029992787805L;

    /**
     * The Data location.
     */
    protected ReadableDataLocation dataLocation;
    /**
     * The Has next.
     */
    protected boolean hasNext = true;    //End of File
    /**
     * The Has next obs.
     */
    protected boolean hasNextObs = true; //End of Obs for current Key
    /**
     * The Has next dataset.
     */
    protected boolean hasNextDataset = true; //End of Dataset
    /**
     * The Dataset header bean.
     */
    protected DatasetHeaderBean datasetHeaderBean;
    /**
     * The Current dsd.
     */
    protected DataStructureBean currentDsd;
    /**
     * The Current dataflow.
     */
    protected DataflowBean currentDataflow;
    /**
     * The Current provision agreement.
     */
    protected ProvisionAgreementBean currentProvisionAgreement;
    /**
     * The Default provision agreement.
     */
    protected ProvisionAgreementBean defaultProvisionAgreement;
    /**
     * The Default dataflow.
     */
    protected DataflowBean defaultDataflow;
    /**
     * The Default dsd.
     */
    protected DataStructureBean defaultDsd;
    /**
     * The Bean retrieval.
     */
    protected SdmxBeanRetrievalManager beanRetrieval;
    /**
     * The Current obs.
     */
    protected Observation currentObs;
    /**
     * The Current key.
     */
    protected Keyable currentKey;
    /**
     * The Header bean.
     */
    protected HeaderBean headerBean;
    /**
     * The Dataset position.
     */
    protected DATASET_POSITION datasetPosition;
    private int datasetIndex = -1;
    private int keyableIndex = -1;
    private int seriesIndex = -1;
    private int obsIndex = -1;


    /**
     * Creates a reader engine based on the data location, the location of available data structures that can be used to retrieve dsds, and the default dsd to use
     *
     * @param dataLocation       the location of the data
     * @param beanRetrieval      giving the ability to retrieve dsds for the datasets this reader engine is reading.  This can be null if there is only one relevant dsd - in which case the default dsd should be provided
     * @param defaultDsd         the default dsd to use if the beanRetrieval is null, or if the bean retrieval does not return the dsd for the given dataset
     * @param dataflowBean       the dataflow bean
     * @param provisionAgreement the provision agreement
     */
    public AbstractDataReaderEngine(ReadableDataLocation dataLocation,
                                    SdmxBeanRetrievalManager beanRetrieval,
                                    DataStructureBean defaultDsd,
                                    DataflowBean dataflowBean,
                                    ProvisionAgreementBean provisionAgreement) {
        this.beanRetrieval = beanRetrieval;
        this.dataLocation = dataLocation;
        this.defaultDsd = defaultDsd;
        this.defaultDataflow = dataflowBean;
        this.defaultProvisionAgreement = provisionAgreement;
        if (defaultDsd == null && beanRetrieval == null) {
            throw new IllegalArgumentException("DataReaderEngine expects a DataStructureBean or SdmxBeanRetrievalManager to be able to interpret the dataset");
        }
        if (defaultDataflow != null && !defaultDataflow.getDataStructureRef().equals(defaultDsd.asReference())) {
            throw new IllegalArgumentException("Can not crete DataReaderEngine Default Dataflow '" + defaultDataflow + "' does not reference Default Data Structure '" + defaultDsd + "' ");
        }
        if (defaultProvisionAgreement != null && defaultDataflow != null && !defaultProvisionAgreement.getStructureUseage().equals(defaultDataflow.asReference())) {
            throw new IllegalArgumentException("Can not crete DataReaderEngine Default Provision '" + defaultProvisionAgreement + "' does not reference Default Data Flow '" + defaultDataflow + "' ");
        }
    }

    @Override
    public void copyToOutputStream(OutputStream outputStream) {
        StreamUtil.copyStream(dataLocation.getInputStream(), outputStream);
    }

    @Override
    public HeaderBean getHeader() {
        return headerBean;
    }

    @Override
    public int getDatasetPosition() {
        return datasetIndex;
    }

    @Override
    public DatasetHeaderBean getCurrentDatasetHeaderBean() {
        return datasetHeaderBean;
    }

    @Override
    public DataflowBean getDataFlow() {
        return currentDataflow;
    }

    @Override
    public ProvisionAgreementBean getProvisionAgreement() {
        return currentProvisionAgreement;
    }

    @Override
    public boolean moveNextDataset() {
        currentKey = null;
        hasNextObs = true;
        obsIndex = -1;
        keyableIndex = -1;
        seriesIndex = -1;

        boolean moveSuccessful = moveNextDatasetInternal();
        if (moveSuccessful) {
            datasetIndex++;
            determineCurrentDataStructure();
        }
        return moveSuccessful;
    }

    @Override
    public DataStructureBean getDataStructure() {
        return currentDsd;
    }


    /**
     * Sets the current provision, which in turn sets the current dataflow & dsd
     *
     * @param currentDataflow
     */
    private void setCurrentProvision(ProvisionAgreementBean provisionAgreementBean) {
        this.currentProvisionAgreement = provisionAgreementBean;

        //If the current dataset header does not reference a Provsion then amend it
        if (getCurrentDatasetHeaderBean() == null) {
            datasetHeaderBean = new DatasetHeaderBeanImpl(UUID.randomUUID().toString(), DATASET_ACTION.INFORMATION, new DatasetStructureReferenceBeanImpl(currentProvisionAgreement.asReference()));
        } else if (getCurrentDatasetHeaderBean().getDataStructureReference() == null) {
            DatasetStructureReferenceBean dsRefBean = new DatasetStructureReferenceBeanImpl(currentProvisionAgreement.asReference());
            datasetHeaderBean = getCurrentDatasetHeaderBean().modifyDataStructureReference(dsRefBean);
        }
        if (currentDataflow == null) {
            if (defaultDataflow != null) {
                setCurrentDataflow(defaultDataflow);
            } else if (beanRetrieval != null) {
                setCurrentDataflow(beanRetrieval.getMaintainableBean(DataflowBean.class, currentProvisionAgreement.getStructureUseage()));
            }
        }
    }


    /**
     * Sets the current dataflow, which in turn sets the current data structure
     *
     * @param currentDataflow
     */
    private void setCurrentDataflow(DataflowBean currentDataflow) {
        //Do not set the current flow if it does not referecne the current DSD
        if (getDataStructure() != null && !currentDataflow.getDataStructureRef().equals(getDataStructure())) {
            return;
        }

        this.currentDataflow = currentDataflow;
        //If the current dataset header does not reference a Dataflow then amend it
        if (getCurrentDatasetHeaderBean() == null) {
            datasetHeaderBean = new DatasetHeaderBeanImpl(UUID.randomUUID().toString(), DATASET_ACTION.INFORMATION, new DatasetStructureReferenceBeanImpl(currentDataflow.asReference()));
        } else if (getCurrentDatasetHeaderBean().getDataStructureReference() == null) {
            DatasetStructureReferenceBean dsRefBean = new DatasetStructureReferenceBeanImpl(currentDataflow.asReference());
            datasetHeaderBean = getCurrentDatasetHeaderBean().modifyDataStructureReference(dsRefBean);
        }
        if (getDataStructure() == null) {
            if (defaultDsd != null && defaultDsd.getUrn().equals(currentDataflow.getDataStructureRef().getTargetUrn())) {
                setCurrentDsd(defaultDsd);
            } else if (beanRetrieval != null) {
                setCurrentDsd(beanRetrieval.getMaintainableBean(DataStructureBean.class, currentDataflow.getDataStructureRef()));
            }
        }
    }

    /**
     * Sets current dsd.
     *
     * @param currentDsd the current dsd
     */
    protected void setCurrentDsd(DataStructureBean currentDsd) {
        this.currentDsd = currentDsd;

        //If the current dataset header does not reference a DSD then amend it
        if (getCurrentDatasetHeaderBean().getDataStructureReference() == null) {
            DatasetStructureReferenceBean dsRefBean = new DatasetStructureReferenceBeanImpl(currentDsd.asReference());
            datasetHeaderBean = getCurrentDatasetHeaderBean().modifyDataStructureReference(dsRefBean);
        }
    }

    private void setCurrentStructure(StructureReferenceBean sRef) {
        if (beanRetrieval == null) {
            return;
        }
        MaintainableBean maint = beanRetrieval.getMaintainableBean(sRef.getMaintainableStructureType().getMaintainableInterface(), sRef);
        if (maint == null) {
            return;
        }
        switch (maint.getStructureType()) {
            case DSD:
                setCurrentDsd((DataStructureBean) maint);
                break;
            case DATAFLOW:
                setCurrentDataflow((DataflowBean) maint);
                break;
            case PROVISION_AGREEMENT:
                setCurrentProvision((ProvisionAgreementBean) maint);
                break;
        }
    }


    /**
     * Sets the currentDsd variable based on the data header for the current dataset that is being read
     */
    private void determineCurrentDataStructure() {
        //1. Set the current DSD to null before trying to resolve it
        currentDsd = null;
        currentDataflow = null;
        currentProvisionAgreement = null;

        DatasetStructureReferenceBean dsStructRef = null;
        if (getCurrentDatasetHeaderBean() != null) {
            dsStructRef = getCurrentDatasetHeaderBean().getDataStructureReference();
            if (dsStructRef == null && getCurrentDatasetHeaderBean() != null && getHeader().getStructures().size() == 1) {
                dsStructRef = getHeader().getStructures().get(0);
            }
        }
        StructureReferenceBean sRef = null;
        if (dsStructRef != null) {
            sRef = dsStructRef.getStructureReference();
            setCurrentStructure(sRef);

            if (getDataStructure() == null) {
                MaintainableBean toCheck = null;
                switch (sRef.getTargetReference()) {
                    case DSD:
                        toCheck = defaultDsd;
                        break;
                    case DATAFLOW:
                        toCheck = defaultDataflow;
                        break;
                    case PROVISION_AGREEMENT:
                        toCheck = defaultProvisionAgreement;
                        break;
                    default:
                        throw new SdmxNotImplementedException("Can not read dataset which references a structure of type: " + sRef.getTargetReference());
                }
                if (toCheck != null && !sRef.isMatch(toCheck)) {
                    throw new SdmxSemmanticException("Can not Read Data File with " + toCheck.getStructureType().getType() + " '" + toCheck.getUrn().split("=")[1] + "'.  This does not match the following structure specified in the dataset header: '" + sRef + "'");
                }
            }

        }
        /**
         * Apply and default structure
         */
        applyDefaults();
        if (getDataStructure() == null) {
            throw new DataSetStructureReferenceException(sRef);
        }
    }

    private void applyDefaults() {
        if (currentProvisionAgreement == null && defaultProvisionAgreement != null) {
            setCurrentProvision(defaultProvisionAgreement);
        } else if (currentDataflow == null && defaultDataflow != null) {
            setCurrentDataflow(defaultDataflow);
        } else if (getDataStructure() == null && defaultDsd != null) {
            setCurrentDsd(defaultDsd);
        }
    }

    @Override
    public final boolean moveNextKeyable() {
        //If the dataset has not been read, then read it!
        if (datasetIndex == -1) {
            moveNextDataset();
        }
        currentKey = null; //Set the current key to null, this is so when the user reads the observation it can generate it on demand

        //If there is no more information left at all, then we return false
        if (!hasNext) {
            return false;
        }
        hasNextObs = true;
        obsIndex = -1;
        keyableIndex++;

        boolean bool = moveNextKeyableInternal();
        if (bool == true && getCurrentKey() != null && currentKey.isSeries()) {
            seriesIndex++;
        }
        return bool;
    }

    @Override
    public final boolean moveNextObservation() {
        currentObs = null;  //Set the current observation to null, this is so when the user reads the observation it can generate it on demand

        //If we are at the end of the file, or the observations for the key, then return false, there is no point in processing anything
        if (!hasNext && !hasNextObs) {
            return false;
        }
        obsIndex++;

        if (currentKey == null) {
            getCurrentKey();
        }
        hasNextObs = moveNextObservationInternal();
        return hasNextObs;
    }

    @Override
    public final Observation getCurrentObservation() {
        if (currentObs != null) {
            return currentObs;
        }
        if (obsIndex < 0) {
            return null;
        }
        currentObs = lazyLoadObservation();
        return currentObs;
    }

    @Override
    public final Keyable getCurrentKey() {
        if (currentKey != null) {
            return currentKey;
        }
        if (keyableIndex < 0) {
            return null;
        }
        currentKey = lazyLoadKey();
        return currentKey;
    }


    /**
     * Lazy load observation observation.
     *
     * @return the observation
     */
    abstract protected Observation lazyLoadObservation();

    /**
     * Lazy load key keyable.
     *
     * @return the keyable
     */
    abstract protected Keyable lazyLoadKey();

    /**
     * Moves the reader to the next dataset, sets the datasetHeaderBean based on the dataset attributes
     *
     * @return true if moves the reader to the next dataset
     */
    abstract protected boolean moveNextDatasetInternal();

    /**
     * Move next keyable internal boolean.
     *
     * @return true if moves next keyable internal boolean.
     */
    abstract protected boolean moveNextKeyableInternal();

    /**
     * Move next observation internal boolean.
     *
     * @return true if moves next observation internal boolean.
     */
    abstract protected boolean moveNextObservationInternal();

    @Override
    public int getKeyablePosition() {
        return keyableIndex;
    }

    @Override
    public int getSeriesPosition() {
        return seriesIndex;
    }

    @Override
    public int getObsPosition() {
        return obsIndex;
    }

    /**
     * Is time series boolean.
     *
     * @return true if it is time series boolean.
     */
    public boolean isTimeSeries() {
        return getCrossSectionConcept().equals(DIMENSION_AT_OBSERVATION.TIME.getVal()) || getCrossSectionConcept().equals(DIMENSION_AT_OBSERVATION.ALL.getVal());
    }

    /**
     * Gets cross section concept.
     *
     * @return the cross section concept
     */
    public String getCrossSectionConcept() {
        if (getCurrentDatasetHeaderBean() == null) {
            return DimensionBean.TIME_DIMENSION_FIXED_ID;
        }
        return getCurrentDatasetHeaderBean().getDataStructureReference().getDimensionAtObservation();
    }

    @Override
    public void reset() {
        hasNext = true;
        keyableIndex = -1;
        datasetIndex = -1;
        obsIndex = -1;
        currentObs = null;
        currentKey = null;
        datasetPosition = null;
    }
}
