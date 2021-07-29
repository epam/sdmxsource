package org.sdmxsource.sdmx.dataparser.factory;

import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DataValidatorFactory;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;

/**
 * The type Mandatory attributes validator factory.
 */
public class MandatoryAttributesValidatorFactory implements DataValidatorFactory {

    @Override
    public DataValidationEngine createInstance(DatasetInformation dsi) {
//		return new MandatoryAttributesValidationEngine(dsi);
        return null;
    }
}
