package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.ReportStructureSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Metadata structure super bean.
 */
public class MetadataStructureSuperBeanImpl extends MaintainableSuperBeanImpl implements MetadataStructureSuperBean {
    private static final long serialVersionUID = 9003173217074741857L;

    private List<ReportStructureSuperBean> reportStructures = new ArrayList<ReportStructureSuperBean>();

    /**
     * Instantiates a new Metadata structure super bean.
     *
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @param msd              the msd
     */
    public MetadataStructureSuperBeanImpl(SdmxBeanRetrievalManager retrievalManager,
                                          SuperBeans existingBeans,
                                          MetadataStructureDefinitionBean msd) {
        super(msd);
        for (ReportStructureBean rsBean : msd.getReportStructures()) {
            this.reportStructures.add(new ReportStructureSuperBeanImpl(retrievalManager, existingBeans, rsBean));
        }
    }

    public MetadataStructureDefinitionBean getBuiltFrom() {
        return (MetadataStructureDefinitionBean) super.getBuiltFrom();
    }

    @Override
    public List<MetadataTargetBean> getMetadataTargets() {
        return getBuiltFrom().getMetadataTargets();
    }

    @Override
    public ReportStructureSuperBean getReportStructure(String reportId) {
        for (ReportStructureSuperBean rs : reportStructures) {
            if (rs.getId().equals(reportId)) {
                return rs;
            }
        }
        return null;
    }

    @Override
    public List<ReportStructureSuperBean> getReportStructures() {
        return new ArrayList<ReportStructureSuperBean>(reportStructures);
    }
}
