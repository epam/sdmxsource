package org.sdmxsource.data;

import org.junit.jupiter.params.provider.Arguments;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;

import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.*;

public class UrnDataProvider {

    public static Stream<Arguments> provideUrnIdentifiableTypeParams() {
        return Stream.of(
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.GroupDimensionDescriptor=ESTAT:STS(2.0).Sibling", GROUP),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:STS(2.0)", DSD),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).SSTSCONS", CATEGORY),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISO:CL_3166A2(1.0).AR", CODE),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", DSD),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=SDMX:SUBJECT_MATTER_DOMAINS(1.0).1.2", CATEGORY),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute=SDMX:CONTACT_METADATA(1.0).CONTACT_REPORT.CONTACT_DETAILS.CONTACT_NAME", METADATA_ATTRIBUTE),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=TFFS.ABC:EXTERNAL_DEBT(1.0)", DATAFLOW),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.base.Agency=TFFS", AGENCY)
        );
    }

    public static Stream<Arguments> provideUrnComponentParams() {
        return Stream.of(
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.GroupDimensionDescriptor=ESTAT:STS(2.0).Sibling", asList("ESTAT", "STS", "Sibling")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:STS(2.0)", asList("ESTAT", "STS")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).SSTSCONS",
                        asList("ESTAT", "ESTAT_DATAFLOWS_SCHEME", "SSTSCONS")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).14.1440.STS.SSTSCONS",
                        asList("ESTAT", "ESTAT_DATAFLOWS_SCHEME", "14", "1440", "STS", "SSTSCONS")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISO:CL_3166A2(1.0).AR", asList("ISO", "CL_3166A2", "AR")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", asList("TFFS", "CRED_EXT_DEBT")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=SDMX:SUBJECT_MATTER_DOMAINS(1.0).1.2",
                        asList("SDMX", "SUBJECT_MATTER_DOMAINS", "1", "2")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute=SDMX:CONTACT_METADATA(1.0).CONTACT_REPORT.CONTACT_DETAILS.CONTACT_NAME",
                        asList("SDMX", "CONTACT_METADATA", "CONTACT_REPORT", "CONTACT_DETAILS", "CONTACT_NAME")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=TFFS.ABC:EXTERNAL_DEBT(1.0)", asList("TFFS.ABC", "EXTERNAL_DEBT")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.base.Agency=TFFS", asList(AgencySchemeBean.DEFAULT_SCHEME, AgencySchemeBean.FIXED_ID, "TFFS")),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.base.Agency=TFFS.ABC", asList("TFFS", AgencySchemeBean.FIXED_ID, "ABC"))
        );
    }

    public static Stream<Arguments> provideUrnVersionParams() {
        return Stream.of(
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.GroupDimensionDescriptor=ESTAT:STS(2.0).Sibling", "2.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:STS(2.0)", "2.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).SSTSCONS", "1.1"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISO:CL_3166A2(1.0).AR", "1.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", "1.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=SDMX:SUBJECT_MATTER_DOMAINS(1.0).1.2", "1.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute=SDMX:CONTACT_METADATA(1.0).CONTACT_REPORT.CONTACT_DETAILS.CONTACT_NAME", "1.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=TFFS.ABC:EXTERNAL_DEBT(1.0)", "1.0"),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.base.Agency=TFFS", null)
        );
    }
}
