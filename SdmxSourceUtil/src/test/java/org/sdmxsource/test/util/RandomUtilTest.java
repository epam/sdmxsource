package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sdmxsource.util.random.RandomUtil;

import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 15, 32, 1024})
    public void shouldCheckGenerateRandomPassword(int len) {
        String generateRandomPassword = RandomUtil.generateRandomPassword(len);

        assertNotNull(generateRandomPassword);
        assertEquals(len, generateRandomPassword.length());
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                assertNotEquals(generateRandomPassword, RandomUtil.generateRandomPassword(len));
            }
        }
    }

    @Test
    public void shouldCheckGenerateRandomString() {
        String generateRandomString = RandomUtil.generateRandomString();

        assertNotNull(generateRandomString);
        assertNotEquals(generateRandomString.length(), 0);
        for (int i = 0; i < 10; i++) {
            assertNotEquals(generateRandomString, RandomUtil.generateRandomString());
        }
    }
}
