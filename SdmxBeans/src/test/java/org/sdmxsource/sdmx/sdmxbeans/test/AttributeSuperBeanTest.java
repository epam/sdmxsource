package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure.AttributeMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.AttributeSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.*;

public class AttributeSuperBeanTest {

    private AttributeSuperBean attributeSuperBean;
    private AttributeBean attributeBean;

    @BeforeEach
    public void setup() {
        attributeBean = buildDsd().getAttributeList().getAttributes().get(0);
        CodelistSuperBean codelistSuperBean = new CodelistSuperBeanImpl(buildCodelist(10).getImmutableInstance());
        ConceptSuperBean conceptSuperBean = new ConceptSuperBeanImpl(buildConceptScheme().get(0).getItems().get(0), codelistSuperBean);

        attributeSuperBean = new AttributeSuperBeanImpl(attributeBean, codelistSuperBean, conceptSuperBean);
    }


    @Test
    public void shouldCheckAttributeMutableSuperBean() {
        var dimensionSuperObject = new AttributeMutableSuperBeanImpl(attributeSuperBean);

        assertEquals(attributeBean.getAssignmentStatus(), dimensionSuperObject.getAssignmentStatus());
        assertEquals(attributeBean.getAttachmentGroup(), dimensionSuperObject.getAttachmentGroup());
        assertEquals(attributeBean.isMandatory(), dimensionSuperObject.getMandatory());
    }

    @Test
    public void shouldCheckAttributeSuperBean() {
        assertEquals(attributeBean.getAssignmentStatus(), attributeSuperBean.getAssignmentStatus());
        assertEquals(attributeBean.getAttachmentGroup(), attributeSuperBean.getAttachmentGroup());
        assertEquals(attributeBean.getAttachmentLevel(), attributeSuperBean.getAttachmentLevel());
        assertEquals(attributeBean.getDimensionReferences(), attributeSuperBean.getDimensionReferences());
        assertEquals(attributeBean.isMandatory(), attributeSuperBean.isMandatory());
        assertEquals(attributeBean.getPrimaryMeasureReference(), attributeSuperBean.getPrimaryMeasureReference());
    }
}
