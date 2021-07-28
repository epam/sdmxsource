package org.sdmxsource.sdmx.api.model.query;

import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

/**
 * A representation of the information that a registration(s) can be queried on via the REST Interface
 */
public interface RESTRegistrationQuery {

    /**
     * Returns the reference to structure that the returned registrations should be attached to, this can be:
     * <ul>
     *  <li>Data Structure</li>
     *  <li>Dataflow</li>
     *  <li>Provision Agreement</li>
     *  <li>Registration</li>
     *  <li>Data Provider</li>
     * </ul>
     *  A Registration is attached to a Provision Agreement, which in turn references a Dataflow and Data Provider,
     *  The Dataflow in turn attaches to a DataStructure.  So a query can be made at any level, and the registrations will be returned that
     *  directly or indirectly match the strucutre(s) as defined by this StructureReference filter.
     * <p>
     *  The StructureReference must contain at the minimum a StructureType all other feilds are optional
     *
     * @return reference
     */
    StructureReferenceBean getReference();

    /**
     * Returns the inclusive date to get Registrations before or null if undefined.
     * So if this value is set to 2013/12/31, then registrations that were last updated before this date will be returned.
     *
     * @return updated before
     */
    SdmxDate getUpdatedBefore();

    /**
     * Returns the inclusive date to get Registrations after or null if undefined.
     * So if this value is set to 2013/12/31, then registrations that were last updated after this date will be returned.
     *
     * @return updated after
     */
    SdmxDate getUpdatedAfter();
}