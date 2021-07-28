package org.sdmxsource.sdmx.dataparser.model.error;

import org.sdmxsource.sdmx.api.exception.ErrorLimitException;
import org.sdmxsource.sdmx.api.exception.ExceptionHandler;

import java.util.*;

/**
 * The type Categorising error handler.
 */
public class CategorisingErrorHandler implements ExceptionHandler {
    private static final String UNCATEGORISED = "General Errors";
    private Map<String, ErrorCategory> errors = new HashMap<String, CategorisingErrorHandler.ErrorCategory>();
    private int limit = -1;

    /**
     * Instantiates a new Categorising error handler.
     *
     * @param limit the limit
     */
    public CategorisingErrorHandler(int limit) {
        this.limit = limit;
    }


    @Override
    public void handleException(Throwable ex) throws RuntimeException {
        StackTraceElement[] st = null;
        if (ex.getCause() != null) {
            st = ex.getCause().getStackTrace();
        } else {
            st = ex.getStackTrace();
        }
        String category = UNCATEGORISED;
        if (st != null && st.length > 0) {
            category = st[0].getClassName();
        }
        ErrorCategory errorCategory = errors.get(category);
        if (errorCategory == null) {
            errorCategory = new ErrorCategory(category);
            errors.put(category, errorCategory);
        }
        errorCategory.addException(ex);
    }

    /**
     * Returns a map of error category to a set of errors
     *
     * @return errors errors
     */
    public Map<String, Set<Error>> getErrors() {
        Map<String, Set<Error>> returnMap = new HashMap<String, Set<Error>>();
        for (String errorCatKey : errors.keySet()) {
            ErrorCategory errorCat = errors.get(errorCatKey);
            returnMap.put(errorCat.name, errorCat.errors);
        }
        return returnMap;
    }

    /**
     * The type Error.
     */
    public class Error {
        private List<String> errorDetails = new ArrayList<String>();

        private Error(Throwable ex) {
            while (ex != null) {
                String errorMessageStr = ex.getMessage();
                if (errorMessageStr == null) {
                    errorDetails.add("Null Pointer");
                } else {
                    for (String currentError : errorMessageStr.split(System.getProperty("line.separator"))) {
                        errorDetails.add(currentError);
                    }
                }
                ex = ex.getCause();
            }
        }

        /**
         * Gets error details.
         *
         * @return the error details
         */
        public List<String> getErrorDetails() {
            return errorDetails;
        }
    }


    /**
     * The type Error category.
     */
    public class ErrorCategory {
        private String name;
        private Set<Error> errors = new HashSet<Error>();
        private boolean hitLimit = false;

        /**
         * Instantiates a new Error category.
         *
         * @param name the name
         */
        public ErrorCategory(String name) {
            this.name = name;
        }

        private void addException(Throwable ex) {
            if (hitLimit) {
                throw new ErrorLimitException(limit);
            }
            int count = errors.size() + 1;
            if (limit > 0 && count > limit) {
                hitLimit = true;
                return;
            }
            this.errors.add(new Error(ex));
        }
    }
}
