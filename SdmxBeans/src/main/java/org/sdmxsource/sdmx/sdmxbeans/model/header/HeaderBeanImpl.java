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

import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ActionType;
import org.sdmx.resources.sdmxml.schemas.v21.common.PayloadStructureType;
import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.message.BaseHeaderType;
import org.sdmx.resources.sdmxml.schemas.v21.message.PartyType;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.random.RandomUtil;

import java.io.Serializable;
import java.util.*;


/**
 * The type Header bean.
 */
public class HeaderBeanImpl implements HeaderBean, Serializable {
    private static final long serialVersionUID = -1069998895797472200L;

    private Map<String, String> additionalAttributes = new HashMap<String, String>();

    private StructureReferenceBean dataProviderReference;
    private DATASET_ACTION datasetAction;
    private String id;
    private String datasetId;
    private Date embargoDate;
    private Date extracted;
    private Date prepared;
    private Date reportingBegin;
    private Date reportingEnd;
    private List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
    private List<TextTypeWrapper> source = new ArrayList<TextTypeWrapper>();
    private List<PartyBean> receiver = new ArrayList<PartyBean>();
    private List<DatasetStructureReferenceBean> structureReferences = new ArrayList<DatasetStructureReferenceBean>();
    private PartyBean sender;
    private boolean test;

    /**
     * Instantiates a new Header bean.
     *
     * @param id       the id
     * @param senderId the sender id
     */
    public HeaderBeanImpl(String id, String senderId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID may not be null");
        }
        if (Character.isDigit(id.charAt(0))) {
            throw new IllegalArgumentException("An ID may not start with a digit!");
        }

        this.id = id;
        this.sender = new PartyBeanImpl(null, senderId, null, null);
        this.prepared = new Date();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ATTRIBUTES			   ////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Minimal Constructor
     *
     * @param id             the id
     * @param prepared       the prepared
     * @param reportingBegin the reporting begin
     * @param reportingEnd   the reporting end
     * @param receiver       the receiver
     * @param sender         the sender
     * @param isTest         the is test
     */
    public HeaderBeanImpl(String id, Date prepared, Date reportingBegin, Date reportingEnd, List<PartyBean> receiver, PartyBean sender, boolean isTest) {
        this(null, null, null, null, id, null, null, null, prepared, reportingBegin, reportingEnd, null, null, receiver, sender, isTest);
    }

    /**
     * Instantiates a new Header bean.
     *
     * @param additionalAttributes  the additional attributes
     * @param structures            the structures
     * @param dataProviderReference the data provider reference
     * @param datasetAction         the dataset action
     * @param id                    the id
     * @param datasetId             the dataset id
     * @param embargoDate           the embargo date
     * @param extracted             the extracted
     * @param prepared              the prepared
     * @param reportingBegin        the reporting begin
     * @param reportingEnd          the reporting end
     * @param name                  the name
     * @param source                the source
     * @param receiver              the receiver
     * @param sender                the sender
     * @param test                  the test
     */
    public HeaderBeanImpl(Map<String, String> additionalAttributes,
                          List<DatasetStructureReferenceBean> structures,
                          StructureReferenceBean dataProviderReference,
                          DATASET_ACTION datasetAction, String id, String datasetId,
                          Date embargoDate, Date extracted, Date prepared,
                          Date reportingBegin, Date reportingEnd, List<TextTypeWrapper> name,
                          List<TextTypeWrapper> source, List<PartyBean> receiver, PartyBean sender, boolean test) {
        if (additionalAttributes != null) {
            this.additionalAttributes = new HashMap<String, String>(additionalAttributes);
        }
        if (structures != null) {
            this.structureReferences = new ArrayList<DatasetStructureReferenceBean>(structures);
        }
        this.dataProviderReference = dataProviderReference;
        this.datasetAction = datasetAction;
        this.id = id;
        this.datasetId = datasetId;
        if (embargoDate != null) {
            this.embargoDate = new Date(embargoDate.getTime());
        }
        if (extracted != null) {
            this.extracted = new Date(extracted.getTime());
        }
        if (prepared != null) {
            this.prepared = new Date(prepared.getTime());
        }
        if (reportingBegin != null) {
            this.reportingBegin = new Date(reportingBegin.getTime());
        }
        if (reportingEnd != null) {
            this.reportingEnd = new Date(reportingEnd.getTime());
        }
        if (name != null) {
            this.name = new ArrayList<TextTypeWrapper>(name);
        }
        if (source != null) {
            this.source = new ArrayList<TextTypeWrapper>(source);
        }
        if (receiver != null) {
            this.receiver = new ArrayList<PartyBean>(receiver);
        }
        this.sender = sender;
        this.test = test;
        validate();
    }


