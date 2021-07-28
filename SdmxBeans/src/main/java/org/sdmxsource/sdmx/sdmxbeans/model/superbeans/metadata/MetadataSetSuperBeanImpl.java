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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataReportBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataSetBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataReportSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.MetadataSetSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.SuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata set super bean.
 */
public class MetadataSetSuperBeanImpl extends SuperBeanImpl implements MetadataSetSuperBean {
    private static final long serialVersionUID = 7206438606756205144L;
    private MetadataStructureDefinitionBean metadataStructure;
    private DataProviderBean dataProvider;
    private List<MetadataReportSuperBean> reports = new ArrayList<MetadataReportSuperBean>();
    private MetadataSetBean builtFrom;


    /**
     * Instantiates a new Metadata set super bean.
     *
     * @param builtFrom        the built from
     * @param retrievalManager the retrieval manager
     */
    public MetadataSetSuperBeanImpl(MetadataSetBean builtFrom, IdentifiableRetrievalManager retrievalManager) {
        super(builtFrom);
        this.builtFrom = builtFrom;
        this.metadataStructure = retrievalManager.getIdentifiableBean(builtFrom.getMsdReference(), MetadataStructureDefinitionBean.class);
        if (builtFrom.getDataProviderReference() != null) {
            this.dataProvider = retrievalManager.getIdentifiableBean(builtFrom.getDataProviderReference(), DataProviderBean.class);
        }
        if (builtFrom.getReports() != null) {
            for (MetadataReportBean currentMR : builtFrom.getReports()) {
                ReportStructureBean rs = getReportStructure(currentMR, metadataStructure.getReportStructures());
                this.reports.add(new MetadataReportSuperBeanImpl(rs, currentMR, retrievalManager));
            }
        }
    }

    private ReportStructureBean getReportStructure(MetadataReportBean currentMR, List<ReportStructureBean> reportStructures) {
        for (ReportStructureBean currentRS : reportStructures) {
            if (currentRS.getId().equals(currentMR.getId())) {
                return currentRS;
            }
        }
        throw new SdmxSemmanticException("Can not find reference to report structure with id " + currentMR.getId());
    }

    @Override
    public String getSetId() {
        return builtFrom.getSetId();
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();

        returnSet.add(metadataStructure);
        if (dataProvider != null) {
            returnSet.add(dataProvider.getMaintainableParent());
        }
        if (reports != null) {
            for (MetadataReportSuperBean report : reports) {
                returnSet.addAll(report.getCompositeBeans());
            }
        }
        return returnSet;
    }

    @Override
    public SdmxDate getReportingBeginDate() {
        return builtFrom.getReportingBeginDate();
    }

    @Override
    public SdmxDate getReportingEndDate() {
        return builtFrom.getReportingEndDate();
    }

    @Override
    public SdmxDate getValidFromDate() {
        return builtFrom.getValidFromDate();
    }

    @Override
    public SdmxDate getValidToDate() {
        return builtFrom.getValidToDate();
    }

    @Override
    public SdmxDate getPublicationYear() {
        return builtFrom.getPublicationYear();
    }

    @Override
    public Object getPublicationPeriod() {
        return builtFrom.getPublicationPeriod();
    }

    @Override
    public MetadataStructureDefinitionBean getMetadataStructure() {
        return metadataStructure;
    }

    @Override
    public DataProviderBean getDataProvider() {
        return dataProvider;
    }

    @Override
    public List<MetadataReportSuperBean> getReports() {
        return new ArrayList<MetadataReportSuperBean>(reports);
    }

    @Override
    public MetadataSetBean getBuiltFrom() {
        return builtFrom;
    }
}
