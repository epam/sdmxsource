package org.sdmxsource.sdmx.sdmxbeans.data;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

public class DataHelper {

    public static CodelistMutableBean buildCodelist(int size) {
        var codelist = new CodelistMutableBeanImpl();
        codelist.setId("TEST");
        codelist.setAgencyId("TEST_AGENCY");
        codelist.setVersion("1.0");
        codelist.addName("en", "Test name");
        String lastCode = null;
        for (int i = 0; i < size; i++) {
            String codeId = String.format("ID%s", i);
            var code = codelist.createItem(codeId, codeId);
            if (lastCode != null && (i % 2) == 0) {
                code.setParentCode(lastCode);
            }
            if ((i % 6) == 0) {
                lastCode = codeId;
            }
        }
        return codelist;
    }

    public static DataStructureBean buildDsd() {
        DataStructureMutableBean dsdMutableObject = new DataStructureMutableBeanImpl();
        dsdMutableObject.setId("TEST_DSD");
        dsdMutableObject.setAgencyId("TEST");
        dsdMutableObject.setVersion("1.0");
        dsdMutableObject.addName("en", "Test data");

        dsdMutableObject.addDimension(getConceptStructureReferenceBean("FREQ"),
                getCodelistStructureReferenceBean("SDMX", "CL_FREQ"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean("ADJUSTMENT"),
                getCodelistStructureReferenceBean("SDMX", "CL_ADJUSTMENT"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean("STS_ACTIVITY"),
                getCodelistStructureReferenceBean("STS", "CL_STS_ACTIVITY"));
        DimensionMutableBean dimensionMutableBean = new DimensionMutableBeanImpl();
        dimensionMutableBean.setConceptRef(getConceptStructureReferenceBean("TIME_PERIOD"));
        dimensionMutableBean.setTimeDimension(true);
        dsdMutableObject.addDimension(dimensionMutableBean);
        dsdMutableObject.addPrimaryMeasure(getConceptStructureReferenceBean("OBS_VALUE"));

        var attributeMutableObject = dsdMutableObject
                .addAttribute(getConceptStructureReferenceBean("DECIMALS"),
                        getCodelistStructureReferenceBean("STS", "CL_DECIMALS"));
        attributeMutableObject.setAttachmentLevel(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP);
        attributeMutableObject.setDimensionReferences(Arrays.asList("FREQ", "ADJUSTMENT", "STS_ACTIVITY"));
        attributeMutableObject.setAssignmentStatus("Mandatory");
        return dsdMutableObject.getImmutableInstance();
    }

    public static List<ConceptSchemeBean> buildConceptScheme() {
        var dsd = buildDsd();
        Map<String, ConceptSchemeMutableBean> conceptSchemes = new HashMap<>();
        dsd.getComponents().forEach(component -> {
            var maintainableRefObject = component.getConceptRef().getMaintainableReference();
            ConceptSchemeMutableBean conceptScheme = new ConceptSchemeMutableBeanImpl();
            if (!conceptSchemes.containsKey(maintainableRefObject.toString())) {
                conceptScheme = new ConceptSchemeMutableBeanImpl();
                conceptScheme.setId(component.getConceptRef().getMaintainableId());
                conceptScheme.setVersion(component.getConceptRef().getVersion());
                conceptScheme.setAgencyId(component.getConceptRef().getAgencyId());
                conceptScheme.addName("en", maintainableRefObject.toString());
                conceptSchemes.put(maintainableRefObject.toString(), conceptScheme);
            }
            var concept = new ConceptMutableBeanImpl();
            concept.setId(component.getConceptRef().getIdentifiableIds()[0]);
            concept.addName("en", component.getConceptRef().getFullId());
            conceptScheme.addItem(concept);
        });

        return conceptSchemes.values().stream()
                .map(ConceptSchemeMutableBean::getImmutableInstance)
                .collect(Collectors.toList());
    }

    public static StructureReferenceBean getConceptStructureReferenceBean(String identfiableId) {
        return new StructureReferenceBeanImpl("TEST_AGENCY", "TEST_CONCEPTS", "1.0", CONCEPT, identfiableId);
    }

    public static StructureReferenceBean getCodelistStructureReferenceBean(String agencyId, String maintainableId) {
        return new StructureReferenceBeanImpl(agencyId, maintainableId, "1.0", CODE_LIST);
    }
}
