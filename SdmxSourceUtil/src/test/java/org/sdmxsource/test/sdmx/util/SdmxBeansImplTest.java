package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SdmxBeansImplTest {

    private final static String TEST_AGENCY = "TEST_AGENCY";
    private final static String NOTHING = "NOTHING";

    private SdmxBeans sdmxBeans;
    private SdmxBeans sdmxBeans2;
    private MaintainableRefBean wildCard;
    private MaintainableRefBean nothing;


    @Mock
    private HeaderBean headerBeanMock;
    @Mock
    private AgencySchemeBean agencySchemeMock;
    @Mock
    private AttachmentConstraintBean attachmentConstraintMock;
    @Mock
    private CategorisationBean categorisationMock;
    @Mock
    private CategorySchemeBean categorySchemeMock;
    @Mock
    private CodelistBean codelistMock;
    @Mock
    private ConceptSchemeBean conceptSchemeMock;
    @Mock
    private ContentConstraintBean contentConstraintMock;
    @Mock
    private DataConsumerSchemeBean dataConsumerSchemeMock;
    @Mock
    private DataProviderSchemeBean dataProviderSchemeMock;
    @Mock
    private DataStructureBean dataStructureMock;
    @Mock
    private DataflowBean dataflowMock;
    @Mock
    private HierarchicalCodelistBean hierarchicalCodelistMock;
    @Mock
    private MetadataFlowBean metadataFlowMock;
    @Mock
    private MetadataStructureDefinitionBean metadataStructureDefinitionMock;
    @Mock
    private OrganisationUnitSchemeBean organisationUnitSchemeMock;
    @Mock
    private ProcessBean processMock;
    @Mock
    private ProvisionAgreementBean provisionAgreementMock;
    @Mock
    private RegistrationBean registrationMock;
    @Mock
    private ReportingTaxonomyBean reportingTaxonomyMock;
    @Mock
    private StructureSetBean structureSetMock;
    @Mock
    private SubscriptionBean subscriptionMock;


    @BeforeEach
    public void setup() {
        sdmxBeans = new SdmxBeansImpl();
        sdmxBeans2 = new SdmxBeansImpl(headerBeanMock);
        wildCard = new MaintainableRefBeanImpl(TEST_AGENCY, null, "1.2");
        nothing = new MaintainableRefBeanImpl(NOTHING, null, "1.0");
    }

    @Test
    public void shouldCheckSdmxBeansImpl() {
        assertFalse(sdmxBeans2.hasStructures());
        assertNotNull(sdmxBeans2.getHeader());
        var s3 = new SdmxBeansImpl(DATASET_ACTION.APPEND);

        assertEquals(sdmxBeans.getAction(), DATASET_ACTION.INFORMATION);
        assertEquals(s3.getAction(), DATASET_ACTION.APPEND);
        assertNull(sdmxBeans.getHeader());
        assertNotNull(sdmxBeans.getAgencies());
        assertTrue(sdmxBeans.getAgencies().isEmpty());
    }

    @Test
    public void shouldCheckAgencySchemeBean() {
        mockBean(agencySchemeMock, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
        sdmxBeans.addAgencyScheme(agencySchemeMock);
        assertFalse(sdmxBeans.getAgenciesSchemes().isEmpty());
        sdmxBeans.removeAgencyScheme(agencySchemeMock);
        assertTrue(sdmxBeans.getAgenciesSchemes().isEmpty());
        sdmxBeans.addIdentifiable(agencySchemeMock);
        assertFalse(sdmxBeans.getAgenciesSchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getAgenciesSchemes().isEmpty());
        assertFalse(sdmxBeans2.getAgenciesSchemes(getMockedMaintainableRefBean(agencySchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getAgenciesSchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getAgenciesSchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckAttachmentConstraintBean() {
        mockBean(attachmentConstraintMock, SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT);
        sdmxBeans.addAttachmentConstraint(attachmentConstraintMock);
        assertFalse(sdmxBeans.getAttachmentConstraints().isEmpty());
        sdmxBeans.removeAttachmentConstraintBean(attachmentConstraintMock);
        assertTrue(sdmxBeans.getAttachmentConstraints().isEmpty());
        sdmxBeans.addIdentifiable(attachmentConstraintMock);
        assertFalse(sdmxBeans.getAttachmentConstraints().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getAttachmentConstraints().isEmpty());
        assertFalse(sdmxBeans2.getAttachmentConstraints(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getAttachmentConstraints(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getAttachmentConstraints(getMockedMaintainableRefBean(attachmentConstraintMock)).isEmpty());
        assertFalse(sdmxBeans2.getAttachmentConstraints(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getAttachmentConstraints(nothing).isEmpty());
    }

    @Test
    public void shouldCheckCategorisationBean() {
        mockBean(categorisationMock, SDMX_STRUCTURE_TYPE.CATEGORISATION);
        sdmxBeans.addCategorisation(categorisationMock);
        assertFalse(sdmxBeans.getCategorisations().isEmpty());
        sdmxBeans.removeCategorisation(categorisationMock);
        assertTrue(sdmxBeans.getCategorisations().isEmpty());
        sdmxBeans.addIdentifiable(categorisationMock);
        assertFalse(sdmxBeans.getCategorisations().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getCategorisations().isEmpty());
        assertFalse(sdmxBeans2.getCategorisations(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getCategorisations(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getCategorisations(getMockedMaintainableRefBean(categorisationMock)).isEmpty());
        assertFalse(sdmxBeans2.getCategorisations(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getCategorisations(nothing).isEmpty());
    }

    @Test
    public void shouldCheckCategorySchemeBean() {
        mockBean(categorySchemeMock, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
        sdmxBeans.addCategoryScheme(categorySchemeMock);
        assertFalse(sdmxBeans.getCategorySchemes().isEmpty());
        sdmxBeans.removeCategoryScheme(categorySchemeMock);
        assertTrue(sdmxBeans.getCategorySchemes().isEmpty());
        sdmxBeans.addIdentifiable(categorySchemeMock);
        assertFalse(sdmxBeans.getCategorySchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getCategorySchemes().isEmpty());
        assertFalse(sdmxBeans2.getCategorySchemes(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getCategorySchemes(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getCategorySchemes(getMockedMaintainableRefBean(categorySchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getCategorySchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getCategorySchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckCodelistBean() {
        mockBean(codelistMock, SDMX_STRUCTURE_TYPE.CODE_LIST);
        sdmxBeans.addCodelist(codelistMock);
        assertFalse(sdmxBeans.getCodelists().isEmpty());
        sdmxBeans.removeCodelist(codelistMock);
        assertTrue(sdmxBeans.getCodelists().isEmpty());
        sdmxBeans.addIdentifiable(codelistMock);
        assertFalse(sdmxBeans.getCodelists().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getCodelists().isEmpty());
        assertFalse(sdmxBeans2.getCodelists(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getCodelists(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getCodelists(getMockedMaintainableRefBean(codelistMock)).isEmpty());
        assertFalse(sdmxBeans2.getCodelists(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getCodelists(nothing).isEmpty());
    }

    @Test
    public void shouldCheckConceptSchemeBean() {
        mockBean(conceptSchemeMock, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
        sdmxBeans.addConceptScheme(conceptSchemeMock);
        assertFalse(sdmxBeans.getConceptSchemes().isEmpty());

        sdmxBeans.removeConceptScheme(conceptSchemeMock);
        assertTrue(sdmxBeans.getConceptSchemes().isEmpty());

        sdmxBeans.addIdentifiable(conceptSchemeMock);
        assertFalse(sdmxBeans.getConceptSchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getConceptSchemes().isEmpty());
        assertFalse(sdmxBeans2.getConceptSchemes(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getConceptSchemes(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getConceptSchemes(getMockedMaintainableRefBean(conceptSchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getConceptSchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getConceptSchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckContentConstraintBean() {
        mockBean(contentConstraintMock, SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT);
        sdmxBeans.addContentConstraintBean(contentConstraintMock);
        assertFalse(sdmxBeans.getContentConstraintBeans().isEmpty());
        sdmxBeans.removeContentConstraintBean(contentConstraintMock);
        assertTrue(sdmxBeans.getContentConstraintBeans().isEmpty());
        sdmxBeans.addIdentifiable(contentConstraintMock);
        assertFalse(sdmxBeans.getContentConstraintBeans().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getContentConstraintBeans().isEmpty());
        assertFalse(sdmxBeans2.getContentConstraintBeans(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getContentConstraintBeans(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getContentConstraintBeans(getMockedMaintainableRefBean(contentConstraintMock)).isEmpty());
        assertFalse(sdmxBeans2.getContentConstraintBeans(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getContentConstraintBeans(nothing).isEmpty());
    }

    @Test
    public void shouldCheckDataConsumerSchemeBean() {
        mockBean(dataConsumerSchemeMock, SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME);
        sdmxBeans.addDataConsumerScheme(dataConsumerSchemeMock);
        assertFalse(sdmxBeans.getDataConsumerSchemes().isEmpty());
        sdmxBeans.removeDataConsumerScheme(dataConsumerSchemeMock);
        assertTrue(sdmxBeans.getDataConsumerSchemes().isEmpty());
        sdmxBeans.addIdentifiable(dataConsumerSchemeMock);
        assertFalse(sdmxBeans.getDataConsumerSchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getDataConsumerSchemes().isEmpty());
        assertFalse(sdmxBeans2.getDataConsumerSchemes(getMockedMaintainableRefBean(dataConsumerSchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getDataConsumerSchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getDataConsumerSchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckDataProviderSchemeBean() {
        mockBean(dataProviderSchemeMock, SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME);
        sdmxBeans.addDataProviderScheme(dataProviderSchemeMock);
        assertFalse(sdmxBeans.getDataProviderSchemes().isEmpty());
        sdmxBeans.removeDataProviderScheme(dataProviderSchemeMock);
        assertTrue(sdmxBeans.getDataProviderSchemes().isEmpty());
        sdmxBeans.addIdentifiable(dataProviderSchemeMock);
        assertFalse(sdmxBeans.getDataProviderSchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getDataProviderSchemes().isEmpty());
        assertFalse(sdmxBeans2.getDataProviderSchemes(getMockedMaintainableRefBean(dataProviderSchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getDataProviderSchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getDataProviderSchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckDataStructureBean() {
        mockBean(dataStructureMock, SDMX_STRUCTURE_TYPE.DSD);
        sdmxBeans.addDataStructure(dataStructureMock);
        assertFalse(sdmxBeans.getDataStructures().isEmpty());
        sdmxBeans.removeDataStructure(dataStructureMock);
        assertTrue(sdmxBeans.getDataStructures().isEmpty());
        sdmxBeans.addIdentifiable(dataStructureMock);
        assertFalse(sdmxBeans.getDataStructures().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getDataStructures().isEmpty());
        assertFalse(sdmxBeans2.getDataStructures(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getDataStructures(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getDataStructures(getMockedMaintainableRefBean(dataStructureMock)).isEmpty());
        assertFalse(sdmxBeans2.getDataStructures(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getDataStructures(nothing).isEmpty());
    }

    @Test
    public void shouldCheckDataFlowBean() {
        mockBean(dataflowMock, SDMX_STRUCTURE_TYPE.DATAFLOW);
        sdmxBeans.addDataflow(dataflowMock);
        assertFalse(sdmxBeans.getDataflows().isEmpty());
        sdmxBeans.removeDataflow(dataflowMock);
        assertTrue(sdmxBeans.getDataflows().isEmpty());
        sdmxBeans.addIdentifiable(dataflowMock);
        assertFalse(sdmxBeans.getDataflows().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getDataflows().isEmpty());
        assertFalse(sdmxBeans2.getDataflows(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getDataflows(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getDataflows(getMockedMaintainableRefBean(dataflowMock)).isEmpty());
        assertFalse(sdmxBeans2.getDataflows(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getDataflows(nothing).isEmpty());
    }

    @Test
    public void shouldCheckHierarchicalCodelistBean() {
        mockBean(hierarchicalCodelistMock, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
        sdmxBeans.addHierarchicalCodelist(hierarchicalCodelistMock);
        assertFalse(sdmxBeans.getHierarchicalCodelists().isEmpty());

        sdmxBeans.removeHierarchicalCodelist(hierarchicalCodelistMock);
        assertTrue(sdmxBeans.getHierarchicalCodelists().isEmpty());

        sdmxBeans.addIdentifiable(hierarchicalCodelistMock);
        assertFalse(sdmxBeans.getHierarchicalCodelists().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getHierarchicalCodelists().isEmpty());
        assertFalse(sdmxBeans2.getHierarchicalCodelists(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getHierarchicalCodelists(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getHierarchicalCodelists(getMockedMaintainableRefBean(hierarchicalCodelistMock)).isEmpty());
        assertFalse(sdmxBeans2.getHierarchicalCodelists(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getHierarchicalCodelists(nothing).isEmpty());
    }

    @Test
    public void shouldCheckMetadataFlowBean() {
        mockBean(metadataFlowMock, SDMX_STRUCTURE_TYPE.METADATA_FLOW);
        sdmxBeans.addMetadataFlow(metadataFlowMock);
        assertFalse(sdmxBeans.getMetadataflows().isEmpty());
        sdmxBeans.removeMetadataFlow(metadataFlowMock);
        assertTrue(sdmxBeans.getMetadataflows().isEmpty());
        sdmxBeans.addIdentifiable(metadataFlowMock);
        assertFalse(sdmxBeans.getMetadataflows().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getMetadataflows().isEmpty());
        assertFalse(sdmxBeans2.getMetadataflows(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getMetadataflows(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getMetadataflows(getMockedMaintainableRefBean(metadataFlowMock)).isEmpty());
        assertFalse(sdmxBeans2.getMetadataflows(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getMetadataflows(nothing).isEmpty());
    }

    @Test
    public void shouldCheckMetadataStructureDefinitionBean() {
        mockBean(metadataStructureDefinitionMock, SDMX_STRUCTURE_TYPE.MSD);
        sdmxBeans.addMetadataStructure(metadataStructureDefinitionMock);
        assertFalse(sdmxBeans.getMetadataStructures().isEmpty());
        sdmxBeans.removeMetadataStructure(metadataStructureDefinitionMock);
        assertTrue(sdmxBeans.getMetadataStructures().isEmpty());
        sdmxBeans.addIdentifiable(metadataStructureDefinitionMock);
        assertFalse(sdmxBeans.getMetadataStructures().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getMetadataStructures().isEmpty());
        assertFalse(sdmxBeans2.getMetadataStructures(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getMetadataStructures(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getMetadataStructures(getMockedMaintainableRefBean(metadataStructureDefinitionMock)).isEmpty());
        assertFalse(sdmxBeans2.getMetadataStructures(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getMetadataStructures(nothing).isEmpty());
    }

    @Test
    public void shouldCheckOrganisationUnitSchemeBean() {
        mockBean(organisationUnitSchemeMock, SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
        sdmxBeans.addOrganisationUnitScheme(organisationUnitSchemeMock);
        assertFalse(sdmxBeans.getOrganisationUnitSchemes().isEmpty());
        sdmxBeans.removeOrganisationUnitScheme(organisationUnitSchemeMock);
        assertTrue(sdmxBeans.getOrganisationUnitSchemes().isEmpty());
        sdmxBeans.addIdentifiable(organisationUnitSchemeMock);
        assertFalse(sdmxBeans.getOrganisationUnitSchemes().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getOrganisationUnitSchemes().isEmpty());
        assertFalse(sdmxBeans2.getOrganisationUnitSchemes(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getOrganisationUnitSchemes(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getOrganisationUnitSchemes(getMockedMaintainableRefBean(organisationUnitSchemeMock)).isEmpty());
        assertFalse(sdmxBeans2.getOrganisationUnitSchemes(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getOrganisationUnitSchemes(nothing).isEmpty());
    }

    @Test
    public void shouldCheckProcessBean() {
        mockBean(processMock, SDMX_STRUCTURE_TYPE.PROCESS);
        sdmxBeans.addProcess(processMock);
        assertFalse(sdmxBeans.getProcesses().isEmpty());
        sdmxBeans.removeProcess(processMock);
        assertTrue(sdmxBeans.getProcesses().isEmpty());
        sdmxBeans.addIdentifiable(processMock);
        assertFalse(sdmxBeans.getProcesses().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getProcesses().isEmpty());
        assertFalse(sdmxBeans2.getProcesses(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getProcesses(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getProcesses(getMockedMaintainableRefBean(processMock)).isEmpty());
        assertFalse(sdmxBeans2.getProcesses(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getProcesses(nothing).isEmpty());
    }

    @Test
    public void shouldCheckProvisionAgreementBean() {
        mockBean(provisionAgreementMock, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
        sdmxBeans.addProvisionAgreement(provisionAgreementMock);
        assertFalse(sdmxBeans.getProvisionAgreements().isEmpty());
        sdmxBeans.removeProvisionAgreement(provisionAgreementMock);
        assertTrue(sdmxBeans.getProvisionAgreements().isEmpty());
        sdmxBeans.addIdentifiable(provisionAgreementMock);
        assertFalse(sdmxBeans.getProvisionAgreements().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getProvisionAgreements().isEmpty());
        assertFalse(sdmxBeans2.getProvisionAgreements(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getProvisionAgreements(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getProvisionAgreements(getMockedMaintainableRefBean(provisionAgreementMock)).isEmpty());
        assertFalse(sdmxBeans2.getProvisionAgreements(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getProvisionAgreements(nothing).isEmpty());
    }

    @Test
    public void shouldCheckRegistrationBean() {
        mockBean(registrationMock, SDMX_STRUCTURE_TYPE.REGISTRATION);
        sdmxBeans.addRegistration(registrationMock);
        assertFalse(sdmxBeans.getRegistrations().isEmpty());
        sdmxBeans.removeRegistration(registrationMock);
        assertTrue(sdmxBeans.getRegistrations().isEmpty());
        sdmxBeans.addIdentifiable(registrationMock);
        assertFalse(sdmxBeans.getRegistrations().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getRegistrations().isEmpty());
        assertFalse(sdmxBeans2.getRegistrations(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getRegistrations(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getRegistrations(getMockedMaintainableRefBean(registrationMock)).isEmpty());
        assertFalse(sdmxBeans2.getRegistrations(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getRegistrations(nothing).isEmpty());
    }

    @Test
    public void shouldCheckReportingTaxonomyBean() {
        mockBean(reportingTaxonomyMock, SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY);
        sdmxBeans.addReportingTaxonomy(reportingTaxonomyMock);
        assertFalse(sdmxBeans.getReportingTaxonomys().isEmpty());
        sdmxBeans.removeReportingTaxonomy(reportingTaxonomyMock);
        assertTrue(sdmxBeans.getReportingTaxonomys().isEmpty());
        sdmxBeans.addIdentifiable(reportingTaxonomyMock);
        assertFalse(sdmxBeans.getReportingTaxonomys().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getReportingTaxonomys().isEmpty());
        assertFalse(sdmxBeans2.getReportingTaxonomys(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getReportingTaxonomys(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getReportingTaxonomys(getMockedMaintainableRefBean(reportingTaxonomyMock)).isEmpty());
        assertFalse(sdmxBeans2.getReportingTaxonomys(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getReportingTaxonomys(nothing).isEmpty());
    }

    @Test
    public void shouldCheckStructureSetBean() {
        mockBean(structureSetMock, SDMX_STRUCTURE_TYPE.STRUCTURE_SET);
        sdmxBeans.addStructureSet(structureSetMock);
        assertFalse(sdmxBeans.getStructureSets().isEmpty());
        sdmxBeans.removeStructureSet(structureSetMock);
        assertTrue(sdmxBeans.getStructureSets().isEmpty());
        sdmxBeans.addIdentifiable(structureSetMock);
        assertFalse(sdmxBeans.getStructureSets().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getStructureSets().isEmpty());
        assertFalse(sdmxBeans2.getStructureSets(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getStructureSets(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getStructureSets(getMockedMaintainableRefBean(structureSetMock)).isEmpty());
        assertFalse(sdmxBeans2.getStructureSets(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getStructureSets(nothing).isEmpty());
    }

    @Test
    public void shouldCheckSubscriptionBean() {
        mockBean(subscriptionMock, SDMX_STRUCTURE_TYPE.SUBSCRIPTION);
        sdmxBeans.addSubscription(subscriptionMock);
        assertFalse(sdmxBeans.getSubscriptions().isEmpty());
        sdmxBeans.removeSubscription(subscriptionMock);
        assertTrue(sdmxBeans.getSubscriptions().isEmpty());
        sdmxBeans.addIdentifiable(subscriptionMock);
        assertFalse(sdmxBeans.getSubscriptions().isEmpty());

        sdmxBeans2.merge(sdmxBeans);
        assertFalse(sdmxBeans2.getSubscriptions().isEmpty());
        assertFalse(sdmxBeans2.getSubscriptions(TEST_AGENCY).isEmpty());
        assertTrue(sdmxBeans2.getSubscriptions(NOTHING).isEmpty());
        assertFalse(sdmxBeans2.getSubscriptions(getMockedMaintainableRefBean(subscriptionMock)).isEmpty());
        assertFalse(sdmxBeans2.getSubscriptions(wildCard).isEmpty());
        assertTrue(sdmxBeans2.getSubscriptions(nothing).isEmpty());
    }

    private <T extends MaintainableBean> void mockBean(T mock, SDMX_STRUCTURE_TYPE structureType) {
        when(mock.getAgencyId()).thenReturn("TEST_AGENCY");
        when(mock.getId()).thenReturn("ID_" + structureType);
        when(mock.getVersion()).thenReturn("1.2");
        when(mock.getStructureType()).thenReturn(structureType);
    }

    private <T extends MaintainableBean> MaintainableRefBean getMockedMaintainableRefBean(T mock) {
        return new MaintainableRefBeanImpl(mock.getAgencyId(), mock.getId(), mock.getVersion());
    }
}
