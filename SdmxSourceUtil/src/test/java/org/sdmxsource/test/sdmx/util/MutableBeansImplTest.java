package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.model.mutable.base.AgencySchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataConsumerSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.OrganisationUnitSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorisationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingTaxonomyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.StructureSetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataFlowMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.SubscriptionMutableBean;
import org.sdmxsource.sdmx.util.beans.container.MutableBeansImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.DATAFLOW;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST;

@ExtendWith(MockitoExtension.class)
public class MutableBeansImplTest {

    @Mock
    private AgencySchemeMutableBean agencySchemeMutableBean;
    @Mock
    private CategorisationMutableBean categorisationMutableBean;
    @Mock
    private CategorySchemeMutableBean categorySchemeMutableBean;
    @Mock
    private CodelistMutableBean codelistMutableBean;
    @Mock
    private ConceptSchemeMutableBean conceptSchemeMutableBean;
    @Mock
    private DataConsumerSchemeMutableBean dataConsumerSchemeMutableBean;
    @Mock
    private DataProviderSchemeMutableBean dataProviderSchemeMutableBean;
    @Mock
    private DataStructureMutableBean dataStructureMutableBean;
    @Mock
    private DataflowMutableBean dataflowMutableBean;
    @Mock
    private HierarchicalCodelistMutableBean hierarchicalCodelistMutableBean;
    @Mock
    private MetadataFlowMutableBean metadataFlowMutableBean;
    @Mock
    private MetadataStructureDefinitionMutableBean metadataStructureDefinitionMutableBean;
    @Mock
    private OrganisationUnitSchemeMutableBean organisationUnitSchemeMutableBean;
    @Mock
    private ProcessMutableBean processMutableBean;
    @Mock
    private ProvisionAgreementMutableBean provisionAgreementMutableBean;
    @Mock
    private ReportingTaxonomyMutableBean reportingTaxonomyMutableBean;
    @Mock
    private StructureSetMutableBean structureSetMutableBean;
    @Mock
    private SubscriptionMutableBean subscriptionMutableBean;
    private MutableBeansImpl mutableBeansImpl;

    @BeforeEach
    public void setup() {
        mutableBeansImpl = new MutableBeansImpl();
        var s3 = new SdmxBeansImpl(DATASET_ACTION.APPEND);

        assertEquals(s3.getAction(), DATASET_ACTION.APPEND);
        assertNotNull(mutableBeansImpl.getAllMaintainables());
        assertTrue(mutableBeansImpl.getAllMaintainables().isEmpty());
    }

    @Test
    public void shouldCheckAgencyScheme() {
        mutableBeansImpl.addAgencyScheme(agencySchemeMutableBean);
        assertFalse(mutableBeansImpl.getAgencySchemeMutableBeans().isEmpty());
        mutableBeansImpl.removeAgencySchemeMutableBeans(agencySchemeMutableBean);
        assertTrue(mutableBeansImpl.getAgencySchemeMutableBeans().isEmpty());
        mutableBeansImpl.addIdentifiable(agencySchemeMutableBean);
        assertFalse(mutableBeansImpl.getAgencySchemeMutableBeans().isEmpty());
    }

    @Test
    public void shouldCheckCategorisation() {
        mutableBeansImpl.addCategorisation(categorisationMutableBean);
        assertFalse(mutableBeansImpl.getCategorisations().isEmpty());
        mutableBeansImpl.removeCategorisation(categorisationMutableBean);
        assertTrue(mutableBeansImpl.getCategorisations().isEmpty());
        mutableBeansImpl.addIdentifiable(categorisationMutableBean);
        assertFalse(mutableBeansImpl.getCategorisations().isEmpty());
    }

    @Test
    public void shouldCheckCategoryScheme() {
        mutableBeansImpl.addCategoryScheme(categorySchemeMutableBean);
        assertFalse(mutableBeansImpl.getCategorySchemes().isEmpty());
        mutableBeansImpl.removeCategoryScheme(categorySchemeMutableBean);
        assertTrue(mutableBeansImpl.getCategorySchemes().isEmpty());
        mutableBeansImpl.addIdentifiable(categorySchemeMutableBean);
        assertFalse(mutableBeansImpl.getCategorySchemes().isEmpty());
    }

