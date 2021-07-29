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
package org.sdmxsource.sdmx.util.beans;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.VersionableUtil;

import java.util.*;


/**
 * This class provides utility methods that can be performed on a MaintainableBean
 *
 * @param <T> the type parameter
 */
public class MaintainableUtil<T extends MaintainableBean> {

    /**
     * Returns a collection keeping only the latest versions of each maintainable passed in.
     * <p>
     * Note, the returned Set is a new Set, no changes are made to the passed in collection.
     *
     * @param maintianables the maintianables
     * @return set set
     */
    public static Set<MaintainableBean> filterCollectionGetLatest(Collection<MaintainableBean> maintianables) {
        Map<String, MaintainableBean> resultMap = new HashMap<String, MaintainableBean>();
        boolean filteredResponse = false;
        for (MaintainableBean currentMaint : maintianables) {
            String key = currentMaint.getStructureType().getType() + "_" + currentMaint.getAgencyId() + "_" + currentMaint.getId();
            if (resultMap.containsKey(key)) {
                filteredResponse = true;
                MaintainableBean storedAgainstKey = resultMap.get(key);
                if (VersionableUtil.isHigherVersion(currentMaint.getVersion(), storedAgainstKey.getVersion())) {
                    resultMap.put(key, currentMaint);
                }
            } else {
                resultMap.put(key, currentMaint);
            }
        }
        if (filteredResponse) {
            return new HashSet<MaintainableBean>(resultMap.values());
        }
        return new HashSet<MaintainableBean>(maintianables);
    }

    /**
     * For a set of maintainables, this method will return the maintainable that has the same urn as the ref bean.
     * <p>
     * If the ref bean does not have a urn, then it will return any matches for the agency id, id and version
     * as the ref bean.
     * <p>
     * If the ref bean does not have a urn OR agency id, id and version set then it will return all the beans.
     * <p>
     *
     * @param populateCollection the populate collection
     * @param maintianables      the maintianables
     * @param ref                the ref
     */
    public static void findMatches(Collection<MaintainableBean> populateCollection, Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
        if (ref == null) {
            throw new IllegalArgumentException("Ref is null");
        }

        if (maintianables != null) {
            for (MaintainableBean currentMaintainable : maintianables) {
                if (match(currentMaintainable, ref)) {
                    populateCollection.add(currentMaintainable);
                }
            }
        }
    }

    /**
     * For a set of maintainables, this method will return the maintainable that has the same urn as the ref bean.
     * <p>
     * If the ref bean does not have a urn, then it will return any matches for the agency id, id and version
     * as the ref bean.
     * <p>
     * If the ref bean does not have a urn OR agency id, id and version set then it will return all the beans.
     * <p>
     *
     * @param maintianables the maintianables
     * @param ref           the ref
     * @return set set
     */
    public static Set<MaintainableBean> findMatches(Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
        if (ref == null) {
            throw new IllegalArgumentException("Ref is null");
        }
        Set<MaintainableBean> returnSet = new HashSet<MaintainableBean>();

        if (maintianables != null) {
            for (MaintainableBean currentMaintainable : maintianables) {
                if (match(currentMaintainable, ref)) {
                    returnSet.add(currentMaintainable);
                }
            }
        }
        return returnSet;
    }

    /**
     * Match boolean.
     *
     * @param maint the maint
     * @param ref   the ref
     * @return the boolean
     */
    public static boolean match(MaintainableBean maint, StructureReferenceBean ref) {
        if (ref == null) {
            return true;
        }
        return ref.isMatch(maint);
    }

    /**
     * Subset of boolean.
     *
     * @param ref  the ref
     * @param ref2 the ref 2
     * @return the boolean
     */
    public static boolean subsetOf(MaintainableRefBean ref, MaintainableRefBean ref2) {
        boolean match = true;

        if (ObjectUtil.validString(ref.getAgencyId())) {
            match = ref.getAgencyId().equals(ref2.getAgencyId());
        }
        if (match) {
            if (ObjectUtil.validString(ref.getMaintainableId())) {
                match = ref.getMaintainableId().equals(ref2.getMaintainableId());
            }
        }
        if (match) {
            if (ObjectUtil.validString(ref.getVersion())) {
                match = ref.getVersion().equals(ref2.getVersion());
            }
        }
        return match;
    }

    /**
     * For a set of maintainables, this method will return the maintainable that matches the parameters of the ref bean.
     * <p>
     * If the ref bean does not have a urn, then it will return the maintainable that has the same agency id, id and version
     * as the ref bean.
     * <p>
     * If the ref bean does not have a urn OR agency id, id and version set then it will result in an error.
     * <p>
     * This method will stop at the first match and return it, no checks are performed on the type of maintainables in the set.
     * <p>
     * If no match is found null is returned.
     *
     * @param maintianables the maintianables
     * @param ref           the ref
     * @return maintainable bean
     */
    public static MaintainableBean resolveReference(Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
        if (ref == null) {
            throw new IllegalArgumentException("Ref is null");
        }
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (!ObjectUtil.validString(ref.getTargetUrn()) && !ObjectUtil.validString(mRef.getAgencyId(), mRef.getMaintainableId(), mRef.getVersion())) {
            throw new IllegalArgumentException("Ref requires a URN or AgencyId, Maintainable Id and Version");
        }
        if (maintianables != null) {
            for (MaintainableBean currentMaintainable : maintianables) {
                if (match(currentMaintainable, ref)) {
                    return currentMaintainable;
                }
            }
        }
        return null;
    }

