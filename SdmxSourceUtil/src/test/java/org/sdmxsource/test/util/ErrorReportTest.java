package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.model.impl.ErrorReport;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorReportTest {

    private final static String MSG = "TESTING 123";

    @Test
    public void shouldCheckErrorReportBuildWithException() {
        ErrorReport error = ErrorReport.build(new Exception(MSG));

        assertNotNull(error.getErrorMessage());
        assertTrue(error.getErrorMessage().contains(MSG));
    }

    @Test
    public void shouldCheckErrorReportBuildWithMessage() {
        ErrorReport error = ErrorReport.build(MSG);

        assertTrue(error.getErrorMessage().contains(MSG));
    }

    @Test
    public void shouldCheckErrorReportBuildWithMessageAndException() {
        ErrorReport error = ErrorReport.build(MSG, new Exception("1", new Exception("2", new Exception("3"))));

        assertTrue(error.getErrorMessage().containsAll(Arrays.asList(MSG + ": 1", "2", "3")));
    }
}
