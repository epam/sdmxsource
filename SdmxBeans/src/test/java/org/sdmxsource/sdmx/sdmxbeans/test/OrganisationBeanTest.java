package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.OrganisationUnitSchemeMutableBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganisationBeanTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.sdmxbeans.data.DataProvider#provideOrganizationTestParams")
    public void shouldCheckOrganisationUnitBean(String parentUnit, boolean hasParent) {
        var organisationScheme = new OrganisationUnitSchemeMutableBeanImpl();
        organisationScheme.setId("TEST");
        organisationScheme.setAgencyId("TEST");
        organisationScheme.setVersion("1.0");
        organisationScheme.addName("en", "Test organisation unit scheme");
        var unit = organisationScheme.createItem("TEST_UNIT1", "Test organisation unit");
        unit.setParentUnit(parentUnit);
        if (parentUnit != null && !parentUnit.isEmpty()) {
            organisationScheme.createItem(parentUnit, parentUnit);
        }

        var immutable = organisationScheme.getImmutableInstance();
        var first = immutable.getItems().stream()
                .filter(organisationUnit -> organisationUnit.getId().equals(unit.getId()))
                .findFirst()
                .get();

        assertEquals(hasParent, first.hasParentUnit());
        if (first.hasParentUnit()) {
            assertEquals(parentUnit, first.getParentUnit());
        }
    }
}
