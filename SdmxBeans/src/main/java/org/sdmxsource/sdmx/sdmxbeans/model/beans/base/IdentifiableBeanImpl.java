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
import org.sdmx.resources.sdmxml.schemas.v21.structure.IdentifiableType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.IdentifiableMutableBean;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.StringUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The type Identifiable bean.
 */
public abstract class IdentifiableBeanImpl extends AnnotableBeanImpl implements IdentifiableBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Uri.
     */
    URI uri;
    private String id;
    private String urn;

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param id            the id
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM A NON-IDENTIFIABLE		 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public IdentifiableBeanImpl(String id, SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(null, structureType, parent);
        setId(id);
        validateIdentifiableAttributes();
    }

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected IdentifiableBeanImpl(IdentifiableBean bean) {
        super(bean);
        this.id = bean.getId();
        this.uri = bean.getUri();
        this.structureType = bean.getStructureType();
        validateIdentifiableAttributes();
    }

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected IdentifiableBeanImpl(IdentifiableMutableBean bean,
                                   SdmxStructureBean parent) {
        super(bean, parent);
        setId(bean.getId());
        setUri(bean.getUri());
        validateIdentifiableAttributes();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//	public IdentifiableBeanImpl(SDMX_STRUCTURE_TYPE structure, SdmxReader reader, SdmxStructureBean parent) {
//		super(structure, reader, parent);
//		
//		id = reader.getAttributeValue("id", true);
//		if(reader.getAttributeValue("uri", false) != null) {
//			setUri(reader.getAttributeValue("uri", false));
//		}
//	}

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public IdentifiableBeanImpl(IdentifiableType createdFrom, SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(createdFrom, structureType, parent);
        this.id = createdFrom.getId();
        setUri(createdFrom.getUri());
        validateIdentifiableAttributes();
    }

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public IdentifiableBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                                String id,
                                String uri,
                                AnnotationsType annotationsType,
                                SdmxStructureBean parent) {
        super(createdFrom, annotationsType, structureType, parent);
        setId(id);
        setUri(uri);
        validateIdentifiableAttributes();
    }

    /**
     * Instantiates a new Identifiable bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public IdentifiableBeanImpl(XmlObject createdFrom,
                                SDMX_STRUCTURE_TYPE structureType,
                                String id,
                                String uri,
                                org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType,
                                SdmxStructureBean parent) {
        super(createdFrom, annotationsType, structureType, parent);
        setId(id);
        setUri(uri);
        validateIdentifiableAttributes();
    }

    /**
     * Validate identifiable attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateIdentifiableAttributes() throws SdmxSemmanticException {
        if (parent == null && !this.structureType.isMaintainable()) {
            throw new SdmxSemmanticException(ExceptionCode.JAVA_REQUIRED_OBJECT_NULL, "parent");
        }
        validateId(true);
    }

    /**
     * Validate id.
     *
     * @param startWithIntAllowed the start with int allowed
     */
    protected void validateId(boolean startWithIntAllowed) {
        if (!ObjectUtil.validString(this.getId())) {
            throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_IDENTIFIABLE_MISSING_ID, this.structureType);
        }
        this.id = ValidationUtil.cleanAndValidateId(getId(), startWithIntAllowed);
        this.id = StringUtil.manualIntern(id);
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
    protected boolean deepEqualsInternal(IdentifiableBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (!ObjectUtil.equivalent(id, bean.getId())) {
            return false;
        }
        if (includeFinalProperties) {
            //If we don't want to test the final properties, then the URI can be ignored, as this can change
            if (!ObjectUtil.equivalent(uri, bean.getUri())) {
                return false;
            }
        }
        if (!ObjectUtil.equivalent(getUrn(), bean.getUrn())) {
            return false;
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public StructureReferenceBean asReference() {
        return new StructureReferenceBeanImpl(this);
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    protected void setId(String id) {
        this.id = StringUtil.manualIntern(id);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    private void setUri(String uriStr) {
        if (uriStr == null) {
            this.uri = null;
            return;
        }
        try {
            this.uri = new URI(uriStr);
        } catch (Throwable th) {
            throw new SdmxSemmanticException("Could not create attribute 'uri' with value '" + uriStr + "'");
        }
    }

    @Override
    public String getFullIdPath(boolean includeDifferentTypes) {
        if (!this.getStructureType().isMaintainable()) {
            List<String> parentIds = getParentIds(includeDifferentTypes);
            String returnId = "";
            String concat = "";
            for (String currentId : parentIds) {
                returnId += concat + currentId;
                concat = ".";
            }
            return returnId;
        }
        return null;
    }


    @Override
    public String getUrn() {
        if (urn == null) {
            List<String> parentIds = getParentIds(true);
            MaintainableBean maintainableParent = getMaintainableParent();
            String ids[] = new String[parentIds.size()];
            parentIds.toArray(ids);
            urn = structureType.generateUrn(maintainableParent.getAgencyId(), maintainableParent.getId(), maintainableParent.getVersion(), ids);
        }
        return urn;
    }

    /**
     * Returns a list of ids for each identifiable (non-maintainable parent), starting from the top level (index 0 of list) to this level (index 0+X)
     *
     * @param includeDifferentTypes the include different types
     * @return parent ids
     */
    protected List<String> getParentIds(boolean includeDifferentTypes) {
        List<String> parentIds = new ArrayList<String>();
        if (!this.getStructureType().isMaintainable()) {
            parentIds.add(this.getId());
            IdentifiableBean currentParent = getIdentifiableParent();
            while (currentParent != null) {
                if (!includeDifferentTypes && this.getStructureType() != currentParent.getStructureType()) {
                    break;
                }
                if (!currentParent.getStructureType().isMaintainable()) {
                    parentIds.add(currentParent.getId());
                    currentParent = currentParent.getIdentifiableParent();
                } else {
                    break;
                }
            }
        }
        Collections.reverse(parentIds);
        return parentIds;
    }

    @Override
    public List<TextTypeWrapper> getAllTextTypes() {
        List<TextTypeWrapper> returnList = new ArrayList<TextTypeWrapper>();
        for (AnnotationBean annotation : getAnnotations()) {
            returnList.addAll(annotation.getText());
        }
        return returnList;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdentifiableBean) {
            IdentifiableBean that = (IdentifiableBean) obj;
            return that.getUrn().equals(this.getUrn());
        }
        return false;
    }

    @Override
    public String toString() {
        try {
            return getUrn();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("\nId: " + getId());
            return sb.toString();
        }
    }

    @Override
    public int hashCode() {
        return getUrn().hashCode();
    }
}
