package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;

import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;

public class CodelistBeanTest {

    private static HashMap<String, Integer> buildDictionary(CodelistMutableBean codelistMutablebean) {
        var dictionary = new HashMap<String, Integer>();
        for (int i = 0; i < codelistMutablebean.getItems().size(); i++) {
            var codeMutableObject = codelistMutablebean.getItems().get(i);
            dictionary.put(codeMutableObject.getId(), i);
        }
        return dictionary;
    }

    @Test
    public void shouldCheckDuplicateCode() {
        CodelistMutableBean mutable = buildCodelist(10);
        var codeMutableObject = mutable.getItems().get(0);
        mutable.addItem(codeMutableObject);

        assertThrows(SdmxSemmanticException.class, () -> new CodelistBeanImpl(mutable));
    }

    @Test
    public void shouldCheckInvalidParent() {
        CodelistMutableBean mutable = buildCodelist(10);
        var firstCodeWithParent = getCodesWithParent(mutable).get(0);
        firstCodeWithParent.setParentCode("DOES_NOT_EXIST");

        assertThrows(SdmxSemmanticException.class, () -> new CodelistBeanImpl(mutable));
    }

    @Test
    public void shouldCheckInvalidParentingFirst() {
        CodelistMutableBean mutable = buildCodelist(10);
        var dictionary = buildDictionary(mutable);
        var firstCodeWithParent = getCodesWithParent(mutable).get(0);
        var parent = mutable.getItems().get(dictionary.getOrDefault(firstCodeWithParent.getParentCode(), 0));
        parent.setParentCode(firstCodeWithParent.getId());

        assertThrows(SdmxSemmanticException.class, () -> new CodelistBeanImpl(mutable));
    }

    @Test
    public void shouldCheckInvalidParentingLast() {
        CodelistMutableBean mutable = buildCodelist(10);
        var dictionary = buildDictionary(mutable);
        List<CodeMutableBean> codesWithParent = getCodesWithParent(mutable);
        var lastCodeWithParent = codesWithParent.get(codesWithParent.size() - 1);
        var parent = mutable.getItems().get(dictionary.getOrDefault(lastCodeWithParent.getParentCode(), 0));
        parent.setParentCode(lastCodeWithParent.getId());

        assertThrows(SdmxSemmanticException.class, () -> new CodelistBeanImpl(mutable));
    }

    private List<CodeMutableBean> getCodesWithParent(CodelistMutableBean codelistMutableBean) {
        return codelistMutableBean.getItems().stream()
                .filter(code -> code.getParentCode() != null)
                .collect(toList());
    }


}
