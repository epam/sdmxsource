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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v21.common.ConceptReferenceType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionListBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.RepresentationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Dimension bean.
 */
public class DimensionBeanImpl extends ComponentBeanImpl implements DimensionBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = Logger.getLogger(DimensionBeanImpl.class);
    private boolean measureDimension;
    private boolean timeDimension;
    private boolean freqDimension;
    private List<CrossReferenceBean> conceptRole = new ArrayList<CrossReferenceBean>();
    private int position;

    /**
     * Instantiates a new Dimension bean.
     *
     * @param bean     the bean
     * @param position the position
     * @param parent   the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionBeanImpl(DimensionMutableBean bean, int position, DimensionListBean parent) {
        super(bean, parent);
        try {
            this.position = position;
            this.measureDimension = bean.isMeasureDimension();
            this.timeDimension = bean.isTimeDimension();
            this.freqDimension = bean.isFrequencyDimension();
            if (bean.getConceptRole() != null) {
                for (StructureReferenceBean currentConceptRole : bean.getConceptRole()) {
                    conceptRole.add(new CrossReferenceBeanImpl(this, currentConceptRole));
                }
            }
            validateDimension();
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
        }
    }

    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.DimensionType dimension, DimensionListBean parent, int position) {
        super(dimension, SDMX_STRUCTURE_TYPE.DIMENSION, parent);
        if (dimension.isSetPosition()) {
            this.position = dimension.getPosition();
        } else {
            this.position = position;
        }
        if (ObjectUtil.validCollection(dimension.getConceptRoleList())) {
            for (ConceptReferenceType conceptReference : dimension.getConceptRoleList()) {
                conceptRole.add(RefUtil.createReference(this, conceptReference));
            }
        }

        validateDimension();
    }

    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
    public DimensionBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.MeasureDimensionType dimension, DimensionListBean parent, int position) {
        super(dimension, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION, parent);
        this.measureDimension = true;
        if (dimension.isSetPosition()) {
            this.position = dimension.getPosition();
        } else {
            this.position = position;
        }
        if (ObjectUtil.validCollection(dimension.getConceptRoleList())) {
            for (ConceptReferenceType conceptReference : dimension.getConceptRoleList()) {
                conceptRole.add(RefUtil.createReference(this, conceptReference));
            }
        }

        validateDimension();
    }

    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
    public DimensionBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.TimeDimensionType dimension, DimensionListBean parent, int position) {
        super(dimension, SDMX_STRUCTURE_TYPE.TIME_DIMENSION, parent);
        this.timeDimension = true;
        if (dimension.isSetPosition()) {
            this.position = dimension.getPosition();
        } else {
            this.position = position;
        }
        if (ObjectUtil.validCollection(dimension.getConceptRoleList())) {
            for (ConceptReferenceType conceptReference : dimension.getConceptRoleList()) {
                conceptRole.add(RefUtil.createReference(this, conceptReference));
            }
        }
        validateDimension();
    }


    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionBeanImpl(DimensionType dimension, DimensionListBean parent, int position) {
        super(dimension,
                SDMX_STRUCTURE_TYPE.DIMENSION,
                dimension.getAnnotations(),
                dimension.getTextFormat(),
                dimension.getCodelistAgency(),
                dimension.getCodelist(),
                dimension.getCodelistVersion(),
                dimension.getConceptSchemeAgency(),
                dimension.getConceptSchemeRef(),
                dimension.getConceptVersion(),
                dimension.getConceptAgency(),
                dimension.getConceptRef(), parent);
        if (parent.getMaintainableParent() instanceof CrossSectionalDataStructureBean) {
            this.measureDimension = dimension.getIsMeasureDimension();
        }
        this.position = position;
        freqDimension = dimension.getIsFrequencyDimension();
        validateDimension();
    }

    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
    public DimensionBeanImpl(TimeDimensionType dimension, DimensionListBean parent, int position) {
        super(dimension,
                SDMX_STRUCTURE_TYPE.TIME_DIMENSION,
                dimension.getAnnotations(),
                dimension.getTextFormat(),
                dimension.getCodelistAgency(),
                dimension.getCodelist(),
                dimension.getCodelistVersion(),
                dimension.getConceptSchemeAgency(),
                dimension.getConceptSchemeRef(),
                dimension.getConceptVersion(),
                dimension.getConceptAgency(),
                dimension.getConceptRef(), parent);
        this.timeDimension = true;
        this.position = position;
        validateDimension();
    }


    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DimensionBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.DimensionType dimension, DimensionListBean parent, int position) {
        super(dimension,
                SDMX_STRUCTURE_TYPE.DIMENSION,
                dimension.getAnnotations(),
                dimension.getCodelist(),
                dimension.getConcept(), parent);
        this.measureDimension = false;
        this.position = position;
    }

    /**
     * Instantiates a new Dimension bean.
     *
     * @param dimension the dimension
     * @param parent    the parent
     * @param position  the position
     */
    public DimensionBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.TimeDimensionType dimension, DimensionListBean parent, int position) {
        super(dimension, SDMX_STRUCTURE_TYPE.TIME_DIMENSION, dimension.getAnnotations(), dimension.getCodelist(), dimension.getConcept(), parent);
        this.timeDimension = true;
        this.position = position;
        validateDimension();
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
            DimensionBean that = (DimensionBean) bean;
            if (measureDimension != that.isMeasureDimension()) {
                return false;
            }
            if (timeDimension != that.isTimeDimension()) {
                return false;
            }
            if (getPosition() != that.getPosition()) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(conceptRole, that.getConceptRole())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validateDimension() {
        //FUNC 2.1 validate only the allowed text format attributes have been set
        if (this.timeDimension) {
            setId(TIME_DIMENSION_FIXED_ID);
            if (this.hasCodedRepresentation()) {
                throw new SdmxSemmanticException("Time Dimension can not have a coded representation");
            }
            if (this.localRepresentation == null || this.localRepresentation.getTextFormat() == null) {
                RepresentationMutableBean rep = new RepresentationMutableBeanImpl();
                TextFormatMutableBean textFormat = new TextFormatMutableBeanImpl();
                textFormat.setTextType(TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD);
                rep.setTextFormat(textFormat);
                this.localRepresentation = new RepresentationBeanImpl(rep, this);

            } else if (localRepresentation.getTextFormat().getTextType() != null) {
                if (!localRepresentation.getTextFormat().getTextType().isValidTimeDimensionTextType()) {
                    //STRIP THE INVALID TEXT FORMAT
                    LOG.warn("Invalid Text Format found on Time Dimension, removing Text Format : " + localRepresentation.getTextFormat().getTextType());
                    RepresentationMutableBean rep = new RepresentationMutableBeanImpl();
                    TextFormatMutableBean textFormat = new TextFormatMutableBeanImpl();
                    textFormat.setTextType(TEXT_TYPE.OBSERVATIONAL_TIME_PERIOD);
                    rep.setTextFormat(textFormat);
                    this.localRepresentation = new RepresentationBeanImpl(rep, this);
                }
            }
        } else {
            if (this.getId().equals(TIME_DIMENSION_FIXED_ID)) {
                throw new SdmxSemmanticException("This dimension is NOT specified as the Time Dimension but is using the reserved id: " + TIME_DIMENSION_FIXED_ID);
            }
        }

        if (this.measureDimension) {
            super.structureType = SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION;

            if (this.getRepresentation() == null || this.getRepresentation().getRepresentation() == null) {
                throw new SdmxSemmanticException("Measure Dimension missing representation");
            }
            if (this.getMaintainableParent() instanceof CrossSectionalDataStructureBean) {
                //Ignore this
            } else {
                if (this.getRepresentation().getRepresentation().getTargetReference() != SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME) {
                    throw new SdmxSemmanticException("Measure Dimension representation must reference a concept scheme, currently it references a " +
                            this.getRepresentation().getRepresentation().getTargetReference().getType());
                }
            }
        }

        // Paranoia check: Ensure Dimension hasn't been declared as two things.
        if (this.measureDimension && this.timeDimension) {
            throw new SdmxSemmanticException("Dimension can not be both a Measure Dimension and a Time Dimension");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected List<String> getParentIds(boolean includeDifferentTypes) {
        List<String> returnList = new ArrayList<String>();
        returnList.add(this.getId());
        return returnList;
    }


    @Override
    public boolean isMeasureDimension() {
        return measureDimension;
    }

    @Override
    public boolean isFrequencyDimension() {
        if (freqDimension) {
            return true;
        }
        return this.getId().equals("FREQ");
    }

    @Override
    public boolean isTimeDimension() {
        return timeDimension;
    }

    @Override
    public List<CrossReferenceBean> getConceptRole() {
        return new ArrayList<CrossReferenceBean>(conceptRole);
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int compareTo(DimensionBean o) {
        if (o.getPosition() == this.getPosition()) {
            if (!o.equals(this)) {
                throw new SdmxSemmanticException("Two dimensions (" + this.getId() + " & " + o.getId() + ") can not share the same dimension position : " + getPosition());
            }
        }
        return o.getPosition() > this.getPosition() ? -1 : +1;
    }
}
