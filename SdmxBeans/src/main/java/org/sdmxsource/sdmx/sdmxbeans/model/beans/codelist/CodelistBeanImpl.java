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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeListType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.CodelistType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodelistMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.*;


/**
 * The type Codelist bean.
 */
public class CodelistBeanImpl extends ItemSchemeBeanImpl<CodeBean> implements CodelistBean {
    private static final long serialVersionUID = 1L;

    private final Map<String, CodeBean> codeMap;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private CodelistBeanImpl(CodelistBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        this.codeMap = new HashMap<>();
    }

    /**
     * Instantiates a new Codelist bean.
     *
     * @param codelist the codelist
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistBeanImpl(CodelistMutableBean codelist) {
        super(codelist);
        codeMap = new HashMap<>();
        try {
            if (codelist.getItems() != null) {
                for (CodeMutableBean mutableCode : codelist.getItems()) {
                    final var code = new CodeBeanImpl(this, mutableCode);
                    this.items.add(code);
                    this.codeMap.put(code.getId(), code);
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
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	public CodelistBeanImpl(SdmxReader reader) {
//		super(validateRootElement(reader), reader);
//		if(reader.moveToElement("Code", "Codelist")) {
//			items.add(new CodeBeanImpl(this, reader));
//			if(reader.peek().equals("Code")) {
//				reader.moveNextElement();
//				while(reader.getCurrentElement().equals("Code")) {
//					items.add(new CodeBeanImpl(this, reader));
//				}
//			}
//		}
//	}

//	private static SDMX_STRUCTURE_TYPE validateRootElement(SdmxReader reader) {
//		if(!reader.getCurrentElement().equals("Codelist")) {
//			throw new SdmxSemmanticException("Can not construct codelist - expecting 'Codelist' Element in SDMX, actual element:" + reader.getCurrentElementValue());
//		}
//		return SDMX_STRUCTURE_TYPE.CODE_LIST;
//	}


    /**
     * Instantiates a new Codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeListType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE_LIST,
                bean.getVersion(),
                bean.getAgency(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        this.codeMap = new HashMap<>();

        try {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.CodeType currentCode : bean.getCodeList()) {
                var code = new CodeBeanImpl(this, currentCode);
                items.add(code);
                codeMap.put(code.getId(), code);
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
    }

    /**
     * Instantiates a new Codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistBeanImpl(CodeListType bean) {
        super(bean,
                SDMX_STRUCTURE_TYPE.CODE_LIST,
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

        this.codeMap = new HashMap<>();

        try {
            for (CodeType currentCode : bean.getCodeList()) {
                CodeBeanImpl code = new CodeBeanImpl(this, currentCode);
                items.add(code);
                codeMap.put(code.getId(), code);
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
    }

    /**
     * Instantiates a new Codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistBeanImpl(CodelistType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE_LIST);
        this.codeMap = new HashMap<>();

        try {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.CodeType currentCode : bean.getCodeList()) {
                CodeBeanImpl code = new CodeBeanImpl(this, currentCode);
                items.add(code);
                codeMap.put(code.getId(), code);
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
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((CodelistBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        Set<String> urns = new HashSet<String>();
        if (items != null && isPartial() == false) {
            //CHECK FOR DUPLICATION OF URN & ILLEGAL PARENTING
            Map<CodeBean, Set<CodeBean>> parentChildMap = new HashMap<CodeBean, Set<CodeBean>>();


            for (CodeBean code : items) {
                if (urns.contains(code.getUrn())) {
                    throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, code.getUrn());
                }
                urns.add(code.getUrn());

                if (ObjectUtil.validString(code.getParentCode())) {
                    CodeBean parentCode = getCode(code.getParentCode());
                    Set<CodeBean> children;
                    if (parentChildMap.containsKey(parentCode)) {
                        children = parentChildMap.get(parentCode);
                    } else {
                        children = new HashSet<CodeBean>();
                        parentChildMap.put(parentCode, children);
                    }
                    children.add(code);
                    //Check that the parent code is not directly or indirectly a child of the code it is parenting
                    recurseParentMap(parentChildMap.get(code), parentCode, parentChildMap);
                }
            }
        }
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Not allowed to start with an integer
        super.validateId(false);
    }

    /**
     * Recurses the map checking the children of each child, if one of the children is the parent code, then an exception is thrown
     *
     * @param children
     * @param parentCode
     * @param parentChildMap
     */
    private void recurseParentMap(Set<CodeBean> children, CodeBean parentCode, Map<CodeBean, Set<CodeBean>> parentChildMap) {
        //If the child is also a parent
        if (children != null) {
            if (children.contains(parentCode)) {
                throw new SdmxSemmanticException(ExceptionCode.PARENT_RECURSIVE_LOOP, parentCode.getId());
            }
            for (CodeBean currentChild : children) {
                recurseParentMap(parentChildMap.get(currentChild), parentCode, parentChildMap);
            }
        }
    }

    private CodeBean getCode(String id) {
        CodeBean code = getCodeById(id);
        if (code == null) {
            throw new SdmxSemmanticException(ExceptionCode.CAN_NOT_RESOLVE_PARENT, id);
        }
        return code;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS  							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CodelistBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new CodelistBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public CodelistMutableBean getMutableInstance() {
        return new CodelistMutableBeanImpl(this);
    }


    @Override
    public CodeBean getCodeById(String id) {
        return codeMap.get(id);
    }
}
