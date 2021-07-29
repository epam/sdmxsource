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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmx.resources.sdmxml.schemas.v21.common.*;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;
import java.util.List;


/**
 * The type Abstract bean assembler.
 */
public class AbstractBeanAssembler {

    // This character MUST be a character that is not a valid character in an ID.
    private final static String DELIMITER = "~";

    /**
     * Valid string boolean.
     *
     * @param string the string
     * @return the boolean
     */
    protected boolean validString(String string) {
        return ObjectUtil.validString(string);
    }

    /**
     * Valid collection boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    protected boolean validCollection(@SuppressWarnings("rawtypes") Collection collection) {
        return ObjectUtil.validCollection(collection);
    }

    /**
     * Get text type text type [ ].
     *
     * @param ttWrapper the tt wrapper
     * @return the text type [ ]
     */
    TextType[] getTextType(List<TextTypeWrapper> ttWrapper) {
        if (!ObjectUtil.validCollection(ttWrapper)) {
            return null;
        }
        TextType[] ttArr = new TextType[ttWrapper.size()];
        for (int i = 0; i < ttWrapper.size(); i++) {
            TextType tt = getTextType(ttWrapper.get(i));
            ttArr[i] = tt;
        }
        return ttArr;
    }

    /**
     * Gets text type.
     *
     * @param ttWrapper the tt wrapper
     * @return the text type
     */
    TextType getTextType(TextTypeWrapper ttWrapper) {
        TextType tt = TextType.Factory.newInstance();
        tt.setLang(ttWrapper.getLocale());
        tt.setStringValue(ttWrapper.getValue());
        return tt;
    }

    /**
     * Gets text type.
     *
     * @param englishString the english string
     * @return the text type
     */
    TextType getTextType(String englishString) {
        TextType tt = TextType.Factory.newInstance();
        tt.setLang("en");
        tt.setStringValue(englishString);
        return tt;
    }

    /**
     * Has annotations boolean.
     *
     * @param annotable the annotable
     * @return the boolean
     */
    boolean hasAnnotations(AnnotableBean annotable) {
        if (ObjectUtil.validCollection(annotable.getAnnotations())) {
            return true;
        }
        return false;
    }

    /**
     * Sets reference.
     *
     * @param ref            the ref
     * @param crossReference the cross reference
     */
    protected void setReference(RefBaseType ref, StructureReferenceBean crossReference) {
        if (crossReference == null) {
            throw new IllegalArgumentException("Could not set reference on artefact, no reference supplied");
        }
        MaintainableRefBean maintRef = crossReference.getMaintainableReference();
        if (crossReference.hasChildReference()) {
            String fullId = getFullIdentifiableId(crossReference);
            if (fullId.contains(DELIMITER)) {
                String containerId = fullId.substring(0, fullId.lastIndexOf(DELIMITER));
                String targetId = fullId.substring(fullId.indexOf(DELIMITER) + 1, fullId.length());
                ref.setContainerID(containerId);
                ref.setId(targetId);
            } else {
                ref.setId(fullId);
            }
            if (ObjectUtil.validString(maintRef.getMaintainableId())) {
                ref.setMaintainableParentID(maintRef.getMaintainableId());
            }
            if (ObjectUtil.validString(maintRef.getVersion())) {
                ref.setMaintainableParentVersion(maintRef.getVersion());
            }
        } else {
            if (ObjectUtil.validString(maintRef.getMaintainableId())) {
                ref.setId(maintRef.getMaintainableId());
            }
            if (ObjectUtil.validString(maintRef.getVersion())) {
                ref.setVersion(maintRef.getVersion());
            }
        }
        if (ObjectUtil.validString(maintRef.getAgencyId())) {
            ref.setAgencyID(maintRef.getAgencyId());
        }
        ref.setPackage(PackageTypeCodelistType.Enum.forString(crossReference.getTargetReference().getUrnPackage()));

        ObjectTypeCodelistType.Enum classEnum;
        switch (crossReference.getTargetReference()) {
            //Place any exclusions to the rule in this case statement
            case DATA_ATTRIBUTE:
                classEnum = ObjectTypeCodelistType.ATTRIBUTE;
                break;
            default:
                String urnClass = crossReference.getTargetReference().getUrnClass();
                classEnum = ObjectTypeCodelistType.Enum.forString(urnClass);
        }

        if (classEnum == null) {
            throw new SdmxSemmanticException("Unknown urnClass : " + crossReference.getTargetReference().getUrnClass());
        }
        ref.setClass1(classEnum);
    }


    private String getFullIdentifiableId(StructureReferenceBean crossReference) {
        String returnString = "";
        IdentifiableRefBean childReference = crossReference.getChildReference();
        String concat = "";
        SDMX_STRUCTURE_TYPE type = null;
        while (childReference != null) {
            if (type != null && childReference.getStructureType() != type) {
                // Change the concat character into a symbol that is not in an ID.
                // We do this so we can't hunt for it later
                concat = DELIMITER;
            }
            returnString += concat + childReference.getId();
            type = childReference.getStructureType();  //Store the previous type
            childReference = childReference.getChildReference();
            concat = ".";
        }
        return returnString;
    }

    /**
     * Gets annotations type.
     *
     * @param annotable the annotable
     * @return the annotations type
     */
    AnnotationsType getAnnotationsType(AnnotableBean annotable) {
        if (!ObjectUtil.validCollection(annotable.getAnnotations())) {
            return null;
        }
        AnnotationsType returnType = AnnotationsType.Factory.newInstance();

        for (AnnotationBean currentAnnotationBean : annotable.getAnnotations()) {
            AnnotationType annotation = returnType.addNewAnnotation();
            if (ObjectUtil.validString(currentAnnotationBean.getId())) {
                annotation.setId(currentAnnotationBean.getId());
            }
            if (ObjectUtil.validCollection(currentAnnotationBean.getText())) {
                annotation.setAnnotationTextArray(getTextType(currentAnnotationBean.getText()));
            }
            if (ObjectUtil.validString(currentAnnotationBean.getTitle())) {
                annotation.setAnnotationTitle(currentAnnotationBean.getTitle());
            }
            if (ObjectUtil.validString(currentAnnotationBean.getType())) {
                annotation.setAnnotationType(currentAnnotationBean.getType());
            }
            if (currentAnnotationBean.getUri() != null) {
                annotation.setAnnotationURL(currentAnnotationBean.getUri().toString());
            }
        }

        return returnType;
    }
}
