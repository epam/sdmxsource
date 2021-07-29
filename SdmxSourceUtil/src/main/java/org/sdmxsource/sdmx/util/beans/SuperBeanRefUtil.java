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

import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.VersionableUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * This class provides utility methods that can be performed on a MaintainableSuperBean
 *
 * @param <T> the type parameter
 */
public class SuperBeanRefUtil<T extends MaintainableSuperBean> {

    /**
     * For a set of maintainables, this method will return the maintainable that has the same agencyId, Id and version as the reference bean.
     * <p>
     * If the ref bean does not have a agency id, id and then it will result in an error.  If version information is missing, then the latest version
     * will be assumed.
     * <p>
     * This method will stop at the first match and return it, no checks are performed on the type of maintainables in the set.
     * <p>
     * If no match is found null is returned.
     *
     * @param maintainables the maintainables
     * @param ref           the ref
     * @return maintainable super bean
     */
    public static MaintainableSuperBean resolveReference(Collection<? extends MaintainableSuperBean> maintainables, MaintainableRefBean ref) {
        if (ref == null) {
            throw new IllegalArgumentException("Ref is null");
        }
        if (!ref.hasAgencyId()) {
            throw new IllegalArgumentException("Ref is mising AgencyId");
        }
        if (!ref.hasMaintainableId()) {
            throw new IllegalArgumentException("Ref is mising Id");
        }

        MaintainableSuperBean latestVersion = null;
        if (maintainables != null) {
            for (MaintainableSuperBean currentMaintainable : maintainables) {
                if (currentMaintainable.getAgencyId().equals(ref.getAgencyId())) {
                    if (currentMaintainable.getId().equals(ref.getMaintainableId())) {
                        if (!ref.hasVersion()) {
                            if (latestVersion == null || VersionableUtil.isHigherVersion(currentMaintainable.getVersion(), latestVersion.getVersion())) {
                                latestVersion = currentMaintainable;
                            }
                        } else if (currentMaintainable.getVersion().equals(ref.getVersion())) {
                            return currentMaintainable;
                        }
                    }
                }
            }
        }
        return latestVersion;
    }

    /**
     * Resolve references set.
     *
     * @param maintainables the maintainables
     * @param ref           the ref
     * @return the set
     */
    public Set<T> resolveReferences(Collection<T> maintainables, MaintainableRefBean ref) {
        Set<T> returnSet = new HashSet<T>();

        if (ref == null) {
            ref = new MaintainableRefBeanImpl();
        }
        boolean hasAgencyFilter = ObjectUtil.validString(ref.getAgencyId());
        boolean hasIdFilter = ObjectUtil.validString(ref.getMaintainableId());
        boolean hasVersionFilter = ObjectUtil.validString(ref.getVersion());

        String agencyId = ref.getAgencyId();
        String id = ref.getMaintainableId();
        String version = ref.getVersion();

        if (maintainables != null) {
            for (T currentMaintainable : maintainables) {
                if (hasAgencyFilter && !agencyId.equals(currentMaintainable.getAgencyId())) {
                    continue;
                }
                if (hasIdFilter && !id.equals(currentMaintainable.getId())) {
                    continue;
                }
                if (hasVersionFilter && !version.equals(currentMaintainable.getVersion())) {
                    continue;
                }
                returnSet.add(currentMaintainable);
            }
        }
        return returnSet;
    }
}
