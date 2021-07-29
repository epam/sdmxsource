package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.util.beans.SDMXBeanUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SDMXBeanUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideSdmxBeanUtilParams")
    public void shouldCheckCreateTertiary(boolean isSet, boolean value, TERTIARY_BOOL expectedValue) {
        assertEquals(expectedValue, SDMXBeanUtil.createTertiary(isSet, value));
    }
}