    /**
     * Resolve reference maintainable bean.
     *
     * @param maintainables the maintainables
     * @param ref           the ref
     * @return the maintainable bean
     */
    public static MaintainableBean resolveReference(Collection<? extends MaintainableBean> maintainables, MaintainableRefBean ref) {
        if (!ObjectUtil.validCollection(maintainables)) {
            return null;
        }
        if (ref == null) {
            if (maintainables.size() == 1) {
                return maintainables.iterator().next();
            }
            throw new IllegalArgumentException("Can not resolve reference, more then one bean supplied and no reference parameters passed in");
        }
        if (maintainables != null) {
            for (MaintainableBean currentMaintainable : maintainables) {
                if (ref.getAgencyId() == null || currentMaintainable.getAgencyId().equals(ref.getAgencyId())) {
                    if (ref.getMaintainableId() == null || currentMaintainable.getId().equals(ref.getMaintainableId())) {
                        if (ref.getVersion() == null || currentMaintainable.getVersion().equals(ref.getVersion())) {
                            return currentMaintainable;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Parses the unique identifier which is made up in the following format:
     * &lt;agency&gt;:&lt;id&gt;(&lt;version&gt;)
     * and returns a MaintainableRefBean from these values.
     *
     * @param identifier the identifier
     * @param type       Simply a value for the error message
     * @return maintainable ref bean
     */
    public static MaintainableRefBean parseUniqueIdentifier(String identifier, String type) {
        // Everything up to the first colon is the agency id
        int colonPos = identifier.indexOf(":");
        if (colonPos == -1) {
            throw new IllegalArgumentException(type + " was specified in an invalid manner. Format is [agency]:[id]([version]). Value specified: " + identifier);
        }
        String[] split = identifier.split(":");
        String agencyId = split[0];

        String remainder = split[1];
        int openBracketPos = remainder.indexOf("(");
        if (openBracketPos == -1) {
            throw new IllegalArgumentException(type + " was specified in an invalid manner. Format is [agency]:[id]([version]). Value specified: " + identifier);
        }
        String id = remainder.substring(0, openBracketPos);
        String version = remainder.substring(openBracketPos + 1, remainder.length() - 1);

        return new MaintainableRefBeanImpl(agencyId, id, version);
    }

    /**
     * Gets the the unique identifier of the specified MaintainableBean which is in the following format:
     * &lt;agency&gt;:&lt;id&gt;(&lt;version&gt;)
     *
     * @param maint the maint
     * @return unique identifier
     */
    public static String getUniqueIdentifier(MaintainableBean maint) {
        StringBuilder sb = new StringBuilder();
        sb.append(maint.getAgencyId());
        sb.append(":");
        sb.append(maint.getId());
        sb.append("(");
        sb.append(maint.getVersion());
        sb.append(")");
        return sb.toString();
    }

    /**
     * Gets the the unique identifier of the specified MaintainableBean which is in the following format:
     * &lt;agency&gt;:&lt;id&gt;(&lt;version&gt;)
     *
     * @param maint the maint
     * @return unique identifier
     */
    public static String getUniqueIdentifier(MaintainableSuperBean maint) {
        StringBuilder sb = new StringBuilder();
        sb.append(maint.getAgencyId());
        sb.append(":");
        sb.append(maint.getId());
        sb.append("(");
        sb.append(maint.getVersion());
        sb.append(")");
        return sb.toString();
    }

    /**
     * Returns a collection keeping only the latest versions of each maintainable passed in.
     * <p>
     * Note, the returned Set is a new Set, no changes are made to the passed in collection.
     *
     * @param maintianables the maintianables
     * @return set set
     */
    public Set<T> filterCollectionGetLatestOfType(Collection<T> maintianables) {
        Map<String, T> resultMap = new HashMap<String, T>();
        boolean filteredResponse = false;
        for (T currentMaint : maintianables) {
            String key = currentMaint.getAgencyId() + "_" + currentMaint.getId();
            if (resultMap.containsKey(key)) {
                filteredResponse = true;
                T storedAgainstKey = resultMap.get(key);
                if (VersionableUtil.isHigherVersion(currentMaint.getVersion(), storedAgainstKey.getVersion())) {
                    resultMap.put(key, currentMaint);
                }
            } else {
                resultMap.put(key, currentMaint);
            }
        }
        if (filteredResponse) {
            return new HashSet<T>(resultMap.values());
        }
        return new HashSet<T>(maintianables);
    }

    /**
     * Returns a collection of maintainables that match the input reference
     *
     * @param maintianables the maintianables
     * @param ref           the ref
     * @return set set
     */
    public Set<T> filterCollection(Collection<T> maintianables, MaintainableRefBean ref) {
        Set<T> returnSet = new HashSet<T>();

        String agencyId = null;
        String id = null;
        String version = null;

        if (ref != null) {
            agencyId = ref.getAgencyId();
            id = ref.getMaintainableId();
            version = ref.getVersion();
        }

        for (T currentMaintainable : maintianables) {
            if (agencyId == null || currentMaintainable.getAgencyId().equals(agencyId)) {
                if (id == null || currentMaintainable.getId().equals(id)) {
                    if (version == null || currentMaintainable.getVersion().equals(version)) {
                        returnSet.add(currentMaintainable);
                    }
                }
            }
        }
        return returnSet;
    }
}
