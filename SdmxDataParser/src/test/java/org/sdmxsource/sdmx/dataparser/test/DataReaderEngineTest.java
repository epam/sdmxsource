package org.sdmxsource.sdmx.dataparser.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.factory.DataReaderFactory;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.engine.ReportedDateEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.FixedConceptEngineImpl;
import org.sdmxsource.sdmx.dataparser.engine.impl.ReportedDateEngineImpl;
import org.sdmxsource.sdmx.dataparser.engine.reader.CompactDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.engine.reader.GenericDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.factory.SdmxDataReaderFactory;
import org.sdmxsource.sdmx.dataparser.manager.DataReaderManager;
import org.sdmxsource.sdmx.dataparser.manager.impl.DataInformationManagerImpl;
import org.sdmxsource.sdmx.dataparser.manager.impl.DataReaderManagerImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

public class DataReaderEngineTest {

    private final static String FREQ = "FREQ";
    private final static String ADJUSTMENT = "ADJUSTMENT";
    private final static String STS_ACTIVITY = "STS_ACTIVITY";
    private final static String TEST_AGENCY_ID = "TEST";
    private final static String VERSION = "1.0";

    private final SdmxSourceReadableDataLocationFactory factory = new SdmxSourceReadableDataLocationFactory();
    private final ReportedDateEngine reportedDateEngine = new ReportedDateEngineImpl();


    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/data/Compact-VersionTwo.xml",
            "src/test/resources/data/Compact-VersionTwoPointOne.xml"})
    public void shouldCheckCompactDataReader(String file) {
        DataStructureBean dsd = buildDsd();
        var sourceData = factory.getReadableDataLocation(new File(file));
        var dataReaderEngine = new CompactDataReaderEngine(sourceData, dsd, null, null);

        validateDataReaderEngine(dataReaderEngine);
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/data/Generic-VersionTwo.xml",
            "src/test/resources/data/Generic-VersionTwoPointOne.xml"})
    public void shouldCheckGenericDataReader(String file) {
        DataStructureBean dsd = buildDsd();
        var sourceData = factory.getReadableDataLocation(new File(file));
        var dataReaderEngine = new GenericDataReaderEngine(sourceData, dsd, null, null);

        validateDataReaderEngine(dataReaderEngine);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.dataparser.data.DataReaderEngineProvider#provideFileLocations")
    public void shouldCheckDataReaderManager(String file) {
        DataStructureBean dsd = buildDsd();
        var sourceData = factory.getReadableDataLocation(new File(file));
        DataReaderManager manager = new DataReaderManagerImpl(new DataReaderFactory[0]);
        var dataReaderEngine = manager.getDataReaderEngine(sourceData, dsd, null);

        validateDataReaderEngine(dataReaderEngine);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.dataparser.data.DataReaderEngineProvider#provideFileLocations")
    public void shouldCheckReportedDateEngine(String file) {
        DataStructureBean dsd = buildDsd();
        var sdmxDataReaderFactory = new SdmxDataReaderFactory(new DataInformationManagerImpl(reportedDateEngine,
                new FixedConceptEngineImpl()), null);
        var sourceData = factory.getReadableDataLocation(new File(file));
        var dataReaderEngine = sdmxDataReaderFactory.getDataReaderEngine(sourceData, dsd, null, null);

        validateDataReaderEngine(dataReaderEngine);
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.dataparser.data.DataReaderEngineProvider#provideBopFileLocations")
    public void shouldCheckBop(String dataFile, String dsdFile) {
        var retrievalManager = new InMemoryRetrievalManager();
        ReadableDataLocation dataLocation = factory.getReadableDataLocation(new File(dsdFile));
        StructureParsingManager manager = new StructureParsingManagerImpl(new SdmxSourceReadableDataLocationFactory(),
                new StructureParserFactory[0]);
        StructureWorkspace structureWorkspace = manager.parseStructures(dataLocation);
        retrievalManager.saveStructures(structureWorkspace.getStructureBeans(false));

        var sdmxDataReaderFactory = new SdmxDataReaderFactory(new DataInformationManagerImpl(reportedDateEngine,
                new FixedConceptEngineImpl()), null);
        var sourceData = factory.getReadableDataLocation(new File(dataFile));
        var dataReaderEngine = sdmxDataReaderFactory.getDataReaderEngine(sourceData, retrievalManager);

        assertAll(
                () -> assertNotNull(dataReaderEngine),
                () -> assertNotNull(dataReaderEngine.getHeader()),
                () -> assertTrue(dataReaderEngine.moveNextDataset())
        );
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.dataparser.data.DataReaderEngineProvider#provideFileLocationsAndDimensions")
    public void shouldCheckDimensionAtObservation(String file, String fileDimAtObs) {
        DataStructureBean dsd = buildDsd();
        var sdmxDataReaderFactory = new SdmxDataReaderFactory(new DataInformationManagerImpl(reportedDateEngine,
                new FixedConceptEngineImpl()), null);
        var sourceData = factory.getReadableDataLocation(new File(file));
        var dataReaderEngine = sdmxDataReaderFactory.getDataReaderEngine(sourceData, dsd, null, null);

        assertAll(
                () -> assertNotNull(dataReaderEngine),
                () -> assertNotNull(dataReaderEngine.getHeader()),
                () -> assertTrue(dataReaderEngine.moveNextDataset()),
                () -> assertTrue(dataReaderEngine.moveNextKeyable()),
                () -> assertTrue(dataReaderEngine.moveNextObservation())
        );

        Keyable currentKey = dataReaderEngine.getCurrentKey();
        var currentObs = dataReaderEngine.getCurrentObservation();

        validateDimensionAtObservation("A", fileDimAtObs, FREQ, currentKey, currentObs);
        validateDimensionAtObservation("N", fileDimAtObs, ADJUSTMENT, currentKey, currentObs);
        validateDimensionAtObservation("A", fileDimAtObs, STS_ACTIVITY, currentKey, currentObs);
        assertEquals("2005", currentObs.getObsTime());
    }

    private <T extends DataReaderEngine> void validateDataReaderEngine(T dataReaderEngine) {
        assertAll(
                () -> assertNotNull(dataReaderEngine),
                () -> assertNotNull(dataReaderEngine.getHeader()),
                () -> assertTrue(dataReaderEngine.moveNextDataset()),
                () -> assertTrue(dataReaderEngine.moveNextKeyable()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertEquals("Q", dataReaderEngine.getCurrentKey().getKeyValue(FREQ)),
                () -> assertEquals("N", dataReaderEngine.getCurrentKey().getKeyValue(ADJUSTMENT)),
                () -> assertEquals("A", dataReaderEngine.getCurrentKey().getKeyValue(STS_ACTIVITY)),
                () -> assertEquals("2005-Q1", dataReaderEngine.getCurrentObservation().getObsTime()));
    }

    private DataStructureBean buildDsd() {
        var dsdMutableObject = dsdMutableObject();
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(FREQ),
                getCodeListStructureReferenceBean("SDMX", "CL_FREQ"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(ADJUSTMENT),
                getCodeListStructureReferenceBean("SDMX", "CL_ADJUSTMENT"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(STS_ACTIVITY),
                getCodeListStructureReferenceBean("STS", "CL_STS_ACTIVITY"));

        DimensionMutableBean dimensionMutableBean = new DimensionMutableBeanImpl();
        dimensionMutableBean.setConceptRef(getConceptStructureReferenceBean("TIME_PERIOD"));
        dimensionMutableBean.setTimeDimension(true);
        dsdMutableObject.addDimension(dimensionMutableBean);
        dsdMutableObject.addPrimaryMeasure(getConceptStructureReferenceBean("OBS_VALUE"));

        AttributeMutableBean attributeMutableObject = dsdMutableObject.addAttribute(
                getConceptStructureReferenceBean("DECIMALS"),
                getCodeListStructureReferenceBean("STS", "CL_DECIMALS"));
        attributeMutableObject.setAttachmentLevel(DIMENSION_GROUP);
        attributeMutableObject.setDimensionReferences(Arrays.asList(FREQ, ADJUSTMENT, STS_ACTIVITY));
        attributeMutableObject.setAssignmentStatus("Mandatory");
        return dsdMutableObject.getImmutableInstance();
    }

    private DataStructureMutableBeanImpl dsdMutableObject() {
        var dsdMutableObject = new DataStructureMutableBeanImpl();
        dsdMutableObject.setAgencyId(TEST_AGENCY_ID);
        dsdMutableObject.setId("TEST_DSD");
        dsdMutableObject.setVersion(VERSION);
        dsdMutableObject.addName("en", "Test data");
        return dsdMutableObject;
    }

    private StructureReferenceBeanImpl getConceptStructureReferenceBean(String identfiableId) {
        return new StructureReferenceBeanImpl(TEST_AGENCY_ID, "TEST_CS", VERSION, CONCEPT, identfiableId);
    }

    private StructureReferenceBeanImpl getCodeListStructureReferenceBean(String agencyId, String maintainableId) {
        return new StructureReferenceBeanImpl(agencyId, maintainableId, VERSION, CODE_LIST);
    }

    private void validateDimensionAtObservation(String expectedValue, String fileDimAtObs, String dimAtObs,
                                                Keyable currentKey, Observation currentObs) {
        if (!fileDimAtObs.equals(dimAtObs)) {
            assertEquals(expectedValue, currentKey.getKeyValue(dimAtObs));
        } else {
            assertEquals(expectedValue, currentObs.getCrossSectionalValue().getCode());
        }
    }


}

