package org.sdmxsource.test.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.util.io.ReadableDataLocationTmp;
import org.sdmxsource.util.xml.XmlUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideXmlUtilParams")
    public void shouldCheckXmlUtil(String file, boolean expectedResult) {
        var reader = new ReadableDataLocationTmp(file);
        assertEquals(expectedResult, XmlUtil.isXML(reader));
    }
}
