package org.sdmxsource.sdmx.api.exception;

import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;


/**
 * The type Data set structure reference exception.
 */
public class DataSetStructureReferenceException extends SdmxException {
    private static final long serialVersionUID = 1L;
    private StructureReferenceBean referenceTo;

    /**
     * Instantiates a new Data set structure reference exception.
     *
     * @param referenceTo the reference to
     */
    public DataSetStructureReferenceException(StructureReferenceBean referenceTo) {
        super(getErrorString(referenceTo));
        this.referenceTo = referenceTo;
    }

    /**
     * Gets error string.
     *
     * @param referenceTo the reference to
     * @return the error string
     */
    protected static String getErrorString(StructureReferenceBean referenceTo) {
        if (referenceTo == null) {
            return "The Dataset can not be read as it does not make reference to any Structure (DSD, Flow, or Provision)";
        }
        return "Can not resolve reference from dataset to structure '" + referenceTo + "'";
    }

    /**
     * Returns the reference that could not be resolved, or null if there was no reference to a structure from the dataset
     *
     * @return reference to
     */
    public StructureReferenceBean getReferenceTo() {
        return referenceTo;
    }

    /**
     * Has reference boolean.
     *
     * @return the boolean
     */
    public boolean hasReference() {
        return referenceTo != null;
    }
}
