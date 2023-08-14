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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.registry;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v21.common.ComponentValueSetType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ContentConstraintTypeCodeType;
import org.sdmx.resources.sdmxml.schemas.v21.common.CubeRegionType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ContentConstraintType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.mutable.registry.ContentConstraintMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.CubeRegionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.KeyValuesMutable;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.ContentConstraintMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.CubeRegionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.KeyValuesMutableImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.*;


/**
 * The type Content constraint bean.
 */
public class ContentConstraintBeanImpl extends ConstraintBeanImpl implements ContentConstraintBean {
    private transient static final Logger LOG = LoggerFactory.getLogger(ContentConstraintBeanImpl.class);

    private static final long serialVersionUID = 896125780368907794L;

    private CubeRegionBean includedCubeRegion;
    private CubeRegionBean excludedCubeRegion;

    private ReferencePeriodBean referencePeriodBean;
    private ReleaseCalendarBean releaseCalendarBean;
    private boolean isDefiningActualDataPresent = true;  //Default Value

    private MetadataTargetRegionBean metadataTargetRegionBean;


    /**
     * Instantiates a new Content constraint bean.
     *
     * @param bean           the bean
     * @param actualLocation the actual location
     * @param isServiceUrl   the is service url
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContentConstraintBeanImpl(ContentConstraintBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Content constraint bean.
     *
     * @param mutable the mutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContentConstraintBeanImpl(ContentConstraintMutableBean mutable) {
        super(mutable);
        if (mutable.getIncludedCubeRegion() != null &&
                (ObjectUtil.validCollection(mutable.getIncludedCubeRegion().getKeyValues()) ||
                        ObjectUtil.validCollection(mutable.getIncludedCubeRegion().getAttributeValues()))) {
            this.includedCubeRegion = new CubeRegionBeanImpl(mutable.getIncludedCubeRegion(), this);
        }
        if (mutable.getExcludedCubeRegion() != null &&
                (ObjectUtil.validCollection(mutable.getExcludedCubeRegion().getKeyValues()) ||
                        ObjectUtil.validCollection(mutable.getExcludedCubeRegion().getAttributeValues()))) {
            this.excludedCubeRegion = new CubeRegionBeanImpl(mutable.getExcludedCubeRegion(), this);
        }

        if (mutable.getReferencePeriod() != null) {
            this.referencePeriodBean = new ReferencePeriodBeanImpl(mutable.getReferencePeriod(), this);
        }
        if (mutable.getReleaseCalendar() != null) {
            this.releaseCalendarBean = new ReleaseCalendarBeanImpl(mutable.getReleaseCalendar(), this);
        }
        if (mutable.getMetadataTargetRegion() != null) {
            this.metadataTargetRegionBean = new MetadataTargetRegionBeanImpl(mutable.getMetadataTargetRegion(), this);
        }
        this.isDefiningActualDataPresent = mutable.getIsDefiningActualDataPresent();

        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Content constraint bean.
     *
     * @param type the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ContentConstraintBeanImpl(ContentConstraintType type) {
        super(type, SDMX_STRUCTURE_TYPE.CONTENT_CONSTRAINT);
        if (type.isSetType()) {
            this.isDefiningActualDataPresent = type.getType() == ContentConstraintTypeCodeType.ACTUAL;
        }
        if (ObjectUtil.validCollection(type.getMetadataKeySetList())) {
            //FUNC 2.1 MetadataKeySet
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "ContentConstraintBeanImpl - MetadataKeySet");
        }

        if (ObjectUtil.validCollection(type.getCubeRegionList())) {
            buildCubeRegions(type.getCubeRegionList());
        }
        if (ObjectUtil.validCollection(type.getMetadataTargetRegionList())) {
            //FUNC 2.1 MetadataTarget Region
            throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "ContentConstraintBeanImpl - MetadataTargetRegionList");
        }
        if (type.getReferencePeriod() != null) {
            this.referencePeriodBean = new ReferencePeriodBeanImpl(type.getReferencePeriod(), this);
        }
        if (type.getReleaseCalendar() != null) {
            this.releaseCalendarBean = new ReleaseCalendarBeanImpl(type.getReleaseCalendar(), this);
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    private void buildCubeRegions(List<CubeRegionType> cubeRegionsTypes) {
        for (CubeRegionType cubeRegionType : cubeRegionsTypes) {
            boolean isExcluded = false;
            boolean negate = false;
            if (cubeRegionType.isSetInclude()) {
                isExcluded = !cubeRegionType.getInclude();
            }
            if (cubeRegionType.getAttributeList() != null) {
                for (ComponentValueSetType currentKey : cubeRegionType.getAttributeList()) {
                    if (currentKey.isSetInclude() && currentKey.getInclude() == false) {
                        negate = true;
                        break;
                    }
                }
            }
            if (!isExcluded) {
                if (cubeRegionType.getKeyValueList() != null) {
                    for (ComponentValueSetType currentKey : cubeRegionType.getKeyValueList()) {
                        if (currentKey.isSetInclude() && currentKey.getInclude() == false) {
                            negate = true;
                            break;
                        }
                    }
                }
            }
            storeCubeRegion(new CubeRegionBeanImpl(cubeRegionType, negate, this), isExcluded);
        }
    }

    @Override
    public MetadataTargetRegionBean getMetadataTargetRegion() {
        return metadataTargetRegionBean;
    }

    /**
     * Takes a cube region, which is either a inclusive or exclusive, and if there is already the inclusive or exclusive cube region
     * stored, then this one will merge into the stored one.  If there are any duplicate values, then an error will be thrown.
     */
    private void storeCubeRegion(CubeRegionBean currentCubeRegion, boolean isExcluded) {
        if (!isExcluded) {
            if (includedCubeRegion == null) {
                includedCubeRegion = currentCubeRegion;
            } else {
                mergeCubeRegion(true, currentCubeRegion);
            }
        } else {
            if (excludedCubeRegion == null) {
                excludedCubeRegion = currentCubeRegion;
            } else {
                mergeCubeRegion(false, currentCubeRegion);
            }
        }
    }

