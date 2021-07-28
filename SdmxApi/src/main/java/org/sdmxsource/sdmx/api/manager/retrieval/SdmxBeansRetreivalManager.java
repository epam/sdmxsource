package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

/**
 * Manages the retrieval of a SdmxBeans container, returning all the known SdmxBeans that the manager is capable of getting
 */
public interface SdmxBeansRetreivalManager {

    /**
     * Returns a copy of the SdmxBeans from this manager.  Any additions or removals using the API of the returned
     * SdmxBeans will not be reflected in the beans returned from this manager.
     *
     * @return sdmx beans
     */
    SdmxBeans getSdmxBeans();

}
