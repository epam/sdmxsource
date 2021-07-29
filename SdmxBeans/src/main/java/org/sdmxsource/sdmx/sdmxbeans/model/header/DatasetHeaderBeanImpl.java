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
package org.sdmxsource.sdmx.sdmxbeans.model.header;

import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.XMLStreamReader;
import java.util.Date;


/**
 * The type Dataset header bean.
 */
public class DatasetHeaderBeanImpl implements DatasetHeaderBean {
    private MaintainableRefBean dataProviderRef;
    private DatasetStructureReferenceBean datasetStructureReferenceBean;
    private String datasetId;
    private Date reportingBeginDate;
    private Date reportingEndDate;
    private Date validFrom;
    private Date validTo;
    private DATASET_ACTION action = DATASET_ACTION.INFORMATION;
    private int publicationYear = -1;
    private String publicationPeriod;
    private String reportingYearStartDate;  //TODO WE NEED A MONTH DAY TYPE


    /**
     * Minimal Constructor
     *
     * @param datasetId                     the dataset id
     * @param action                        the action
     * @param datasetStructureReferenceBean the dataset structure reference bean
     */
    public DatasetHeaderBeanImpl(String datasetId, DATASET_ACTION action, DatasetStructureReferenceBean datasetStructureReferenceBean) {
        this.datasetId = datasetId;
        if (action != null) {
            this.action = action;
        }
        this.datasetStructureReferenceBean = datasetStructureReferenceBean;
    }

    /**
     * Instantiates a new Dataset header bean.
     *
     * @param datasetId                     the dataset id
     * @param action                        the action
     * @param datasetStructureReferenceBean the dataset structure reference bean
     * @param dataProviderRef               the data provider ref
     * @param reportingBeginDate            the reporting begin date
     * @param reportingEndDate              the reporting end date
     * @param validFrom                     the valid from
     * @param validTo                       the valid to
     * @param publicationYear               the publication year
     * @param publicationPeriod             the publication period
     * @param reportingYearStartDate        the reporting year start date
     */
    public DatasetHeaderBeanImpl(String datasetId, DATASET_ACTION action, DatasetStructureReferenceBean datasetStructureReferenceBean, MaintainableRefBean dataProviderRef,
                                 Date reportingBeginDate, Date reportingEndDate, Date validFrom, Date validTo, int publicationYear, String publicationPeriod, String reportingYearStartDate) {
        this.dataProviderRef = dataProviderRef;
        this.datasetStructureReferenceBean = datasetStructureReferenceBean;
        this.datasetId = datasetId;
        this.reportingBeginDate = reportingBeginDate;
        this.reportingEndDate = reportingEndDate;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.action = action;
        this.publicationYear = publicationYear;
        this.publicationPeriod = publicationPeriod;
        this.reportingYearStartDate = reportingYearStartDate;
    }


    /**
     * Create an instance from a parser reading the dataset XML Node and the header
     *
     * @param parser        the parser
     * @param datasetHeader the dataset header
     */
    public DatasetHeaderBeanImpl(XMLStreamReader parser, HeaderBean datasetHeader) {
        if (parser.getAttributeValue(null, "structureRef") != null) {
            String structureRef = parser.getAttributeValue(null, "structureRef");
            datasetStructureReferenceBean = getStructureFromHeader(datasetHeader, structureRef);
            if (datasetStructureReferenceBean == null) {
                throw new SdmxSemmanticException("Dataset references Structure that is not defined in the Header of the message.  Structure reference defined by Dataset is:" + structureRef);
            }
        } else {
            datasetStructureReferenceBean = generateOrUseDefaultStructure(parser, datasetHeader);
        }

        if (parser.getAttributeValue(null, "action") != null) {
            this.action = DATASET_ACTION.getAction(parser.getAttributeValue(null, "action"));
        } else if (datasetHeader.getAction() != null) {
            this.action = datasetHeader.getAction();
        }


//		if(parser.getAttributeValue(null, "dataProviderID") != null) {
//			datasetAttributes.setDataProviderId(parser.getAttributeValue(null, "dataProviderID"));
//		}
//		if(parser.getAttributeValue(null, "dataProviderSchemeAgencyId") != null) {
//			datasetAttributes.setDataProviderSchemeAgencyId(parser.getAttributeValue(null, "dataProviderSchemeAgencyId"));
//		}
//		if(parser.getAttributeValue(null, "dataProviderSchemeId") != null) {
//			datasetAttributes.setDataProviderSchemeId(parser.getAttributeValue(null, "dataProviderSchemeId"));
//		}
        if (parser.getAttributeValue(null, "datasetID") != null) {
            this.datasetId = parser.getAttributeValue(null, "datasetID");
        }

        if (parser.getAttributeValue(null, "publicationPeriod") != null) {
            this.publicationPeriod = parser.getAttributeValue(null, "publicationPeriod");
        }
        if (parser.getAttributeValue(null, "publicationYear") != null) {
            this.publicationYear = Integer.parseInt(parser.getAttributeValue(null, "publicationYear"));
        }
        if (parser.getAttributeValue(null, "reportingBeginDate") != null) {
            this.reportingBeginDate = DateUtil.formatDate(parser.getAttributeValue(null, "reportingBeginDate"), true);
        }
        if (parser.getAttributeValue(null, "reportingEndDate") != null) {
            this.reportingEndDate = DateUtil.formatDate(parser.getAttributeValue(null, "reportingEndDate"), true);
        }
        if (parser.getAttributeValue(null, "validFromDate") != null) {
            this.validFrom = DateUtil.formatDate(parser.getAttributeValue(null, "validFromDate"), true);
        }
        if (parser.getAttributeValue(null, "validToDate") != null) {
            this.validTo = DateUtil.formatDate(parser.getAttributeValue(null, "validToDate"), true);
        }
    }

