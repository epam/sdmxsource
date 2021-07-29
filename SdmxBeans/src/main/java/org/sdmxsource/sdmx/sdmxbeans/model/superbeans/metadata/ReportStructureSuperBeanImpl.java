package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataAttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.ReportStructureSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.IdentifiableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Report structure super bean.
 */
public class ReportStructureSuperBeanImpl extends IdentifiableSuperBeanImpl implements ReportStructureSuperBean {
    private static final long serialVersionUID = 1L;
    private List<MetadataTargetBean> targets = new ArrayList<MetadataTargetBean>();
    private List<MetadataAttributeSuperBean> metadataAttributes = new ArrayList<MetadataAttributeSuperBean>();

    /**
     * Instantiates a new Report structure super bean.
     *
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @param reportStructure  the report structure
     */
    public ReportStructureSuperBeanImpl(
            SdmxBeanRetrievalManager retrievalManager,
            SuperBeans existingBeans,
            ReportStructureBean reportStructure) {
        super(reportStructure);
        for (MetadataAttributeBean currentMa : reportStructure.getMetadataAttributes()) {
            this.metadataAttributes.add(new MetadataAttributeSuperBeanImpl(retrievalManager, existingBeans, currentMa));
        }
        MetadataStructureDefinitionBean msdBean = reportStructure.getParent(MetadataStructureDefinitionBean.class, false);
        for (String metadataTarget : reportStructure.getTargetMetadatas()) {
            for (MetadataTargetBean mdTaget : msdBean.getMetadataTargets()) {
                if (mdTaget.getId().equals(metadataTarget)) {
                    this.targets.add(mdTaget);
                    break;
                }
            }
        }
    }

    @Override
    public MetadataAttributeSuperBean getMetadataAttributeById(String id) {
        for (MetadataAttributeSuperBean currentMa : metadataAttributes) {
            if (currentMa.getId().equals(id)) {
                return currentMa;
            }
        }
        return null;
    }

    @Override
    public List<MetadataAttributeSuperBean> getMetadataAttributes() {
        return new ArrayList<MetadataAttributeSuperBean>(metadataAttributes);
    }

    @Override
    public List<MetadataTargetBean> getMetadataTargets() {
        return new ArrayList<MetadataTargetBean>(targets);
    }
}
