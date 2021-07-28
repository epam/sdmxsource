package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTRegistrationQuery;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Rest registration query.
 */
public class RESTRegistrationQueryImpl implements RESTRegistrationQuery {
    private static final Set<String> supportedTypes;

    static {
        supportedTypes = new HashSet<String>();
        addSupportedType(SDMX_STRUCTURE_TYPE.DATA_PROVIDER);
        addSupportedType(SDMX_STRUCTURE_TYPE.DSD);
        addSupportedType(SDMX_STRUCTURE_TYPE.DATAFLOW);
        addSupportedType(SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
        addSupportedType(SDMX_STRUCTURE_TYPE.REGISTRATION);
    }

    private StructureReferenceBean structureReferenceBean;
    private SdmxDate updatedBeforeDate;
    private SdmxDate updatedAfterDate;

    /**
     * Constructed from REST arguments
     *
     * @param queryString     expects                        [provision|dataflow|datastructure|dataprovider]/agencyId/Id/Version/?startPeriod=2012&amp;endPeriod=2013
     * @param queryParameters the query parameters
     */
    public RESTRegistrationQueryImpl(String[] queryString, Map<String, String> queryParameters) {
        parserQueryString(queryString);
        parserQueryParameters(queryParameters);
    }

    /**
     * Instantiates a new Rest registration query.
     *
     * @param structureReferenceBean the structure reference bean
     * @param updatedBeforeDate      the updated before date
     * @param updatedAfterDate       the updated after date
     */
    public RESTRegistrationQueryImpl(StructureReferenceBean structureReferenceBean, SdmxDate updatedBeforeDate, SdmxDate updatedAfterDate) {
//		Assert.notNull(structureReferenceBean, "Structure Reference can not be null");
//		Assert.notNull(structureReferenceBean.getTargetReference(), "Structure Reference, Structure Type can not be null");

        this.structureReferenceBean = structureReferenceBean;
        this.updatedBeforeDate = updatedBeforeDate;
        this.updatedAfterDate = updatedAfterDate;
    }

    private static void addSupportedType(SDMX_STRUCTURE_TYPE type) {
        supportedTypes.add(type.getUrnClass().toLowerCase());
    }

    private void parserQueryString(String[] queryString) {
        if (queryString.length < 2) {
            throw new SdmxSemmanticException("Registration Query expected query by structure type as second argument, supported values are: " + supportedTypes);
        }
        if (!supportedTypes.contains(queryString[1].toLowerCase())) {
            throw new SdmxSemmanticException(queryString[1] + " is not a supported type, supported types are " + supportedTypes);
        }

        SDMX_STRUCTURE_TYPE structureType = SDMX_STRUCTURE_TYPE.parseClass(queryString[1]);

        String agencyId = null;
        String id = null;
        String version = null;
        String identifiableId = null;

        if (queryString.length > 2) {
            agencyId = queryString[2];
        }
        if (queryString.length > 3) {
            if (structureType == SDMX_STRUCTURE_TYPE.DATA_PROVIDER) {
                identifiableId = queryString[3];
            } else {
                id = queryString[3];
            }
        }
        if (queryString.length > 4) {
            version = queryString[4];
        }
        if (queryString.length > 5) {
            throw new SdmxSemmanticException("Unexpected 5th argument '" + queryString[5] + "' ");
        }
        structureReferenceBean = new StructureReferenceBeanImpl(agencyId, id, version, structureType, identifiableId);
    }

    private void parserQueryParameters(Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                if (key.equalsIgnoreCase("updatedBeforeDate")) {
                    setUpdatedBeforeDate(params.get(key));
                } else if (key.equalsIgnoreCase("updatedAfterDate")) {
                    setUpdatedAfterDate(params.get(key));
                } else {
                    throw new SdmxSemmanticException("Unknown query parameter  '" + key +
                            "' allowed parameters [updatedBeforeDate, updatedAfterDate]");
                }
            }
        }
    }

    private void setUpdatedBeforeDate(String updatedBeforeDate) {
        try {
            this.updatedBeforeDate = new SdmxDateImpl(updatedBeforeDate);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Could not format 'updatedBeforeDate' value " + updatedBeforeDate + " as a date");
        }
    }

    private void setUpdatedAfterDate(String updatedAfterDate) {
        try {
            this.updatedAfterDate = new SdmxDateImpl(updatedAfterDate);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Could not format 'updatedAfterDate' value " + updatedAfterDate + " as a date");
        }
    }

    @Override
    public StructureReferenceBean getReference() {
        return structureReferenceBean;
    }

    @Override
    public SdmxDate getUpdatedBefore() {
        return updatedBeforeDate;
    }

    @Override
    public SdmxDate getUpdatedAfter() {
        return updatedAfterDate;
    }
}
