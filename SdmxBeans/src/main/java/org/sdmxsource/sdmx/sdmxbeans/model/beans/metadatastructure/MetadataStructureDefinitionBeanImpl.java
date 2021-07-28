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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.MetadataStructureDefinitionType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ReportStructureType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataStructureType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataTargetType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.ReportStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.MetadataTargetMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.ReportStructureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.MetadataStructureDefinitionMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Metadata structure definition bean.
 */
public class MetadataStructureDefinitionBeanImpl extends MaintainableBeanImpl implements MetadataStructureDefinitionBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = Logger.getLogger(MetadataStructureDefinitionBeanImpl.class);
    private List<ReportStructureBean> reportStructures = new ArrayList<ReportStructureBean>();
    private List<MetadataTargetBean> metadataTarget = new ArrayList<MetadataTargetBean>();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private MetadataStructureDefinitionBeanImpl(MetadataStructureDefinitionBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub MetadataStructureDefinitionBean Built");
    }


    /**
     * Instantiates a new Metadata structure definition bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataStructureDefinitionBeanImpl(MetadataStructureDefinitionMutableBean bean) {
        super(bean);
        LOG.debug("Building MetadataStructureDefinitionBean from Mutable Bean");
        try {
            if (bean.getReportStructures() != null) {
                for (ReportStructureMutableBean currentBean : bean.getReportStructures()) {
                    this.reportStructures.add(new ReportStructureBeanImpl(this, currentBean));
                }
            }
            if (bean.getMetadataTargets() != null) {
                for (MetadataTargetMutableBean currentBean : bean.getMetadataTargets()) {
                    this.metadataTarget.add(new MetadataTargetBeanImpl(this, currentBean));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataStructureDefinitionBean Built " + this);
        }
    }


    /**
     * Instantiates a new Metadata structure definition bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataStructureDefinitionBeanImpl(MetadataStructureType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.MSD);
        LOG.debug("Building MetadataStructureDefinitionBean from 2.1 SDMX");
        try {
            if (bean.getMetadataStructureComponents() != null) {
                if (bean.getMetadataStructureComponents().getMetadataTargetList() != null) {
                    for (MetadataTargetType currentMetadataTarget : bean.getMetadataStructureComponents().getMetadataTargetList()) {
                        this.metadataTarget.add(new MetadataTargetBeanImpl(currentMetadataTarget, this));
                    }
                }
                if (bean.getMetadataStructureComponents().getReportStructureList() != null) {
                    for (org.sdmx.resources.sdmxml.schemas.v21.structure.ReportStructureType currentRs : bean.getMetadataStructureComponents().getReportStructureList()) {
                        reportStructures.add(new ReportStructureBeanImpl(this, currentRs));
                    }
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataStructureDefinitionBean Built " + this);
        }
    }


    /**
     * Instantiates a new Metadata structure definition bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public MetadataStructureDefinitionBeanImpl(MetadataStructureDefinitionType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.MSD,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building MetadataStructureDefinitionBean from 2.0 SDMX");
        try {
            //FUNC 2.1 do we support backward compatability here???
            if (bean.getReportStructureList() != null) {
                for (ReportStructureType currentRs : bean.getReportStructureList()) {
                    reportStructures.add(new ReportStructureBeanImpl(this, currentRs));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MetadataStructureDefinitionBean Built " + this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        if (!isExternalReference.isTrue()) {
            if (!ObjectUtil.validCollection(metadataTarget)) {
                throw new SdmxSemmanticException("Metadata Structure Definition requires at least one Metadata Target");
            }
            if (!ObjectUtil.validCollection(reportStructures)) {
                throw new SdmxSemmanticException("Metadata Structure Definition requires at least one Report Structure");
            }
        }
        Set<String> metadataTargetIds = new HashSet<String>();
        for (MetadataTargetBean currentTarget : metadataTarget) {
            metadataTargetIds.add(currentTarget.getId());
        }
        for (ReportStructureBean currentReportStructure : reportStructures) {
            for (String currentTarget : currentReportStructure.getTargetMetadatas()) {
                if (!metadataTargetIds.contains(currentTarget)) {
                    throw new SdmxSemmanticException("Report Structure references undefined metadata target '" + currentTarget + "'");
                }
            }
        }


        //FUNC 2.1 Validation
        //		Set<String> identiferComponentId = new HashSet<String>();
        //		if(targetIdentifiers != null) {
        //			FullTargetIdentifierBean ftiBean = targetIdentifiers.getFullTargetIdentifier();
        //			if(ftiBean == null) {
        //				throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "FullTargetIdentifier");
        //			}
        //			Set<String> identifierComponent = new HashSet<String>();
        //			Set<String> identifierComponentUrns = new HashSet<String>();
        //			if(ftiBean.getIdentifierComponents() != null) {
        //				for(IdentifierComponentBean icBean : ftiBean.getIdentifierComponents()) {
        //					if(identifierComponentUrns.contains(icBean.getUrn())) {
        //						throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, icBean.getUrn());
        //					}
        //					identifierComponentUrns.add(icBean.getUrn());
        //					identifierComponent.add(icBean.getId());
        //				}
        //			}
        //			identiferComponentId.add(ftiBean.getId());
        //			if(targetIdentifiers.getPartialTargetIdentifiers() != null) {
        //				Set<String> partialTargetIdentifierUrns = new HashSet<String>();
        //				for(PartialTargetIdentifierBean ptiBean : targetIdentifiers.getPartialTargetIdentifiers()) {
        //					if(partialTargetIdentifierUrns.contains(ptiBean.getUrn())) {
        //						throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, ptiBean.getUrn());
        //					}
        //					partialTargetIdentifierUrns.add(ptiBean.getUrn());
        //					if(ftiBean.getId().equals(ptiBean.getId())) {
        //						throw new SdmxSemmanticException(ExceptionCode.PARTIAL_TARGET_ID_DUPLICATES_FULL_TARGET_ID, ptiBean.getId());
        //					}
        //					identiferComponentId.add(ptiBean.getId());
        //					if(ptiBean.getIdentifierComponentRef() != null) {
        //						for(String ftiRef : ptiBean.getIdentifierComponentRef()) {
        //							if(!identifierComponent.contains(ftiRef)) {
        //								throw new SdmxSemmanticException(ExceptionCode.REFERENCE_ERROR, SDMX_STRUCTURE_TYPE.FULL_TARGET_IDENTIFIER, SDMX_STRUCTURE_TYPE.PARTIAL_TARGET_IDENTIFIER, ptiBean);
        //							}
        //						}
        //					}
        //				}
        //			}
        //		}
        //		//VALIDATE REPORT STRUCTURES
        //		Set<String> conceptIds;
        //		Set<String> reportStructureUrns = new HashSet<String>();
        //		for(ReportStructureBean rsBean : reportStructures) {
        //			if(reportStructureUrns.contains(rsBean.getUrn())) {
        //				throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, rsBean.getUrn());
        //			}
        //			reportStructureUrns.add(rsBean.getUrn());
        //			conceptIds = new HashSet<String>();
        //			try {
        //				if(!identiferComponentId.contains(rsBean.getTarget())) {
        //					throw new SdmxSemmanticException(ExceptionCode.REPORT_STRUCTURE_INVALID_IDENTIFIER_REFERENCE, rsBean.getTarget());
        //				}
        //				if(rsBean.getMetadataAttributes() == null || rsBean.getMetadataAttributes().size() == 0) {
        //					throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.getUrn(), "MetadataAttribute");
        //				}
        //				for(MetadataAttributeBean maBean : rsBean.getMetadataAttributes()) {
        //					String conceptId = maBean.getConceptRef().getId();
        //					if(conceptIds.contains(conceptId)) {
        //						throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_CONCEPT, maBean.getUrn());
        //					}
        //					conceptIds.add(conceptId);
        //				}
        //			} catch(ValidationException e) {
        //				throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, new Object[] {rsBean.getUrn()});
        //			}
        //		}
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
            MetadataStructureDefinitionBean that = (MetadataStructureDefinitionBean) bean;
            if (!super.equivalent(reportStructures, that.getReportStructures(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(metadataTarget, that.getMetadataTargets(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public MetadataStructureDefinitionBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new MetadataStructureDefinitionBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public MetadataStructureDefinitionMutableBean getMutableInstance() {
        return new MetadataStructureDefinitionMutableBeanImpl(this);
    }

    @Override
    public List<MetadataTargetBean> getMetadataTargets() {
        return new ArrayList<MetadataTargetBean>(metadataTarget);
    }

    @Override
    public List<ReportStructureBean> getReportStructures() {
        return new ArrayList<ReportStructureBean>(reportStructures);
    }

    @Override
    public MetadataTargetBean getMetadataTarget(String id) {
        for (MetadataTargetBean currentTarget : metadataTarget) {
            if (currentTarget.getId().equals(id)) {
                return currentTarget;
            }
        }
        return null;
    }

    @Override
    public ReportStructureBean getReportStructure(String id) {
        for (ReportStructureBean rs : reportStructures) {
            if (rs.getId().equals(id)) {
                return rs;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(reportStructures, composites);
        super.addToCompositeSet(metadataTarget, composites);
        return composites;
    }
}
