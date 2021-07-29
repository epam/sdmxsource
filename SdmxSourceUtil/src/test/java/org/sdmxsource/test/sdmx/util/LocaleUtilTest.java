package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.util.beans.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocaleUtilTest {

    @Test
    public void shouldCheckGetStringByDefaultLocale() {
        Map<Locale, String> locales = new HashMap<>();
        locales.put(Locale.ENGLISH, "EN test");
        locales.put(Locale.FRENCH, "FR test");
        locales.put(Locale.FRANCE, "fr-FR test");

        var stringByDefaultLocale = LocaleUtil.getStringByDefaultLocale(locales);

        assertNotNull(stringByDefaultLocale);
        assertEquals(stringByDefaultLocale, "EN test");
    }
}
