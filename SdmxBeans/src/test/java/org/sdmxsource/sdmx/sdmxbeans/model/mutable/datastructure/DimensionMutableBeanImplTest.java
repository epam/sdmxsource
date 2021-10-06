package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.getConceptStructureReferenceBean;

class DimensionMutableBeanImplTest {
    @Test
    void shouldCheckRolesToSetIsFrequencyFlagWhenSingleRolePresent() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(singletonList(getConceptStructureReferenceBean("FREQ")));

        assertTrue(subject.isFrequencyDimension());
    }

    @Test
    void shouldCheckRolesToSetIsFrequencyFlagWhenMultipleRolesPresent() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("FREQ"),
                getConceptStructureReferenceBean("VAR")
        ));

        assertTrue(subject.isFrequencyDimension());
    }

    @Test
    void shouldCheckRolesToSetIsFrequencyFlagWhenNoFrequencyRole() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("GEO"),
                getConceptStructureReferenceBean("VAR")
        ));

        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldCheckRolesToSetIsFrequencyFlagWhenNoRoles() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of());

        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldCheckRolesToSetIsFrequencyFlagWhenNull() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(null);

        assertFalse(subject.isFrequencyDimension());
    }
}