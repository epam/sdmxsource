package org.sdmxsource.sdmx.dataparser.engine.writer.jsonsupport;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean.TIME_DIMENSION_FIXED_ID;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.dataparser.engine.reader.json.JsonDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.test.Series;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.SuperBeansBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.structureretrieval.manager.SdmxSuperBeanRetrievalManagerImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

public class ReadWriteEngineTest {

    private final ReadableDataLocationFactory dataLocationFactory = new SdmxSourceReadableDataLocationFactory();
    private SdmxBeanRetrievalManager sdmxBeanRetrievalManager;

    @Test
    public void shouldReadWriteTimeseriesDataset() throws IOException {
        var superBeanRetrievalManager = getDataStructureSuperBean("structure/AGENCY_ID_SIMPLE_DF(2.0.0).xml");

        var dsdRef = new MaintainableRefBeanImpl("AGENCY_ID", "SIMPLE_DSD", "3.0.0");
        var dsdSuperBean = superBeanRetrievalManager.getDataStructureSuperBean(dsdRef);
        var dsd = dsdSuperBean.getBuiltFrom();

        var dataflowRef = new MaintainableRefBeanImpl("AGENCY_ID", "SIMPLE_DF", "2.0.0");
        var dataflowSuperBean = superBeanRetrievalManager.getDataflowSuperBean(dataflowRef);
        var dataflow = dataflowSuperBean.getBuiltFrom();

        var is = ReadWriteEngineTest.class.getClassLoader().getResourceAsStream("data/json/timeseries-init-data.json");
        byte[] initDataBytes = is.readAllBytes();
        var dataLocation = dataLocationFactory.getReadableDataLocation(initDataBytes);
        var reader = new JsonDataReaderEngine(dataLocation, sdmxBeanRetrievalManager, dsd, dataflow, null);

        assertTrue(reader.moveNextDataset());
        var series = readSeries(reader);
        var datasetAttributes = reader.getDatasetAttributes(); // TODO works only if attributes go before series
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataWriterEngine writer = new JsonDataWriterEngine(output, superBeanRetrievalManager, false);

        writer.startDataset(null, dataflow, dsd, reader.getCurrentDatasetHeaderBean());
        datasetAttributes.forEach(attribute -> writer.writeAttributeValue(attribute.getConcept(), attribute.getCode())); // TODO always writes attributes after series
        series.forEach(it -> writeSeries(writer, it, TIME_DIMENSION_FIXED_ID));

       /* writer.startGroup("A1"); // TODO unable to write group attributes
        writer.writeGroupKeyValue("DATA_DOMAIN", "cpi");
        writer.writeGroupKeyValue("COUNTRY", "c_1");
        writer.writeGroupKeyValue("COUNTERPART_AREA", "ca_1");
        writer.writeAttributeValue("A1", "group test");
*/
        writer.close();
        assertThatJson(output.toString()).whenIgnoringPaths("header").isEqualTo(new String(initDataBytes));
    }

