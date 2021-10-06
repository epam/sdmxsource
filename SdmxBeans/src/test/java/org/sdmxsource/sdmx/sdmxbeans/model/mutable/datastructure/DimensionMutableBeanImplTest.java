package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.getConceptStructureReferenceBean;

class DimensionMutableBeanImplTest {
    @Test
    void shouldBeFrequencyDimensionWhenFrequencyRoleIsPresent() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(singletonList(getConceptStructureReferenceBean("FREQ")));

        assertTrue(subject.isFrequencyDimension());
    }

    @Test
    void shouldBeFrequencyDimensionWhenFrequencyRoleIsPresentAmongOtherRoles() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("FREQ"),
                getConceptStructureReferenceBean("VAR")
        ));

        assertTrue(subject.isFrequencyDimension());
    }

    @Test
    void shouldNotBeFrequencyDimensionWhenWhenNoFrequencyRole() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("GEO"),
                getConceptStructureReferenceBean("VAR")
        ));

        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void sshouldBeFrequencyDimensionWhenNoRoles() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of());

        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldBeFrequencyDimensionWhenNull() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(null);

        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldNotBeFrequencyDimensionAfterFreqRoleIsRemoved() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("FREQ"),
                getConceptStructureReferenceBean("VAR")
        ));
        assertTrue(subject.isFrequencyDimension());

        subject.setConceptRole(List.of(
                getConceptStructureReferenceBean("VAR")
        ));
        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldAddFrequencyRoleWhenIsFrequencyIsSetToTrue() {
        var subject = new DimensionMutableBeanImpl();

        subject.setFrequencyDimension(true);

        assertEquals("FREQ", subject.getConceptRole().get(0).getChildReference().getId());
    }

    @Test
    void shouldRemoveFrequencyRoleWhenIsFrequencyIsSetToFalse() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(new ArrayList<>(List.of(
                getConceptStructureReferenceBean("FREQ"),
                getConceptStructureReferenceBean("VAR")
        )));

        subject.setFrequencyDimension(false);

        assertEquals(1, subject.getConceptRole().size());
        assertEquals("VAR", subject.getConceptRole().get(0).getChildReference().getId());
        assertFalse(subject.isFrequencyDimension());
    }

    @Test
    void shouldRemoveFrequencyRoleWhenIsFrequencyIsSetToFalseSingleRole() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(new ArrayList<>(List.of(
                getConceptStructureReferenceBean("FREQ")
        )));

        subject.setFrequencyDimension(false);

        assertEquals(0, subject.getConceptRole().size());
    }

    @Test
    void shouldNotFailWhenIsFrequencyIsSetToFalseWhileRolesAreNull() {
        var subject = new DimensionMutableBeanImpl();
        subject.setConceptRole(null);

        subject.setFrequencyDimension(false);
    }
}