package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.util.date.DateUtil;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideDateValidParams")
    public void shouldCheckGetTimeFormatOfValidDate(String dateStr, TIME_FORMAT timeFormat) {
        var timeFormatOfDate = DateUtil.getTimeFormatOfDate(dateStr);

        assertNotNull(timeFormatOfDate);
        assertEquals(timeFormat, timeFormatOfDate);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideDateInvalidParams")
    public void shouldCheckGetTimeFormatOfInvalidDate(String dateStr) {
        assertThrows(SdmxSemmanticException.class, () -> DateUtil.getTimeFormatOfDate(dateStr));
    }
}
