package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;

public interface MetadataAttributeSuperBean extends ComponentSuperBean, MetadataAttributeContainer {

    Integer getMinOccurs();

    Integer getMaxOccurs();

    TERTIARY_BOOL getPresentational();


}
