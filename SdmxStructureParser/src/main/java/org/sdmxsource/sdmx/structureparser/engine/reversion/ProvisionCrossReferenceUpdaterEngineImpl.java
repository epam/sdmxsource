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
package org.sdmxsource.sdmx.structureparser.engine.reversion;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.ProvisionAgreementMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.ProvisionCrossReferenceUpdaterEngine;

import java.util.Map;


/**
 * The type Provision cross reference updater engine.
 */
public class ProvisionCrossReferenceUpdaterEngineImpl implements ProvisionCrossReferenceUpdaterEngine {

    @Override
    public ProvisionAgreementBean updateReferences(ProvisionAgreementBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        ProvisionAgreementMutableBean provision = maintianable.getMutableInstance();
        provision.setVersion(newVersionNumber);

        StructureReferenceBean newTarget = updateReferences.get(provision.getDataproviderRef());
        if (newTarget != null) {
            provision.setDataproviderRef(newTarget);
        }
        newTarget = updateReferences.get(provision.getStructureUsage());
        if (newTarget != null) {
            provision.setStructureUsage(newTarget);
        }
        return provision.getImmutableInstance();
    }

}