    private void mergeCubeRegion(boolean mergeIncluded, CubeRegionBean toMerge) {
        CubeRegionMutableBean mutable = null;
        if (mergeIncluded) {
            mutable = new CubeRegionMutableBeanImpl(includedCubeRegion);
        } else {
            mutable = new CubeRegionMutableBeanImpl(excludedCubeRegion);
        }
        mergeKeyValues(mutable.getKeyValues(), toMerge.getKeyValues());
        mergeKeyValues(mutable.getAttributeValues(), toMerge.getAttributeValues());

        if (mergeIncluded) {
            includedCubeRegion = new CubeRegionBeanImpl(mutable, this);
        } else {
            excludedCubeRegion = new CubeRegionBeanImpl(mutable, this);
        }
    }


    private void mergeKeyValues(List<KeyValuesMutable> existingKeyValues, List<KeyValues> toMerge) {
        for (KeyValues currentKeyValues : toMerge) {
            KeyValuesMutable existingMutable = getKeyValues(currentKeyValues.getId(), existingKeyValues);
            //Nothing exists yet, add this key values container in
            if (existingMutable == null) {
                existingKeyValues.add(new KeyValuesMutableImpl(currentKeyValues));
                continue;
            }
            //We need to merge what we have with what we've got
            if (currentKeyValues.getTimeRange() != null) {
                if (ObjectUtil.validCollection(existingMutable.getKeyValues())) {
                    throw new SdmxSemmanticException("Can not create CubeRegion as it is defining both a TimeRange and a Set of allowed values for a Key Value with the same Id");
                }
                throw new SdmxSemmanticException("Can not create CubeRegion as it is a TimeRange twice for a Key Value with the same Id");
            }
            for (String currentValue : currentKeyValues.getValues()) {
                if (existingMutable.getKeyValues().contains(currentValue)) {
                    if (existingMutable.isCascadeValue(currentValue) != currentKeyValues.isCascadeValue(currentValue)) {
                        throw new SdmxSemmanticException("Can not create CubeRegion as it defines a Key/Value '" + currentKeyValues.getId() + "'/'+currentValue)+' twice, once with cascade values set to true, and once false");
                    }
                    LOG.warn("Duplicate definition of KeyValue in 2 different Cube Regions");
                } else {
                    existingMutable.addValue(currentValue);
                }
                if (currentKeyValues.isCascadeValue(currentValue)) {
                    existingMutable.addCascade(currentValue);
                }
            }
        }
    }