    @Test
    public void shouldReadWriteFlatDataset() throws IOException {
        String dimAtObs = DatasetStructureReferenceBean.ALL_DIMENSIONS;
        var superBeanRetrievalManager = getDataStructureSuperBean("structure/AGENCY_SIMPLE_GENERAL_DF(1.0.0).xml");

        var dsdRef = new MaintainableRefBeanImpl("AGENCY", "GENERAL_SIMPLE_DSD", "1.0.0");
        var dsdSuperBean = superBeanRetrievalManager.getDataStructureSuperBean(dsdRef);
        var dsd = dsdSuperBean.getBuiltFrom();

        var dataflowRef = new MaintainableRefBeanImpl("AGENCY", "SIMPLE_GENERAL_DF", "1.0.0");
        var dataflowSuperBean = superBeanRetrievalManager.getDataflowSuperBean(dataflowRef);
        var dataflow = dataflowSuperBean.getBuiltFrom();

        var is = ReadWriteEngineTest.class.getClassLoader().getResourceAsStream("data/json/flat-init-data.json");
        byte[] initDataBytes = is.readAllBytes();
        var dataLocation = dataLocationFactory.getReadableDataLocation(initDataBytes);
        var reader = new JsonDataReaderEngine(dataLocation, sdmxBeanRetrievalManager, dsd, dataflow, null);

        assertTrue(reader.moveNextDataset());
        var series = readSeries(reader);
        var datasetAttributes = reader.getDatasetAttributes();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataWriterEngine writer = new JsonDataWriterEngine(output, superBeanRetrievalManager, true);

        DatasetStructureReferenceBean datasetStructureReferenceBean = new DatasetStructureReferenceBeanImpl(
            UUID.randomUUID().toString(), dsd.asReference(), null, null, dimAtObs);
        DatasetHeaderBean datasetHeaderBean = new DatasetHeaderBeanImpl(null, DATASET_ACTION.INFORMATION,
            datasetStructureReferenceBean, dataflowRef, null, null, null, null,
            -1, null, null);
        writer.startDataset(null, dataflow, dsd, datasetHeaderBean);
        datasetAttributes.forEach(attribute -> writer.writeAttributeValue(attribute.getConcept(), attribute.getCode()));
        series.forEach(it -> writeSeries(writer, it, TIME_DIMENSION_FIXED_ID)); // TODO works only if pass dim at obs as time dimension

       /* writer.startGroup("A1"); // TODO not working
        writer.writeGroupKeyValue("DATA_DOMAIN", "cpi");
        writer.writeGroupKeyValue("COUNTRY", "c_1");
        writer.writeGroupKeyValue("COUNTERPART_AREA", "ca_1");
        writer.writeAttributeValue("A1", "group test");*/

        writer.close();
        assertThatJson(output.toString()).whenIgnoringPaths("header")
            .isEqualTo(new String(initDataBytes));
    }

    private List<Series> readSeries(DataReaderEngine reader) {
        List<Series> series = new ArrayList<>();

        while (reader.moveNextKeyable()) {
            var currentKey = reader.getCurrentKey();

            var dimensions = currentKey.getKey().stream()
                .collect(Collectors.toMap(KeyValue::getConcept, KeyValue::getCode,(s, s2) -> s, LinkedHashMap::new));

            var attributes = currentKey.getAttributes().stream()
                .collect(Collectors.toMap(KeyValue::getConcept, KeyValue::getCode));

            List<Observation> observations = new ArrayList<>();
            while (reader.moveNextObservation()) {
                observations.add(reader.getCurrentObservation());
            }

            series.add(new Series("", attributes, dimensions, observations));
        }

        return series;
    }

    private void writeSeries(DataWriterEngine writer, Series series, String dimAtObs) {
        writer.startSeries();
        series.getDimensions().forEach(writer::writeSeriesKeyValue);
        series.getAttributes().forEach(writer::writeAttributeValue);
        series.getObservations().forEach(obs -> writeObservation(writer, obs, dimAtObs));
    }

    private void writeObservation(DataWriterEngine writer, Observation obs, String dimAtObs) {
        writer.writeObservation(dimAtObs, obs.getObsTime(), obs.getObservationValue(), obs.getAnnotations().toArray(AnnotationBean[]::new));
        obs.getAttributes().forEach(keyValue -> writer.writeAttributeValue(keyValue.getConcept(), keyValue.getCode()));
    }

    private SdmxSuperBeanRetrievalManager getDataStructureSuperBean(String structurePath) {
        try (var is = SeriesDataWriter.class.getClassLoader().getResourceAsStream(structurePath)) {
            StructureParsingManager manager = new StructureParsingManagerImpl(dataLocationFactory, new StructureParserFactory[0]);
            StructureWorkspace structureWorkspace = manager.parseStructures(dataLocationFactory.getReadableDataLocation(is));
            sdmxBeanRetrievalManager = new InMemoryRetrievalManager(structureWorkspace.getStructureBeans(true));
            return new SdmxSuperBeanRetrievalManagerImpl(new SuperBeansBuilderImpl(), sdmxBeanRetrievalManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
