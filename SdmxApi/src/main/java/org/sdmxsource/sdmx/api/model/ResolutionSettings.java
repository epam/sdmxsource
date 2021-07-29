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
package org.sdmxsource.sdmx.api.model;

import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;

/**
 * Contains all the settings for parsing structures.
 * <p>
 * The setting are given by enumerated flags at construction, making instances immutable.
 * <p>
 * Functions are available to reinterpret these settings when applied to parsing XML structures into beans.
 * <p>
 * The StructureSettings take into account two settings for parsing SDMX-ML documents
 * <ol>
 *   <li>Resolve Cross References</li>
 *   <li>Resolve External References</li>
 * </ol>
 * <p>
 * Cross References are declared by structures such as Key Families also known as a Data Structure Definition (DSD) (see <code>DataStructureBean</code>).  A DSD
 * contains a number of Dimensions (<code>DimensionBean</code>) a Dimension may declare a reference to a Codelist and a Concept through reference parameters.
 * <p>
 * If a system is to resolve the cross references, then it can do this by either of the following ways:
 * <ul>
 *   <li>Resolve the external reference exists at the given URI</li>
 *   <li>Resolve the external reference exists, and substitute it for the provided 'stub' artifact</li>
 *   <li>Either of the above two can be lenient (do not error if it can not be done) or not (throw exception if this could not be done)</li>
 * </ul>
 * <p>
 * External References are declared by MaintainableBean structures by having their isExternal attribute set to true.  In this instance the Maintainable artifact
 * is declaring that it is not the full artifact, but just an empty container whose purpose is to declare the existence of the artifact (stub).  The stub also has
 * the responsibility of declaring where to get the full artifact, this is defined by the URI attribute.
 * <p>
 * The StructureSettings provides the following options for resolving cross references:
 * <ul>
 *   <li>Do  not resolve</li>
 *   <li>Resolve all the references (it is up to the implementation to decide how deep to resolve)</li>
 *   <li>Resolve all the references except for Agencies</li>
 * </ul>
 * <p>
 * External references are when a structure is declared, like a codelist, as a 'stub'
 * containing the minimum possible information for it to be valid against the schema;
 * i.e it has a name, id and agency id, but it also has a URI which says:
 * if you want the full codelists, with a 100 million codes, it's retrievable from this location.
 * This is useful if someone wants to store information in the registry, but maintain the contents of the artifacts externally.
 * <p>
 *
 * @see DataStructureBean which is built from an XML message.
 * @see SuperBean which is built from resolving all the cross referenced artifacts.
 */
public class ResolutionSettings implements Cloneable {
    private RESOLVE_EXTERNAL_SETTING resolveExternal;
    private RESOLVE_CROSS_REFERENCES resolveCrossRef;
    private int resolutionDepth;

    /**
     * Constructor, all settings provided here, immutable hereafter.
     *
     * @param resolveExternal the resolve external
     * @param resolveCrossRef the resolve cross ref
     * @param resolutionDepth - required if resolving references.  0 indicates resolve all, any other positive integer is the level of recursion to resolve
     */
    public ResolutionSettings(RESOLVE_EXTERNAL_SETTING resolveExternal, RESOLVE_CROSS_REFERENCES resolveCrossRef,
                              int resolutionDepth) {
        this.resolveExternal = resolveExternal;
        this.resolveCrossRef = resolveCrossRef;
        this.resolutionDepth = resolutionDepth;
    }

    /**
     * Constructor, all settings provided here, immutable hereafter.
     *
     * @param resolveExternal the resolve external
     * @param resolveCrossRef the resolve cross ref
     */
    public ResolutionSettings(RESOLVE_EXTERNAL_SETTING resolveExternal, RESOLVE_CROSS_REFERENCES resolveCrossRef) {
        this.resolveExternal = resolveExternal;
        this.resolveCrossRef = resolveCrossRef;
    }

    /**
     * Gets resolution depth.
     *
     * @return the resolution depth
     */
    public int getResolutionDepth() {
        return resolutionDepth;
    }

