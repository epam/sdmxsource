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
package org.sdmxsource.sdmx.structureparser.manager.impl;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.manager.parse.StructureVersionIncrementManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.StructureVersionRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferencingRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.CrossReferenceReversionEngine;
import org.sdmxsource.sdmx.structureparser.engine.reversion.CrossReferenceReversionEngineImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.VersionableUtil;

import java.util.*;


/**
 * The type Structure version increment manager.
 */
public class StructureVersionIncrementManagerImpl implements StructureVersionIncrementManager {
    private final CrossReferenceReversionEngine crossReferenceReversionEngine;
    private final StructureVersionRetrievalManager structureVersionRetrievalManager;
    private Logger LOG = Logger.getLogger(StructureVersionIncrementManagerImpl.class);
    private CrossReferencingRetrievalManager crossReferencingRetrievalManager;
    private SdmxBeanRetrievalManager beanRetrievalManager;

    /**
     * Instantiates a new Structure version increment manager.
     *
     * @param crossReferenceReversionEngine    the cross reference reversion engine
     * @param structureVersionRetrievalManager the structure version retrieval manager
     */
    public StructureVersionIncrementManagerImpl(
            final CrossReferenceReversionEngine crossReferenceReversionEngine,
            final StructureVersionRetrievalManager structureVersionRetrievalManager) {
        this.crossReferenceReversionEngine = crossReferenceReversionEngine != null ? crossReferenceReversionEngine : new CrossReferenceReversionEngineImpl();
        this.structureVersionRetrievalManager = structureVersionRetrievalManager;
    }


    @Override
    public void incrementVersions(SdmxBeans beans) {
        LOG.info("Update Versions of Structures if existing structures found");
        //Store a map of old versions vs the new version
        Map<StructureReferenceBean, StructureReferenceBean> oldVsNew = new HashMap<StructureReferenceBean, StructureReferenceBean>();
        Map<MaintainableBean, MaintainableBean> oldMaintVsNew = new HashMap<MaintainableBean, MaintainableBean>();
        Set<MaintainableBean> updatedMaintainables = new HashSet<MaintainableBean>();
        Set<MaintainableBean> oldMaintainables = new HashSet<MaintainableBean>();

        for (MaintainableBean currentMaint : beans.getAllMaintainables()) {
            LOG.debug("Auto Version - check latest version for maintainable: " + currentMaint);

            MaintainableBean persistedMaintainable = structureVersionRetrievalManager.getLatest(currentMaint);
            if (persistedMaintainable == null) {
                persistedMaintainable = beanRetrievalManager.getMaintainableBean(currentMaint.asReference());
            }

            if (persistedMaintainable != null) {
                if (VersionableUtil.isHigherVersion(persistedMaintainable.getVersion(), currentMaint.getVersion())) {
                    //Modify version of maintainable to be the same as persisted maintainable
                    MaintainableMutableBean mutableInstance = currentMaint.getMutableInstance();
                    mutableInstance.setVersion(persistedMaintainable.getVersion());

                    //Remove the Maintainable from the submission - as we've changed the versions
                    beans.removeMaintainable(currentMaint);

                    currentMaint = mutableInstance.getImmutableInstance();
                }
                if (persistedMaintainable.getVersion().equals(currentMaint.getVersion())) {
                    LOG.debug("Latest version is '" + persistedMaintainable.getVersion() + "' perform update checks");
                    if (!currentMaint.deepEquals(persistedMaintainable, true)) {

                        Set<IdentifiableBean> allIdentifiables1 = currentMaint.getIdentifiableComposites();
                        Set<IdentifiableBean> allIdentifiables2 = persistedMaintainable.getIdentifiableComposites();

                        boolean containsAll = allIdentifiables1.containsAll(allIdentifiables2) && allIdentifiables2.containsAll(allIdentifiables1);
                        if (LOG.isInfoEnabled()) {
                            String increment = containsAll ? "Minor" : "Major";
                            LOG.info("Perform " + increment + " Version Increment for structure:" + currentMaint.getUrn());
                        }

                        //Increment the version number
                        MaintainableBean newVersion = incrmentVersion(currentMaint, persistedMaintainable.getVersion(), !containsAll);

                        //Remove the Maintainable from the submission
                        beans.removeMaintainable(currentMaint);

                        //Store the newly updated maintainable in a container for further processing
                        updatedMaintainables.add(newVersion);
                        oldMaintainables.add(currentMaint);
                        //Store the old version number mappings to the new version number
                        oldMaintVsNew.put(currentMaint, newVersion);
                        oldVsNew.put(currentMaint.asReference(), newVersion.asReference());

                        String oldVersionNumber = currentMaint.getVersion();
                        addOldVsNewReferences(oldVersionNumber, newVersion, oldVsNew);
                    }
                }
            }
        }


        //Create a set of parent beans to not update (regardless of version)
        Set<MaintainableBean> filterSet = new HashSet<MaintainableBean>(updatedMaintainables);
        filterSet.addAll(beans.getAllMaintainables());

        //Get all the referencing structures to reversion them
        List<MaintainableBean> referencingStructures = recurseUpTree(oldMaintainables, new HashSet<MaintainableBean>(), filterSet);

        for (MaintainableBean currentReferencingStructure : referencingStructures) {
            LOG.info("Perform Minor Version Increment on referencing structure:" + currentReferencingStructure);
            String newVersionNumber;
            if (oldMaintVsNew.containsKey(currentReferencingStructure)) {
                //The old maintainable is also in the submission and has had it's version number incremented, use this version
                currentReferencingStructure = oldMaintVsNew.get(currentReferencingStructure);
                updatedMaintainables.remove(currentReferencingStructure);
                newVersionNumber = currentReferencingStructure.getVersion();
            } else {
                newVersionNumber = VersionableUtil.incrementVersion(currentReferencingStructure.getVersion(), false);
            }
            MaintainableBean updatedMaintainable = crossReferenceReversionEngine.udpateReferences(currentReferencingStructure, oldVsNew, newVersionNumber);
            addOldVsNewReferences(currentReferencingStructure.getVersion(), updatedMaintainable, oldVsNew);

            updatedMaintainables.add(updatedMaintainable);
        }

        for (MaintainableBean currentReferencingStructure : updatedMaintainables) {
            MaintainableBean updatedMaintainable = crossReferenceReversionEngine.udpateReferences(currentReferencingStructure, oldVsNew, currentReferencingStructure.getVersion());
            beans.addIdentifiable(updatedMaintainable);
        }

        //Update the references of any structures that existed in the submission
        for (MaintainableBean currentReferencingStructure : beans.getAllMaintainables()) {
            MaintainableBean updatedMaintainable = crossReferenceReversionEngine.udpateReferences(currentReferencingStructure, oldVsNew, currentReferencingStructure.getVersion());
            beans.addIdentifiable(updatedMaintainable);
        }
    }

