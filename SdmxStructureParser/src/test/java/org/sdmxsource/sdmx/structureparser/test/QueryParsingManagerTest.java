package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.structureparser.manager.parsing.QueryParsingManager;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.QueryParsingManagerImpl;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class QueryParsingManagerTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideQueryParsingManagerParams")
    public void shouldCheckQueryParserManagerV21(String file) {
        var fileReadableDataLocation = new ReadableDataLocationTmp(file);
        QueryParsingManager queryParsingManager = new QueryParsingManagerImpl();
        var queryWorkspace = queryParsingManager.parseQueries(fileReadableDataLocation);

        assertNotNull(queryWorkspace);
        assertNotNull(queryWorkspace.getComplexStructureQuery());
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideInvalidQueryParsingManagerParams")
    public void shouldCheckQueryParserManagerForInvalidFile(String file) {
        var fileReadableDataLocation = new ReadableDataLocationTmp(file);
        QueryParsingManager queryParsingManager = new QueryParsingManagerImpl();
        assertThrows(SdmxSyntaxException.class, () -> queryParsingManager.parseQueries(fileReadableDataLocation));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideV20QueryParsingManagerParams")
    public void shouldCheckQueryParserManagerV20(String file, List<SDMX_STRUCTURE_TYPE> requests, boolean resolveRefs) {
        var fileReadableDataLocation = new ReadableDataLocationTmp(file);
        QueryParsingManager queryParsingManager = new QueryParsingManagerImpl();
        var queryWorkspace = queryParsingManager.parseQueries(fileReadableDataLocation);

        assertNotNull(queryWorkspace);
        assertTrue(queryWorkspace.hasStructureQueries());
        assertNotEquals(0, queryWorkspace.getSimpleStructureQueries().size());
        assertEquals(requests.size(), queryWorkspace.getSimpleStructureQueries().size());
        assertEquals(resolveRefs, queryWorkspace.isResolveReferences());

        List<SDMX_STRUCTURE_TYPE> actualStructureTypes = queryWorkspace.getSimpleStructureQueries()
                .stream()
                .map(StructureReferenceBean::getMaintainableStructureType)
                .collect(Collectors.toList());
        assertTrue(requests.containsAll(actualStructureTypes) && actualStructureTypes.containsAll(requests));
    }
}
