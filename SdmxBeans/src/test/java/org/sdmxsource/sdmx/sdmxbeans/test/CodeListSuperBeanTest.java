package org.sdmxsource.sdmx.sdmxbeans.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.CodelistMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;

public class CodeListSuperBeanTest {

    private CodelistBean codelistBean;
    private CodelistSuperBean codelistSuperBean;

    @BeforeEach
    public void setup() {
        codelistBean = buildCodelist(5_000).getImmutableInstance();
        codelistSuperBean = new CodelistSuperBeanImpl(codelistBean);
    }

    @Test
    public void shouldCheckGetCodeById() {
        assertNotNull(codelistSuperBean.getCodeByValue(codelistBean.getItems().get(0).getId()));
    }

    @Test
    public void shouldCheckExceptionForInvalidCodeId() {
        assertNull(codelistSuperBean.getCodeByValue("DOES_NOT_EXIST"));
    }

    @Test
    public void shouldCheckNumberOfCodes() {
        List<CodeBean> codesWithoutParent = codelistBean.getItems().stream()
                .filter(code -> code.getParentCode() == null)
                .toList();

        assertEquals(codesWithoutParent.size(), codelistSuperBean.getCodes().size());
    }

    @Test
    public void shouldCheckCodeListMutableBean() {
        CodelistMutableSuperBean sut = new CodelistMutableSuperBeanImpl(codelistSuperBean);

        assertEquals(sut.getCodes().size(), codelistSuperBean.getCodes().size());
    }

}
