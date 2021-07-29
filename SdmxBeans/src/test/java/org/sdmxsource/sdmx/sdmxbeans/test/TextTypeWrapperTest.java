package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextTypeWrapperTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.sdmxbeans.data.DataProvider#provideLocaleParams")
    public void shouldCheckLocale(String locale) {
        var text = new TextTypeWrapperImpl(locale, "Test value", null);

        assertEquals(locale, text.getLocale());
    }

    @Test
    public void shouldCheckInvalidLocale() {
        String invalidLocale = "zz";
        assertThrows(SdmxSemmanticException.class, () ->
                new TextTypeWrapperImpl(invalidLocale, "Test value", null));
    }
}
