package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorySchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategoryMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme.CategorySchemeMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CATEGORY;

public class CategoryReferenceTest {

    private final static String ROOT_CATEGORY = "ROOT_CAT";
    private final static String SECOND_LEVEL_CATEGORY = "SecondLevel1";
    private final static String THIRD_LEVEL_CATEGORY = "ThirdLevel1";

    @Test
    public void shouldCheckIfCategoryReferenceIsCreatedWithFullPath() {
        var categorySchemeObject = getCategorySchemeMutableBean();
        var categoryBean = categorySchemeObject.getCategory(ROOT_CATEGORY, SECOND_LEVEL_CATEGORY, THIRD_LEVEL_CATEGORY);
        StructureReferenceBean expectedStructureReferenceBean =
                new StructureReferenceBeanImpl(categorySchemeObject, CATEGORY, ROOT_CATEGORY, SECOND_LEVEL_CATEGORY, THIRD_LEVEL_CATEGORY);

        assertEquals(expectedStructureReferenceBean, categoryBean.asReference());
        assertEquals("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=TEST:TEST_CAT_SCH(1.0).ROOT_CAT.SecondLevel1.ThirdLevel1",
                categoryBean.asReference().getTargetUrn());
    }

    private CategorySchemeBean getCategorySchemeMutableBean() {
        CategorySchemeMutableBean categoryScheme = new CategorySchemeMutableBeanImpl();
        categoryScheme.setId("TEST_CAT_SCH");
        categoryScheme.setAgencyId("TEST");
        categoryScheme.setVersion("1.0");
        categoryScheme.addName("en", "Test CategoryScheme");

        CategoryMutableBean rootCategory = getCategoryMutableBean(ROOT_CATEGORY, "Root category");
        categoryScheme.addItem(rootCategory);
        CategoryMutableBean secondLevelCategory1 = getCategoryMutableBean(SECOND_LEVEL_CATEGORY, "2nd level 1");
        rootCategory.addItem(secondLevelCategory1);
        secondLevelCategory1.addItem(getCategoryMutableBean(THIRD_LEVEL_CATEGORY, "3rd level 1"));
        return categoryScheme.getImmutableInstance();
    }

    private CategoryMutableBean getCategoryMutableBean(String id, String name) {
        CategoryMutableBean category = new CategoryMutableBeanImpl();
        category.setId(id);
        category.addName("en", name);
        return category;
    }

}
