package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.IdentifiableSuperBean;

import java.util.List;

public interface ReportStructureSuperBean extends IdentifiableSuperBean, MetadataAttributeContainer {

    List<MetadataTargetBean> getMetadataTargets();

}
