package org.sdmxsource.sdmx.dataparser.factory;

import org.sdmxsource.sdmx.api.manager.retrieval.ConstraintRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.ConstrainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.ConstraintValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.ContentConstraintModel;
import org.sdmxsource.sdmx.dataparser.model.DataValidatorFactory;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;

/**
 * The type Constraint validator factory.
 */
public class ConstraintValidatorFactory implements DataValidatorFactory {

    private ConstraintRetrievalManager constraintRetrievalManager;
    private SdmxBeanRetrievalManager beanRetrievalManager;

    @Override
    public DataValidationEngine createInstance(DatasetInformation dsi) {
        StructureReferenceBean sRef;
        if (dsi.getProvisionAgreement() != null) {
            sRef = dsi.getProvisionAgreement().asReference();
        } else if (dsi.getDataflow() != null) {
            sRef = dsi.getDataflow().asReference();
        } else {
            sRef = dsi.getDsd().asReference();
        }

        MaintainableBean maint = beanRetrievalManager.getMaintainableBean(sRef);
        ContentConstraintBean constraintBean = null;
        if (maint instanceof ConstrainableBean) {
            constraintBean = constraintRetrievalManager.getConstraintDefiningAllowedData((ConstrainableBean) maint);
        }

        ContentConstraintModel constraintModel;
        if (constraintBean != null) {
            constraintModel = new ContentConstraintModel(constraintBean, dsi.getDsd());
            return new ConstraintValidationEngine(constraintModel);
        }

        // If there are no constraints simply return a null instead of a DataValidationEngine
        return null;
    }

    /**
     * Sets constraint retrieval manager.
     *
     * @param constraintRetrievalManager the constraint retrieval manager
     */
    public void setConstraintRetrievalManager(ConstraintRetrievalManager constraintRetrievalManager) {
        this.constraintRetrievalManager = constraintRetrievalManager;
    }

    /**
     * Sets bean retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public void setBeanRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }
}
