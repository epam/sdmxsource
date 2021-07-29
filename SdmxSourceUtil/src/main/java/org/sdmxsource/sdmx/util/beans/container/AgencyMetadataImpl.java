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
package org.sdmxsource.sdmx.util.beans.container;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.AgencyMetadata;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Agency metadata.
 */
public class AgencyMetadataImpl implements AgencyMetadata {
    private static final long serialVersionUID = 4951693846664635463L;
    private Map<SDMX_STRUCTURE_TYPE, Integer> structureMap = new HashMap<SDMX_STRUCTURE_TYPE, Integer>();
    private String agencyId;

    /**
     * Instantiates a new Agency metadata.
     */
    public AgencyMetadataImpl() {
        super();
    }

    /**
     * Instantiates a new Agency metadata.
     *
     * @param agencyId the agency id
     * @param beans    the beans
     */
    public AgencyMetadataImpl(String agencyId, SdmxBeans beans) {
        this.agencyId = agencyId;
        structureMap.put(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, beans.getAgencyScheme(agencyId) == null ? 0 : 1);
        structureMap.put(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT, beans.getAttachmentConstraints(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT, beans.getContentConstraintBeans(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME, beans.getDataProviderScheme(agencyId) == null ? 0 : 1);
        structureMap.put(SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME, beans.getDataConsumerScheme(agencyId) == null ? 0 : 1);
        structureMap.put(SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME, beans.getOrganisationUnitSchemes(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.CATEGORISATION, beans.getCategorisations(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.DATAFLOW, beans.getDataflows(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.METADATA_FLOW, beans.getMetadataflows(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME, beans.getCategorySchemes(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, beans.getConceptSchemes(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.CODE_LIST, beans.getCodelists(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST, beans.getHierarchicalCodelists(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.MSD, beans.getMetadataStructures(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.DSD, beans.getDataStructures(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.PROCESS, beans.getProcesses(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY, beans.getReportingTaxonomys(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.STRUCTURE_SET, beans.getStructureSets(agencyId).size());
        structureMap.put(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT, beans.getProvisionAgreements(agencyId).size());
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets agency id.
     *
     * @param agencyId the agency id
     */
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public Integer getNumberOfMaintainables(SDMX_STRUCTURE_TYPE structureType) {
        if (structureMap.containsKey(structureType)) {
            return structureMap.get(structureType);
        }
        return 0;
    }

    @Override
    public Integer getNumberMaintainables() {
        int i = 0;
        for (SDMX_STRUCTURE_TYPE currentMaint : SDMX_STRUCTURE_TYPE.getMaintainableStructureTypes()) {
            i += getNumberOfMaintainables(currentMaint);
        }
        return i;
    }

    /**
     * Sets number maintainables.
     *
     * @param num the num
     */
    public void setNumberMaintainables(Integer num) {
        //DO NOTHING - this is here for passing to external applications
    }

    @Override
    public Integer getNumberCategorisations() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.CATEGORISATION);
    }

    /**
     * Sets number categorisations.
     *
     * @param num the num
     */
    public void setNumberCategorisations(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.CATEGORISATION, num);
    }

    @Override
    public Integer getNumberAgencySchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
    }

    /**
     * Sets number agency schemes.
     *
     * @param num the num
     */
    public void setNumberAgencySchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.AGENCY_SCHEME, num);
    }

    @Override
    public Integer getNumberAttachmentConstraint() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT);
    }

    /**
     * Sets number attachment constraint.
     *
     * @param num the num
     */
    public void setNumberAttachmentConstraint(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.ATTACHMENT_CONSTRAINT, num);
    }

    @Override
    public Integer getNumberContentConstraintBean() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT);
    }

    /**
     * Sets number content constraint bean.
     *
     * @param num the num
     */
    public void setNumberContentConstraintBean(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT, num);
    }

    @Override
    public Integer getNumberDataProviderSchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME);
    }

    /**
     * Sets number data provider schemes.
     *
     * @param num the num
     */
    public void setNumberDataProviderSchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME, num);
    }

    @Override
    public Integer getNumberDataConsumerSchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME);
    }

    /**
     * Sets number data consumer schemes.
     *
     * @param num the num
     */
    public void setNumberDataConsumerSchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME, num);
    }

    @Override
    public Integer getNumberOrganisationUnitSchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
    }

    /**
     * Sets number organisation unit schemes.
     *
     * @param num the num
     */
    public void setNumberOrganisationUnitSchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME, num);
    }

    @Override
    public Integer getNumberDataflows() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.DATAFLOW);
    }

    /**
     * Sets number dataflows.
     *
     * @param num the num
     */
    public void setNumberDataflows(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.DATAFLOW, num);
    }

    @Override
    public Integer getNumberMetadataflows() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.METADATA_FLOW);
    }

    /**
     * Sets number metadataflows.
     *
     * @param num the num
     */
    public void setNumberMetadataflows(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.METADATA_FLOW, num);
    }

    @Override
    public Integer getNumberCategorySchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME);
    }

    /**
     * Sets number category schemes.
     *
     * @param num the num
     */
    public void setNumberCategorySchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME, num);
    }

    @Override
    public Integer getNumberConceptSchemes() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
    }

    /**
     * Sets number concept schemes.
     *
     * @param num the num
     */
    public void setNumberConceptSchemes(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME, num);
    }

    @Override
    public Integer getNumberCodelists() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.CODE_LIST);
    }

    /**
     * Sets number codelists.
     *
     * @param num the num
     */
    public void setNumberCodelists(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.CODE_LIST, num);
    }

    @Override
    public Integer getNumberHierarchicalCodelists() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
    }

    /**
     * Sets number hierarchical codelists.
     *
     * @param num the num
     */
    public void setNumberHierarchicalCodelists(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST, num);
    }

    @Override
    public Integer getNumberMetadataStructureDefinitions() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.MSD);
    }

    /**
     * Sets number metadata structure definitions.
     *
     * @param num the num
     */
    public void setNumberMetadataStructureDefinitions(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.MSD, num);
    }

    @Override
    public Integer getNumberDataStructures() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.DSD);
    }

    /**
     * Sets number data structures.
     *
     * @param num the num
     */
    public void setNumberDataStructures(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.DSD, num);
    }

    @Override
    public Integer getNumberStructureSets() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.STRUCTURE_SET);
    }

    /**
     * Sets number structure sets.
     *
     * @param num the num
     */
    public void setNumberStructureSets(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.STRUCTURE_SET, num);
    }

    @Override
    public Integer getNumberReportingTaxonomies() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY);
    }

    /**
     * Sets number reporting taxonomies.
     *
     * @param num the num
     */
    public void setNumberReportingTaxonomies(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY, num);
    }

    @Override
    public Integer getNumberProcesses() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.PROCESS);
    }

    /**
     * Sets number processes.
     *
     * @param num the num
     */
    public void setNumberProcesses(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.PROCESS, num);
    }

    @Override
    public Integer getNumberProvisions() {
        return structureMap.get(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
    }

    /**
     * Sets number provisions.
     *
     * @param num the num
     */
    public void setNumberProvisions(Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT, num);
    }

    /**
     * Sets structure.
     *
     * @param structureType the structure type
     * @param num           the num
     */
    public void setStructure(SDMX_STRUCTURE_TYPE structureType, Integer num) {
        if (num == null) {
            num = 0;
        }
        structureMap.put(structureType, num);
    }
}
