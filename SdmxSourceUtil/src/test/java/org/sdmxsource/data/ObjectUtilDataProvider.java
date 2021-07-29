package org.sdmxsource.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.*;

public class ObjectUtilDataProvider {

    public static Stream<Arguments> provideEquivalentObjectsParams() {
        return Stream.of(
                Arguments.of(1, 2, false),
                Arguments.of("PAOK", "OLE", false),
                Arguments.of(4, "4", false),
                Arguments.of(4, 4, true),
                Arguments.of(CATEGORY, CATEGORY, true),
                Arguments.of(CATEGORY, DATAFLOW, false),
                Arguments.of("4", "4", true),
                Arguments.of(null, "4", false),
                Arguments.of(null, null, true),
                Arguments.of("PAOK", "PAOK", true),
                Arguments.of("41", "4", false),
                Arguments.of("A", "a", false)
        );
    }

    public static Stream<Arguments> provideEquivalentCollectionParams() {
        return Stream.of(
                Arguments.of(singletonList("PAOK"), singletonList("OLE"), false),
                Arguments.of(singletonList("PAOK"), singletonList("PAOK"), true),
                Arguments.of(singletonList("PAOK"), new ArrayList<>(), false),
                Arguments.of(asList("PAOK", "OLE"), singletonList("OLE"), false),
                Arguments.of(asList("OLE", "OLE"), singletonList("OLE"), false),
                Arguments.of(asList("PAOK", "OLE"), asList("PAOK", "OLE"), true),
                Arguments.of(asList("OLE", "PAOK"), asList("PAOK", "OLE"), false),
                Arguments.of(null, singletonList("4"), false),
                Arguments.of(null, new ArrayList<>(), false),

                Arguments.of(asList(CATEGORY, CATEGORY), asList(CATEGORY_SCHEME, CATEGORY), false),
                Arguments.of(asList(CATEGORY_SCHEME, CATEGORY), asList(CATEGORY_SCHEME, CATEGORY), true),

                Arguments.of(new ArrayList<>(), asList(1, 2), false),
                Arguments.of(asList(1, 2), asList(2, 1), false),
                Arguments.of(asList(2, 2), asList(2, 2), true),
                Arguments.of(asList(1, 2, 3), asList(1, 2, 3, 4), false),
                Arguments.of(singletonList(1.0), singletonList(1.0), true),
                Arguments.of(asList(1.0, 2.0), singletonList(2.0), false)
        );
    }

    public static Stream<Arguments> provideValidArrayParams() {
        return Stream.of(
                Arguments.of(new Object[]{"PAOK"}, true),
                Arguments.of(new Object[]{"PAOK", "OLE"}, true),
                Arguments.of(new Object[]{}, false)
        );
    }

    public static Stream<Arguments> provideValidObjectParams() {
        return Stream.of(
                Arguments.of(new Object[]{"PAOK"}, true),
                Arguments.of(new Object[]{"PAOK", "OLE"}, true),
                Arguments.of(new Object[]{"PAOK", "OLE", null}, false),
                Arguments.of(new Object[]{}, true),
                Arguments.of(new Object[]{null}, false),
                Arguments.of(null, false)
        );
    }

    public static Stream<Arguments> provideValidOneStringParams() {
        return Stream.of(
                Arguments.of(new String[]{"PAOK"}, true),
                Arguments.of(new String[]{"PAOK", "OLE"}, true),
                Arguments.of(new String[]{"PAOK", "OLE", null}, true),
                Arguments.of(new String[]{null, "OLE"}, true),
                Arguments.of(new String[]{null, ""}, false),
                Arguments.of(new String[]{""}, false),
                Arguments.of(new String[]{"   "}, false),
                Arguments.of(new String[]{}, false),
                Arguments.of(new String[]{null, null}, false),
                Arguments.of(null, false)
        );
    }

    public static Stream<Arguments> provideValidStringParams() {
        return Stream.of(
                Arguments.of(new String[]{"PAOK"}, true),
                Arguments.of(new String[]{"PAOK", "OLE"}, true),
                Arguments.of(new String[]{"PAOK", "OLE", null}, false),
                Arguments.of(new String[]{"PAOK", "OLE", ""}, false),
                Arguments.of(new String[]{"PAOK", "OLE", " "}, false),
                Arguments.of(new String[]{null, "OLE"}, false),
                Arguments.of(new String[]{null, ""}, false),
                Arguments.of(new String[]{""}, false),
                Arguments.of(new String[]{"   "}, false),
                Arguments.of(new String[]{null, null}, false),
                Arguments.of(null, false)
        );
    }

    public static Stream<Arguments> provideValidUriStringParams() {
        return Stream.of(
                Arguments.of("file://PAOK", true),
                Arguments.of("urn:1", true),
                Arguments.of("", false)
        );
    }

}
