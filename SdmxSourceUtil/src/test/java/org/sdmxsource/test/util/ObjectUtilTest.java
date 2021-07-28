package org.sdmxsource.test.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.util.ObjectUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectUtilTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideEquivalentObjectsParams")
    public void shouldCheckEquivalentObjects(Object a, Object b, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.equivalent(a, b));
    }


    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideEquivalentCollectionParams")
    public void shouldCheckEquivalentCollections(Collection<Object> a, Collection<Object> b, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.equivalentCollection(a, b));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidArrayParams")
    public void shouldCheckValidArray(Object[] a, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.validArray(a));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidArrayParams")
    public void shouldCheckValidCollection(Object[] a, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.validCollection(Arrays.asList(a)));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidObjectParams")
    public void shouldCheckValidObject(Object[] a, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.validObject(a));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidOneStringParams")
    public void shouldCheckValidOneString(String[] a, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.validOneString(a));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidStringParams")
    public void shouldCheckValidString(String[] a, boolean expectedResult) {
        assertEquals(expectedResult, ObjectUtil.validString(a));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.ObjectUtilDataProvider#provideValidUriStringParams")
    public void shouldCheckValidUri(String a, boolean expectedResult) {
        try {
            assertEquals(expectedResult, ObjectUtil.validString(new URI(a).toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
