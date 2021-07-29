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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemBeanImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Code bean.
 */
public class CodeBeanImpl extends ItemBeanImpl implements CodeBean {
    private static final long serialVersionUID = 1L;
    private String parentCode;

    /**
     * Instantiates a new Code bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodeBeanImpl(CodelistBean parent, CodeMutableBean bean) {
        super(bean, parent);
        this.parentCode = bean.getParentCode();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	public CodeBeanImpl(CodelistBean parent, SdmxReader reader) {
//		super(validateRootElement(reader), reader, parent);
//		if(reader.moveToElement("Parent", "Code")) {
//			reader.moveNextElement();  //Move to the Ref Node
//			if(!reader.getCurrentElement().equals("Ref")) {
//				throw new SdmxSemmanticException("Expecting 'Ref' element after 'Parent' element for Code");
//			}
//			this.parentCode = reader.getAttributeValue("id", true);
//		} 
//	}

//	private static SDMX_STRUCTURE_TYPE validateRootElement(SdmxReader reader) {
//		if(!reader.getCurrentElement().equals("Code")) {
//			throw new SdmxSemmanticException("Can not construct code - expecting 'Code' Element in SDMX, actual element:" + reader.getCurrentElementValue());
//		}
//		return SDMX_STRUCTURE_TYPE.CODE;
//	}

    /**
     * Instantiates a new Code bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodeBeanImpl(CodelistBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.CodeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE, parent);
        this.parentCode = (bean.getParent() != null) ? bean.getParent().getRef().getId() : null;
    }

    /**
     * Instantiates a new Code bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodeBeanImpl(CodelistBean parent, CodeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE, bean.getValue(), null, bean.getDescriptionList(), null, bean.getAnnotations(), parent);

        // In SDMX 2.0 it is perfectly valid for the XML to state a code has a parentCode which is blank. e.g.:
        //    <str:Code value="aCode" parentCode="">
        // This can cause issues when manipulating the beans, so police the input by not setting the
        // parentCode if it is an empty string.
        if (ObjectUtil.validString(bean.getParentCode())) {
            this.parentCode = bean.getParentCode();
        }
    }

    /**
     * Instantiates a new Code bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodeBeanImpl(CodelistBean parent,
                        org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE, bean.getValue(), null, bean.getDescriptionList(), null, bean.getAnnotations(), parent);
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
            CodeBean that = (CodeBean) bean;
            if (!ObjectUtil.equivalent(parentCode, that.getParentCode())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS  							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getParentCode() {
        return parentCode;
    }
}
