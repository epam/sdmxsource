package org.sdmxsource.sdmx.structureparser.data;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;

import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA.VERSION_TWO;
import static org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA.VERSION_TWO_POINT_ONE;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.*;

public abstract class DataProvider implements ArgumentsProvider {

    public static Stream<Arguments> provideQueryParsingManagerParams() {
        return Stream.of(
                Arguments.of("src/test/resources/query_cl_all.xml"),
                Arguments.of("src/test/resources/query_cl_regions.xml"),
                Arguments.of("src/test/resources/query_demo_stub.xml"),
                Arguments.of("src/test/resources/query_esms_children.xml"),
                Arguments.of("src/test/resources/query_esms_descendants.xml")
        );
    }

    public static Stream<Arguments> provideInvalidQueryParsingManagerParams() {
        return Stream.of(
                Arguments.of("src/test/resources/query_dataflow_nodataflowwhere.xml"),
                Arguments.of("src/test/resources/query_dataflow_nodataflowwhere.xml"),
                Arguments.of("src/test/resources/v20/getSyntaxError.xml")
        );
    }

    public static Stream<Arguments> provideV20QueryParsingManagerParams() {
        return Stream.of(
                Arguments.of("src/test/resources/v20/QueryStructureRequest.xml", singletonList(CODE_LIST), false),
                Arguments.of("src/test/resources/v20/QueryStructureRequestDataflowCodelist.xml", asList(DATAFLOW, CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getCategoryWithResolveRef.xml", singletonList(CATEGORY_SCHEME), true),
                Arguments.of("src/test/resources/v20/getCategory.xml", singletonList(CATEGORY_SCHEME), false),
                Arguments.of("src/test/resources/v20/getCodelistFailure.xml", singletonList(CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getCodelist.xml", singletonList(CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getConceptScheme.xml", singletonList(CONCEPT_SCHEME), false),
                Arguments.of("src/test/resources/v20/getDataflowResolveRef.xml", singletonList(DATAFLOW), true),
                Arguments.of("src/test/resources/v20/getKeyfamilyResolveRef.xml", singletonList(DSD), true),
                Arguments.of("src/test/resources/v20/getAllCodeList.xml", singletonList(CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getAllConceptSchemes.xml", singletonList(CONCEPT_SCHEME), false),
                Arguments.of("src/test/resources/v20/getAllConceptSchemesResolveRef.xml", singletonList(CONCEPT_SCHEME), true),
                Arguments.of("src/test/resources/v20/getAvailableDataAdjustmentWithRefAreaFregConstrains.xml", asList(DATAFLOW, CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getAvailableDataStsActivityWithAdjustmentRefAreaFregConstrains.xml", asList(DATAFLOW, CODE_LIST), false),
                Arguments.of("src/test/resources/v20/getAvailableDataWithTimeConstrain.xml", asList(DATAFLOW, CODE_LIST), false)

        );
    }

    public static Stream<Arguments> provideV20StructureParsingParams() {
        return Stream.of(
                Arguments.of("src/test/resources/v20/CENSAGR_CAPOAZ_GEN+IT1+1.3.xml"),
                Arguments.of("src/test/resources/v20/EGR_1_TS+ESTAT+1.4.xml"),
                Arguments.of("src/test/resources/v20/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME.xml"),
                Arguments.of("src/test/resources/v20/CENSUSHUB+ESTAT+1.1_alllevels.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+DEMOGRAPHY+2.1.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+HCL_SAMPLE+2.0.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+HCL_SAMPLE_NZ+2.1.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+SSTSCONS_PROD_M+2.0.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+STS+2.0.xml"),
                Arguments.of("src/test/resources/v20/QueryProvisioningRequest.xml"),
                Arguments.of("src/test/resources/v20/QueryProvisioningResponse.xml"),
                Arguments.of("src/test/resources/v20/QueryRegistrationRequest.xml"),
                Arguments.of("src/test/resources/v20/queryResponse-estat-sts.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureRequest.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureRequestDataflowCodelist.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureResponse.xml"),
                Arguments.of("src/test/resources/v20/SubmitProvisioningRequest.xml"),
                Arguments.of("src/test/resources/v20/SubmitProvisioningResponse.xml"),
                Arguments.of("src/test/resources/v20/SubmitRegistrationResponse.xml"),
                Arguments.of("src/test/resources/v20/SubmitStructureRequest.xml"),
                Arguments.of("src/test/resources/v20/SubmitStructureResponse.xml"),
                Arguments.of("src/test/resources/v20/response.xml"),
                Arguments.of("src/test/resources/v20/global-registry-stub-keyfamily.xml")
        );
    }

    public static Stream<Arguments> provideV20StructureWritingParams() {
        return Stream.of(
                Arguments.of("src/test/resources/v20/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME_annotations.xml"),
                Arguments.of("src/test/resources/v20/CENSAGR_CAPOAZ_GEN+IT1+1.3.xml"),
                Arguments.of("src/test/resources/v20/EGR_1_TS+ESTAT+1.4.xml"),
                Arguments.of("src/test/resources/v20/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME.xml"),
                Arguments.of("src/test/resources/v20/CENSUSHUB+ESTAT+1.1_alllevels.xml"),
                Arguments.of("src/test/resources/v20/CL_SEX_v1.1.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+DEMOGRAPHY+2.1.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+HCL_SAMPLE+2.0.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+HCL_SAMPLE_NZ+2.1.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+SSTSCONS_PROD_M+2.0.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+STS+2.0.xml"),
                Arguments.of("src/test/resources/v20/ESTAT+TESTLEVELS+1.0.xml"),
                Arguments.of("src/test/resources/v20/queryResponse-estat-sts.xml"),
                Arguments.of("src/test/resources/v20/QueryResponseDataflowCategories.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureRequest.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureRequestDataflowCodelist.xml"),
                Arguments.of("src/test/resources/v20/QueryStructureResponse.xml"),
                Arguments.of("src/test/resources/v20/SubmitStructureRequest.xml"),
                Arguments.of("src/test/resources/v20/response.xml"),
                Arguments.of("src/test/resources/v20/QueryProvisioningRequest.xml"),
                Arguments.of("src/test/resources/v20/QueryRegistrationRequest.xml"),
                Arguments.of("src/test/resources/v20/SubmitProvisioningResponse.xml"),
                Arguments.of("src/test/resources/v20/SubmitRegistrationResponse.xml")
        );
    }

    public static Stream<Arguments> provideInvalidStructureParsingParams() {
        return Stream.of(
                Arguments.of("src/test/resources/v20/CR1_12_data_report.xml", IllegalArgumentException.class),
                Arguments.of("src/test/resources/v20/ESTAT_STS2GRP_v2.2.xml", MaintainableBeanException.class),
                Arguments.of("src/test/resources/v20/query.xml", IllegalArgumentException.class),
                Arguments.of("src/test/resources/v20/QUESTIONNAIRE_MSD_v0.xml", MaintainableBeanException.class),
                Arguments.of("src/test/resources/v20/SDMX_Query_Type_A_NO_TRANS.xml", IllegalArgumentException.class),
                Arguments.of("src/test/resources/v20/SubmitRegistrationRequest.xml", SdmxNotImplementedException.class),
                Arguments.of("src/test/resources/v20/imf-stub-keyfamilies.xml", MaintainableBeanException.class),
                Arguments.of("src/test/resources/v21/response_demo_stub.xml", MaintainableBeanException.class)
        );
    }

    public static Stream<Arguments> provideV21StructureParsingParams() {
        return Stream.of(
                Arguments.of("src/test/resources/v21/structures.xml"),
                Arguments.of("src/test/resources/v21/ecb_exr_ng_full.xml"),
                Arguments.of("src/test/resources/v21/repsonse_cl_all.xml"),
                Arguments.of("src/test/resources/v21/ESTAT_STS_3.0.xml"),
                Arguments.of("src/test/resources/v21/ESTAT_STS_3.1.xml"),
                Arguments.of("src/test/resources/v21/21_IT1+SEP_IND_COSTR_PR+1.3.xml"),
                Arguments.of("src/test/resources/v21/CNS_PR+UN+1.1.xml"),
                Arguments.of("src/test/resources/v21/TEST_5+ESTAT+1.0.xml"),
                Arguments.of("src/test/resources/v21/StructureSet-sdmxv2.1-ESTAT+STS+2.0.xml"),
                Arguments.of("src/test/resources/v21/AGENCIES+ESTAT+1.0.xml"),
                Arguments.of("src/test/resources/v21/StructureSet_example.xml"),
                Arguments.of("src/test/resources/v21/Constraint_Sample1.xml"),
                Arguments.of("src/test/resources/v21/Constraint_Sample2.xml"),
                Arguments.of("src/test/resources/v21/MSDv21-TEST.xml")
        );
    }

    public static Stream<Arguments> provideSchemaEnumTypes() {
        return Stream.of(
                Arguments.of(VERSION_TWO),
                Arguments.of(VERSION_TWO_POINT_ONE)
        );
    }
}
