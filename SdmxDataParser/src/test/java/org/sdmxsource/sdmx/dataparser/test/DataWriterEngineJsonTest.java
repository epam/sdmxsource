package org.sdmxsource.sdmx.dataparser.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DIMENSION_AT_OBSERVATION;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
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
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.PrimaryMeasureSuperBean;
import org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport.JsonDataWriterEngine;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.AttributeSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DataStructureSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DimensionSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.PrimaryMeasureSuperBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class DataWriterEngineJsonTest {

    private final static String FREQ = "FREQ";
    private final static String ADJUSTMENT = "ADJUSTMENT";
    private final static String STS_ACTIVITY = "STS_ACTIVITY";
    private final static String TIME_PERIOD = "TIME_PERIOD";
    private final static String TEST_AGENCY_ID = "TEST";
    private final static String VERSION = "1.0";
    private static final String OBS_VALUE = "OBS_VALUE";
    private static final String DECIMALS = "DECIMALS";
    @Mock
    private SdmxSuperBeanRetrievalManager sdmxSuperBeanRetrievalManager;

    @Test
    void check_21_SERIES_0() throws IOException {

        Map<String, CodelistSuperBean> codelistSuperBeanMap = getCodelistSuperBeanMap();
        Map<String, ConceptSuperBean> conceptSuperBeanMap = getConceptSuperBeanMap(codelistSuperBeanMap);

        DataStructureSuperBean superDsd = getDataStructureSuperBean(codelistSuperBeanMap, conceptSuperBeanMap);
        DataStructureBean dsd = superDsd.getBuiltFrom();
        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);
        DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries0(dsd, dataflowBean);

        var os = new ByteArrayOutputStream();
        when(sdmxSuperBeanRetrievalManager.getDataStructureSuperBean(any())).thenReturn(superDsd);
        var dataWriterEngine = new JsonDataWriterEngine(os, sdmxSuperBeanRetrievalManager, false);

        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineJsonTest.class.getClassLoader()
                .getResourceAsStream("writer/Json_ts_0.json")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void check_21_SERIES_1() throws IOException {

        Map<String, CodelistSuperBean> codelistSuperBeanMap = getCodelistSuperBeanMap();
        Map<String, ConceptSuperBean> conceptSuperBeanMap = getConceptSuperBeanMap(codelistSuperBeanMap);

        DataStructureSuperBean superDsd = getDataStructureSuperBean(codelistSuperBeanMap, conceptSuperBeanMap);
        DataStructureBean dsd = superDsd.getBuiltFrom();
        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);
        DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries1(dsd, dataflowBean);

        var os = new ByteArrayOutputStream();
        when(sdmxSuperBeanRetrievalManager.getDataStructureSuperBean(any())).thenReturn(superDsd);
        var dataWriterEngine = new JsonDataWriterEngine(os, sdmxSuperBeanRetrievalManager, false);

        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineJsonTest.class.getClassLoader()
                .getResourceAsStream("writer/Json_ts_empty.json")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void check_21_SERIES_2() throws IOException {

        Map<String, CodelistSuperBean> codelistSuperBeanMap = getCodelistSuperBeanMap();
        Map<String, ConceptSuperBean> conceptSuperBeanMap = getConceptSuperBeanMap(codelistSuperBeanMap);

        DataStructureSuperBean superDsd = getDataStructureSuperBean(codelistSuperBeanMap, conceptSuperBeanMap);
        DataStructureBean dsd = superDsd.getBuiltFrom();
        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);
        DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries2(dsd, dataflowBean);

        var os = new ByteArrayOutputStream();
        when(sdmxSuperBeanRetrievalManager.getDataStructureSuperBean(any())).thenReturn(superDsd);
        var dataWriterEngine = new JsonDataWriterEngine(os, sdmxSuperBeanRetrievalManager, false);

        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineJsonTest.class.getClassLoader()
                .getResourceAsStream("writer/Json_ts_empty_2.json")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void check_21_SERIES_3() throws IOException {

        Map<String, CodelistSuperBean> codelistSuperBeanMap = getCodelistSuperBeanMap();
        Map<String, ConceptSuperBean> conceptSuperBeanMap = getConceptSuperBeanMap(codelistSuperBeanMap);

        DataStructureSuperBean superDsd = getDataStructureSuperBean(codelistSuperBeanMap, conceptSuperBeanMap);
        DataStructureBean dsd = superDsd.getBuiltFrom();
        DatasetHeaderBean datasetHeaderBean = getDatasetHeaderBean(dsd, null);
        DataflowBean dataflowBean = getDataflowBean(dsd);
        List<Series> seriesList = buildSeries3(dsd, dataflowBean);

        var os = new ByteArrayOutputStream();
        when(sdmxSuperBeanRetrievalManager.getDataStructureSuperBean(any())).thenReturn(superDsd);
        var dataWriterEngine = new JsonDataWriterEngine(os, sdmxSuperBeanRetrievalManager, false);

        generate(dsd, dataflowBean, getHeaderBean(), datasetHeaderBean, dataWriterEngine, seriesList);

        final var expected = new String(Objects.requireNonNull(DataWriterEngineJsonTest.class.getClassLoader()
                .getResourceAsStream("writer/Json_ts_3.json")).readAllBytes());
        assertEquals(expected, os.toString(StandardCharsets.UTF_8));
    }

    private DataStructureSuperBean getDataStructureSuperBean(Map<String, CodelistSuperBean> codelistSuperBeanMap,
                                                             Map<String, ConceptSuperBean> conceptSuperBeanMap) {
        DataStructureBean dsd = buildDsd();
        List<DimensionSuperBean> dims = getDimensionSuperBeans(codelistSuperBeanMap, conceptSuperBeanMap, dsd);
        List<AttributeSuperBean> attrs = getAttributeSuperBeans(codelistSuperBeanMap, conceptSuperBeanMap, dsd);
        PrimaryMeasureSuperBean prim = new PrimaryMeasureSuperBeanImpl(dsd.getPrimaryMeasure(), null,
                conceptSuperBeanMap.get(OBS_VALUE));
        DataStructureSuperBean superDsd = new DataStructureSuperBeanImpl(dsd, dims, attrs, prim);
        return superDsd;
    }

    private List<AttributeSuperBean> getAttributeSuperBeans(Map<String, CodelistSuperBean> codelistSuperBeanMap,
                                                            Map<String, ConceptSuperBean> conceptSuperBeanMap,
                                                            DataStructureBean dsd) {
        List<AttributeSuperBean> attrs = dsd.getAttributes().stream()
                .map(a -> new AttributeSuperBeanImpl(a,
                        codelistSuperBeanMap.get(a.getId()),
                        conceptSuperBeanMap.get(a.getId())))
                .collect(Collectors.toList());
        return attrs;
    }

    private List<DimensionSuperBean> getDimensionSuperBeans(Map<String, CodelistSuperBean> codelistSuperBeanMap,
                                                            Map<String, ConceptSuperBean> conceptSuperBeanMap,
                                                            DataStructureBean dsd) {
        List<DimensionSuperBean> dims = dsd.getDimensionList().getDimensions().stream()
                .map(d -> new DimensionSuperBeanImpl(d,
                        codelistSuperBeanMap.get(d.getId()),
                        conceptSuperBeanMap.get(d.getId())))
                .collect(Collectors.toList());
        return dims;
    }

    private Map<String, ConceptSuperBean> getConceptSuperBeanMap(Map<String, CodelistSuperBean> codelistSuperBeanMap) {
        Map<String, ConceptSuperBean> conceptSuperBeanMap = new HashMap<>();
        final var schemeMutableBean = new ConceptSchemeMutableBeanImpl();
        schemeMutableBean.setId("TEST_CS");
        schemeMutableBean.setAgencyId("TEST");
        schemeMutableBean.addName("en", "TEST_CS");
        var conceptScheme = schemeMutableBean.getImmutableInstance();
        conceptSuperBeanMap.put(FREQ, new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme,
                schemeMutableBean.createItem(FREQ, FREQ)),
                codelistSuperBeanMap.get(FREQ)));
        conceptSuperBeanMap.put(ADJUSTMENT,
                new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme, schemeMutableBean.createItem(ADJUSTMENT,
                        ADJUSTMENT)),
                        codelistSuperBeanMap.get(ADJUSTMENT)));
        conceptSuperBeanMap.put(STS_ACTIVITY,
                new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme, schemeMutableBean.createItem(STS_ACTIVITY
                        , STS_ACTIVITY)),
                        codelistSuperBeanMap.get(STS_ACTIVITY)));
        conceptSuperBeanMap.put(TIME_PERIOD,
                new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme, schemeMutableBean.createItem(TIME_PERIOD,
                        TIME_PERIOD)),
                        codelistSuperBeanMap.get(TIME_PERIOD)));
        conceptSuperBeanMap.put(DECIMALS,
                new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme, schemeMutableBean.createItem(DECIMALS,
                        DECIMALS)),
                        codelistSuperBeanMap.get(DECIMALS)));
        conceptSuperBeanMap.put(OBS_VALUE, new ConceptSuperBeanImpl(new ConceptBeanImpl(conceptScheme,
                schemeMutableBean.createItem(OBS_VALUE, OBS_VALUE)), null));
        return conceptSuperBeanMap;
    }

    private Map<String, CodelistSuperBean> getCodelistSuperBeanMap() {
        Map<String, CodelistSuperBean> codelistSuperBeanMap = new HashMap<>();
        var codelistBean = new CodelistMutableBeanImpl();
        codelistBean.setAgencyId("SDMX");
        codelistBean.setId("CL_FREQ");
        codelistBean.addName("en", "CL_FREQ");
        codelistBean.createItem("A", "Annually");
        codelistBean.createItem("M", "Monthly");
        codelistBean.createItem("Q", "Quarterly");
        codelistSuperBeanMap.put(FREQ, new CodelistSuperBeanImpl(codelistBean.getImmutableInstance()));
        codelistBean = new CodelistMutableBeanImpl();
        codelistBean.setAgencyId("SDMX");
        codelistBean.setId("CL_ADJUSTMENT");
        codelistBean.addName("en", "CL_ADJUSTMENT");
        codelistBean.createItem("N", "No");
        codelistSuperBeanMap.put(ADJUSTMENT, new CodelistSuperBeanImpl(codelistBean.getImmutableInstance()));
        codelistBean = new CodelistMutableBeanImpl();
        codelistBean.setAgencyId("STS");
        codelistBean.setId("CL_STS_ACTIVITY");
        codelistBean.addName("en", "CL_STS_ACTIVITY");
        codelistBean.createItem("A", "Act");
        codelistSuperBeanMap.put(STS_ACTIVITY, new CodelistSuperBeanImpl(codelistBean.getImmutableInstance()));
        codelistBean = new CodelistMutableBeanImpl();
        codelistBean.setAgencyId("STS");
        codelistBean.setId("CL_DECIMALS");
        codelistBean.addName("en", "CL_DECIMALS");
        codelistBean.createItem("1", "1");
        codelistBean.createItem("2", "2");
        codelistBean.createItem("3", "3");
        codelistSuperBeanMap.put(DECIMALS, new CodelistSuperBeanImpl(codelistBean.getImmutableInstance()));
        return codelistSuperBeanMap;
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
                getConceptStructureReferenceBean(DECIMALS),
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

    private List<Series> buildSeries1(DataStructureBean dsd, DataflowBean dataflowBean) {
        final var result = new ArrayList<Series>();
        var seriesKey = Map.of(FREQ, "A", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("A.N.A", Map.of(), seriesKey, List.of()));
        seriesKey = Map.of(FREQ, "Q", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("Q.N.A", Map.of(DECIMALS, "1"), seriesKey, List.of()));
        seriesKey = Map.of(FREQ, "M", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("M.N.A", Map.of(DECIMALS, "2"), seriesKey, List.of()));
        return result;
    }

    private List<Series> buildSeries2(DataStructureBean dsd, DataflowBean dataflowBean) {
        final var result = new ArrayList<Series>();
        var seriesKey = Map.of(FREQ, "A", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("A.N.A", Map.of(DECIMALS, "2"), seriesKey, List.of()));
        seriesKey = Map.of(FREQ, "Q", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("Q.N.A", Map.of(DECIMALS, "1"), seriesKey, List.of()));
        return result;
    }

    private List<Series> buildSeries3(DataStructureBean dsd, DataflowBean dataflowBean) {
        final var result = new ArrayList<Series>();
        var seriesKey = Map.of(FREQ, "A", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("A.N.A", Map.of(DECIMALS, "2"), seriesKey, List.of()));
        seriesKey = Map.of(FREQ, "Q", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("Q.N.A", Map.of(DECIMALS, "1"), seriesKey, List.of(
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q1", "10"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q2", "11"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q3", "12"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q4", "25")
                                                                                )));
        seriesKey = Map.of(FREQ, "M", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("M.N.A", Map.of(), seriesKey, List.of(
                createObservation(dsd, dataflowBean, seriesKey, "2022-M01", "0"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M02", "1"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M03", "2"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M04", "3"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M05", "4"),
                createObservation(dsd, dataflowBean, seriesKey, "2022-M06", "5")
                                                                   )));
        return result;
    }

    private List<Series> buildSeries0(DataStructureBean dsd, DataflowBean dataflowBean) {
        final var result = new ArrayList<Series>();
        var seriesKey = Map.of(FREQ, "Q", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("Q.N.A", Map.of(DECIMALS, "1"), seriesKey, List.of(
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q1", "10"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q2", "11"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q3", "12"),
                createObservation(dsd, dataflowBean, seriesKey, "2021-Q4", "25")
                                                                                )));
        seriesKey = Map.of(FREQ, "M", ADJUSTMENT, "N", STS_ACTIVITY, "A");
        result.add(new Series("M.N.A", Map.of(), seriesKey, List.of(
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


    private void writeSeries(Series s, DataWriterEngine dataWriterEngine, DataStructureBean dsd) {
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

