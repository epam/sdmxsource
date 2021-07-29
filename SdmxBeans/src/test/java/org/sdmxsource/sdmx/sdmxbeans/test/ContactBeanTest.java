package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ContactBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ContactMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactBeanTest {

    @Test
    public void shouldCheckRoleBuiltFromMutable() {
        String role = "Test Role";
        var contact = new ContactMutableBeanImpl();
        contact.setId("ZZ9");
        List<TextTypeWrapperMutableBean> names = new ArrayList<>();
        names.add(getTextTypeWrapperMutableBean("en", "Test Name"));
        names.add(getTextTypeWrapperMutableBean("fr", "Test FR NAme"));
        contact.setNames(names);
        contact.addRole(getTextTypeWrapperMutableBean("en", role));
        var immutable = new ContactBeanImpl(contact);

        assertEquals(1, immutable.getRole().size());
        assertEquals(role, immutable.getRole().get(0).getValue());
    }

    private TextTypeWrapperMutableBean getTextTypeWrapperMutableBean(String locale, String value) {
        var textTypeWrapperMutableBean = new TextTypeWrapperMutableBeanImpl();
        textTypeWrapperMutableBean.setLocale(locale);
        textTypeWrapperMutableBean.setValue(value);
        return textTypeWrapperMutableBean;
    }
}
