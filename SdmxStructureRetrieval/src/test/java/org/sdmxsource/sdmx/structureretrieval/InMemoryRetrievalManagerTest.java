package org.sdmxsource.sdmx.structureretrieval;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryRetrievalManagerTest {

    @Test
    public void shouldCheckSaveStructure() {
        StructureReferenceBean structureReferenceBean =
                new StructureReferenceBeanImpl("TEST_AGENCY", "TEST_DSD", "1.0", SDMX_STRUCTURE_TYPE.DSD);
        DataflowMutableBean dataflow = new DataflowMutableBeanImpl();
        dataflow.setAgencyId("TEST_AGENCY");
        dataflow.setId("TEST_DF");
        dataflow.addName("en", "A test DF");
        dataflow.setDataStructureRef(structureReferenceBean);

        var retrievalManager = new InMemoryRetrievalManager();
        assertEquals(0, retrievalManager.getMaintainableBeans(DataflowBean.class).size());
        retrievalManager.saveStructure(dataflow.getImmutableInstance());
        assertEquals(1, retrievalManager.getMaintainableBeans(DataflowBean.class).size());
    }

}
