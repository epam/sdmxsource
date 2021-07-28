package org.sdmxsource.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class VersionableUtilDataProvider {

    public static Stream<Arguments> provideIncrementParams() {
        return Stream.of(
                Arguments.of("1", true, "2.0"),
                Arguments.of("1.0", true, "2.0"),
                Arguments.of("1.4", true, "2.0"),
                Arguments.of("1.4.3", true, "2.0"),
                Arguments.of("2", false, "2.1"),
                Arguments.of("2.0", false, "2.1"),
                Arguments.of("2.0.0", false, "2.1"),
                Arguments.of("2.0.1", false, "2.1")
        );
    }

    public static Stream<Arguments> provideTwoVersionParams() {
        return Stream.of(
                Arguments.of("1", "2.0", false),
                Arguments.of("1.1", "2.0", false),
                Arguments.of("1.6", "2.0", false),
                Arguments.of("1.3.3", "2.0", false),
                Arguments.of("1", "2", false),
                Arguments.of("1.1", "2.2.3", false),
                Arguments.of("1.3.3", "2.9.3", false),
                Arguments.of("3", "2.0", true),
                Arguments.of("3.1", "2.0", true),
                Arguments.of("3.3.3", "2.0", true),
                Arguments.of("3.6", "2", true),
                Arguments.of("3.3.3", "2.9.3", true),
                Arguments.of("3.1", "2.2.3", true)
        );
    }

    public static Stream<Arguments> provideValidVersionParams() {
        return Stream.of(
                Arguments.of("1", true),
                Arguments.of("1.1", true),
                Arguments.of("1.6", true),
                Arguments.of("1.3.3", true),
                Arguments.of("1.o.1", false),
                Arguments.of("", false),
                Arguments.of("1.", false),
                Arguments.of("1.A", false),
                Arguments.of("0x02", false),
                Arguments.of("02", true),
                Arguments.of(".1", false),
                Arguments.of(".1.", false),
                Arguments.of("VERSION", false)
        );
    }
}
