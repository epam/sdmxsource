package org.sdmxsource.sdmx.dataparser.engine.reader;

import org.sdmxsource.sdmx.api.constants.DATASET_POSITION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.util.stax.StaxUtil;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.*;

/**
 * The type Cross section data reader engine.
 */
public class CrossSectionDataReaderEngine extends AbstractSdmxDataReaderEngine {
    private static final long serialVersionUID = -4720059292567565010L;
    private Map<String, String> datasetAttributes = new HashMap<String, String>();
    private Map<String, String> sectionValues = new HashMap<String, String>();
    private Map<String, String> previousKey = new HashMap<String, String>();
    private List<String> dimensions = new ArrayList<String>();
    private Set<String> seriesAttributes = new HashSet<String>();
    private Set<String> obsAttributes = new HashSet<String>();
    private String obsValue;

    /**
     * Instantiates a new Cross section data reader engine.
     *
     * @param dataLocation           the data location
     * @param dataStructureBean      the data structure bean
     * @param dataflowBean           the dataflow bean
     * @param provisionAgreementBean the provision agreement bean
     */
    /*
     * Creates a reader engine based on the data location, and the data structure to use to interpret the data
     * @param dataLocation the location of the data
     * @param dataStructureBean the dsd to use to interpret the data
     */
    public CrossSectionDataReaderEngine(ReadableDataLocation dataLocation, DataStructureBean dataStructureBean, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
        this(dataLocation, null, dataStructureBean, dataflowBean, provisionAgreementBean);
    }

    /**
     * Creates a reader engine based on the data location, the location of available data structures that can be used to retrieve dsds, and the default dsd to use
     *
     * @param dataLocation           the location of the data
     * @param beanRetrieval          giving the ability to retrieve dsds for the datasets this reader engine is reading.  This can be null if there is only one relevent dsd - in which case the default dsd should be provided
     * @param dataStructureBean      the dsd to use if the beanRetrieval is null, or if the bean retrieval does not return the dsd for the given dataset
     * @param dataflowBean           the dataflow bean
     * @param provisionAgreementBean the provision agreement bean
     */
    public CrossSectionDataReaderEngine(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrieval, DataStructureBean dataStructureBean, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
        super(dataLocation, beanRetrieval, dataStructureBean, dataflowBean, provisionAgreementBean);
        reset();
    }


    @Override
    public DataReaderEngine createCopy() {
        return new CrossSectionDataReaderEngine(dataLocation, beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
    }

    @Override
    protected void setCurrentDsd(DataStructureBean currentDsd) {
        super.setCurrentDsd(currentDsd);
        dimensions = new ArrayList<String>();
        seriesAttributes = new HashSet<String>();
        obsAttributes = new HashSet<String>();
        for (DimensionBean currentDim : currentDsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            dimensions.add(currentDim.getId());
        }
        for (AttributeBean currentAttr : currentDsd.getSeriesAttributes(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
            seriesAttributes.add(currentAttr.getId());
        }
        for (AttributeBean currentAttr : currentDsd.getObservationAttributes(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
            obsAttributes.add(currentAttr.getId());
        }
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        List<KeyValue> kv = new ArrayList<KeyValue>();
        for (AttributeBean currentAttr : currentDsd.getDatasetAttributes()) {
            String concept = currentAttr.getConceptRef().getIdentifiableIds()[0];
            String val = datasetAttributes.get(concept);
            if (val != null) {
                kv.add(new KeyValueImpl(val, concept));
            }
        }
        return kv;
    }

    @Override
    protected Keyable processGroupNode() throws XMLStreamException {
        return null;
    }

    @Override
    protected Keyable processSeriesNode() throws XMLStreamException {
        List<KeyValue> key = new ArrayList<KeyValue>();
        for (String dim : dimensions) {
            key.add(new KeyValueImpl(getFromMap(dim), dim));
        }
        List<KeyValue> attributes = new ArrayList<KeyValue>();
        for (String attr : seriesAttributes) {
            String val = getFromMap(attr);
            if (val != null) {
                attributes.add(new KeyValueImpl(val, attr));
            }
        }

        Keyable returnKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, null, null, null);
        super.currentObs = processObsNode(returnKey);
        return returnKey;
    }

    private Observation processObsNode(Keyable key) throws XMLStreamException {
        List<KeyValue> attributes = new ArrayList<KeyValue>();
        for (String attr : obsAttributes) {
            String val = getFromMap(attr);
            if (val != null) {
                attributes.add(new KeyValueImpl(val, attr));
            }
        }
        return new ObservationImpl(key, getFromMap(DimensionBean.TIME_DIMENSION_FIXED_ID), obsValue, attributes, null);
    }


    @Override
    protected Observation processObsNode(XMLStreamReader parser)
            throws XMLStreamException {
        return processObsNode(getCurrentKey());
    }

    private String getFromMap(String conceptId) {
        String val = previousKey.get(conceptId);
        if (val == null) {
            val = datasetAttributes.get(conceptId);
        }
        return val;
    }


    @Override
    protected boolean next(boolean includeObs) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("DataSet")) {
                    datasetPosition = DATASET_POSITION.DATASET;
                    processDataSetNode();
                    return true;
                } else if (nodeName.equals("Group")) {
                    processSectionGroup();
                    continue;
                } else if (nodeName.equals("Section")) {
                    continue;
                } else if (nodeName.equals("OBS_VALUE")) {
                    processSectionObs();
                    return true;
                } else if (nodeName.equals("Annotations")) {
                    StaxUtil.skipNode(parser);
                } else {
                    throw new SdmxSyntaxException("Unexpected Node in XML: " + nodeName);
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
//				String nodeName = parser.getLocalName();
//				if(nodeName.equals("Series")) {
//					datasetPosition = null;
//				} else if(nodeName.equals("Group")) {
//					datasetPosition = null;
//				}
            }
        }
        datasetPosition = null;
        hasNext = false;
        return false;
    }


    private void processDataSetNode() {
        this.datasetHeaderBean = new DatasetHeaderBeanImpl(parser, headerBean);
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = parser.getAttributeLocalName(i);
            attributeId = getComponentId(attributeId);
            if (currentDsd.getComponent(attributeId) != null) {
                datasetAttributes.put(attributeId, parser.getAttributeValue(i));
            }
        }
    }

    private void processSectionGroup() {
        sectionValues.clear();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = parser.getAttributeLocalName(i);
            attributeId = getComponentId(attributeId);
            if (currentDsd.getComponent(attributeId) != null) {
                sectionValues.put(attributeId, parser.getAttributeValue(i));
            }
        }
    }

    private void processSectionObs() {
        obsValue = null;
        Map<String, String> obsMap = new HashMap<String, String>(sectionValues);
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = parser.getAttributeLocalName(i);
            attributeId = getComponentId(attributeId);
            if (currentDsd.getComponent(attributeId) != null) {
                obsMap.put(attributeId, parser.getAttributeValue(i));
            } else if (attributeId.equals("value")) {
                obsValue = parser.getAttributeValue(i);
            }
        }
        datasetPosition = DATASET_POSITION.OBSERAVTION_AS_SERIES;
        for (String dim : dimensions) {
            if (!obsMap.get(dim).equals(previousKey.get(dim))) {
                //datasetPosition  = DATASET_POSITION.SERIES;
                break;
            }
        }

        previousKey = obsMap;
    }
}
