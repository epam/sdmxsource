package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideValidationUtilValidParams")
    public void shouldCheckCleanAndValidateId(String id, boolean startWithInteger) {
        String validateId = ValidationUtil.cleanAndValidateId(id, startWithInteger);
        if (id == null || id.isBlank()) {
            assertNull(validateId);
        } else {
            assertEquals(id, validateId);
        }
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideValidationUtilInvalidParams")
    public void shouldCheckExceptionOnCleanAndValidateId(String id, boolean startWithInteger) {
        assertThrows(SdmxSemmanticException.class, () -> ValidationUtil.cleanAndValidateId(id, startWithInteger));
    }

}
