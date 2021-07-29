package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.SubmitStructureResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.SubmitStructureResponseBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.FileUtil;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA.VERSION_TWO_POINT_ONE;

public class SubmitStructureResponseBuilderTest {

    final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    final static String FILE_SUFFIX = ".xml";
    private final SubmitStructureResponseBuilder responseBuilder = new SubmitStructureResponseBuilderImpl();

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideSchemaEnumTypes")
    public void shouldCheckBuildSuccessResponse(SDMX_SCHEMA version) {
        SdmxBeans sdmxObjects = new SdmxBeansImpl();
        sdmxObjects.addCodelist(getDefaultCodelistBean());
        var output = responseBuilder.buildSuccessResponse(sdmxObjects, version);
        var filePrefix = "TestBuildSuccessResponse" + version;
        File outputFile = FileUtil.createTemporaryFile(filePrefix, FILE_SUFFIX);
        try {
            output.save(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadableDataLocation dataLocation = new ReadableDataLocationTmp(outputFile);
        XMLParser.validateXML(dataLocation, version);
    }

    @Test
    public void shouldCheckBuildSuccessResponseFile() {
        String file = "src/test/resources/v21/NA_PENS+ESTAT+1.0.xml";
        ReadableDataLocation dataLocation = new ReadableDataLocationTmp(file);
        StructureParsingManager parsingManager = new StructureParsingManagerImpl(new SdmxSourceReadableDataLocationFactory(),
                new StructureParserFactory[0]);
        var structureWorkspace = parsingManager.parseStructures(dataLocation);
        SdmxBeans structureBeans = structureWorkspace.getStructureBeans(false);

        var output = responseBuilder.buildSuccessResponse(structureBeans, VERSION_TWO_POINT_ONE);
        String filePrefix = "TestBuildSuccessResponse" + VERSION_TWO_POINT_ONE;
        final File outputFile = FileUtil.createTemporaryFile(filePrefix, FILE_SUFFIX);
        try {
            output.save(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadableDataLocation dataLocation2 = new ReadableDataLocationTmp(outputFile);
        XMLParser.validateXML(dataLocation2, VERSION_TWO_POINT_ONE);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideSchemaEnumTypes")
    public void shouldCheckBuildErrorResponse(SDMX_SCHEMA version) {
        SdmxBeans sdmxObjects = new SdmxBeansImpl();
        sdmxObjects.addCodelist(getDefaultCodelistBean());
        String errorMessage = "Invalid syntax";
        var output = responseBuilder.buildErrorResponse(new SdmxSyntaxException(errorMessage),
                new StructureReferenceBeanImpl("TEST", "CL_TEST", "1.0", SDMX_STRUCTURE_TYPE.CODE_LIST), version);
        var filePrefix = "build/tmp/TestBuildErrorResponse" + version;
        File outputFile = FileUtil.createTemporaryFile(filePrefix, FILE_SUFFIX);
        try {
            output.save(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadableDataLocation dataLocation = new ReadableDataLocationTmp(outputFile);
        XMLParser.validateXML(dataLocation, version);

        assertTrue(FileUtil.readFileAsString(outputFile.getAbsolutePath()).contains(errorMessage));
    }

    private CodelistBean getDefaultCodelistBean() {
        var codelist = new CodelistMutableBeanImpl();
        codelist.setId("CL_TEST");
        codelist.setVersion("1.0");
        codelist.setAgencyId("TEST");
        codelist.addName("en", "Test Codelist");
        for (int i = 0; i < 10; i++) {
            CodeMutableBean item = new CodeMutableBeanImpl();
            item.setId("TEST_" + i);
            item.addName("en", "Name for " + item.getId());
            codelist.addItem(item);
        }
        return codelist.getImmutableInstance();
    }

}
