package org.sdmxsource.sdmx.querybuilder.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;
import org.sdmxsource.sdmx.api.factory.StructureQueryFactory;
import org.sdmxsource.sdmx.api.manager.query.StructureQueryBuilderManager;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.querybuilder.manager.StructureQueryBuilderManagerImpl;
import org.sdmxsource.sdmx.querybuilder.model.RestQueryFormat;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.RESTStructureQueryImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StructureQueryBuilderRestTest {

    private final StructureQueryFactory[] factories = new StructureQueryFactory[0];
    private final StructureQueryBuilderManager structureQueryBuilderManager =
            new StructureQueryBuilderManagerImpl(factories);


    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.querybuilder.data.RestStructureQueryProvider#provideRestStructureQueryParams")
    public void shouldCheckRestStructureQuery(String agency, String id, String version, String expectedResult,
                                              STRUCTURE_QUERY_DETAIL structureQueryDetail,
                                              STRUCTURE_REFERENCE_DETAIL structureReferenceDetail) {
        var dataflowRef = new StructureReferenceBeanImpl(agency, id, version, SDMX_STRUCTURE_TYPE.DATAFLOW);
        String request = getStructureQueryFormat(dataflowRef, version, structureQueryDetail, structureReferenceDetail);

        assertNotNull(request);
        assertEquals(request, expectedResult);
    }

    @Test
    public void shouldCheckRestStructureRoundTrip() {
        String restQuery = "datastructure/all/all/all";
        var restStructureQuery = new RESTStructureQueryImpl(restQuery);
        assertNotNull(restStructureQuery.getStructureReference());
        String buildStructureQuery = structureQueryBuilderManager
                .buildStructureQuery(restStructureQuery, new RestQueryFormat());

        assertNotNull(buildStructureQuery);
        assertTrue(buildStructureQuery.startsWith(restQuery));
    }

    private String getStructureQueryFormat(StructureReferenceBean dataflow, String version,
                                           STRUCTURE_QUERY_DETAIL structureQueryDetail,
                                           STRUCTURE_REFERENCE_DETAIL structureReferenceDetail) {
        boolean returnLatest = version == null;
        SDMX_STRUCTURE_TYPE specificStructureReference =
                Objects.equals(structureReferenceDetail, STRUCTURE_REFERENCE_DETAIL.SPECIFIC) ?
                        SDMX_STRUCTURE_TYPE.CODE_LIST : null;
        var structureQuery = new RESTStructureQueryImpl(structureQueryDetail, structureReferenceDetail,
                specificStructureReference, dataflow, returnLatest);
        return structureQueryBuilderManager.buildStructureQuery(structureQuery, new RestQueryFormat());
    }

}
