package org.sdmxsource.sdmx.dataparser.factory;

import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.SimpleDataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DataValidatorFactory;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;


/**
 * The type Simple validator factory.
 */
public class SimpleValidatorFactory implements DataValidatorFactory {

    @Override
    public DataValidationEngine createInstance(DatasetInformation dsi) {
        return new SimpleDataValidationEngine(dsi);
    }

}
