package org.sdmxsource.sdmx.dataparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DIMENSION_AT_OBSERVATION;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.dataparser.engine.writer.streaming.StreamCompactDataWriterEngine;
import org.sdmxsource.sdmx.dataparser.engine.writer.streaming.StreamGenericDataWriterEngine;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

class DataWriterEngineTest {

    private final static String FREQ = "FREQ";
    private final static String ADJUSTMENT = "ADJUSTMENT";
    private final static String STS_ACTIVITY = "STS_ACTIVITY";
    private final static String TIME_PERIOD = "TIME_PERIOD";
    private final static String TEST_AGENCY_ID = "TEST";
    private final static String VERSION = "1.0";
    private static final String OBS_VALUE = "OBS_VALUE";

    @Test
    void checkStreaming_COMPACT_21_SERIES() throws IOException {
        DataStructureBean dsd = buildDsd();

        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamCompactDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);

        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);

        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);
        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineTest.class.getClassLoader()
                .getResourceAsStream("writer/Compact_ts.xml")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void checkStreaming_COMPACT_21_FLAT() throws IOException {
        DataStructureBean dsd = buildDsd();
        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamCompactDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);
        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);

        generate(dsd, dataflowBean, getHeaderBean(), getDatasetHeaderBean(dsd, DIMENSION_AT_OBSERVATION.ALL.getVal()),
                dataWriterEngine, seriesList);

        System.out.println(os.toString(StandardCharsets.UTF_8));

        final var expected = new String(Objects.requireNonNull(DataWriterEngineTest.class.getClassLoader()
                .getResourceAsStream("writer/Compact_flat.xml")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void checkStreaming_GENERIC_21_SERIES() throws IOException {
        DataStructureBean dsd = buildDsd();

        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamGenericDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);

        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);

        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);
        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineTest.class.getClassLoader()
                .getResourceAsStream("writer/Generic_ts.xml")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void checkStreaming_GENERIC_21_FLAT() throws IOException {
        DataStructureBean dsd = buildDsd();
        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamCompactDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);
        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);

        generate(dsd, dataflowBean, getHeaderBean(), getDatasetHeaderBean(dsd, DIMENSION_AT_OBSERVATION.ALL.getVal()),
                dataWriterEngine, seriesList);

        System.out.println(os.toString(StandardCharsets.UTF_8));

        final var expected = new String(Objects.requireNonNull(DataWriterEngineTest.class.getClassLoader()
                .getResourceAsStream("writer/Generic_flat.xml")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void checkStreaming_COMPACT_21_DIFF_STRUCTURE_DATASET() throws IOException {
        DataStructureBean dsd = buildDsd();
        DataStructureBean dsd2 = buildDifferentDsd();

        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamCompactDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);

        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);

        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);
        final var headerBean = getHeaderBean();
        Exception ex = assertThrows(RuntimeException.class, () -> {
            generateDiffDatasets(dsd, dsd2, headerBean, datasetHeaderBean, dataWriterEngine, seriesList);
        });

        assertEquals("java.lang.IllegalArgumentException: Datasets with different structure not supported.", ex.getMessage());

    }

    @Test
    void checkStreaming_GENERIC_21_DIFF_STRUCTURE_DATASET() throws IOException {
        DataStructureBean dsd = buildDsd();
        DataStructureBean dsd2 = buildDifferentDsd();

        var os = new ByteArrayOutputStream();
        var dataWriterEngine = new StreamGenericDataWriterEngine(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, os);

        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);

        final DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries(dsd, dataflowBean);
        final var headerBean = getHeaderBean();
        Exception ex = assertThrows(RuntimeException.class, () -> {
            generateDiffDatasets(dsd, dsd2, headerBean, datasetHeaderBean, dataWriterEngine, seriesList);
        });

        assertEquals("java.lang.IllegalArgumentException: Datasets with different structure not supported.", ex.getMessage());

    }

    private void generate(DataStructureBean dsd, DataflowBean dataflowBean, HeaderBean headerBean,
                          DatasetHeaderBean datasetHeaderBean,
                          DataWriterEngine dataWriterEngine, List<Series> seriesList) {

        dataWriterEngine.writeHeader(headerBean);
        dataWriterEngine.startDataset(null, dataflowBean, dsd, datasetHeaderBean);

        seriesList.forEach(s -> {
            writeSeries(s, dataWriterEngine, dsd);

            List<Observation> observations = s.getObservations();
            observations.forEach(obs -> {
                final var flat = DIMENSION_AT_OBSERVATION.ALL.getVal()
                        .equals(datasetHeaderBean.getDataStructureReference().getDimensionAtObservation());
                writeObservation(obs, dataWriterEngine, flat);
            });
        });

        dataWriterEngine.close();
    }

    private void generateDiffDatasets(DataStructureBean dsd, DataStructureBean dsd2,
                                      HeaderBean headerBean, DatasetHeaderBean datasetHeaderBean,
                                      DataWriterEngine dataWriterEngine, List<Series> seriesList) {

        dataWriterEngine.writeHeader(headerBean);
        dataWriterEngine.startDataset(null, getDataflowBean(dsd), dsd, datasetHeaderBean);

        seriesList.forEach(s -> {
            writeSeries(s, dataWriterEngine, dsd);

            List<Observation> observations = s.getObservations();
            observations.forEach(obs -> {
                final var flat = DIMENSION_AT_OBSERVATION.ALL.getVal()
                        .equals(datasetHeaderBean.getDataStructureReference().getDimensionAtObservation());
                writeObservation(obs, dataWriterEngine, flat);
            });
        });

        dataWriterEngine.startDataset(null, getDataflowBean(dsd2), dsd2, datasetHeaderBean);

        dataWriterEngine.close();
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
        dimensionMutableBean.setConceptRef(getConceptStructureReferenceBean(TIME_PERIOD));
        dimensionMutableBean.setTimeDimension(true);
        dsdMutableObject.addDimension(dimensionMutableBean);
        dsdMutableObject.addPrimaryMeasure(getConceptStructureReferenceBean(OBS_VALUE));


        AttributeMutableBean attributeMutableObject = dsdMutableObject.addAttribute(
                getConceptStructureReferenceBean("DECIMALS"),
                getCodeListStructureReferenceBean("STS", "CL_DECIMALS"));
        attributeMutableObject.setAttachmentLevel(DIMENSION_GROUP);
        attributeMutableObject.setDimensionReferences(Arrays.asList(FREQ, ADJUSTMENT, STS_ACTIVITY));
        attributeMutableObject.setAssignmentStatus("Mandatory");
        return dsdMutableObject.getImmutableInstance();
    }

    private DataStructureBean buildDifferentDsd() {
        var dsdMutableObject = dsdMutableObject();
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(FREQ),
                getCodeListStructureReferenceBean("SDMX", "CL_FREQ"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(ADJUSTMENT),
                getCodeListStructureReferenceBean("SDMX", "CL_ADJUSTMENT"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(STS_ACTIVITY),
                getCodeListStructureReferenceBean("SDMX", "CL_STS_ACTIVITY2"));

        DimensionMutableBean dimensionMutableBean = new DimensionMutableBeanImpl();
        dimensionMutableBean.setConceptRef(getConceptStructureReferenceBean(TIME_PERIOD));
        dimensionMutableBean.setTimeDimension(true);
        dsdMutableObject.addDimension(dimensionMutableBean);
        dsdMutableObject.addPrimaryMeasure(getConceptStructureReferenceBean(OBS_VALUE));


        AttributeMutableBean attributeMutableObject = dsdMutableObject.addAttribute(
                getConceptStructureReferenceBean("DECIMALS"),
                getCodeListStructureReferenceBean("STS", "CL_DECIMALS"));
        attributeMutableObject.setAttachmentLevel(DIMENSION_GROUP);
        attributeMutableObject.setDimensionReferences(Arrays.asList(FREQ, ADJUSTMENT, STS_ACTIVITY));
        attributeMutableObject.setAssignmentStatus("Mandatory");
        return dsdMutableObject.getImmutableInstance();
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

    private HeaderBean getHeaderBean() {
        String senderId = "SENDER_ID";
        var sender = new PartyBeanImpl(List.of(), senderId, List.of(), "+02:00");
        Calendar c = Calendar.getInstance();
        int zoneOffSet = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now()).getTotalSeconds() / 60 / 60;
        c.set(2021, Calendar.JULY, 7, zoneOffSet, 0, 0);
        var prepared = c.getTime();
        return new HeaderBeanImpl("MESSAGE_ID", prepared, null, null, null, sender, true);
    }

    private DatasetHeaderBean getDatasetHeaderBean(DataStructureBean dsd, String dimensionAtObservation) {
        var datasetStructureReferenceBean = new DatasetStructureReferenceBeanImpl("DATASET_ID", dsd.asReference(),
                null, null, dimensionAtObservation);
        MaintainableRefBean dataProviderRef = new MaintainableRefBeanImpl("AGENCY", "FLOW_ID", "1.0");

        return new DatasetHeaderBeanImpl(null, DATASET_ACTION.INFORMATION,
                datasetStructureReferenceBean, dataProviderRef, null, null, null, null,
                -1, null, null);
    }

    private List<Series> buildSeries(DataStructureBean dsd, DataflowBean dataflowBean) {
        final var result = new ArrayList<Series>();
        final var seriesKey = Map.of(FREQ, "Q", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("Q.N.A", null, seriesKey, List.of(
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q1", "10"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q2", "11"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q3", "12"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q4", "25")
        )));
        result.add(new Series("M.N.A", null, seriesKey, List.of(
                createObservation(dsd, dataflowBean, seriesKey, "2022-M01", "0"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M02", "1"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M03", "2"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M04", "3"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M05", "4"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M06", "5")
        )));
        return result;
    }

    private ObservationImpl createObservation(DataStructureBean dsd, DataflowBean dataflowBean,
                                              Map<String, String> seriesKey, String obsTime, String obsValue) {
        final var obsKey = new HashMap<>(seriesKey);
        obsKey.put(TIME_PERIOD, obsTime);
        obsKey.put(OBS_VALUE, obsValue);
        List<KeyValue> keys = obsKey.entrySet()
                .stream()
                .map(e -> new KeyValueImpl(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        var timeFormat = DateUtil.getTimeFormatOfDate(obsKey.get(TIME_PERIOD));
        final var keyable = new KeyableImpl(dataflowBean, dsd, keys, new ArrayList<>(), timeFormat);
        return new ObservationImpl(keyable, obsKey.get(TIME_PERIOD), obsKey.get(OBS_VALUE), List.of());
    }


    protected void writeSeries(Series s, DataWriterEngine dataWriterEngine, DataStructureBean dsd) {
        Map<String, String> dimensions = s.getDimensions();
        dataWriterEngine.startSeries();
        for (var dimensionBean : dsd.getDimensionList().getDimensions()) {
            if (dimensionBean.isMeasureDimension()) {
                continue;
            }
            if (dimensionBean.isTimeDimension()) {
                continue;
            }
            String id = dimensionBean.getId();
            String value = dimensions.get(id);
            dataWriterEngine.writeSeriesKeyValue(id, value);
        }
        var attributes = s.getAttributes();
        if (attributes != null) {
            attributes.forEach(dataWriterEngine::writeAttributeValue);
        }
    }

    protected void writeObservation(Observation obs, DataWriterEngine dataWriterEngine, boolean flat) {
        if (flat) {
            dataWriterEngine.writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, obs.getObsTime(),
                    obs.getObservationValue(), obs.getAnnotations().toArray(new AnnotationBean[0]));
        } else {
            dataWriterEngine.writeObservation(obs.getObsTime(), obs.getObservationValue(),
                    obs.getAnnotations().toArray(AnnotationBean[]::new));
        }
        obs.getAttributes().forEach(entry -> dataWriterEngine.writeAttributeValue(entry.getConcept(), entry.getCode()));
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

}

