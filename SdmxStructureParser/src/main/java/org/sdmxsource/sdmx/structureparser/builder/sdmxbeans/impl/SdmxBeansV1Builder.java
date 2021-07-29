/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureDocument;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.util.*;


/**
 * The type Sdmx beans v 1 builder.
 */
public class SdmxBeansV1Builder extends AbstractSdmxBeansBuilder implements Builder<SdmxBeans, StructureDocument> {

    @Override
    public SdmxBeans build(StructureDocument structuresDoc) throws SdmxException {
        Set<String> urns = new HashSet<String>();
        org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.StructureType structures = structuresDoc.getStructure();
        SdmxBeansImpl beans = new SdmxBeansImpl(new HeaderBeanImpl(structures.getHeader()));

        if (structures.getCodeLists() != null && structures.getCodeLists().getCodeListList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeListType currentType : structures.getCodeLists().getCodeListList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new CodelistBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CODE_LIST, currentType.getAgency(), currentType.getId(), currentType.getVersion());
                }
            }
        }

        Map<String, List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType>> conceptAgencyMap
                = new HashMap<String, List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType>>();
        if (structures.getConcepts() != null && structures.getConcepts().getConceptList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType currentType : structures.getConcepts().getConceptList()) {
                List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType> concepts;
                if (conceptAgencyMap.containsKey(currentType.getAgency())) {
                    concepts = conceptAgencyMap.get(currentType.getAgency());
                } else {
                    concepts = new ArrayList<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType>();
                    conceptAgencyMap.put(currentType.getAgency(), concepts);
                }
                concepts.add(currentType);
            }
        }
        for (String currentConceptAgency : conceptAgencyMap.keySet()) {
            try {
                addIfNotDuplicateURN(beans, urns, new ConceptSchemeBeanImpl(currentConceptAgency, conceptAgencyMap.get(currentConceptAgency)));
            } catch (Throwable th) {
                throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, currentConceptAgency, ConceptSchemeBean.DEFAULT_SCHEME_ID, ConceptSchemeBean.DEFAULT_SCHEME_VERSION);
            }
        }
        if (structures.getKeyFamilies() != null && structures.getKeyFamilies().getKeyFamilyList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.KeyFamilyType currentType : structures.getKeyFamilies().getKeyFamilyList()) {
                try {
                    addIfNotDuplicateURN(beans, urns, new DataStructureBeanImpl(currentType));
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DSD, currentType.getAgency(), currentType.getId(), currentType.getVersion());
                }
            }
        }
        return beans;
    }
}
