package org.sdmxsource.sdmx.dataparser.factory;

import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.DeepDataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DataValidatorFactory;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;

/**
 * The type Deep validator factory.
 */
public class DeepValidatorFactory implements DataValidatorFactory {

    @Override
    public DataValidationEngine createInstance(DatasetInformation dsi) {
        return new DeepDataValidationEngine(dsi);
    }
}
