package org.sdmxsource.sdmx.dataparser.model;

import java.util.Date;

/**
 * Describes options for data transformation
 */
public class TransformOptions {
    private boolean includeObs;
    private boolean includeAttributes;
    private boolean includeHeader;
    private String pivotDimension;
    private Integer maxObs;
    private Date dateFrom;
    private Date dateTo;
    private boolean copyHeader;
    private boolean closeWriter;


    /**
     * Gets pivot dimension.
     *
     * @return the pivot dimension
     */
    public String getPivotDimension() {
        return pivotDimension;
    }

    /**
     * Sets pivot dimension.
     *
     * @param pivotDimension the pivot dimension
     */
    public void setPivotDimension(String pivotDimension) {
        this.pivotDimension = pivotDimension;
    }

    /**
     * Is include obs boolean.
     *
     * @return the boolean
     */
    public boolean isIncludeObs() {
        return includeObs;
    }

    /**
     * Sets include obs.
     *
     * @param includeObs the include obs
     */
    public void setIncludeObs(boolean includeObs) {
        this.includeObs = includeObs;
    }

    /**
     * Is include attributes boolean.
     *
     * @return the boolean
     */
    public boolean isIncludeAttributes() {
        return includeAttributes;
    }

    /**
     * Sets include attributes.
     *
     * @param includeAttributes the include attributes
     */
    public void setIncludeAttributes(boolean includeAttributes) {
        this.includeAttributes = includeAttributes;
    }

    /**
     * Is include header boolean.
     *
     * @return the boolean
     */
    public boolean isIncludeHeader() {
        return includeHeader;
    }

    /**
     * Sets include header.
     *
     * @param includeHeader the include header
     */
    public void setIncludeHeader(boolean includeHeader) {
        this.includeHeader = includeHeader;
    }

    /**
     * Gets max obs.
     *
     * @return the max obs
     */
    public Integer getMaxObs() {
        return maxObs;
    }

    /**
     * Sets max obs.
     *
     * @param maxObs the max obs
     */
    public void setMaxObs(Integer maxObs) {
        this.maxObs = maxObs;
    }

    /**
     * Gets date from.
     *
     * @return the date from
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets date from.
     *
     * @param dateFrom the date from
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Gets date to.
     *
     * @return the date to
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * Sets date to.
     *
     * @param dateTo the date to
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Is copy header boolean.
     *
     * @return the boolean
     */
    public boolean isCopyHeader() {
        return copyHeader;
    }

    /**
     * Sets copy header.
     *
     * @param copyHeader the copy header
     */
    public void setCopyHeader(boolean copyHeader) {
        this.copyHeader = copyHeader;
    }

    /**
     * Is close writer boolean.
     *
     * @return the boolean
     */
    public boolean isCloseWriter() {
        return closeWriter;
    }

    /**
     * Sets close writer.
     *
     * @param closeWriter the close writer
     */
    public void setCloseWriter(boolean closeWriter) {
        this.closeWriter = closeWriter;
    }
}
