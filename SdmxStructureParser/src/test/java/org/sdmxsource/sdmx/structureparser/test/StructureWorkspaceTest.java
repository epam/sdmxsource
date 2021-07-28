package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureFormat;
import org.sdmxsource.sdmx.structureparser.manager.impl.StructureWriterManagerImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.FileUtil;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.sdmxsource.sdmx.structureparser.test.SubmitStructureResponseBuilderTest.FILE_SUFFIX;
import static org.sdmxsource.sdmx.structureparser.test.SubmitStructureResponseBuilderTest.TMP_DIR;

public class StructureWorkspaceTest {

    private final StructureParsingManager parsingManager =
            new StructureParsingManagerImpl(new SdmxSourceReadableDataLocationFactory(),
                    new StructureParserFactory[0]);

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideV20StructureParsingParams")
    public void shouldCheckStructureParsingManagerV20(String file) {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);
        StructureWorkspace structureWorkspace = parsingManager.parseStructures(fileReadableDataLocation);
        assertNotNull(structureWorkspace);
        assertNotNull(structureWorkspace.getStructureBeans(false));
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideInvalidStructureParsingParams")
    public <T> void shouldCheckStructureParsingManagerForInvalidFile(String file, Class<T> exception) {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);

        assertThatThrownBy(() -> parsingManager.parseStructures(fileReadableDataLocation))
                .isInstanceOf(exception);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideV21StructureParsingParams")
    public void shouldCheckStructureParsingManagerV21(String file) {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);
        StructureWorkspace structureWorkspace = parsingManager.parseStructures(fileReadableDataLocation);
        assertNotNull(structureWorkspace);
        SdmxBeans sdmxBeans = structureWorkspace.getStructureBeans(false);
        assertNotNull(sdmxBeans);
        assertNotNull(sdmxBeans.getMutableBeans());
    }

    @Test
    public void shouldCheckStructureParsingManagerComponentMapV21() {
        String file = "src/test/resources/v21/StructureSet-sdmxv2.1-ESTAT+STS+2.0.xml";
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);
        StructureWorkspace structureWorkspace = parsingManager.parseStructures(fileReadableDataLocation);
        assertNotNull(structureWorkspace);
        SdmxBeans sdmxBeans = structureWorkspace.getStructureBeans(false);

        assertNotNull(sdmxBeans);
        assertNotEquals(0, sdmxBeans.getStructureSets().size());
        assertEquals(1, sdmxBeans.getStructureSets().iterator().next().getStructureMapList().size());
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideV21StructureParsingParams")
    public void shouldCheckReadWriteSdmxV21(String file) {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);
        SdmxBeans sdmxBeans = parsingManager.parseStructures(fileReadableDataLocation).getStructureBeans(false);
        assertNotNull(sdmxBeans);
        StructureWriterManager writerManager = new StructureWriterManagerImpl();
        var outputFile = FileUtil.createTemporaryFile(file.replaceAll(FILE_SUFFIX, "-out"), FILE_SUFFIX);
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            writerManager.writeStructures(sdmxBeans,
                    new SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT.SDMX_V21_STRUCTURE_DOCUMENT), outputStream);
            FileInputStream inputStream = new FileInputStream(outputFile);
            XMLParser.validateXML(inputStream, SDMX_SCHEMA.VERSION_TWO_POINT_ONE);
            inputStream.close();
            outputStream.close();
            ReadableDataLocation location = new ReadableDataLocationTmp(FileUtil.getNewestFile(TMP_DIR));
            SdmxBeans sdmxBeans2 = parsingManager.parseStructures(location).getStructureBeans(false);
            assertEquals(sdmxBeans.getAllMaintainables().size(), sdmxBeans2.getAllMaintainables().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/v21/CL_CODELIST_PARENT.xml", "src/test/resources/v21/CL_CODELIST_PARENT2.xml"})
    public void shouldCheckCodelistWithParentCode(String file) {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp(file);
        StructureWorkspace structureWorkspace = parsingManager.parseStructures(fileReadableDataLocation);

        assertNotNull(structureWorkspace);
        SdmxBeans sdmxBeans = structureWorkspace.getStructureBeans(false);
        assertNotNull(sdmxBeans);
        assertFalse(sdmxBeans.getCodelists().isEmpty());
        var codelist = sdmxBeans.getCodelists().stream()
                .filter(code -> !code.getItems().isEmpty())
                .iterator().next();

        assertNotNull(codelist);
    }
}
