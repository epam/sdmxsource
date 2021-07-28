package org.sdmxsource.sdmx.dataparser.manager.impl;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.ErrorLimitException;
import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.validation.DataValidationManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DataValidatorFactory;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Delegates the validation of Keys and Observations to the registered factories.
 */
public class DataValidationManagerImpl implements DataValidationManager {

    private Set<DataValidatorFactory> validatorFactories;

    private SdmxSuperBeanRetrievalManager superRetrievalManager;

    /**
     * Add validator factory.
     *
     * @param fac the fac
     */
    public void addValidatorFactory(DataValidatorFactory fac) {
        if (validatorFactories == null) {
            validatorFactories = new HashSet<DataValidatorFactory>();
        }
        validatorFactories.add(fac);
    }

    public void validateData(DataReaderEngine dataReaderEngine, ExceptionHandler exceptionHandler) {
        if (dataReaderEngine == null) {
            throw new IllegalArgumentException("AbstractDataValidationEngine can not be constructucted: DataReaderEngine can not be null");
        }
        dataReaderEngine.reset();

        try {
            while (dataReaderEngine.moveNextDataset()) {
                // Construct a DataSetInformation
                DataStructureBean dataStructure = dataReaderEngine.getDataStructure();

                DataflowBean dataFlow = dataReaderEngine.getDataFlow();
                ProvisionAgreementBean provisionAgreement = dataReaderEngine.getProvisionAgreement();
                DatasetHeaderBean currentDatasetHeaderBean = dataReaderEngine.getCurrentDatasetHeaderBean();

                DatasetInformation dsi = new DatasetInformation(currentDatasetHeaderBean, dataStructure, dataFlow, provisionAgreement, superRetrievalManager);

                // Create new DataValidationEngines and supply each with the dsi object
                Set<DataValidationEngine> allValidators = new HashSet<DataValidationEngine>();
                for (DataValidatorFactory aFac : validatorFactories) {
                    // DataValidators may be null
                    DataValidationEngine dataValidationEngine = aFac.createInstance(dsi);
                    if (dataValidationEngine != null) {
                        allValidators.add(dataValidationEngine);
                    }
                }
                for (DataValidationEngine validator : allValidators) {
                    List<KeyValue> dsAttributes = dataReaderEngine.getDatasetAttributes();
                    try {
                        validator.validateDataSetAttributes(dsAttributes);
                    } catch (SdmxException e) {
                        exceptionHandler.handleException(e);
                    }
                }

                // Analyse the data file asking each DataValidationEngine to perform validation
                while (moveNextKeyable(dataReaderEngine, exceptionHandler)) {
                    Keyable key = null;
                    try {
                        key = dataReaderEngine.getCurrentKey();
                    } catch (SdmxException e) {
                        exceptionHandler.handleException(e);
                    }

                    if (key != null) {
                        for (DataValidationEngine validator : allValidators) {
                            try {
                                validator.validateKey(key);
                            } catch (Throwable th) {
                                if (key.isSeries()) {
                                    exceptionHandler.handleException(new SdmxSemmanticException(th, "Error in series: " + key.getShortCode()));
                                } else {
                                    exceptionHandler.handleException(new SdmxSemmanticException(th, "Error in group: " + key.getShortCode()));
                                }
                            }
                        }

                        while (moveNextObservation(dataReaderEngine, exceptionHandler)) {
                            Observation obs = dataReaderEngine.getCurrentObservation();
                            for (DataValidationEngine validator : allValidators) {
                                try {
                                    validator.validateObs(obs);
                                } catch (Throwable th) {
                                    exceptionHandler.handleException(new SdmxSemmanticException(th, "Error in observation '" + key.getShortCode() + ":" + obs.getObsTime() + "' "));
                                }
                            }
                        }

                        for (DataValidationEngine validator : allValidators) {
                            try {
                                validator.finishedObs(exceptionHandler);
                            } catch (Throwable th) {
                                // If this end-validation failed, simply return. The calling code will catch the ExceptionHandler for errors.
                                // If we were to throw this Throwable OR if we were to not perform a return here, the lower catch block
                                // would catch the exceptions and duplicate the errors.
                                return;
                            }
                        }
                    }
                }
            }
        } catch (ErrorLimitException ex) {
            throw ex;
        } catch (Throwable th) {
            exceptionHandler.handleException(th);
        }
    }

    private boolean moveNextKeyable(DataReaderEngine dre, ExceptionHandler exceptionHandler) {
        boolean hasNext = false;
        try {
            hasNext = dre.moveNextKeyable();
        } catch (SdmxException e) {
            exceptionHandler.handleException(e);
        }
        return hasNext;
    }

    private boolean moveNextObservation(DataReaderEngine dre, ExceptionHandler exceptionHandler) {
        boolean hasNext = false;
        try {
            hasNext = dre.moveNextObservation();
        } catch (SdmxException e) {
            exceptionHandler.handleException(e);
        }
        return hasNext;
    }

    /**
     * Sets validator factories.
     *
     * @param validatorFactories the validator factories
     */
    public void setValidatorFactories(Set<DataValidatorFactory> validatorFactories) {
        this.validatorFactories = validatorFactories;
    }

    /**
     * Sets super bean retrieval manager.
     *
     * @param superBeanRetrievalManager the super bean retrieval manager
     */
    public void setSuperBeanRetrievalManager(SdmxSuperBeanRetrievalManager superBeanRetrievalManager) {
        this.superRetrievalManager = superBeanRetrievalManager;
    }
}
