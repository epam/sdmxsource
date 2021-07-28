package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.CodelistMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;

public class CodeListSuperBeanTest {

    private CodelistBean codelistBean;
    private CodelistSuperBean codelistSuperBean;

    @BeforeEach
    public void setup() {
        codelistBean = buildCodelist(10).getImmutableInstance();
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
                .collect(toList());

        assertEquals(codesWithoutParent.size(), codelistSuperBean.getCodes().size());
    }

    @Test
    public void shouldCheckCodeListMutableBean() {
        CodelistMutableSuperBean sut = new CodelistMutableSuperBeanImpl(codelistSuperBean);

        assertEquals(sut.getCodes().size(), codelistSuperBean.getCodes().size());
    }

}
