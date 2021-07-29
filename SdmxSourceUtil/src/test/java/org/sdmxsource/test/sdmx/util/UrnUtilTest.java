package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.util.beans.UrnUtil;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UrnUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.UrnDataProvider#provideUrnIdentifiableTypeParams")
    public void shouldCheckGetValidIdentifiableType(String urn, SDMX_STRUCTURE_TYPE expectedResult) {
        assertEquals(expectedResult, UrnUtil.getIdentifiableType(urn));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.UrnDataProvider#provideUrnComponentParams")
    public void shouldCheckGetUrnComponents(String urn, List<String> expectedResult) {
        List<String> urnComponents = asList(UrnUtil.getUrnComponents(urn));

        assertFalse(urnComponents.isEmpty());
        assertEquals(expectedResult, urnComponents);
    }

    @Test
    public void shouldCheckGetUrnPostfix() {
        String urn = "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:STS(2.0)";
        String expectedPostFix = "ESTAT:STS(2.0)";
        String urnPostfix = UrnUtil.getUrnPostfix(urn);

        assertEquals(expectedPostFix, urnPostfix);
    }

    @Test
    public void shouldCheckGetUrnPostfixByParams() {
        String expectedPostFix = "AGENCY:ID(1.2)";
        String urnPostfix = UrnUtil.getUrnPostfix("AGENCY", "ID", "1.2");

        assertEquals(expectedPostFix, urnPostfix);
    }

    @Test
    public void shouldCheckGetUrnPostfixByIdParams() {
        String expectedPostFix = "AGENCY:ID(1.2).ENA";
        String urnPostfix = UrnUtil.getUrnPostfix("AGENCY", "ID", "1.2", "ENA");

        assertEquals(expectedPostFix, urnPostfix);
    }

    @Test
    public void shouldCheckGetUrnPrefix() {
        String urn = "urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute=SDMX:CONTACT_METADATA(1.0).CONTACT_REPORT.CONTACT_DETAILS.CONTACT_NAME";
        String expectedPostFix = "urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute";
        String urnPrefix = UrnUtil.getUrnPrefix(urn);

        assertEquals(expectedPostFix, urnPrefix);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.UrnDataProvider#provideUrnVersionParams")
    public void shouldCheckGetVersionFromUrn(String urn, String version) {
        String urnVersion = UrnUtil.getVersionFromUrn(urn);

        assertEquals(version, urnVersion);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.UrnDataProvider#provideUrnIdentifiableTypeParams")
    public void shouldCheckValidateURN(String urn, SDMX_STRUCTURE_TYPE expectedResult) {
        UrnUtil.validateURN(urn, expectedResult);
    }
}
