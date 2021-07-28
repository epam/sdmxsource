package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MaintainableRefBeanImplTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideMaintainableRefParams")
    public void shouldCheckMaintainableRefBeanImpl(String agencyId, boolean hasAgency, String id, boolean hasId,
                                                   String version, boolean hasVersion, boolean expectedResult) {
        var maintRef = new MaintainableRefBeanImpl(agencyId, id, version);
        assertEquals(hasAgency, maintRef.hasAgencyId());
        assertEquals(hasVersion, maintRef.hasVersion());
        assertEquals(hasId, maintRef.hasMaintainableId());

        var comparingMainRef = new MaintainableRefBeanImpl("AGENCY", "TEST", "2.0");
        assertEquals(expectedResult, maintRef.equals(comparingMainRef));
    }
}
