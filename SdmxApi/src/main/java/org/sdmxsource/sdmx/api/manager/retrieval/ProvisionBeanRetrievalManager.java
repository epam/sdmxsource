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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Set;


/**
 * Manages the retrieval of provision agreements using simple reference of structures that directly reference a provision
 *
 * @author Matt Nelson
 */
public interface ProvisionBeanRetrievalManager {


    /**
     * Returns all the provisions in the system
     *
     * @return provisions
     */
    Set<ProvisionAgreementBean> getProvisions();

    /**
     * Returns a list of provisions that match the structure reference.
     * <p>
     * The structure reference can either be referencing a Provision structure, a Data or MetdataFlow, or a DataProvider.
     *
     * @param structureRef the structure ref
     * @return provisions
     */
    Set<ProvisionAgreementBean> getProvisions(StructureReferenceBean structureRef);

    /**
     * Returns the provision agreement that the registration is referencing
     *
     * @param registration the registration
     * @return provision
     */
    ProvisionAgreementBean getProvision(RegistrationBean registration);

    /**
     * Returns all the provision Agreements that are referencing the given dataflow
     *
     * @param dataflow the dataflow
     * @return provisions
     */
    Set<ProvisionAgreementBean> getProvisions(DataflowBean dataflow);

    /**
     * Returns all the provision Agreements that are referencing the given metadataflow
     *
     * @param metadataflow the metadataflow
     * @return provisions
     */
    Set<ProvisionAgreementBean> getProvisions(MetadataFlowBean metadataflow);


}