    /**
     * Gets resolve external.
     *
     * @return the resolve external
     */
    public RESOLVE_EXTERNAL_SETTING getResolveExternal() {
        return resolveExternal;
    }

    /**
     * Gets resolve cross ref.
     *
     * @return the resolve cross ref
     */
    public RESOLVE_CROSS_REFERENCES getResolveCrossRef() {
        return resolveCrossRef;
    }

    /**
     * Returns true if external references are to be retrieved and replace the stub
     *
     * @return true if external references are to be retrieved and replace the stub
     */
    public boolean isResolveExternalReferences() {
        return resolveExternal != RESOLVE_EXTERNAL_SETTING.DO_NOT_RESOLVE;
    }

    /**
     * Returns true if the cross referenced structures need to be resolved to exist
     *
     * @return true if the cross referenced structures need to be resolved to exist
     */
    public boolean isResolveCrossReferences() {
        return resolveCrossRef != RESOLVE_CROSS_REFERENCES.DO_NOT_RESOLVE;
    }

    /**
     * Returns true if the cross referenced structures need to be resolved to exist
     *
     * @return true if the cross referenced structures need to be resolved to exist
     */
    public boolean isResolveAgencyReferences() {
        return resolveCrossRef == RESOLVE_CROSS_REFERENCES.RESOLVE_ALL;
    }

    /**
     * Returns true if the resolved external references should be substituted for the maintainable stub
     *
     * @return true if the resolved external references should be substituted for the maintainable stub
     */
    public boolean isSubstituteExternal() {
        return resolveExternal == RESOLVE_EXTERNAL_SETTING.RESOLVE_SUBSTITUTE ||
                resolveExternal == RESOLVE_EXTERNAL_SETTING.RESOLVE_SUBSTITUTE_LENIENT;
    }

    /**
     * Returns true if the cross referenced structures need to be resolved to exist
     *
     * @return true if the cross referenced structures need to be resolved to exist
     */
    public boolean isLenient() {
        return resolveExternal == RESOLVE_EXTERNAL_SETTING.RESOLVE_LENIENT ||
                resolveExternal == RESOLVE_EXTERNAL_SETTING.RESOLVE_SUBSTITUTE_LENIENT;
    }

    @Override
    public ResolutionSettings clone() {
        return new ResolutionSettings(resolveExternal, resolveCrossRef, resolutionDepth);
    }

    @Override
    public String toString() {
        return "Resolve external=" + resolveExternal + ", resolve cross references=" + resolveCrossRef + ", resolution depth=" + resolutionDepth;
    }

    /**
     * Flag options for handling External References (structures not present but specified with a URI,
     * indicating a remote file containing the missing structures).
     */
    public enum RESOLVE_EXTERNAL_SETTING {
        /**
         * DO NOT RESOLVE EXTERNAL REFERENCES
         */
        DO_NOT_RESOLVE,
        /**
         * RESOLVE EXTERNAL REFERENCES
         */
        RESOLVE,
        /**
         * RESOLVE EXTERNAL REFERENCES AND SUBSTITUTE THEM IN FOR THE STUB
         */
        RESOLVE_SUBSTITUTE,
        /**
         * RESOLVE EXTERNAL REFERENCES - ADD ERROR ANNOTATIONS IF ERRORS OCCUR
         */
        RESOLVE_LENIENT,
        /**
         * RESOLVE EXTERNAL REFERENCES AND SUBSTITUTE THEM IN FOR THE STUB - ADD ERROR ANNOTATIONS IF ERRORS OCCUR
         */
        RESOLVE_SUBSTITUTE_LENIENT,
    }

    /**
     * Flag options for handling Cross References
     */
    public enum RESOLVE_CROSS_REFERENCES {
        /**
         * DO NOT RESOLVE CROSS REFERENCES
         */
        DO_NOT_RESOLVE,
        /**
         * RESOLVE CROSS REFERENCES INCLUDING AGENCIES
         */
        RESOLVE_ALL,
        /**
         * RESOLVE CROSS REFERENCES EXCLUDING AGENCIES
         */
        RESOLVE_EXCLUDE_AGENCIES;
    }

}
