package org.sdmxsource.sdmx.structureparser.data;

import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public static SdmxBeans getStructures(String fileName) {
        StructureParsingManager manager = new StructureParsingManagerImpl(new SdmxSourceReadableDataLocationFactory(),
                new StructureParserFactory[0]);
        var readable = new ReadableDataLocationTmp("src/test/resources/structures/" + fileName);
        var structureWorkspace = manager.parseStructures(readable);
        return structureWorkspace.getStructureBeans(true);
    }

    public static SdmxBeans getStructures() {
        return getStructures("Structures.xml");
    }

    public static DataStructureBean getDataStructure() {
        return getStructures().getDataStructures().iterator().next();
    }

    public static AttributeBean getAttributeBean() {
        return getDataStructure().getAttributes().iterator().next();
    }

    public static ConceptSchemeBean getConceptScheme() {
        return getStructures().getConceptSchemes().iterator().next();
    }

    public static DataflowBean getDataFlow() {
        return getStructures().getDataflows().iterator().next();
    }

    public static DimensionBean getDimension() {
        return getDataStructure().getDimensionList().getDimensions().get(0);
    }

    public static List<CodelistBean> getCodelistsForHierarchical() {
        return new ArrayList<>(getStructures("CodelistsForHierarchical.xml").getCodelists());
    }

    public static HierarchicalCodelistBean getHierarchicalCodelist() {
        return getStructures().getHierarchicalCodelists().iterator().next();
    }

    public static ProcessBean getProcess() {
        return getStructures("Process.xml").getProcesses().iterator().next();
    }

    public static List<ProcessStepBean> getProcessSteps() {
        return getProcess().getProcessSteps();
    }

    public static ProvisionAgreementBean getProvisionAgreement() {
        return getStructures("ProvisionAgreement.xml").getProvisionAgreements().iterator().next();
    }

    public static RegistrationBean getRegistration() {
        return getStructures("Registration.xml").getRegistrations().iterator().next();
    }
}
