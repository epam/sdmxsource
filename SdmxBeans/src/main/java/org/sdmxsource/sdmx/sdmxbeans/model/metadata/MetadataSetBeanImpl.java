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
package org.sdmxsource.sdmx.sdmxbeans.model.metadata;

import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.MetadataSetType;
import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReportType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataReportBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataSetBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata set bean.
 */
public class MetadataSetBeanImpl extends SDMXBeanImpl implements MetadataSetBean {
    private static final long serialVersionUID = -4992186811361863150L;

    private String _setId;
    private CrossReferenceBean _structureRef;
    private SdmxDate _reportingBeginDate;
    private SdmxDate _reportingEndDate;
    private SdmxDate _publicationYear;
    private SdmxDate _validFromDate;
    private SdmxDate _validToDate;
    private Object _publicationPeriod;

    private CrossReferenceBean _dataProviderReference;
    private List<TextTypeWrapper> _names = new ArrayList<TextTypeWrapper>();
    private List<MetadataReportBean> _reports = new ArrayList<MetadataReportBean>();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM SPLIT					 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private MetadataSetBeanImpl(MetadataSetBeanImpl metadataSet, MetadataReportBean report) {
        super(SDMX_STRUCTURE_TYPE.METADATA_SET, null);

        this._setId = metadataSet.getSetId();
        this._structureRef = metadataSet._structureRef;
        this._reportingBeginDate = metadataSet._reportingBeginDate;
        this._reportingEndDate = metadataSet._reportingEndDate;
        this._publicationYear = metadataSet._publicationYear;
        this._validFromDate = metadataSet._validFromDate;
        this._validToDate = metadataSet._validToDate;
        this._publicationPeriod = metadataSet._publicationPeriod;
        this._dataProviderReference = metadataSet._dataProviderReference;
        this._names = metadataSet.getNames();
        this._reports.add(report);
        validate();
    }

    /**
     * Instantiates a new Metadata set bean.
     *
     * @param parent      the parent
     * @param createdFrom the created from
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataSetBeanImpl(MetadataBean parent, MetadataSetType createdFrom) {
        super(SDMX_STRUCTURE_TYPE.METADATA_SET, null);

        this._setId = createdFrom.getSetID();

        for (DatasetStructureReferenceBean structurereference : parent.getHeader().getStructures()) {
            if (structurereference.getId().equals(createdFrom.getStructureRef())) {
                _structureRef = new CrossReferenceBeanImpl(this, structurereference.getStructureReference());
                break;
            }
        }
        if (createdFrom.getNameList() != null) {
            this._names = TextTypeUtil.wrapTextTypeV21(createdFrom.getNameList(), this);
        }
        if (createdFrom.getReportingBeginDate() != null) {
            this._reportingBeginDate = new SdmxDateImpl(createdFrom.getReportingBeginDate().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (createdFrom.getReportingEndDate() != null) {
            this._reportingEndDate = new SdmxDateImpl(createdFrom.getReportingEndDate().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (createdFrom.getPublicationYear() != null) {
            this._publicationYear = new SdmxDateImpl(createdFrom.getPublicationYear().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (createdFrom.getValidFromDate() != null) {
            this._validFromDate = new SdmxDateImpl(createdFrom.getValidFromDate().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (createdFrom.getValidToDate() != null) {
            this._validToDate = new SdmxDateImpl(createdFrom.getValidToDate().getTime(), TIME_FORMAT.DATE_TIME);
        }
        //FUNC Publication Period
        this._publicationPeriod = createdFrom.getPublicationPeriod();

        if (createdFrom.getDataProvider() != null) {
            this._dataProviderReference = RefUtil.createReference(this, createdFrom.getDataProvider());
        }
        if (ObjectUtil.validCollection(createdFrom.getReportList())) {
            for (ReportType currentReport : createdFrom.getReportList()) {
                this._reports.add(new MetadataReportBeanImpl(this, currentReport));
            }
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        String setId = getSetId();
        if (setId == null) {
            setId = "";
        } else {
            this._setId = ValidationUtil.cleanAndValidateId(getSetId(), true);
        }
        if (!ObjectUtil.validCollection(_reports)) {
            throw new SdmxSemmanticException("Metadata Set " + setId + " requires at least one Report");
        }
        if (_structureRef == null) {
            throw new SdmxSemmanticException("Metadata Set " + setId + " requires a reference to an MSD");
        }
        if (_structureRef.getTargetReference() != SDMX_STRUCTURE_TYPE.MSD) {
            throw new SdmxSemmanticException("Metadata Set " + setId + " reference must be a reference to an MSD");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<MetadataSetBean> splitReports() {
        List<MetadataSetBean> returnList = new ArrayList<MetadataSetBean>();
        for (MetadataReportBean reportBean : _reports) {
            returnList.add(new MetadataSetBeanImpl(this, reportBean));
        }
        return returnList;
    }

    @Override
    public List<TextTypeWrapper> getNames() {
        return _names;
    }

    @Override
    public String getSetId() {
        return _setId;
    }

    @Override
    public CrossReferenceBean getMsdReference() {
        return _structureRef;
    }

    @Override
    public SdmxDate getPublicationYear() {
        return _publicationYear;
    }

    @Override
    public Object getPublicationPeriod() {
        return _publicationPeriod;
    }

    @Override
    public CrossReferenceBean getDataProviderReference() {
        return _dataProviderReference;
    }

    @Override
    public SdmxDate getReportingBeginDate() {
        return _reportingBeginDate;
    }

    @Override
    public SdmxDate getReportingEndDate() {
        return _reportingEndDate;
    }

    @Override
    public SdmxDate getValidFromDate() {
        return _validFromDate;
    }

    @Override
    public SdmxDate getValidToDate() {
        return _validToDate;
    }

    @Override
    public List<MetadataReportBean> getReports() {
        return new ArrayList<MetadataReportBean>(_reports);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            MetadataSetBean that = (MetadataSetBean) bean;
            if (!ObjectUtil.equivalent(_setId, that.getSetId())) {
                return false;
            }
            if (!super.equivalent(_structureRef, that.getMsdReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_reportingBeginDate, that.getReportingBeginDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_reportingEndDate, that.getReportingEndDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_publicationYear, that.getPublicationYear())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_validFromDate, that.getValidFromDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_validToDate, that.getValidToDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_publicationPeriod, that.getPublicationPeriod())) {
                return false;
            }
            if (!ObjectUtil.equivalent(_dataProviderReference, that.getDataProviderReference())) {
                return false;
            }
            if (!super.equivalent(_names, that.getNames(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(_reports, that.getReports(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MetadataSetBean) {
            MetadataSetBean that = (MetadataSetBean) obj;
            return that.deepEquals(this, true);
        }
        return false;
    }

    @Override
    public String toString() {
        return getSetId();
    }

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(_names, composites);
        super.addToCompositeSet(_reports, composites);
        return composites;
    }
}
