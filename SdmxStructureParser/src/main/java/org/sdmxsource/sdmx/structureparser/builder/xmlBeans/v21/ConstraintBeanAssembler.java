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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.common.DistinctKeyType;
import org.sdmx.resources.sdmxml.schemas.v21.common.QueryableDataSourceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintAttachmentType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataKeySetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataKeySetType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstrainedDataKeyBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintAttachmentBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.Assembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;


/**
 * The type Constraint bean assembler.
 */
public class ConstraintBeanAssembler extends MaintainableBeanAssembler implements Assembler<ConstraintType, ConstraintBean> {

    @Override
    public void assemble(ConstraintType assembleInto, ConstraintBean assembleFrom) throws SdmxException {
        super.assembleMaintainable(assembleInto, assembleFrom);

        if (assembleFrom.getConstraintAttachment() != null) {
            buildConstraintAttachment(assembleInto.addNewConstraintAttachment(), assembleFrom.getConstraintAttachment());
        }

        if (assembleFrom.getIncludedSeriesKeys() != null) {
            DataKeySetType type = assembleInto.addNewDataKeySet();
            buildDataKeySet(type, assembleFrom.getIncludedSeriesKeys(), true);
        }

        if (assembleFrom.getExcludedSeriesKeys() != null) {
            DataKeySetType type = assembleInto.addNewDataKeySet();
            buildDataKeySet(type, assembleFrom.getExcludedSeriesKeys(), false);
        }

        MetadataKeySetType metadataType = null;
        if (assembleFrom.getIncludedMetadataKeys() != null) {
            metadataType = assembleInto.addNewMetadataKeySet();
            buildDataKeySet(metadataType, assembleFrom.getIncludedMetadataKeys(), true);
        }

        if (assembleFrom.getExcludedMetadataKeys() != null) {
            if (metadataType == null) {
                metadataType = assembleInto.addNewMetadataKeySet();
            }
            buildDataKeySet(metadataType, assembleFrom.getExcludedMetadataKeys(), false);
        }
    }

    private void buildDataKeySet(MetadataKeySetType type, ConstraintDataKeySetBean bean, boolean included) {
        type.setIsIncluded(included);
        for (ConstrainedDataKeyBean dkBean : bean.getConstrainedDataKeys()) {
            DistinctKeyType keyType = type.addNewKey();
            for (KeyValue kv : dkBean.getKeyValues()) {
                ComponentValueSetType cvType = keyType.addNewKeyValue();
                cvType.setId(kv.getConcept());
                cvType.addNewValue().setStringValue(kv.getCode());
            }
        }
    }

    private void buildDataKeySet(DataKeySetType type, ConstraintDataKeySetBean bean, boolean included) {
        type.setIsIncluded(included);
        for (ConstrainedDataKeyBean dkBean : bean.getConstrainedDataKeys()) {
            DistinctKeyType keyType = type.addNewKey();
            for (KeyValue kv : dkBean.getKeyValues()) {
                ComponentValueSetType cvType = keyType.addNewKeyValue();
                cvType.setId(kv.getConcept());
                cvType.addNewValue().setStringValue(kv.getCode());
            }
        }
    }

    private void buildConstraintAttachment(ConstraintAttachmentType type, ConstraintAttachmentBean bean) {
        if (bean.getDataOrMetadataSetReference() != null) {
            if (bean.getDataOrMetadataSetReference().isDataSetReference()) {
                SetReferenceType refType = type.addNewDataSet();
                refType.setID(bean.getDataOrMetadataSetReference().getSetId());
                super.setReference(refType.addNewDataProvider().addNewRef(), bean.getDataOrMetadataSetReference().getDataSetReference());
            } else {
                SetReferenceType mdsRefType = type.addNewMetadataSet();
                mdsRefType.setID(bean.getDataOrMetadataSetReference().getSetId());
                super.setReference(mdsRefType.addNewDataProvider().addNewRef(), bean.getDataOrMetadataSetReference().getDataSetReference());
            }
        }
        if (bean.getDataSources() != null) {
            for (DataSourceBean ds : bean.getDataSources()) {
                if (ds.isSimpleDatasource()) {
                    if (ds.getDataUrl() != null) {
                        type.addSimpleDataSource(ds.getDataUrl().toString());
                    }
                } else {
                    QueryableDataSourceType dsType = type.addNewQueryableDataSource();
                    if (ds.getDataUrl() != null) {
                        dsType.setDataURL(ds.getDataUrl().toString());
                    }
                    dsType.setIsRESTDatasource(ds.isRESTDatasource());
                    dsType.setIsWebServiceDatasource(ds.isWebServiceDatasource());
                    if (ds.getWadlUrl() != null) {
                        dsType.setWADLURL(ds.getWadlUrl().toString());
                    }
                    if (ds.getWSDLUrl() != null) {
                        dsType.setWSDLURL(ds.getWSDLUrl().toString());
                    }
                }
            }
        }
        for (CrossReferenceBean currentRef : bean.getStructureReference()) {
            addAttachment(type, currentRef);
        }
    }

    private void addAttachment(ConstraintAttachmentType type, CrossReferenceBean attachment) {
        switch (attachment.getTargetReference()) {
            case DATA_PROVIDER:
                super.setReference(type.addNewDataProvider().addNewRef(), attachment);
                break;
            case DSD:
                super.setReference(type.addNewDataStructure().addNewRef(), attachment);
                break;
            case MSD:
                super.setReference(type.addNewMetadataStructure().addNewRef(), attachment);
                break;
            case DATAFLOW:
                super.setReference(type.addNewDataflow().addNewRef(), attachment);
                break;
            case METADATA_FLOW:
                super.setReference(type.addNewMetadataflow().addNewRef(), attachment);
                break;
            case PROVISION_AGREEMENT:
                super.setReference(type.addNewProvisionAgreement().addNewRef(), attachment);
                break;
        }
    }
}
