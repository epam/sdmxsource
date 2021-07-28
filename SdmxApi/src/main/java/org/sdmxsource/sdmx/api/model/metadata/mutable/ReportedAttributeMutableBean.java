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
package org.sdmxsource.sdmx.api.model.metadata.mutable;

import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.metadata.ReportedAttributeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.IdentifiableMutableBean;

import java.util.List;


public interface ReportedAttributeMutableBean extends IdentifiableMutableBean {

    /**
     * @return a list of texts for this component - will return an empty list if no Texts exist.
     */
    List<TextTypeWrapper> getTexts();

    /**
     * @return the text in the default locale
     */
    String getText();

    /**
     * Structured Text is XHTML
     *
     * @return a list of structured texts for this component - will return an empty list if no Texts exist.
     */
    List<TextTypeWrapper> getStructuredTexts();

    /**
     * Structured Text is XHTML
     *
     * @return the structured text in the default locale
     */
    String getStructuredText();

    /**
     * @return child attributes
     */
    List<ReportedAttributeBean> getReportedAttributes();

//	/**
//	 * @return a list of texts for this component - will return an empty list if no Texts exist.
//	 */
//	void setTexts(List<TextTypeWrapper> texts);
//	
//	/**
//	 * @return the text in the default locale
//	 */
//	String getText();
//
//	/**
//	 * Structured Text is XHTML
//	 * @return a list of structured texts for this component - will return an empty list if no Texts exist.
//	 */
//	List<TextTypeWrapper> getStructuredTexts();
//	
//	/**
//	 * Structured Text is XHTML
//	 * @return the structured text in the default locale
//	 */
//	String getStructuredText();
//
//	/**
//	 * @return child attributes
//	 */
//	List<ReportedAttributeBean> getReportedAttributes();
//

}
