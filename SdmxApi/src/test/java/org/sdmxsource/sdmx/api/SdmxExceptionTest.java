package org.sdmxsource.sdmx.api;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SdmxExceptionTest {

    @Test
    public void shouldReturnSdmxNotImplementedExceptionMessage() {
        var exception = new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Any");
        assertEquals("405", exception.getMessage());
    }

    @Test
    public void shouldReturnNotImplementedErrorType() {
        var exception = new SdmxNotImplementedException();
        assertEquals("Not Implemented Exception", exception.getErrorType());
    }

    @Test
    public void shouldReturnSdmxNotImplementedExceptionMessageWhenStringIsArgument() {
        var exception = new SdmxNotImplementedException("Any");
        assertEquals("Any", exception.getMessage());
    }
}
