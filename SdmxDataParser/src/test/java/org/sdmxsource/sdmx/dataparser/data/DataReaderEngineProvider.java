package org.sdmxsource.sdmx.dataparser.data;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public abstract class DataReaderEngineProvider implements ArgumentsProvider {

    public static Stream<Arguments> provideFileLocations() {
        return Stream.of(
                Arguments.of("src/test/resources/data/Compact21-alldim.xml"),
                Arguments.of("src/test/resources/data/Compact-VersionTwo.xml"),
                Arguments.of("src/test/resources/data/Compact-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/data/Compact-VersionTwoPointOne-ts.xml"),
                Arguments.of("src/test/resources/data/Generic-VersionTwo.xml"),
                Arguments.of("src/test/resources/data/Generic-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/data/Generic-VersionTwoPointOne-ts.xml")
        );
    }

    public static Stream<Arguments> provideFileLocationsAndDimensions() {
        return Stream.of(
                Arguments.of("src/test/resources/data/Compact21-ADJUSTMENT.xml", "ADJUSTMENT"),
                Arguments.of("src/test/resources/data/Compact21-FREQ.xml", "FREQ"),
                Arguments.of("src/test/resources/data/Compact21-STS_ACTIVITY.xml", "STS_ACTIVITY"),
                Arguments.of("src/test/resources/data/Generic21-ADJUSTMENT.xml", "ADJUSTMENT"),
                Arguments.of("src/test/resources/data/Generic21-FREQ.xml", "FREQ"),
                Arguments.of("src/test/resources/data/Generic21-STS_ACTIVITY.xml", "STS_ACTIVITY")
        );
    }

    public static Stream<Arguments> provideBopFileLocations() {
        return Stream.of(
                Arguments.of("src/test/resources/data/SDMX-BOP-BdE-sample-corrected.xml", "src/test/resources/structure/DataStructure-IMF.BOP(1.0).xml"),
                Arguments.of("src/test/resources/data/SDMX-BOP-BdE-sample.xml", "src/test/resources/structure/DataStructure-IMF.BOP(1.0).xml"),
                Arguments.of("src/test/resources/data/ESTAT_NA_MAIN_1_0.xml", "src/test/resources/structure/ESTAT+NA_MAIN+1.0.xml")
        );
    }
}
