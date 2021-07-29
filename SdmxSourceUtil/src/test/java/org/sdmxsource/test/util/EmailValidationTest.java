package org.sdmxsource.test.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.util.email.EmailValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailValidationTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideEmailValidationParams")
    public void shouldCheckEmailValidation(String email, boolean expectedResult) {

        assertEquals(expectedResult, EmailValidation.validateEmail(email));
    }
}
