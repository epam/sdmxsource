package org.sdmxsource.sdmx.api.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;

import java.util.List;

/**
 * The interface Metadata structure super bean.
 */
public interface MetadataStructureSuperBean extends MaintainableSuperBean {

    @Override
    MetadataStructureDefinitionBean getBuiltFrom();


    /**
     * Gets metadata targets.
     *
     * @return the metadata targets
     */
    List<MetadataTargetBean> getMetadataTargets();


    /**
     * Gets report structures.
     *
     * @return the report structures
     */
    List<ReportStructureSuperBean> getReportStructures();

    /**
     * Returns the report structure for the given id, or null if none exist
     *
     * @param reportId the report id
     * @return report structure
     */
    ReportStructureSuperBean getReportStructure(String reportId);

}