    /**
     * Recurse all the way up the tree until all referenced structures are found - in the order in which they are found
     *
     * @param beans
     * @param oldVsNew
     * @param ignoreParents
     * @return
     */
    private List<MaintainableBean> recurseUpTree(Collection<MaintainableBean> getParentsFor,
                                                 Set<MaintainableBean> ignoreParents,
                                                 Set<MaintainableBean> filterSet) {
        List<MaintainableBean> crossReferencingStructures = new ArrayList<MaintainableBean>();

        for (MaintainableBean oldBean : getParentsFor) {
            crossReferencingStructures.addAll(crossReferencingRetrievalManager.getCrossReferencingStructures(oldBean.asReference(), false));
        }
        //Filter out the parents we do not want to reversion
        crossReferencingStructures.removeAll(ignoreParents);
        filterReferencingStructures(crossReferencingStructures, filterSet);

        ignoreParents.addAll(crossReferencingStructures);

        if (crossReferencingStructures.size() > 0) {
            Collection<MaintainableBean> ancestors = recurseUpTree(crossReferencingStructures, ignoreParents, filterSet);
            for (MaintainableBean currentAncestor : ancestors) {
                if (!crossReferencingStructures.contains(currentAncestor)) {
                    crossReferencingStructures.addAll(ancestors);
                }
            }
        }
        return crossReferencingStructures;
    }

    private void filterReferencingStructures(Collection<MaintainableBean> refereningStructures, Set<MaintainableBean> alreadyReversionedMaintainables) {
        Set<MaintainableBean> removeSet = new HashSet<MaintainableBean>();
        for (MaintainableBean currentReference : refereningStructures) {
            for (MaintainableBean alreadyReversionedReference : alreadyReversionedMaintainables) {
                if (currentReference.getStructureType() == alreadyReversionedReference.getStructureType()) {
                    if (currentReference.getAgencyId().equals(alreadyReversionedReference.getAgencyId())) {
                        if (currentReference.getId().equals(alreadyReversionedReference.getId())) {
                            removeSet.add(currentReference);
                        }
                    }
                }
            }
        }
        refereningStructures.removeAll(removeSet);
    }

    private void addOldVsNewReferences(String oldVersionNumber, MaintainableBean newVersion, Map<StructureReferenceBean, StructureReferenceBean> oldVsNew) {
        addOldVsNewReferencesToMap(oldVersionNumber, newVersion, oldVsNew);
        for (IdentifiableBean composite : newVersion.getIdentifiableComposites()) {
            addOldVsNewReferencesToMap(oldVersionNumber, composite, oldVsNew);
        }
    }

    private void addOldVsNewReferencesToMap(String oldVersionNumber, IdentifiableBean newVersion, Map<StructureReferenceBean, StructureReferenceBean> oldVsNew) {
        StructureReferenceBean asReference = newVersion.asReference();
        MaintainableRefBean mRef = asReference.getMaintainableReference();
        StructureReferenceBean oldReference = new StructureReferenceBeanImpl(mRef.getAgencyId(), mRef.getMaintainableId(), oldVersionNumber, asReference.getTargetReference(), asReference.getIdentifiableIds());
        oldVsNew.put(oldReference, asReference);
    }


    /**
     * Increments the version of the old maintainable
     *
     * @param beans
     * @param updatedMaintainables
     * @param currentMaint
     * @param majorIncrement
     * @return
     */
    private MaintainableBean incrmentVersion(MaintainableBean currentMaint, String incrementFromVersion, boolean majorIncrement) {
        MaintainableMutableBean mutable = currentMaint.getMutableInstance();
        String newVersion = VersionableUtil.incrementVersion(incrementFromVersion, majorIncrement);
        mutable.setVersion(newVersion);

        MaintainableBean newMaint = mutable.getImmutableInstance();
        return newMaint;
    }

    /**
     * Sets bean retrieval manager.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public void setBeanRetrievalManager(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }

    /**
     * Sets cross referencing retrieval manager.
     *
     * @param crossReferencingRetrievalManager the cross referencing retrieval manager
     */
    public void setCrossReferencingRetrievalManager(CrossReferencingRetrievalManager crossReferencingRetrievalManager) {
        this.crossReferencingRetrievalManager = crossReferencingRetrievalManager;
    }
}