    @Test
    public void shouldCheckCodelist() {
        mutableBeansImpl.addCodelist(codelistMutableBean);
        assertFalse(mutableBeansImpl.getCodelists().isEmpty());
        mutableBeansImpl.removeCodelist(codelistMutableBean);
        assertTrue(mutableBeansImpl.getCodelists().isEmpty());
        mutableBeansImpl.addIdentifiable(codelistMutableBean);
        assertFalse(mutableBeansImpl.getCodelists().isEmpty());
    }

    @Test
    public void shouldCheckConceptScheme() {
        mutableBeansImpl.addConceptScheme(conceptSchemeMutableBean);
        assertFalse(mutableBeansImpl.getConceptSchemes().isEmpty());
        mutableBeansImpl.removeConceptScheme(conceptSchemeMutableBean);
        assertTrue(mutableBeansImpl.getConceptSchemes().isEmpty());
        mutableBeansImpl.addIdentifiable(conceptSchemeMutableBean);
        assertFalse(mutableBeansImpl.getConceptSchemes().isEmpty());
    }

    @Test
    public void shouldCheckDataConsumerScheme() {
        mutableBeansImpl.addDataConsumerScheme(dataConsumerSchemeMutableBean);
        assertFalse(mutableBeansImpl.getDataConsumberSchemeMutableBeans().isEmpty());
        mutableBeansImpl.removeDataConsumberSchemeMutableBeans(dataConsumerSchemeMutableBean);
        assertTrue(mutableBeansImpl.getDataConsumberSchemeMutableBeans().isEmpty());
        mutableBeansImpl.addIdentifiable(dataConsumerSchemeMutableBean);
        assertFalse(mutableBeansImpl.getDataConsumberSchemeMutableBeans().isEmpty());
    }

    @Test
    public void shouldCheckDataProviderScheme() {
        mutableBeansImpl.addDataProviderScheme(dataProviderSchemeMutableBean);
        assertFalse(mutableBeansImpl.getDataProviderSchemeMutableBeans().isEmpty());
        mutableBeansImpl.removeDataProviderSchemeMutableBeans(dataProviderSchemeMutableBean);
        assertTrue(mutableBeansImpl.getDataProviderSchemeMutableBeans().isEmpty());
        mutableBeansImpl.addIdentifiable(dataProviderSchemeMutableBean);
        assertFalse(mutableBeansImpl.getDataProviderSchemeMutableBeans().isEmpty());
    }

    @Test
    public void shouldCheckDataStructure() {
        mutableBeansImpl.addDataStructure(dataStructureMutableBean);
        assertFalse(mutableBeansImpl.getDataStructures().isEmpty());
        mutableBeansImpl.removeDataStructure(dataStructureMutableBean);
        assertTrue(mutableBeansImpl.getDataStructures().isEmpty());
        mutableBeansImpl.addIdentifiable(dataStructureMutableBean);
        assertFalse(mutableBeansImpl.getDataStructures().isEmpty());
    }

    @Test
    public void shouldCheckDataFlow() {
        mutableBeansImpl.addDataflow(dataflowMutableBean);
        assertFalse(mutableBeansImpl.getDataflows().isEmpty());
        mutableBeansImpl.removeDataflow(dataflowMutableBean);
        assertTrue(mutableBeansImpl.getDataflows().isEmpty());
        mutableBeansImpl.addIdentifiable(dataflowMutableBean);
        assertFalse(mutableBeansImpl.getDataflows().isEmpty());

        var dataFlowMutableBean = new MutableBeansImpl(new ArrayList<>(mutableBeansImpl.getDataflows()));
        assertFalse(dataFlowMutableBean.getDataflows().isEmpty());
        assertTrue(dataFlowMutableBean.getMaintainables(HIERARCHICAL_CODELIST).isEmpty());
        assertFalse(dataFlowMutableBean.getMaintainables(DATAFLOW).isEmpty());
    }

    @Test
    public void shouldCheckHierarchicalCodelist() {
        mutableBeansImpl.addHierarchicalCodelist(hierarchicalCodelistMutableBean);
        assertFalse(mutableBeansImpl.getHierarchicalCodelists().isEmpty());
        mutableBeansImpl.removeHierarchicalCodelist(hierarchicalCodelistMutableBean);
        assertTrue(mutableBeansImpl.getHierarchicalCodelists().isEmpty());
        mutableBeansImpl.addIdentifiable(hierarchicalCodelistMutableBean);
        assertFalse(mutableBeansImpl.getHierarchicalCodelists().isEmpty());
    }

