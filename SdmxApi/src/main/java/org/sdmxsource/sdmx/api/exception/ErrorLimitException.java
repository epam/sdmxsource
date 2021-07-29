package org.sdmxsource.sdmx.api.exception;


/**
 * Throws when exception handlers have their exception limit hit.
 */
public class ErrorLimitException extends SdmxException {
    private static final long serialVersionUID = -7510893732511873219L;

    public ErrorLimitException(int limit) {
        super("Error Limit of '" + limit + "' hit");
    }
}
