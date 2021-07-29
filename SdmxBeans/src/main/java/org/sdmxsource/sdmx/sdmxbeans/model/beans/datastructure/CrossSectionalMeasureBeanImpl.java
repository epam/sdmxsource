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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CrossSectionalMeasureType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalMeasureBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.CrossSectionalMeasureMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;


/**
 * The type Cross sectional measure bean.
 */
public class CrossSectionalMeasureBeanImpl extends ComponentBeanImpl implements CrossSectionalMeasureBean {
    private static final long serialVersionUID = -6945109855247194247L;
    private String measureDimension;
    private String code;


    /**
     * Instantiates a new Cross sectional measure bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CrossSectionalMeasureBeanImpl(CrossSectionalMeasureMutableBean bean, CrossSectionalDataStructureBean parent) {
        super(bean, parent);
        this.measureDimension = bean.getMeasureDimension();
        this.code = bean.getCode();
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Cross sectional measure bean.
     *
     * @param createdFrom the created from
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CrossSectionalMeasureBeanImpl(CrossSectionalMeasureType createdFrom, IdentifiableBean parent) {
        super(createdFrom,
                SDMX_STRUCTURE_TYPE.CROSS_SECTIONAL_MEASURE,
                createdFrom.getAnnotations(),
                createdFrom.getTextFormat(),
                createdFrom.getCodelistAgency(),
                createdFrom.getCodelist(),
                createdFrom.getCodelistVersion(),
                createdFrom.getConceptSchemeAgency(),
                createdFrom.getConceptSchemeRef(),
                createdFrom.getConceptVersion(),
                createdFrom.getConceptAgency(),
                createdFrom.getConceptRef(), parent);
        this.measureDimension = createdFrom.getMeasureDimension();
        this.code = createdFrom.getCode();
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (measureDimension == null) {
            throw new SdmxSemmanticException("Cross Sectional Measure Dimension missing mandatory Measure Dimension reference");
        }
        if (code == null) {
            throw new SdmxSemmanticException("Cross Sectional Measure Dimension missing mandatory Code reference");
        }
        if (((CrossSectionalDataStructureBean) getMaintainableParent()).getDimension(measureDimension) == null) {
            throw new SdmxSemmanticException("Cross Sectional Measure Dimension references non-existent dimension " + measureDimension);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getMeasureDimension() {
        return measureDimension;
    }

    @Override
    public String getCode() {
        return code;
    }
}
