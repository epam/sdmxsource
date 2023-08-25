package org.sdmxsource.data;

import org.junit.jupiter.params.provider.Arguments;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;

import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> provideDateValidParams() {
        return Stream.of(
                Arguments.of("2003", TIME_FORMAT.YEAR),
                Arguments.of("1900", TIME_FORMAT.YEAR),
                Arguments.of("2500", TIME_FORMAT.YEAR),

                Arguments.of("2004-Q1", TIME_FORMAT.QUARTER_OF_YEAR),
                Arguments.of("2004-Q2", TIME_FORMAT.QUARTER_OF_YEAR),
                Arguments.of("2004-Q3", TIME_FORMAT.QUARTER_OF_YEAR),
                Arguments.of("2012-Q2", TIME_FORMAT.QUARTER_OF_YEAR),
                Arguments.of("2012-Q3", TIME_FORMAT.QUARTER_OF_YEAR),
                Arguments.of("2004-Q4", TIME_FORMAT.QUARTER_OF_YEAR),

                Arguments.of("2004-B1", TIME_FORMAT.HALF_OF_YEAR),
                Arguments.of("2004-B2", TIME_FORMAT.HALF_OF_YEAR),

                Arguments.of("2004-02", TIME_FORMAT.MONTH),

                Arguments.of("2004-W3", TIME_FORMAT.WEEK),
                Arguments.of("2004-W33", TIME_FORMAT.WEEK),
                Arguments.of("2004-W52", TIME_FORMAT.WEEK),
                Arguments.of("2003-W53", TIME_FORMAT.WEEK),

                Arguments.of("2003-01-02", TIME_FORMAT.DATE),
                Arguments.of("2003-01-01", TIME_FORMAT.DATE),
                Arguments.of("2012-03-01", TIME_FORMAT.DATE),
                Arguments.of("2012-02-29", TIME_FORMAT.DATE),
                Arguments.of("2002-02-28", TIME_FORMAT.DATE),
                Arguments.of("2003-12-31", TIME_FORMAT.DATE),

                Arguments.of("2012-12-31T23:59:59", TIME_FORMAT.HOUR),
                Arguments.of("2012-12-31T00:00:00", TIME_FORMAT.HOUR),
                Arguments.of("2012-01-01T00:00:00", TIME_FORMAT.HOUR),
                Arguments.of("2012-01-01T13:00:59", TIME_FORMAT.HOUR)
        );
    }

    public static Stream<Arguments> provideDateInvalidParams() {
        return Stream.of(
                Arguments.of("2004-13"),
                Arguments.of("2004-00"),
                Arguments.of("2004-1"),
                Arguments.of("2004-B3"),
                Arguments.of("2004-Q5"),
                Arguments.of("2004-Q0"),
                Arguments.of("2004-Q"),
                Arguments.of("20040"),
                Arguments.of("2004-W02"),
                Arguments.of("2004-01-00"),
                Arguments.of("2004-00-01"),
                Arguments.of("2004-13-01"),
                Arguments.of("2004-02-30"),
                Arguments.of("2004-09-31"),
                Arguments.of("2004-08-32"),
                Arguments.of("2012-02-29T24:00:00"),
                Arguments.of("2012-02-29 13:00:00")
        );
    }

    public static Stream<Arguments> provideEmailValidationParams() {
        return Stream.of(
                Arguments.of("soemone@domain.com", true),
                Arguments.of("soemone@domain.foo.com", true),
                Arguments.of("soemone@domain.foo.bar.com", true),
                Arguments.of("name.soemone@domain.com", true),
                Arguments.of("n1ame.soem4one@domain.foo.com", true),
                Arguments.of("name.soemone@domain.foo.bar.com", true),
                Arguments.of("name.soemone@domai3n.foo.bar.com", true),
                Arguments.of("name.soe4mone@domain.eu", true),
                Arguments.of("name.soemo2ne@domain.int", true),
                Arguments.of("name.s1232oemone@do3main.org", true),
                Arguments.of("NAME.S1232OEMONE@DO3MAIn.org", true),
                Arguments.of("name.soemone-nodomain", false),
                Arguments.of("namesoemonenodomain", false),
                Arguments.of("name@192.168.1.1", true)
        );
    }

    public static Stream<Arguments> provideSdmxBeanUtilParams() {
        return Stream.of(
                Arguments.of(false, false, TERTIARY_BOOL.UNSET),
                Arguments.of(false, true, TERTIARY_BOOL.UNSET),
                Arguments.of(true, false, TERTIARY_BOOL.FALSE),
                Arguments.of(true, true, TERTIARY_BOOL.TRUE)
        );
    }

    public static Stream<Arguments> provideValidationUtilValidParams() {
        return Stream.of(
                Arguments.of("PAOK", false),
                Arguments.of("PAOK1", false),
                Arguments.of("paok1", false),
                Arguments.of("paok$4", false),
                Arguments.of("paok@4_ole", false),
                Arguments.of("1233", true),
                Arguments.of("123$3@", true),
                Arguments.of(null, true),
                Arguments.of(null, false),
                Arguments.of("", true),
                Arguments.of("", false)
        );
    }

    public static Stream<Arguments> provideValidationUtilInvalidParams() {
        return Stream.of(
                Arguments.of("1233k_$4-@*ole*_1234_", false),
                Arguments.of("123A-Z", false),
                Arguments.of("(1.0)", false),
                Arguments.of("(1.0)", true),
                Arguments.of("STS)", true),
                Arguments.of("STS)", false),
                Arguments.of("123STS)", true),
                Arguments.of("$@STS)", false)
        );
    }

    public static Stream<Arguments> provideMaintainableRefParams() {
        return Stream.of(
                Arguments.of(null, false, null, false, null, false, false),
                Arguments.of(null, false, null, false, "", false, false),
                Arguments.of(null, false, "", false, "", false, false),
                Arguments.of(null, false, "", false, " ", false, false),
                Arguments.of("", false, null, false, null, false, false),
                Arguments.of("", false, null, false, "", false, false),
                Arguments.of(" ", false, "", false, " ", false, false),
                Arguments.of(null, false, null, false, "1.0", true, false),
                Arguments.of("", false, "", false, "1.0", true, false),
                Arguments.of(" ", false, " ", false, "1.0", true, false),
                Arguments.of(null, false, "TEST", true, null, false, false),
                Arguments.of("", false, "TEST", true, null, false, false),
                Arguments.of("", false, "TEST", true, "", false, false),
                Arguments.of(null, false, "TEST", true, "", false, false),
                Arguments.of(null, false, "TEST", true, "1.0", true, false),
                Arguments.of("AGENCY", true, "TEST", true, "", false, false),
                Arguments.of("AGENCY", true, "TEST", true, "1.0", true, false),
                Arguments.of("AGENCY", true, "TST", true, "2.0", true, false),
                Arguments.of("AGNCY", true, "TEST", true, "2.0", true, false),
                Arguments.of("AGENCY", true, "TEST", true, "2.0", true, true)
        );
    }

    public static Stream<Arguments> provideStreamUtilParams() {
        return Stream.of(
                Arguments.of("src/test/resources/TestFile.csv", 3, 3),
                Arguments.of("src/test/resources/TestFile.csv", 0, 0)
        );
    }

    public static Stream<Arguments> provideStructureReferenceParams() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, null, ""),
                Arguments.of(null, " ", ""),
                Arguments.of("", null, null),
                Arguments.of("", null, " "),
                Arguments.of("", " ", null),
                Arguments.of(" ", null, null),
                Arguments.of(" ", "", null),
                Arguments.of(null, null, "1.0"),
                Arguments.of("", "", "1.0"),
                Arguments.of(null, "TEST", null),
                Arguments.of("", "TEST", null),
                Arguments.of(null, "TEST", ""),
                Arguments.of(null, "TEST", "1.0"),
                Arguments.of("AGENCY", "TEST", null),
                Arguments.of("AGENCY", "TEST", ""),
                Arguments.of("AGENCY", "TEST", "1.0"),
                Arguments.of("AGENCY", "TST", "2.0"),
                Arguments.of("AGNCY", "TEST", "2.0"),
                Arguments.of("AGENCY", "TEST", "2.0")
        );
    }

    public static Stream<Arguments> provideStructureReferenceUrnParams() {
        return Stream.of(
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.GroupDimensionDescriptor=ESTAT:STS(2.0).Sibling", SDMX_STRUCTURE_TYPE.GROUP, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:STS(2.0)", SDMX_STRUCTURE_TYPE.DSD, false),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).SSTSCONS", SDMX_STRUCTURE_TYPE.CATEGORY, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.1).14.1440.STS.SSTSCONS", SDMX_STRUCTURE_TYPE.CATEGORY, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISO:CL_3166A2(1.0).AR", SDMX_STRUCTURE_TYPE.CODE, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", SDMX_STRUCTURE_TYPE.DSD, false),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=SDMX:SUBJECT_MATTER_DOMAINS(1.0).1.2", SDMX_STRUCTURE_TYPE.CATEGORY, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataAttribute=SDMX:CONTACT_METADATA(1.0).CONTACT_REPORT.CONTACT_DETAILS.CONTACT_NAME", SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE, true),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=TFFS.ABC:EXTERNAL_DEBT(1.0)", SDMX_STRUCTURE_TYPE.DATAFLOW, false),
                Arguments.of("urn:sdmx:org.sdmx.infomodel.base.Agency=TFFS", SDMX_STRUCTURE_TYPE.AGENCY, true)
        );
    }


    public static Stream<Arguments> provideXmlUtilParams() {
        return Stream.of(
                Arguments.of("src/test/resources/TestFile.csv", false),
                Arguments.of("src/test/resources/queryResponse-estat-sts.xml", true)
        );
    }

}
