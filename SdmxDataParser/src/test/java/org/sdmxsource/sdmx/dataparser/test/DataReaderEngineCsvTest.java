package org.sdmxsource.sdmx.dataparser.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.dataparser.engine.reader.csv.StreamCsvDataReaderEngine;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

class DataReaderEngineCsvTest {

    private final static String FREQ = "FREQ";
    private final static String ADJUSTMENT = "ADJUSTMENT";
    private final static String STS_ACTIVITY = "STS_ACTIVITY";
    private final static String TEST_AGENCY_ID = "TEST";
    private final static String VERSION = "1.0";

    private final SdmxSourceReadableDataLocationFactory factory = new SdmxSourceReadableDataLocationFactory();


    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/data/CsvTest01.csv"})
    void shouldCheckStreamCsvDataReader(String file) {
        DataStructureBean dsd = buildDsd();
        DataflowBean dataflowBean = getDataflowBean(dsd);
        var sourceData = factory.getReadableDataLocation(new File(file));
        var dataReaderEngine = new StreamCsvDataReaderEngine(sourceData, null, dsd, dataflowBean, null);

        validateDataReaderEngine(dataReaderEngine);
    }


    private <T extends DataReaderEngine> void validateDataReaderEngine(T dataReaderEngine) {
        assertAll(
                () -> assertNotNull(dataReaderEngine),
                () -> assertTrue(dataReaderEngine.moveNextDataset()),
                () -> assertTrue(dataReaderEngine.moveNextKeyable()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertEquals("Q.N.A", getKeyIdentifier(dataReaderEngine.getCurrentKey())),
                () -> assertEquals("Q", dataReaderEngine.getCurrentKey().getKeyValue(FREQ)),
                () -> assertEquals("N", dataReaderEngine.getCurrentKey().getKeyValue(ADJUSTMENT)),
                () -> assertEquals("A", dataReaderEngine.getCurrentKey().getKeyValue(STS_ACTIVITY)),
                () -> assertEquals("2021-Q1", dataReaderEngine.getCurrentObservation().getObsTime()),
                () -> assertEquals("10", dataReaderEngine.getCurrentObservation().getObservationValue()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertFalse(dataReaderEngine.moveNextObservation()),
                () -> assertTrue(dataReaderEngine.moveNextKeyable()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertTrue(dataReaderEngine.moveNextObservation()),
                () -> assertEquals("M.N.A", getKeyIdentifier(dataReaderEngine.getCurrentKey())),
                () -> assertEquals("M", dataReaderEngine.getCurrentKey().getKeyValue(FREQ)),
                () -> assertEquals("N", dataReaderEngine.getCurrentKey().getKeyValue(ADJUSTMENT)),
                () -> assertEquals("A", dataReaderEngine.getCurrentKey().getKeyValue(STS_ACTIVITY)),
                () -> assertEquals("2022-M02", dataReaderEngine.getCurrentObservation().getObsTime()),
                () -> assertEquals("1", dataReaderEngine.getCurrentObservation().getObservationValue()));
    }

    private DataflowBean getDataflowBean(DataStructureBean dsd) {
        DataflowMutableBean bean = new DataflowMutableBeanImpl();
        bean.setDataStructureRef(dsd.asReference());
        bean.setId("FLOW_ID");
        bean.addName("en", "FLOW_NAME");
        bean.setAgencyId("AGENCY");
        bean.setVersion("1.0");

        return bean.getImmutableInstance();
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

    private String getKeyIdentifier(Keyable seriesKey) {
        List<KeyValue> key = seriesKey.getKey();
        if (key.isEmpty()) {
            throw new InvalidParameterException("key does not have elements");
        }

        return key.stream()
                .map(KeyValue::getCode)
                .collect(Collectors.joining("."));
    }

}

