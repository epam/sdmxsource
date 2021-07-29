package org.sdmxsource.sdmx.querybuilder.data;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL.*;
import static org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL.*;

public abstract class RestStructureQueryProvider implements ArgumentsProvider {

    public static Stream<Arguments> provideRestStructureQueryParams() {
        return Stream.of(
                Arguments.of(null, null, null, "dataflow/all/all/latest/?references=none&detail=full", FULL, NONE),
                Arguments.of(null, null, null, "dataflow/all/all/latest/?references=none&detail=allstubs", ALL_STUBS, null),
                Arguments.of(null, null, null, "dataflow/all/all/latest/?references=all&detail=full", null, ALL),
                Arguments.of(null, null, null, "dataflow/all/all/latest/?references=descendants&detail=full", null, DESCENDANTS),
                Arguments.of(null, null, "1.0", "dataflow/all/all/1.0/?references=all&detail=allstubs", ALL_STUBS, ALL),
                Arguments.of(null, null, "1.0", "dataflow/all/all/1.0/?references=children&detail=full", null, CHILDREN),
                Arguments.of(null, null, "1.0", "dataflow/all/all/1.0/?references=descendants&detail=full", FULL, DESCENDANTS),
                Arguments.of(null, null, "1.0", "dataflow/all/all/1.0/?references=parents&detail=full", null, PARENTS),
                Arguments.of(null, "TEST", null, "dataflow/all/TEST/latest/?references=children&detail=allstubs", ALL_STUBS, CHILDREN),
                Arguments.of(null, "TEST", null, "dataflow/all/TEST/latest/?references=none&detail=full", null, null),
                Arguments.of(null, "TEST", null, "dataflow/all/TEST/latest/?references=none&detail=full", null, NONE),
                Arguments.of(null, "TEST", null, "dataflow/all/TEST/latest/?references=descendants&detail=allstubs", ALL_STUBS, DESCENDANTS),
                Arguments.of(null, "TEST", "1.0", "dataflow/all/TEST/1.0/?references=parents&detail=allstubs", ALL_STUBS, PARENTS),
                Arguments.of(null, "TEST", "1.0", "dataflow/all/TEST/1.0/?references=none&detail=full", FULL, null),
                Arguments.of(null, "TEST", "1.0", "dataflow/all/TEST/1.0/?references=parentsandsiblings&detail=full", null, PARENTS_SIBLINGS),
                Arguments.of(null, "TEST", "1.0", "dataflow/all/TEST/1.0/?references=codelist&detail=full", FULL, SPECIFIC),
                Arguments.of(null, "TEST", "1.0", "dataflow/all/TEST/1.0/?references=parents&detail=referencestubs", REFERENCED_STUBS, PARENTS),
                Arguments.of("AGENCY", "TEST", null, "dataflow/AGENCY/TEST/latest/?references=parentsandsiblings&detail=full", FULL, PARENTS_SIBLINGS),
                Arguments.of("AGENCY", "TEST", null, "dataflow/AGENCY/TEST/latest/?references=children&detail=full", FULL, CHILDREN),
                Arguments.of("AGENCY", "TEST", null, "dataflow/AGENCY/TEST/latest/?references=none&detail=allstubs", ALL_STUBS, NONE),
                Arguments.of("AGENCY", "TEST", null, "dataflow/AGENCY/TEST/latest/?references=parentsandsiblings&detail=referencestubs", REFERENCED_STUBS, PARENTS_SIBLINGS),
                Arguments.of("AGENCY", "TEST", "1.0", "dataflow/AGENCY/TEST/1.0/?references=codelist&detail=allstubs", ALL_STUBS, SPECIFIC),
                Arguments.of("AGENCY", "TEST", "1.0", "dataflow/AGENCY/TEST/1.0/?references=codelist&detail=full", null, SPECIFIC),
                Arguments.of("AGENCY", "TEST", "1.0", "dataflow/AGENCY/TEST/1.0/?references=none&detail=referencestubs", REFERENCED_STUBS, null)
        );
    }
}