    @Override
    public DatasetHeaderBean modifyDataStructureReference(DatasetStructureReferenceBean datasetStructureReferenceBean) {
        return new DatasetHeaderBeanImpl(getDatasetId(),
                getAction(),
                datasetStructureReferenceBean,
                getDataProviderReference(),
                getReportingBeginDate(),
                getReportingEndDate(),
                getValidFrom(),
                getValidTo(),
                getPublicationYear(),
                getPublicationPeriod(), null);  //TODO reportingYearStartDate
    }

    private DatasetStructureReferenceBean generateOrUseDefaultStructure(XMLStreamReader parser, HeaderBean datasetHeader) {
        StructureReferenceBean sRef = null;
        MaintainableRefBean dataflowReference = getDataflowReference(parser);
        MaintainableRefBean dsdReference = getDsdReference(parser);
        if (dataflowReference != null) {
            sRef = new StructureReferenceBeanImpl(dataflowReference, SDMX_STRUCTURE_TYPE.DATAFLOW);
        } else if (dsdReference != null) {
            sRef = new StructureReferenceBeanImpl(dsdReference, SDMX_STRUCTURE_TYPE.DSD);
        } else if (datasetHeader.getStructures().size() == 1) {
            return datasetHeader.getStructures().get(0);
        } else {
            return null;
        }
        return new DatasetStructureReferenceBeanImpl("generated", sRef, null, null, DimensionBean.TIME_DIMENSION_FIXED_ID);
    }

    private DatasetStructureReferenceBean getStructureFromHeader(HeaderBean header, String structureRef) {
        if (header.getStructures() == null) {
            return null;
        }
        for (DatasetStructureReferenceBean currentStructure : header.getStructures()) {
            if (currentStructure.getId().equals(structureRef)) {
                return currentStructure;
            }
        }
        return null;
    }

    private MaintainableRefBean getDataflowReference(XMLStreamReader parser) {
        String dfId = null;
        String dfAcy = null;
        if (parser.getAttributeValue(null, "dataflowAgencyID") != null) {
            dfAcy = parser.getAttributeValue(null, "dataflowAgencyID");
        }
        if (parser.getAttributeValue(null, "dataflowID") != null) {
            dfId = parser.getAttributeValue(null, "dataflowID");
        }
        if (ObjectUtil.validString(dfId)) {
            return new MaintainableRefBeanImpl(dfAcy, dfId, MaintainableBean.DEFAULT_VERSION);
        }
        return null;
    }

    private MaintainableRefBean getDsdReference(XMLStreamReader parser) {
        if (parser.getAttributeValue(null, "keyFamilyURI") != null) {
            String dsdUri = parser.getAttributeValue(null, "keyFamilyURI");
            StructureReferenceBean dsdRef = new StructureReferenceBeanImpl(dsdUri);
            return dsdRef.getMaintainableReference();
        }
        return null;
    }

    @Override
    public boolean isTimeSeries() {
        if (datasetStructureReferenceBean == null) {
            return true;
        }
        return datasetStructureReferenceBean.isTimeSeries();
    }

    @Override
    public DatasetStructureReferenceBean getDataStructureReference() {
        return datasetStructureReferenceBean;
    }

    @Override
    public DATASET_ACTION getAction() {
        return action;
    }

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(DATASET_ACTION action) {
        this.action = action;
    }

    @Override
    public MaintainableRefBean getDataProviderReference() {
        return dataProviderRef;
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    /**
     * Sets dataset id.
     *
     * @param datasetId the dataset id
     */
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public Date getReportingBeginDate() {
        return reportingBeginDate;
    }

    /**
     * Sets reporting begin date.
     *
     * @param reportingBeginDate the reporting begin date
     */
    public void setReportingBeginDate(Date reportingBeginDate) {
        this.reportingBeginDate = reportingBeginDate;
    }

    @Override
    public Date getReportingEndDate() {
        return reportingEndDate;
    }

    /**
     * Sets reporting end date.
     *
     * @param reportingEndDate the reporting end date
     */
    public void setReportingEndDate(Date reportingEndDate) {
        this.reportingEndDate = reportingEndDate;
    }

    @Override
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets valid from.
     *
     * @param validFrom the valid from
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Date getValidTo() {
        return validTo;
    }

    /**
     * Sets valid to.
     *
     * @param validTo the valid to
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets publication year.
     *
     * @param publicationYear the publication year
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String getPublicationPeriod() {
        return publicationPeriod;
    }

    /**
     * Sets publication period.
     *
     * @param publicationPeriod the publication period
     */
    public void setPublicationPeriod(String publicationPeriod) {
        this.publicationPeriod = publicationPeriod;
    }

    /**
     * Sets data provider ref.
     *
     * @param dataProviderRef the data provider ref
     */
    public void setDataProviderRef(MaintainableRefBean dataProviderRef) {
        this.dataProviderRef = dataProviderRef;
    }

    /**
     * Sets dataset structure reference bean.
     *
     * @param datasetStructureReferenceBean the dataset structure reference bean
     */
    public void setDatasetStructureReferenceBean(DatasetStructureReferenceBean datasetStructureReferenceBean) {
        this.datasetStructureReferenceBean = datasetStructureReferenceBean;
    }

    /**
     * Sets reporting year start date.
     *
     * @param reportingYearStartDate the reporting year start date
     */
    public void setReportingYearStartDate(String reportingYearStartDate) {
        this.reportingYearStartDate = reportingYearStartDate;
    }
}