    private KeyValuesMutable getKeyValues(String id, List<KeyValuesMutable> kvs) {
        for (KeyValuesMutable kvMutable : kvs) {
            if (kvMutable.getId().equals(id)) {
                return kvMutable;
            }
        }
        return null;
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
            ContentConstraintBean that = (ContentConstraintBean) bean;
            if (!super.equivalent(getIncludedCubeRegion(), that.getIncludedCubeRegion(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(getExcludedCubeRegion(), that.getExcludedCubeRegion(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(referencePeriodBean, that.getReferencePeriod(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(releaseCalendarBean, that.getReleaseCalendar(), includeFinalProperties)) {
                return false;
            }
            if (isDefiningActualDataPresent != that.isDefiningActualDataPresent()) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        super.validateMaintainableAttributes();

        Map<String, Set<String>> includedCodesForKey = new HashMap<String, Set<String>>();
        Set<String> wildcardedConcepts = new HashSet<String>();


        if (getIncludedSeriesKeys() != null) {
            for (ConstrainedDataKeyBean cdkb : getIncludedSeriesKeys().getConstrainedDataKeys()) {
                for (KeyValue kv : cdkb.getKeyValues()) {
                    if (containsKey(excludedCubeRegion, kv)) {
                        throw new SdmxSemmanticException("Constraint is in conflict with itself.  Included series key contains component '" + kv.getConcept() + "' with value '" + kv.getCode() + "'.  This code has also been specified as excluded by the constraint's CubeRegion");
                    }
                    if (kv.getCode().equals(ContentConstraintBean.WILDCARD_CODE)) {
                        wildcardedConcepts.add(kv.getConcept());
                        includedCodesForKey.remove(kv.getConcept());
                    } else if (!wildcardedConcepts.contains(kv.getConcept())) {
                        Set<String> includedCodes = includedCodesForKey.get(kv.getConcept());
                        if (includedCodes == null) {
                            includedCodes = new HashSet<String>();
                            includedCodesForKey.put(kv.getConcept(), includedCodes);
                        }
                        includedCodes.add(kv.getCode());
                    }
                }
            }
        }
        if (getIncludedCubeRegion() != null) {
            //Check we are not including any more / less then the series includes
            for (KeyValues kvs : getIncludedCubeRegion().getKeyValues()) {
                Set<String> allIncludes = includedCodesForKey.get(kvs.getId());
                if (allIncludes != null) {
                    if (!allIncludes.containsAll(kvs.getValues()) || !kvs.getValues().containsAll(allIncludes)) {
                        throw new SdmxSemmanticException("Constraint is in conflict with itself. The constraint defines valid series, this can not be further restricted by the cube region.  The " +
                                "Cube Region has further restricted dimension '" + kvs.getId() + "' by not including all the codes defined by the keyset.");
                    }
                }
                if (getExcludedCubeRegion() != null) {
                    validateNoKeyValuesDuplicates(kvs, getExcludedCubeRegion().getKeyValues());
                }
            }
        }
        if (getExcludedCubeRegion() != null) {
            for (KeyValues kvs : getExcludedCubeRegion().getKeyValues()) {
                Set<String> allIncludes = includedCodesForKey.get(kvs.getId());
                if (allIncludes != null) {
                    throw new SdmxSemmanticException("Constraint is in conflict with itself. The constraint defines valid series, the dimension  '" + kvs.getId() + "' can not be further restriced by the cube region to " +
                            "exclude codes which are already marked for inclusion by the keyset");
                }
                if (getIncludedCubeRegion() != null) {
                    validateNoKeyValuesDuplicates(kvs, getIncludedCubeRegion().getKeyValues());
                }
            }
        }

    }

    private void validateNoKeyValuesDuplicates(KeyValues kvs, List<KeyValues> kvsList) {
        for (KeyValues currentKvs : kvsList) {
            if (currentKvs.getId().equals(kvs.getId())) {
                for (String currentValue : currentKvs.getValues()) {
                    if (kvs.getValues().contains(currentValue)) {
                        throw new SdmxSemmanticException("CubeRegion contains a Key Value that is both included and excluded Id='" + kvs.getId() + "' Value='" + currentValue + "'");
                    }
                }
            }
        }
    }

    private boolean containsKey(CubeRegionBean cubeRegion, KeyValue kv) {
        if (cubeRegion != null) {
            return cubeRegion.getValues(kv.getConcept()).contains(kv.getCode());
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ContentConstraintBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new ContentConstraintBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public ContentConstraintMutableBean getMutableInstance() {
        return new ContentConstraintMutableBeanImpl(this);
    }

    @Override
    public CubeRegionBean getIncludedCubeRegion() {
        return includedCubeRegion;
    }

    @Override
    public CubeRegionBean getExcludedCubeRegion() {
        return excludedCubeRegion;
    }

    @Override
    public ReferencePeriodBean getReferencePeriod() {
        return referencePeriodBean;
    }

    @Override
    public ReleaseCalendarBean getReleaseCalendar() {
        return releaseCalendarBean;
    }

    @Override
    public boolean isDefiningActualDataPresent() {
        return isDefiningActualDataPresent;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(includedCubeRegion, composites);
        super.addToCompositeSet(excludedCubeRegion, composites);
        super.addToCompositeSet(referencePeriodBean, composites);
        super.addToCompositeSet(releaseCalendarBean, composites);
        super.addToCompositeSet(metadataTargetRegionBean, composites);
        return composites;
    }
}
