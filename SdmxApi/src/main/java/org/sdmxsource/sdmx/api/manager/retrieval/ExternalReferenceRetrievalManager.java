package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.exception.SdmxNoResultsException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

/**
 * This interface is responsible for providing the capabilities of resolving externally referenced structures
 */
public interface ExternalReferenceRetrievalManager {

    /**
     * Resolve the full structure from the stub structure
     * <p>
     * If the external structure has isExternalReference().isTrue() == false, then it is not externally maintained and no
     * action will be taken, the same structure will be passed back.  Otherwise the external structure will be resolved using
     * the StructureURL or ServiceURL obtained from the structure
     *
     * @param externalStructure the external structure
     * @return maintainable bean
     * @throws SdmxNoResultsException if the maintainable could not be resolved from the given endpoint
     */
    MaintainableBean resolveFullStructure(MaintainableBean externalStructure) throws SdmxNoResultsException;

}