    /**
     * Instantiates a new Header bean.
     *
     * @param headerType the header type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HeaderBeanImpl(BaseHeaderType headerType) {
        test = headerType.getTest();
        if (headerType.getDataProvider() != null) {
            dataProviderReference = RefUtil.createReference(headerType
                    .getDataProvider());
        }
        if (headerType.getDataSetAction() != null) {
            switch (headerType.getDataSetAction().intValue()) {
                case ActionType.INT_APPEND:
                    datasetAction = DATASET_ACTION.APPEND;
                    break;
                case ActionType.INT_REPLACE:
                    datasetAction = DATASET_ACTION.REPLACE;
                    break;
                case ActionType.INT_DELETE:
                    datasetAction = DATASET_ACTION.DELETE;
                    break;
            }
        }
        if (ObjectUtil.validCollection(headerType.getDataSetIDList())) {
            datasetId = headerType.getDataSetIDArray(0);
        }
        id = headerType.getID();
        if (headerType.getEmbargoDate() != null) {
            embargoDate = headerType.getEmbargoDate().getTime();
        }
        if (headerType.getExtracted() != null) {
            extracted = headerType.getExtracted().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getNameList())) {
            for (TextType tt : headerType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (headerType.getPrepared() != null) {
            prepared = headerType.getPrepared().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getReceiverList())) {
            for (PartyType party : headerType.getReceiverList()) {
                this.receiver.add(new PartyBeanImpl(party));
            }
        }
        if (headerType.getReportingBegin() != null) {
            reportingBegin = DateUtil.formatDate(headerType.getReportingBegin(), true);
        }
        if (headerType.getReportingEnd() != null) {
            reportingEnd = DateUtil.formatDate(headerType.getReportingEnd(), true);
        }
        if (headerType.getSender() != null) {
            sender = new PartyBeanImpl(headerType.getSender());
        }
        if (ObjectUtil.validCollection(headerType.getSourceList())) {
            for (TextType tt : headerType.getSourceList()) {
                source.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validCollection(headerType.getStructureList())) {
            for (PayloadStructureType payloadSt : headerType.getStructureList()) {
                structureReferences.add(new DatasetStructureReferenceBeanImpl(payloadSt));
            }
        }
        validate();
    }


    /**
     * Instantiates a new Header bean.
     *
     * @param headerType the header type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.0 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HeaderBeanImpl(HeaderType headerType) {
        test = headerType.getTest();

        if (headerType.getDataSetAction() != null) {
            switch (headerType.getDataSetAction().intValue()) {
                case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_APPEND:
                    datasetAction = DATASET_ACTION.APPEND;
                    break;
                case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_REPLACE:
                    datasetAction = DATASET_ACTION.REPLACE;
                    break;
                case org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.INT_DELETE:
                    datasetAction = DATASET_ACTION.DELETE;
                    break;
            }
        }
        this.id = headerType.getID();
        this.datasetId = headerType.getDataSetID();
        if (headerType.getExtracted() != null) {
            extracted = headerType.getExtracted().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : headerType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (headerType.getPrepared() != null) {
            prepared = headerType.getPrepared().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getReceiverList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.message.PartyType party : headerType.getReceiverList()) {
                this.receiver.add(new PartyBeanImpl(party));
            }
        }
        if (headerType.getReportingBegin() != null) {
            reportingBegin = DateUtil.formatDate(headerType.getReportingBegin(), true);
        }
        if (headerType.getReportingEnd() != null) {
            reportingEnd = DateUtil.formatDate(headerType.getReportingEnd(), true);
        }
        if (ObjectUtil.validCollection(headerType.getSenderList())) {
            sender = new PartyBeanImpl(headerType.getSenderList().get(0));
        }
        if (ObjectUtil.validCollection(headerType.getSourceList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : headerType.getSourceList()) {
                source.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validString(headerType.getKeyFamilyAgency())) {
            additionalAttributes.put(HeaderBean.DSD_AGENCY_REF, headerType.getKeyFamilyAgency());
        }
        if (ObjectUtil.validString(headerType.getKeyFamilyRef())) {
            additionalAttributes.put(HeaderBean.DSD_REF, headerType.getKeyFamilyRef());
        }
        validate();
    }


    /**
     * Instantiates a new Header bean.
     *
     * @param headerType the header type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1.0 SCHEMA			///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HeaderBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.HeaderType headerType) {
        test = headerType.getTest();
        if (headerType.getDataSetAction() != null) {
            switch (headerType.getDataSetAction().intValue()) {
                case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.ActionType.INT_UPDATE:
                    datasetAction = DATASET_ACTION.REPLACE;
                    break;
                case org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.ActionType.INT_DELETE:
                    datasetAction = DATASET_ACTION.REPLACE;
                    break;
            }
        }
        this.id = headerType.getID();
        this.datasetId = headerType.getDataSetID();
        if (headerType.getExtracted() != null) {
            extracted = headerType.getExtracted().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : headerType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (headerType.getPrepared() != null) {
            prepared = headerType.getPrepared().getTime();
        }
        if (ObjectUtil.validCollection(headerType.getReceiverList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.PartyType party : headerType.getReceiverList()) {
                this.receiver.add(new PartyBeanImpl(party));
            }
        }
        if (headerType.getReportingBegin() != null) {
            reportingBegin = DateUtil.formatDate(headerType.getReportingBegin(), true);
        }
        if (headerType.getReportingEnd() != null) {
            reportingEnd = DateUtil.formatDate(headerType.getReportingEnd(), true);
        }
        if (headerType.getSender() != null) {
            sender = new PartyBeanImpl(headerType.getSender());
        }
        if (ObjectUtil.validCollection(headerType.getSourceList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : headerType.getSourceList()) {
                source.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validString(headerType.getKeyFamilyAgency())) {
            additionalAttributes.put(HeaderBean.DSD_AGENCY_REF, headerType.getKeyFamilyAgency());
        }
        if (ObjectUtil.validString(headerType.getKeyFamilyRef())) {
            additionalAttributes.put(HeaderBean.DSD_REF, headerType.getKeyFamilyRef());
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION 						///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            id = RandomUtil.generateRandomString();
        }
        if (prepared == null) {
            prepared = new Date();
        }
        if (sender == null) {
            if (!ObjectUtil.validString(id)) {
                throw new SdmxSemmanticException("Header missing sender");
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS 						///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public StructureReferenceBean getDataProviderReference() {
        return dataProviderReference;
    }

    @Override
    public void setDataProviderReference(StructureReferenceBean dataProvider) {
        if (dataProvider != null) {
            if (dataProvider.getTargetReference() != SDMX_STRUCTURE_TYPE.DATA_PROVIDER) {
                throw new SdmxSemmanticException("Header.setDataProviderReference - structure type does not reference a data provider, it references a " + dataProvider.getTargetReference().getType());
            }
            if (dataProvider.getTargetUrn() == null) {
                throw new SdmxSemmanticException("Header.setDataProviderReference - data provider reference incomplete");
            }
        }
        this.dataProviderReference = dataProvider;
    }

    @Override
    public DatasetStructureReferenceBean getStructureById(String structureId) {
        for (DatasetStructureReferenceBean currentStructure : structureReferences) {
            if (currentStructure.getId().equals(structureId)) {
                return currentStructure;
            }
        }
        return null;
    }

    @Override
    public void addStructure(DatasetStructureReferenceBean ref) {
        structureReferences.add(ref);
    }

    @Override
    public List<DatasetStructureReferenceBean> getStructures() {
        return new ArrayList<DatasetStructureReferenceBean>(structureReferences);
    }

    @Override
    public DATASET_ACTION getAction() {
        return datasetAction;
    }

    @Override
    public void setAction(DATASET_ACTION action) {
        this.datasetAction = action;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        validate();
    }

    @Override
    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public Date getEmbargoDate() {
        if (embargoDate != null) {
            return new Date(embargoDate.getTime());
        }
        return null;
    }

    @Override
    public void setEmbargoDate(Date aDate) {
        this.embargoDate = aDate;
    }

    @Override
    public Date getExtracted() {
        if (extracted != null) {
            return new Date(extracted.getTime());
        }
        return null;
    }

    @Override
    public List<TextTypeWrapper> getName() {
        return new ArrayList<TextTypeWrapper>(name);
    }

    @Override
    public Date getPrepared() {
        if (prepared != null) {
            return new Date(prepared.getTime());
        }
        return null;
    }

    @Override
    public List<PartyBean> getReceiver() {
        return new ArrayList<PartyBean>(receiver);
    }

    @Override
    public Date getReportingBegin() {
        return reportingBegin;
    }

    @Override
    public void setReportingBegin(Date date) {
        this.reportingBegin = date;
    }

    @Override
    public Date getReportingEnd() {
        return reportingEnd;
    }

    @Override
    public void setReportingEnd(Date date) {
        this.reportingEnd = date;
    }

    @Override
    public List<TextTypeWrapper> getSource() {
        return new ArrayList<TextTypeWrapper>(source);
    }

    @Override
    public PartyBean getSender() {
        return sender;
    }

    @Override
    public void setSender(PartyBean party) {
        this.sender = party;
        validate();
    }

    @Override
    public boolean isTest() {
        return test;
    }

    @Override
    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public Map<String, String> getAdditionalAttributes() {
        return new HashMap<String, String>(additionalAttributes);
    }

    @Override
    public boolean hasAdditionalAttribute(String headerField) {
        return additionalAttributes.containsKey(headerField);
    }

    @Override
    public String getAdditionalAttribute(String headerField) {
        return additionalAttributes.get(headerField);
    }

    @Override
    public void addName(TextTypeWrapper name) {
        this.name.add(name);
    }

    @Override
    public void addReceiver(PartyBean recevier) {
        this.receiver.add(recevier);
    }

    @Override
    public void addSource(TextTypeWrapper source) {
        this.source.add(source);
    }
}
