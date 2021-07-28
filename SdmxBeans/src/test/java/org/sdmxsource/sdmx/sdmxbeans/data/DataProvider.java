package org.sdmxsource.sdmx.sdmxbeans.data;

import org.junit.jupiter.params.provider.Arguments;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;

import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.getConceptStructureReferenceBean;

public class DataProvider {

    public static Stream<Arguments> provideFreqDimensionBeans() {
        return Stream.of(
                Arguments.of(getFreqDimensionWithConceptRole()),
                Arguments.of(getFreqDimensionWithFreqId()),
                Arguments.of(getFreqDimensionWithFreqConcept()));
    }

    public static Stream<Arguments> provideOrganizationTestParams() {
        return Stream.of(
                Arguments.of("TEST_PARENT", true),
                Arguments.of(null, false));
    }

    public static Stream<Arguments> provideLocaleParams() {
        return Stream.of(
                Arguments.of("en"),
                Arguments.of("en-GB"),
                Arguments.of("en-US"),
                Arguments.of("lo"),
                Arguments.of("el"),
                Arguments.of("el-GR"),
                Arguments.of("de"),
                Arguments.of("de-DE"),
                Arguments.of("cs"),
                Arguments.of("hr"),
                Arguments.of("la"));
    }

    private static DimensionMutableBean getFreqDimensionWithConceptRole() {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setId("FREQ");
        dimension.setConceptRole(singletonList(getConceptStructureReferenceBean("FREQ")));
        dimension.setConceptRef(getConceptStructureReferenceBean("FREQ"));
        return dimension;
    }

    private static DimensionMutableBean getFreqDimensionWithFreqConcept() {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setConceptRef(getConceptStructureReferenceBean("FREQ"));
        return dimension;
    }

    private static DimensionMutableBean getFreqDimensionWithFreqId() {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setId("FREQ");
        dimension.setConceptRef(getConceptStructureReferenceBean("SOMETHING_ELSE"));
        return dimension;
    }

}
