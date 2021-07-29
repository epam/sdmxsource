package org.sdmxsource.sdmx.sdmxbeans.model.superbeans;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;

/**
 * The type Super beans util.
 */
public class SuperBeansUtil {

    /**
     * Build codelist codelist super bean.
     *
     * @param codelistRef      the codelist ref
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @return the codelist super bean
     */
    public static CodelistSuperBean buildCodelist(CrossReferenceBean codelistRef,
                                                  SdmxBeanRetrievalManager retrievalManager,
                                                  SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }

        if (codelistRef != null) {
            for (CodelistSuperBean codelistSb : existingBeans.getCodelists()) {
                if (codelistRef.getMaintainableUrn().equals(codelistSb.getUrn())) {
                    return codelistSb;
                }
            }

            CodelistBean codelistBean = retrievalManager.getIdentifiableBean(codelistRef, CodelistBean.class);

            if (codelistBean == null) {
                throw new CrossReferenceException(codelistRef);
            }
            CodelistSuperBean codelistSuperBean = new CodelistSuperBeanImpl(codelistBean);

            existingBeans.addCodelist(codelistSuperBean);
            return codelistSuperBean;
        }
        return null;
    }

    /**
     * Build concept concept super bean.
     *
     * @param conceptRef       the concept ref
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @return the concept super bean
     */
    public static ConceptSuperBean buildConcept(CrossReferenceBean conceptRef,
                                                SdmxBeanRetrievalManager retrievalManager,
                                                SuperBeans existingBeans) {

        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }

        String conceptId = conceptRef.getChildReference().getId();

        //Return an existing super bean if you have one
        for (ConceptSchemeSuperBean csSuperBean : existingBeans.getConceptSchemes()) {
            if (csSuperBean.getUrn().equals(conceptRef.getMaintainableUrn())) {
                for (ConceptSuperBean concept : csSuperBean.getConcepts()) {
                    if (concept.getId().equals(conceptId)) {
                        return concept;
                    }
                }
            }
        }
        //Could not find an existing one.
        ConceptBean conceptBean = retrievalManager.getIdentifiableBean(conceptRef, ConceptBean.class);
        if (conceptBean == null) {
            throw new CrossReferenceException(conceptRef);
        }
        return new ConceptSuperBeanImpl(conceptBean, retrievalManager, existingBeans);
    }
}
