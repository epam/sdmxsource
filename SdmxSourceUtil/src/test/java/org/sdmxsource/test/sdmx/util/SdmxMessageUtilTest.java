package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SdmxMessageUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.SdmxMessageUtilDataProvider#provideSdmxMessageUtilParams")
    public void shouldCheckGetDataSetActionMessageTypeAndSdmxSchema(String file, DATASET_ACTION datasetAction,
                                                                    MESSAGE_TYPE messageType, SDMX_SCHEMA sdmxSchema) {
        var readable = new ReadableDataLocationTmp(file);

        assertEquals(datasetAction, SdmxMessageUtil.getDataSetAction(readable));
        assertEquals(messageType, SdmxMessageUtil.getMessageType(readable));
        assertEquals(sdmxSchema, SdmxMessageUtil.getSchemaVersion(readable));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.SdmxMessageUtilDataProvider#provideQueryMessageValidParams")
    public void shouldCheckGetQueryMessageTypes(String file, QUERY_MESSAGE_TYPE queryMessageType) {
        var readable = new ReadableDataLocationTmp(file);
        List<QUERY_MESSAGE_TYPE> queryMessageEnumTypes = SdmxMessageUtil.getQueryMessageTypes(readable);
        assertNotNull(queryMessageEnumTypes);
        assertFalse(queryMessageEnumTypes.isEmpty());

        List<QUERY_MESSAGE_TYPE> filteredQueryMessageEnumTypes = queryMessageEnumTypes.stream()
                .filter(type -> type.equals(queryMessageType))
                .collect(Collectors.toList());
        assertEquals(filteredQueryMessageEnumTypes, queryMessageEnumTypes);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.SdmxMessageUtilDataProvider#provideQueryMessageInvalidParams")
    public void shouldCheckExceptionOnGetQueryMessageTypes(String file) {
        var readable = new ReadableDataLocationTmp(file);

        assertThrows(IllegalArgumentException.class, () -> SdmxMessageUtil.getQueryMessageTypes(readable));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.SdmxMessageUtilDataProvider#provideRegistryMessageValidParams")
    public void shouldCheckGetRegistryMessageType(String file, REGISTRY_MESSAGE_TYPE registryMessageType) {
        var readable = new ReadableDataLocationTmp(file);

        assertEquals(registryMessageType, SdmxMessageUtil.getRegistryMessageType(readable));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.SdmxMessageUtilDataProvider#provideRegistryMessageInvalidParams")
    public void shouldCheckExceptionOnGetRegistryMessageTypes(String file) {
        var readable = new ReadableDataLocationTmp(file);

        assertThrows(IllegalArgumentException.class, () -> SdmxMessageUtil.getRegistryMessageType(readable));
    }

}
