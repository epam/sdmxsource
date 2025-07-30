package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.util.date.DateUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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

    @Test
    public void shouldCheckGetTimeFormatOfDate() {
        LocalDateTime ldt = LocalDateTime.of(2025, 7, 25, 14, 22, 5); // Year, Month, Day, Hour, Minute, Second
        Instant instant = ldt.toInstant(ZoneOffset.UTC);
        String formatedDate = DateUtil.formatDate(Date.from(instant));

        assertEquals("2025-07-25T14:22:05Z", formatedDate);
    }
}
