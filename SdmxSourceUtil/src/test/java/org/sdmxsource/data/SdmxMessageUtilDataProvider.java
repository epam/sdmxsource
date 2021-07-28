package org.sdmxsource.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.sdmxsource.sdmx.api.constants.DATASET_ACTION.APPEND;
import static org.sdmxsource.sdmx.api.constants.DATASET_ACTION.INFORMATION;
import static org.sdmxsource.sdmx.api.constants.MESSAGE_TYPE.*;
import static org.sdmxsource.sdmx.api.constants.QUERY_MESSAGE_TYPE.*;
import static org.sdmxsource.sdmx.api.constants.REGISTRY_MESSAGE_TYPE.*;
import static org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA.VERSION_TWO;
import static org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA.VERSION_TWO_POINT_ONE;

public class SdmxMessageUtilDataProvider {

    public static Stream<Arguments> provideSdmxMessageUtilParams() {
        return Stream.of(
                Arguments.of("src/test/resources/Compact-VersionTwo.xml", INFORMATION, COMPACT_DATA, VERSION_TWO),
                Arguments.of("src/test/resources/Generic-VersionTwo.xml", INFORMATION, GENERIC_DATA, VERSION_TWO),
                Arguments.of("src/test/resources/Generic-VersionTwoPointOne.xml", INFORMATION, GENERIC_DATA, VERSION_TWO_POINT_ONE),
                Arguments.of("src/test/resources/Compact-VersionTwoPointOne.xml", INFORMATION, COMPACT_DATA, VERSION_TWO_POINT_ONE),
                Arguments.of("src/test/resources/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME.xml", APPEND, STRUCTURE, VERSION_TWO),
                Arguments.of("src/test/resources/CENSUSHUB+ESTAT+1.1_alllevels.xml", APPEND, STRUCTURE, VERSION_TWO),
                Arguments.of("src/test/resources/CR1_12_data_report.xml", APPEND, GENERIC_METADATA, VERSION_TWO),
                Arguments.of("src/test/resources/query.xml", APPEND, QUERY, VERSION_TWO),
                Arguments.of("src/test/resources/SDMX_Query_Type_A_NO_TRANS.xml", APPEND, QUERY, VERSION_TWO),
                Arguments.of("src/test/resources/QueryProvisioningRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/QueryProvisioningResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/QueryRegistrationRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/QueryRegistrationResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/QueryResponseDataflowCategories.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/QueryStructureRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitProvisioningRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitProvisioningResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitRegistrationRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitRegistrationResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitStructureRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitStructureResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitSubscriptionRequest.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO),
                Arguments.of("src/test/resources/SubmitSubscriptionResponse.xml", APPEND, REGISTRY_INTERFACE, VERSION_TWO)
        );
    }

    public static Stream<Arguments> provideQueryMessageValidParams() {
        return Stream.of(
                Arguments.of("src/test/resources/query.xml", DATA_WHERE),
                Arguments.of("src/test/resources/SDMX_Query_Type_A_NO_TRANS.xml", DATA_WHERE),
                Arguments.of("src/test/resources/TC-1.3.3.2_SSTSCONS_PROD_QT2_StartTime_Q.xml", DATA_WHERE),
                Arguments.of("src/test/resources/TC-1.1.2_SSTSCONS_PROD_A_DimsAttsClauses.xml", DATA_WHERE),

                Arguments.of("src/test/resources/query_cl_all.xml", CODELIST_WHERE),
                Arguments.of("src/test/resources/query_cl_regions.xml", CODELIST_WHERE),
                Arguments.of("src/test/resources/query_demo_stub.xml", DSD_WHERE),
                Arguments.of("src/test/resources/query_esms_children.xml", MDS_WHERE)
        );
    }

    public static Stream<Arguments> provideQueryMessageInvalidParams() {
        return Stream.of(
                Arguments.of("src/test/resources/Compact-VersionTwo.xml"),
                Arguments.of("src/test/resources/Generic-VersionTwo.xml"),
                Arguments.of("src/test/resources/Generic-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/Compact-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME.xml"),
                Arguments.of("src/test/resources/CENSUSHUB+ESTAT+1.1_alllevels.xml"),
                Arguments.of("src/test/resources/CR1_12_data_report.xml"),
                Arguments.of("src/test/resources/QueryProvisioningRequest.xml"),
                Arguments.of("src/test/resources/QueryRegistrationRequest.xml"),
                Arguments.of("src/test/resources/QueryProvisioningRequest.xml"),
                Arguments.of("src/test/resources/QueryProvisioningResponse.xml"),
                Arguments.of("src/test/resources/QueryRegistrationRequest.xml"),
                Arguments.of("src/test/resources/QueryRegistrationResponse.xml"),
                Arguments.of("src/test/resources/QueryResponseDataflowCategories.xml"),
                Arguments.of("src/test/resources/QueryStructureRequest.xml"),
                Arguments.of("src/test/resources/SubmitProvisioningRequest.xml"),
                Arguments.of("src/test/resources/SubmitProvisioningResponse.xml"),
                Arguments.of("src/test/resources/SubmitRegistrationRequest.xml"),
                Arguments.of("src/test/resources/SubmitRegistrationResponse.xml"),
                Arguments.of("src/test/resources/SubmitStructureRequest.xml"),
                Arguments.of("src/test/resources/SubmitStructureResponse.xml"),
                Arguments.of("src/test/resources/SubmitSubscriptionRequest.xml"),
                Arguments.of("src/test/resources/SubmitSubscriptionResponse.xml")
        );
    }

    public static Stream<Arguments> provideRegistryMessageValidParams() {
        return Stream.of(
                Arguments.of("src/test/resources/QueryProvisioningRequest.xml", QUERY_PROVISION_REQUEST),
                Arguments.of("src/test/resources/QueryProvisioningResponse.xml", QUERY_PROVISION_RESPONSE),
                Arguments.of("src/test/resources/QueryRegistrationRequest.xml", QUERY_REGISTRATION_REQUEST),
                Arguments.of("src/test/resources/QueryRegistrationResponse.xml", QUERY_REGISTRATION_RESPONSE),
                Arguments.of("src/test/resources/QueryResponseDataflowCategories.xml", QUERY_STRUCTURE_RESPONSE),
                Arguments.of("src/test/resources/QueryStructureRequest.xml", QUERY_STRUCTURE_REQUEST),
                Arguments.of("src/test/resources/SubmitProvisioningRequest.xml", SUBMIT_PROVISION_REQUEST),
                Arguments.of("src/test/resources/SubmitProvisioningResponse.xml", SUBMIT_PROVISION_RESPONSE),
                Arguments.of("src/test/resources/SubmitRegistrationRequest.xml", SUBMIT_REGISTRATION_REQUEST),
                Arguments.of("src/test/resources/SubmitRegistrationResponse.xml", SUBMIT_REGISTRATION_RESPONSE),
                Arguments.of("src/test/resources/SubmitStructureRequest.xml", SUBMIT_STRUCTURE_REQUEST),
                Arguments.of("src/test/resources/SubmitStructureResponse.xml", SUBMIT_STRUCTURE_RESPONSE),
                Arguments.of("src/test/resources/SubmitSubscriptionRequest.xml", SUBMIT_SUBSCRIPTION_REQUEST),
                Arguments.of("src/test/resources/SubmitSubscriptionResponse.xml", SUBMIT_SUBSCRIPTION_RESPONSE)
        );
    }

    public static Stream<Arguments> provideRegistryMessageInvalidParams() {
        return Stream.of(
                Arguments.of("src/test/resources/Compact-VersionTwo.xml"),
                Arguments.of("src/test/resources/Generic-VersionTwo.xml"),
                Arguments.of("src/test/resources/Generic-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/Compact-VersionTwoPointOne.xml"),
                Arguments.of("src/test/resources/CATEGORY_SCHEME_ESTAT_DATAFLOWS_SCHEME.xml"),
                Arguments.of("src/test/resources/CENSUSHUB+ESTAT+1.1_alllevels.xml"),
                Arguments.of("src/test/resources/CR1_12_data_report.xml"),
                Arguments.of("src/test/resources/TC-1.3.3.2_SSTSCONS_PROD_QT2_StartTime_Q.xml"),
                Arguments.of("src/test/resources/TC-1.1.2_SSTSCONS_PROD_A_DimsAttsClauses.xml"),
                Arguments.of("src/test/resources/query_cl_all.xml"),
                Arguments.of("src/test/resources/query_cl_regions.xml"),
                Arguments.of("src/test/resources/query_demo_stub.xml"),
                Arguments.of("src/test/resources/query_esms_children.xml")
        );
    }

}
