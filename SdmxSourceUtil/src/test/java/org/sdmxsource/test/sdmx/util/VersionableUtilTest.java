package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.util.VersionableUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionableUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.VersionableUtilDataProvider#provideIncrementParams")
    public void shouldCheckIncrementVersion(String version, boolean majorInc, String expectedResult) {
        assertEquals(expectedResult, VersionableUtil.incrementVersion(version, majorInc));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.VersionableUtilDataProvider#provideTwoVersionParams")
    public void shouldCheckIsHigherVersion(String versionA, String versionB, boolean expectedResult) {
        assertEquals(expectedResult, VersionableUtil.isHigherVersion(versionA, versionB));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.VersionableUtilDataProvider#provideValidVersionParams")
    public void shouldCheckValidVersion(String version, boolean expectedResult) {
        assertEquals(expectedResult, VersionableUtil.validVersion(version));
    }
}
