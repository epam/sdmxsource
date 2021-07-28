package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataAttributeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.ComponentSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Metadata attribute super bean.
 */
public class MetadataAttributeSuperBeanImpl extends ComponentSuperBeanImpl implements MetadataAttributeSuperBean {
    private static final long serialVersionUID = 1L;
    private MetadataAttributeBean maBean;
    private List<MetadataAttributeSuperBean> metadataAttributes = new ArrayList<MetadataAttributeSuperBean>();

    /**
     * Instantiates a new Metadata attribute super bean.
     *
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @param maBean           the ma bean
     */
    public MetadataAttributeSuperBeanImpl(SdmxBeanRetrievalManager retrievalManager,
                                          SuperBeans existingBeans,
                                          MetadataAttributeBean maBean) {
        super(maBean, retrievalManager, existingBeans);
        this.maBean = maBean;
        for (MetadataAttributeBean currentChildMa : this.maBean.getMetadataAttributes()) {
            this.metadataAttributes.add(new MetadataAttributeSuperBeanImpl(retrievalManager, existingBeans, currentChildMa));
        }
    }

    @Override
    public Integer getMinOccurs() {
        return maBean.getMinOccurs();
    }

    @Override
    public Integer getMaxOccurs() {
        return maBean.getMaxOccurs();
    }

    @Override
    public TERTIARY_BOOL getPresentational() {
        return maBean.getPresentational();
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
        return metadataAttributes;
    }
}
