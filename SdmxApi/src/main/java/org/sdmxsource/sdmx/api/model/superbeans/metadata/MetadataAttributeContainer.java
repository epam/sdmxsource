package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import java.util.List;

/**
 * The interface Metadata attribute container.
 */
public interface MetadataAttributeContainer {

    /**
     * Returns any metadata attributes
     * <p>
     * <b>NOTE</b>The list is a copy so modifying the returned list will not
     * be reflected in the MetadataAttributeBean instance
     *
     * @return metadata attributes
     */
    List<MetadataAttributeSuperBean> getMetadataAttributes();

    /**
     * Returns the metadata attribute with the given id, or null if none exist
     *
     * @param id the id
     * @return metadata attribute by id
     */
    MetadataAttributeSuperBean getMetadataAttributeById(String id);

}
