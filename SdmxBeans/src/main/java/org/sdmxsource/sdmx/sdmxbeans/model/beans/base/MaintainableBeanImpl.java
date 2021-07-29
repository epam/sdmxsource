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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MaintainableType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.StringUtil;
import org.sdmxsource.util.VersionableUtil;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The type Maintainable bean.
 */
public abstract class MaintainableBeanImpl extends NameableBeanImpl implements MaintainableBean {
    private static final long serialVersionUID = 1L;
    /**
     * The End date.
     */
    protected SdmxDate endDate;
    /**
     * The Start date.
     */
    protected SdmxDate startDate;
    /**
     * The Agency id.
     */
    protected String agencyId;
    /**
     * The Is final.
     */
    protected TERTIARY_BOOL isFinal = TERTIARY_BOOL.UNSET;
    /**
     * The Is external reference.
     */
    protected TERTIARY_BOOL isExternalReference = TERTIARY_BOOL.UNSET;
    private String version;
    private URL serviceURL;
    private URL structureURL;

    /**
     * Instantiates a new Maintainable bean.
     *
     * @param bean           the bean
     * @param actualLocation the actual location
     * @param isServiceUrl   the is service url
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MaintainableBeanImpl(MaintainableBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean);
        this.endDate = bean.getEndDate();
        this.startDate = bean.getStartDate();
        this.version = bean.getVersion();
        this.agencyId = bean.getAgencyId();
        this.serviceURL = bean.getServiceURL();
        this.structureURL = bean.getStructureURL();
        this.isExternalReference = TERTIARY_BOOL.TRUE;  //Stub Bean is Externally Referenced
        if (actualLocation == null) {
            throw new SdmxSemmanticException("Stub Beans require a URL defining the actual service to obtain the full Sdmx artefact from");
        }
        if (isServiceUrl) {
            this.serviceURL = actualLocation;
        } else {
            this.structureURL = actualLocation;
        }
        if (bean.isFinal() != null) {
            this.isFinal = bean.isFinal();
        }
        validateMaintainableAttributes();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	public MaintainableBeanImpl(SDMX_STRUCTURE_TYPE structure, SdmxReader reader) {
//		super(structure, reader, null);
//		
//		Map<String, String> attributes = reader.getAttributes();
//		this.version = attributes.get("version");
//		this.agencyId = attributes.get("agencyID");
//		
//		if(attributes.containsKey("validFrom")) {
//			this.startDate = new SdmxDateImpl(attributes.get("validFrom"));
//		}
//		if(attributes.containsKey("validTo")) {
//			this.endDate = new SdmxDateImpl(attributes.get("validTo"));
//		}
//		
//		if(attributes.containsKey("serviceURL")) {
//			setServiceURL(attributes.get("serviceURL"));
//		}
//		
//		if(attributes.containsKey("structureURL")) {
//			setStructureURL(attributes.get("structureURL"));
//		}
//		
//		validateMaintainableAttributes();
//	}

    /**
     * Instantiates a new Maintainable bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MaintainableBeanImpl(MaintainableMutableBean bean) {
        super(bean, null);
        this.agencyId = bean.getAgencyId();

        if (bean.getStartDate() != null) {
            this.startDate = new SdmxDateImpl(bean.getStartDate(), TIME_FORMAT.DATE_TIME);
        }
        if (bean.getEndDate() != null) {
            this.endDate = new SdmxDateImpl(bean.getEndDate(), TIME_FORMAT.DATE_TIME);
        }
        if (bean.getFinalStructure() != null) {
            this.isFinal = bean.getFinalStructure();
        }
        if (bean.getExternalReference() != null) {
            this.isExternalReference = bean.getExternalReference();
        }
        if (bean.isStub()) {
            this.isExternalReference = TERTIARY_BOOL.TRUE;
        }
        if (bean.getServiceURL() != null) {
            setServiceURL(bean.getServiceURL());
        }
        if (bean.getStructureURL() != null) {
            setStructureURL(bean.getStructureURL());
        }
        this.version = bean.getVersion();
        validateMaintainableAttributes();
    }


    /**
     * Instantiates a new Maintainable bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MaintainableBeanImpl(MaintainableType createdFrom, SDMX_STRUCTURE_TYPE structureType) {
        super(createdFrom, structureType, null);
        this.agencyId = createdFrom.getAgencyID();
        if (createdFrom.getValidFrom() != null) {
            this.startDate = new SdmxDateImpl(createdFrom.getValidFrom().toString());
        }
        if (createdFrom.getValidTo() != null) {
            this.endDate = new SdmxDateImpl(createdFrom.getValidTo().toString());
        }
        this.isFinal = createTertiary(createdFrom.isSetIsFinal(), createdFrom.getIsFinal());
        this.version = createdFrom.getVersion();
        this.isExternalReference = createTertiary(createdFrom.isSetIsExternalReference(), createdFrom.getIsExternalReference());
        setServiceURL(createdFrom.getServiceURL());
        setStructureURL(createdFrom.getStructureURL());
        validateMaintainableAttributes();
    }

    /**
     * Instantiates a new Maintainable bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param endDate             the end date
     * @param startDate           the start date
     * @param version             the version
     * @param isFinal             the is final
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param description         the description
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MaintainableBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                                   Object endDate,
                                   Object startDate,
                                   String version,
                                   TERTIARY_BOOL isFinal,
                                   String agencyId,
                                   String id,
                                   String uri,
                                   List<TextType> name,
                                   List<TextType> description,
                                   TERTIARY_BOOL isExternalReference,
                                   AnnotationsType annotationsType) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, null);
        this.agencyId = agencyId;
        if (startDate != null) {
            this.startDate = new SdmxDateImpl(startDate.toString());
        }
        if (endDate != null) {
            this.endDate = new SdmxDateImpl(endDate.toString());
        }
        if (isFinal != null) {
            this.isFinal = isFinal;
        }
        this.version = version;
        if (isExternalReference != null) {
            this.isExternalReference = isExternalReference;
        }
        if (this.isExternalReference == TERTIARY_BOOL.TRUE) {
            setStructureURL(uri);
        }
        validateMaintainableAttributes();
    }

    /**
     * Instantiates a new Maintainable bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param version             the version
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected MaintainableBeanImpl(XmlObject createdFrom,
                                   SDMX_STRUCTURE_TYPE structureType,
                                   String version,
                                   String agencyId,
                                   String id,
                                   String uri,
                                   List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                                   TERTIARY_BOOL isExternalReference,
                                   org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType) {
        super(createdFrom, structureType, id, uri, name, null, annotationsType, null);
        this.agencyId = agencyId;
        this.version = version;
        this.isExternalReference = isExternalReference;
        if (this.isExternalReference == TERTIARY_BOOL.TRUE) {
            setStructureURL(uri);
        }
        validateMaintainableAttributes();
    }


    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected boolean deepEqualsInternal(MaintainableBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (includeFinalProperties) {
            if (!ObjectUtil.equivalent(startDate, bean.getStartDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(endDate, bean.getEndDate())) {
                return false;
            }
            if (!ObjectUtil.equivalent(isExternalReference.isTrue(), bean.isExternalReference().isTrue())) {
                return false;
            }
            if (!ObjectUtil.equivalent(serviceURL, bean.getServiceURL())) {
                return false;
            }
            if (!ObjectUtil.equivalent(structureURL, bean.getStructureURL())) {
                return false;
            }
        }

        if (!ObjectUtil.equivalent(agencyId, bean.getAgencyId())) {
            return false;
        }
        if (!ObjectUtil.equivalent(isFinal.isTrue(), bean.isFinal().isTrue())) {
            return false;
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }


    /**
     * Validate maintainable attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateMaintainableAttributes() throws SdmxSemmanticException {
        validateAgencyId();
        agencyId = StringUtil.manualIntern(agencyId);

        if (!ObjectUtil.validString(version)) {
            version = DEFAULT_VERSION;
        } else if (!version.contains(".")) {
            version += ".0";
        } else if (version.endsWith(".")) {
            version += "0";
        }

        version = StringUtil.manualIntern(version);
        if (!VersionableUtil.validVersion(version)) {
            throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_INVALID_VERSION, version);
        }
        if (endDate != null && startDate != null) {
            if (startDate.isLater(endDate)) {
                throw new SdmxSemmanticException(ExceptionCode.END_DATE_BEFORE_START_DATE);
            }
        }
        if (this.isExternalReference == null) {
            this.isExternalReference = TERTIARY_BOOL.UNSET;
        }
        if (this.isExternalReference.isTrue()) {
            if (this.structureURL == null && this.serviceURL == null) {
                throw new SdmxSemmanticException(ExceptionCode.EXTERNAL_STRUCTURE_MISSING_URI);
            }
        }
        if (ObjectUtil.validString(getId())) {
            if (getId().equals("ALL")) {
                throw new SdmxSemmanticException("ALL is a reserved word and can not be used for an id");
            }
        }
    }

    /**
     * Validate agency id.
     */
    protected void validateAgencyId() {
        if (!ObjectUtil.validString(this.getAgencyId())) {
            throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_MAINTAINABLE_MISSING_AGENCY, this.structureType);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	@Override
//	public Set<SDMXBean> getComposites() {
//		Method[] arr1 = MaintainableBean.class.getMethods();
//		Method[] arr2 =SDMXBean.class.getMethods();
//		Method[] arrMerge = new Method[arr1.length + arr2.length];
//		int i = 0;
//		for(Method m : arr1) {
//			arrMerge[i] = m;
//			i++;
//		}
//		for(Method m : arr2) {
//			arrMerge[i] = m;
//			i++;
//		}
//		generateSdmxBeanComposites(arrMerge);
//		return new HashSet<SDMXBean>(composites);
//	}

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    @Override
    public SdmxDate getEndDate() {
        return endDate;
    }

    @Override
    public SdmxDate getStartDate() {
        return startDate;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public TERTIARY_BOOL isFinal() {
        return isFinal;
    }

    @Override
    public TERTIARY_BOOL isExternalReference() {
        return this.isExternalReference;
    }

    @Override
    public int compareTo(MaintainableBean maintainableBean) {
        return VersionableUtil.isHigherVersion(this.version, maintainableBean.getVersion()) ? +1 : -1;
    }


    @Override
    public MaintainableBean addAnnotations(Map<StructureReferenceBean, Set<AnnotationMutableBean>> annotationsMap) {
        MaintainableBean copy = getMutableInstance().getImmutableInstance();
        for (StructureReferenceBean ref : annotationsMap.keySet()) {
            Set<AnnotationMutableBean> annotations = annotationsMap.get(ref);
            if (copy.asReference().equals(ref)) {
                ((AnnotableBeanImpl) copy).addAnnotations(annotations);
                return copy;
            } else {
                for (IdentifiableBean identifiable : copy.getIdentifiableComposites()) {
                    if (identifiable.asReference().equals(ref)) {
                        ((AnnotableBeanImpl) identifiable).addAnnotations(annotations);
                        return copy;
                    }
                }
            }
        }
        return copy;
    }

    @Override
    public URL getServiceURL() {
        return serviceURL;
    }

    private void setServiceURL(String serviceURLStr) {
        if (serviceURLStr == null) {
            this.serviceURL = null;
            return;
        }
        try {
            this.serviceURL = new URL(serviceURLStr);
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Could not create attribute 'serviceURL' with value '" + serviceURLStr + "'");
        }
    }

    @Override
    public URL getStructureURL() {
        return structureURL;
    }

    private void setStructureURL(String structureURLStr) {
        if (structureURLStr == null) {
            this.structureURL = null;
            return;
        }
        try {
            this.structureURL = new URL(structureURLStr);
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Could not create attribute 'structureURL' with value '" + structureURLStr + "'");
        }
    }
}
