package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;

/**
 * The interface Data validator factory.
 */
public interface DataValidatorFactory {

    /**
     * Creates a new instance of a DataValidationEngine, however it may return null
     * (see the ConstraintValidatorEngine).
     *
     * @param dsi the dsi
     * @return data validation engine
     */
    public DataValidationEngine createInstance(DatasetInformation dsi);
}