    @Test
    public void shouldCheckMetadataFlow() {
        mutableBeansImpl.addMetadataFlow(metadataFlowMutableBean);
        assertFalse(mutableBeansImpl.getMetadataflows().isEmpty());
        mutableBeansImpl.removeMetadataFlow(metadataFlowMutableBean);
        assertTrue(mutableBeansImpl.getMetadataflows().isEmpty());
        mutableBeansImpl.addIdentifiable(metadataFlowMutableBean);
        assertFalse(mutableBeansImpl.getMetadataflows().isEmpty());
    }

    @Test
    public void shouldCheckMetadataStructureDefinition() {
        mutableBeansImpl.addMetadataStructure(metadataStructureDefinitionMutableBean);
        assertFalse(mutableBeansImpl.getMetadataStructures().isEmpty());
        mutableBeansImpl.removeMetadataStructure(metadataStructureDefinitionMutableBean);
        assertTrue(mutableBeansImpl.getMetadataStructures().isEmpty());
        mutableBeansImpl.addIdentifiable(metadataStructureDefinitionMutableBean);
        assertFalse(mutableBeansImpl.getMetadataStructures().isEmpty());
    }

    @Test
    public void shouldCheckOrganisationUnitScheme() {
        mutableBeansImpl.addOrganisationUnitScheme(organisationUnitSchemeMutableBean);
        assertFalse(mutableBeansImpl.getOrganisationUnitSchemes().isEmpty());
        mutableBeansImpl.removeOrganisationUnitScheme(organisationUnitSchemeMutableBean);
        assertTrue(mutableBeansImpl.getOrganisationUnitSchemes().isEmpty());
        mutableBeansImpl.addIdentifiable(organisationUnitSchemeMutableBean);
        assertFalse(mutableBeansImpl.getOrganisationUnitSchemes().isEmpty());
    }

    @Test
    public void shouldCheckProcess() {
        mutableBeansImpl.addProcess(processMutableBean);
        assertFalse(mutableBeansImpl.getProcesses().isEmpty());
        mutableBeansImpl.removeProcess(processMutableBean);
        assertTrue(mutableBeansImpl.getProcesses().isEmpty());
        mutableBeansImpl.addIdentifiable(processMutableBean);
        assertFalse(mutableBeansImpl.getProcesses().isEmpty());
    }

    @Test
    public void shouldCheckProvisionAgreement() {
        mutableBeansImpl.addProvision(provisionAgreementMutableBean);
        assertFalse(mutableBeansImpl.getProvisions().isEmpty());
        mutableBeansImpl.removeProvision(provisionAgreementMutableBean);
        assertTrue(mutableBeansImpl.getProvisions().isEmpty());
        mutableBeansImpl.addIdentifiable(provisionAgreementMutableBean);
        assertFalse(mutableBeansImpl.getProvisions().isEmpty());
    }

    @Test
    public void shouldCheckReportingTaxonomy() {
        mutableBeansImpl.addReportingTaxonomy(reportingTaxonomyMutableBean);
        assertFalse(mutableBeansImpl.getReportingTaxonomys().isEmpty());
        mutableBeansImpl.removeReportingTaxonomy(reportingTaxonomyMutableBean);
        assertTrue(mutableBeansImpl.getReportingTaxonomys().isEmpty());
        mutableBeansImpl.addIdentifiable(reportingTaxonomyMutableBean);
        assertFalse(mutableBeansImpl.getReportingTaxonomys().isEmpty());
    }

    @Test
    public void shouldCheckStructureSet() {
        mutableBeansImpl.addStructureSet(structureSetMutableBean);
        assertFalse(mutableBeansImpl.getStructureSets().isEmpty());
        mutableBeansImpl.removeStructureSet(structureSetMutableBean);
        assertTrue(mutableBeansImpl.getStructureSets().isEmpty());
        mutableBeansImpl.addIdentifiable(structureSetMutableBean);
        assertFalse(mutableBeansImpl.getStructureSets().isEmpty());
    }

}
