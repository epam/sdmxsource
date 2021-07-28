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
package org.sdmxsource.sdmx.structureretrieval.engine;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.ProvisionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

import java.util.Map;
import java.util.Set;


/**
 * The purpose of the CrossReferenceResolverEngine is to resolve cross references for beans, either MaintainableBeans,
 * beans within a SdmxBeans container, ProvisionBeans, and RegistrationBeans.
 */
public interface CrossReferenceResolverEngine {

    /**
     * Returns a Map of Identifiable Beans alongside any cross references they declare that could not be found in the set of SdmxBeans provided, and the SdmxBeanRetrievalManager (if given).
     *
     * <b>NOTE : </b>An empty Map is returned if all cross references are present.
     *
     * @param beans                        - the beans to return the Map of missing references for
     * @param numberLevelsDeep             - the number of times to recurse down the reference stack.  An example is a dataflow directly references a DSD (numberLevelsDeep=1), the DSD                         may in turn reference a Codelist (numberLevelsDeep=2).  The numberLevelsDeep argument can be a positive integer specifying how deeply to resolve the                         references, an argument of 0 (zero) implies there is no limit, and the resolver engine will continue recursing until it has found every directly and indirectly referenced
     * @param identifiableRetrievalManager the identifiable retrieval manager
     * @return Map of IdentifiableBean vs Set of CrossReferences that could not be resolved for the IdentifiableBean - an empty Map is returned if all cross references are present
     */
    Map<IdentifiableBean, Set<CrossReferenceBean>> getMissingCrossReferences(SdmxBeans beans, int numberLevelsDeep, IdentifiableRetrievalManager identifiableRetrievalManager);

    /**
     * For the included beans, returns a map of agency URN to maintainable Bean that references the agency
     *
     * @param beans                        the beans
     * @param identifiableRetrievalManager the identifiable retrieval manager
     * @return missing agencies
     */
    Map<String, Set<MaintainableBean>> getMissingAgencies(SdmxBeans beans, IdentifiableRetrievalManager identifiableRetrievalManager);

    /**
     * Resolves all references and returns a Map containing all the input beans and the beans that are cross referenced,
     * the Map's keyset contains the Identifiable that is the referencer and the Map's value collection contains the referenced artifacts.
     *
     * @param beans                        - the SdmxBeans container, containing all the beans to check references for
     * @param resolveAgencies              - if true the resolver engine will also attempt to resolve referenced agencies
     * @param numberLevelsDeep             - the number of times to recurse down the reference stack.  An example is a dataflow directly references a DSD (numberLevelsDeep=1), the DSD                         may in turn reference a Codelist (numberLevelsDeep=2).  The numberLevelsDeep argument can be a positive integer specifying how deeply to resolve the                         references, an argument of 0 (zero) implies there is no limit, and the resolver engine will continue recursing until it has found every directly and indirectly referenced                         artifact.  Note that there is no risk of infinite recursion in calling this.
     * @param identifiableRetrievalManager the identifiable retrieval manager
     * @return Map of referencers vs referencees
     * @throws CrossReferenceException - if any of the references could not be resolved
     */
    Map<IdentifiableBean, Set<IdentifiableBean>> resolveReferences(SdmxBeans beans,
                                                                   boolean resolveAgencies,
                                                                   int numberLevelsDeep,
                                                                   IdentifiableRetrievalManager identifiableRetrievalManager) throws CrossReferenceException;

    /**
     * Returns a set of IdentifiableBeans that the MaintainableBean cross references
     *
     * @param bean                         the bean
     * @param resolveAgencies              - if true will also resolve the agencies
     * @param numberLevelsDeep             - the number of times to recurse down the reference stack.  An example is a dataflow directly references a DSD (numberLevelsDeep=1), the DSD                         may in turn reference a Codelist (numberLevelsDeep=2).  The numberLevelsDeep argument can be a positive integer specifying how deeply to resolve the                         references, an argument of 0 (zero) implies there is no limit, and the resolver engine will continue recursing until it has found every directly and indirectly referenced                         artifact.  Note that there is no risk of infinite recursion in calling this.
     * @param identifiableRetrievalManager the identifiable retrieval manager
     * @return the set
     * @throws CrossReferenceException - if any of the references could not be resolved
     */
    Set<IdentifiableBean> resolveReferences(MaintainableBean bean,
                                            boolean resolveAgencies,
                                            int numberLevelsDeep,
                                            IdentifiableRetrievalManager identifiableRetrievalManager) throws CrossReferenceException;

    /**
     * Returns a set of IdentifiableBeans that are directly referenced from this registration
     *
     * @param registation          - the registration to resolve the references for
     * @param provRetrievalManager - Used to resolve the provision references.  Can be null if registration is not linked to a provision
     * @return set set
     */
    Set<IdentifiableBean> resolveReferences(RegistrationBean registation, ProvisionBeanRetrievalManager provRetrievalManager);

    /**
     * Returns a set of structures that are directly referenced from this provision
     *
     * @param provision                    - the provision to resolve the references for
     * @param identifiableRetrievalManager - must not be null as this will be used to resolve the references
     * @return set set
     */
    Set<IdentifiableBean> resolveReferences(ProvisionAgreementBean provision, IdentifiableRetrievalManager identifiableRetrievalManager);

    /**
     * Resolves a reference from a cross reference bean
     *
     * @param crossReference               the cross reference
     * @param identifiableRetrievalManager the identifiable retrieval manager
     * @return identifiable bean
     * @throws CrossReferenceException the cross reference exception
     */
    IdentifiableBean resolveCrossReference(CrossReferenceBean crossReference, IdentifiableRetrievalManager identifiableRetrievalManager) throws CrossReferenceException;
}
