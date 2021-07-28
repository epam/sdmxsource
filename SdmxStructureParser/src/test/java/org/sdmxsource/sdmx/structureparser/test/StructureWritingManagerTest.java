package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataSourceMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.RegistrationMutableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureFormat;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataProviderMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataProviderSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.DataSourceMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ProvisionAgreementMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.RegistrationMutableBeanImpl;
import org.sdmxsource.sdmx.structureparser.manager.impl.StructureWriterManagerImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.FileUtil;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.DATAFLOW;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.DATA_PROVIDER;
import static org.sdmxsource.sdmx.structureparser.test.SubmitStructureResponseBuilderTest.FILE_SUFFIX;
import static org.sdmxsource.sdmx.structureparser.test.SubmitStructureResponseBuilderTest.TMP_DIR;

public class StructureWritingManagerTest {

    private final StructureParsingManager parsingManager =
            new StructureParsingManagerImpl(new SdmxSourceReadableDataLocationFactory(),
                    new StructureParserFactory[0]);
    private final StructureWriterManager writerManager = new StructureWriterManagerImpl();

    @Test
    public void shouldCheckWriteProvisionAgreement() {
        String fileName = "src/test/resources/v21/test-sdmxv2.1-ESTAT+STS+2.0.xml";
        var sdmxBeans = parsingManager.parseStructures(new ReadableDataLocationTmp(fileName)).getStructureBeans(false);

        ProvisionAgreementMutableBean provisionAgreement = buildProvisionAgreementMutableBean();
        sdmxBeans.addProvisionAgreement(provisionAgreement.getImmutableInstance());
        sdmxBeans.addDataProviderScheme(buildDataProviderSchemeMutableBean());
        sdmxBeans.addDataflow(buildDataflowMutableBean(sdmxBeans));

        var file = FileUtil.createTemporaryFile("test-pa", FILE_SUFFIX);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            writerManager.writeStructures(sdmxBeans,
                    new SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT.SDMX_V21_STRUCTURE_DOCUMENT), stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        var fileReadableDataLocation = new ReadableDataLocationTmp(FileUtil.getNewestFile(TMP_DIR));
        SdmxBeans loaded = parsingManager.parseStructures(fileReadableDataLocation).getStructureBeans(false);

        assertFalse(loaded.getProvisionAgreements().isEmpty());
        assertTrue(provisionAgreement.getImmutableInstance()
                .deepEquals(loaded.getProvisionAgreements().iterator().next(), false));
    }

    @Test
    public void shouldCheckWritingRegistration() {
        String fileName = "src/test/resources/v21/test-pa.xml";
        var sdmxBeans = parsingManager.parseStructures(new ReadableDataLocationTmp(fileName)).getStructureBeans(false);

        SdmxBeans registrationContainer = new SdmxBeansImpl();
        registrationContainer.addRegistration(buildRegistrationBean(sdmxBeans));

        var file = FileUtil.createTemporaryFile("test-registration", FILE_SUFFIX);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            writerManager.writeStructures(registrationContainer,
                    new SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT.SDMX_V21_STRUCTURE_DOCUMENT), stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fileReadableDataLocation = new ReadableDataLocationTmp(FileUtil.getNewestFile(TMP_DIR));
        SdmxBeans loaded = parsingManager.parseStructures(fileReadableDataLocation).getStructureBeans(false);

        assertFalse(loaded.getRegistrations().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.structureparser.data.DataProvider#provideV20StructureWritingParams")
    public void shouldCheckWriteStructures(String file) {
        var fileInfo = new File(file);
        ReadableDataLocation location = new ReadableDataLocationTmp(fileInfo);
        SdmxBeans sdmxBeans = parsingManager.parseStructures(location).getStructureBeans(false);
        var sdmxStructureFormat = new SdmxStructureFormat(STRUCTURE_OUTPUT_FORMAT.SDMX_V2_STRUCTURE_DOCUMENT);
        var outputFileName = FileUtil.createTemporaryFile(file, "-output.xml");
        try {
            FileOutputStream stream = new FileOutputStream(outputFileName);
            FileInputStream inputStream = new FileInputStream(outputFileName);
            writerManager.writeStructures(sdmxBeans, sdmxStructureFormat, stream);
            XMLParser.validateXML(inputStream, SDMX_SCHEMA.VERSION_TWO);
            stream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadableDataLocation outputLocation = new ReadableDataLocationTmp(FileUtil.getNewestFile(TMP_DIR));
        SdmxBeans sdmxBeans2 = parsingManager.parseStructures(outputLocation).getStructureBeans(false);

        assertEquals(sdmxBeans.getAllMaintainables().size(), sdmxBeans2.getAllMaintainables().size());
    }

    private ProvisionAgreementMutableBean buildProvisionAgreementMutableBean() {
        ProvisionAgreementMutableBean provisionAgreement = new ProvisionAgreementMutableBeanImpl();
        provisionAgreement.setId("TEST_PA");
        provisionAgreement.setAgencyId("TEST");
        provisionAgreement.setVersion("1.0");
        provisionAgreement.addName("en", "This is a test PA");
        provisionAgreement.setDataproviderRef(new StructureReferenceBeanImpl("TEST", DataProviderSchemeBean.FIXED_ID, DataProviderSchemeBean.FIXED_VERSION, DATA_PROVIDER, "DP_TEST"));
        provisionAgreement.setStructureUsage(new StructureReferenceBeanImpl("TEST", "DATAFLOW_TEST", "1.2", DATAFLOW));
        return provisionAgreement;
    }

    private DataProviderSchemeBean buildDataProviderSchemeMutableBean() {
        DataProviderSchemeMutableBean dataProviderScheme = new DataProviderSchemeMutableBeanImpl();
        dataProviderScheme.setAgencyId("TEST");
        dataProviderScheme.addName("en", "Test name");

        DataProviderMutableBean dataProvider = new DataProviderMutableBeanImpl();
        dataProvider.setId("DP_TEST");
        dataProvider.addName("en", "Name for test DP");
        dataProviderScheme.addItem(dataProvider);

        return dataProviderScheme.getImmutableInstance();
    }

    private DataflowBean buildDataflowMutableBean(SdmxBeans sdmxBeans) {
        DataflowMutableBean dataflow = new DataflowMutableBeanImpl();
        dataflow.setId("DATAFLOW_TEST");
        dataflow.setAgencyId("TEST");
        dataflow.setVersion("1.2");
        dataflow.addName("en", "Name for test dataflow");
        dataflow.setDataStructureRef(sdmxBeans.getDataStructures().iterator().next().asReference());
        return dataflow.getImmutableInstance();
    }

    private RegistrationBean buildRegistrationBean(SdmxBeans sdmxBeans) {
        RegistrationMutableBean registration = new RegistrationMutableBeanImpl();
        DataSourceMutableBean dataSourceMutableBean = new DataSourceMutableBeanImpl();
        dataSourceMutableBean.setDataUrl("http://webservice.org/ws/rest/data/DF_TEST");
        dataSourceMutableBean.setRESTDatasource(true);
        registration.setDataSource(dataSourceMutableBean);
        registration.setProvisionAgreementRef(sdmxBeans.getProvisionAgreements().iterator().next().asReference());
        registration.setAgencyId("NA");
        return registration.getImmutableInstance();
    }

}
