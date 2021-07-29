package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.util.sort.MaintainableSortByIdentifiers;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;

@ExtendWith(MockitoExtension.class)
public class MaintainableSortByIdentifiersTest {

    @Mock
    private CodelistBean codelistBean;
    @Mock
    private CodelistBean codelistBean2;

    @Test
    public void shouldCheckMaintainableSortByIdentifiersCompare() {
        var comp = new MaintainableSortByIdentifiers();
        mockCodelistBean(codelistBean);

        when(codelistBean2.getStructureType()).thenReturn(CODE_LIST);
        when(codelistBean2.getAgencyId()).thenReturn("TestAgency2");

        assertNotEquals(0, comp.compare(codelistBean, codelistBean2));

        when(codelistBean2.getAgencyId()).thenReturn("TestAgency");
        when(codelistBean2.getId()).thenReturn("Test4");

        assertNotEquals(0, comp.compare(codelistBean, codelistBean2));

        when(codelistBean2.getId()).thenReturn("Test");
        when(codelistBean2.getVersion()).thenReturn("2.0");

        assertNotEquals(0, comp.compare(codelistBean, codelistBean2));

        mockCodelistBean(codelistBean2);

        assertNotEquals(0, comp.compare(codelistBean, codelistBean2));
    }

    private void mockCodelistBean(CodelistBean codelistBean) {
        when(codelistBean.getStructureType()).thenReturn(CODE_LIST);
        when(codelistBean.getId()).thenReturn("Test");
        when(codelistBean.getAgencyId()).thenReturn("TestAgency");
        when(codelistBean.getVersion()).thenReturn("1.0");
    }


}
