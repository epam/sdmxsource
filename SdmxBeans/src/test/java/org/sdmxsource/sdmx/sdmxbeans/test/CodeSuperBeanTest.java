package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.CodeMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.CodelistMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodeSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;

public class CodeSuperBeanTest {

    private CodelistBean codelistBean;
    private CodeBean code;

    @BeforeEach
    private void setup() {
        codelistBean = buildCodelist(10).getImmutableInstance();
        code = codelistBean.getItems().get(0);
    }


    @Test
    public void shouldCheckCodeMutableSuperBean() {
        CodeSuperBean codeSuperBean = new CodeSuperBeanImpl(null, code, new HashMap<>(), null);
        var sut = new CodeMutableSuperBeanImpl(new CodelistMutableSuperBeanImpl(new CodelistSuperBeanImpl(codelistBean)),
                codeSuperBean, null);

        assertEquals(code.getId(), sut.getId());
    }

    @Test
    public void shouldCheckCodeSuperBean() {
        var sut = new CodeSuperBeanImpl(null, code, new HashMap<>(), null);

        assertEquals(code.getId(), sut.getId());
    }
}
