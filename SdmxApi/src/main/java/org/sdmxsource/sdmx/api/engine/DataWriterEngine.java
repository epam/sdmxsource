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
package org.sdmxsource.sdmx.api.engine;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;

import java.util.Date;
import java.util.List;


/**
 * A DataWriterEngine has the ability to write data to a given location in a given format based on the calls to the methods provided.
 * <p>
 * The Data Format that is written out depends on the implementation of the DataWriter.
 * <p>
 * The output location also depends on the implementation of the DataWriter
 *
 * @author Matt Nelson
 */
public interface DataWriterEngine extends WriterEngine {

    /**
     * Starts a dataset with the data conforming to the DSD.
     *
     * @param provision         agreement (optional)   The provision agreement can be provided to give extra information about the dataset
     * @param dataflow          (optional) The dataflow can be provided to give extra information about the dataset
     * @param dataStructureBean (mandatory) the data structure is used to know the dimenisonality of the data
     * @param header            (optional) containing, amongst others, the dataset action, reporting dates, dimension at observation if null then the dimension at observation is  assumed to be TIME_PERIOD and the dataset action is assumed to be INFORMATION
     * @param annotations       (optional) any additional annotations that are attached to the dataset, can be null if no annotations exist
     * @throws IllegalArgumentException if the dataStructureBean is null
     */
    void startDataset(ProvisionAgreementBean provision, DataflowBean dataflow, DataStructureBean dataStructureBean, DatasetHeaderBean header, AnnotationBean... annotations);


    /**
     * Starts a group with the given id, the subsequent calls to <code>writeGroupKeyValue</code> will write the id/values to this group.  After
     * the group key is complete <code>writeAttributeValue</code> may be called to add attributes to this group.
     * <p>
     * <b>Example Usage</b>
     * A group 'demo' is made up of 3 concepts (Country/Sex/Status), and has an attribute (Collection).
     * <code>
     * DataWriterEngine dre = //Create instance
     * dre.startGroup("demo");
     * dre.writeGroupKeyValue("Country", "FR");
     * dre.writeGroupKeyValue("Sex", "M");
     * dre.writeGroupKeyValue("Status", "U");
     * dre.writeAttributeValue("Collection", "A");
     * </code>
     * Any subsequent calls, for example to start a series, or to start a new group, will close off this existing group.
     *
     * @param groupId     the id of the group
     * @param annotations any additional annotations that are attached to the group, can be null if no annotations exist
     */
    void startGroup(String groupId, AnnotationBean... annotations);

    /**
     * Writes a group key value, for example 'Country' is 'France'.  A group may contain multiple id/value pairs in the key, so this method may be called consecutively with an id / value for each key item.
     * <p>
     * If this method is called consecutively multiple times and a duplicate id is passed in, then an exception will be thrown, as a group can only declare one value for a given id.
     * <p>
     * The <code>startGroup</code> method must be called before calling this method to add the first id/value, as the WriterEngine needs to know what group to assign the id/values to.
     *
     * @param id    the id of the concept or dimension
     * @param value the value of the group key
     */
    void writeGroupKeyValue(String id, String value);

    /**
     * Starts a new series, closes off any existing series keys or attribute/observations.
     *
     * @param annotations any additional annotations that are attached to the series, can be null if no annotations exist
     */
    void startSeries(AnnotationBean... annotations);

    /**
     * Writes a series key value.  This will have the effect of closing off any observation, or attribute values if they are any present
     * <p>
     * If this method is called after calling writeAttribute or writeObservation, then the engine will start a new series.
     *
     * @param id    the id of the value for example 'Country'
     * @param value the value for the given id for example 'FR'
     */
    void writeSeriesKeyValue(String id, String value);

    /**
     * Writes an attribute value for the given id.
     * <p>
     * If this method is called immediately after a <code>writeSeriesKeyValue</code> method call then it will write
     * the attribute at the series level.  If it is called after a <code>writeGroupKeyValue</code> it will write the attribute against the group.
     * <p>
     * If this method is called immediately after a <code>writeObservation</code> method call then it will write the attribute at the observation level.
     *
     * @param id    the id of the given value for example 'OBS_STATUS'
     * @param value the value for the given id for example 'M'
     */
    void writeAttributeValue(String id, String value);

    /**
     * Writes an observation, the observation concept is assumed to be that which has been defined to be at the observation level (as declared in the start dataset method DatasetHeaderBean).
     *
     * @param obsConceptValue may be the observation time, or the cross section value
     * @param obsValue        the observation value - can be numerical
     * @param annotations     any additional annotations that are attached to the observation, can be null if no annotations exist
     * @throws IllegalArgumentException if the Observation at dimension is AllDimensions (flat dataset)
     */
    void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations);

    /**
     * Writes an Observation value against the current series.
     * <p>
     * The current series is determined by the latest writeKeyValue call,
     * If this is a cross sectional dataset, then the obsConcept is expected to be the cross sectional concept value - for example if this is cross sectional on Country the id may be "FR"
     * If this is a time series dataset then the obsConcept is expected to be the observation time - for example 2006-12-12
     *
     * @param observationConceptId the concept id for the observation, for example 'COUNTRY'.  If this is a Time Series, then the id will be DimensionBean.TIME_DIMENSION_FIXED_ID.
     * @param obsConceptValue      the obs concept value
     * @param obsValue             the observation value - can be numerical
     * @param annotations          any additional annotations that are attached to the observation, can be null if no annotations exist
     */
    void writeObservation(String observationConceptId, String obsConceptValue, String obsValue, AnnotationBean... annotations);

    /**
     * Writes an Observation value against the current series.
     * <p>
     * The date is formatted as a string, the format rules are determined by the TIME_FORMAT argument.
     * <p>
     * Validates the following:
     * <ul>
     *   <li>The obsTime does not replicate a previously reported obsTime for the current series</li>
     * </ul>
     *
     * @param obsTime        the time of the observation
     * @param obsValue       the observation value - can be numerical
     * @param sdmxTimeFormat the time format to format the obsTime in when converting to a string
     * @param annotations    the annotations
     */
    void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations);

    /**
     * Writes the footer message (if supported by the writer implementation), and then completes the XML document, closes off all the tags, closes any resources.
     * <b>NOTE:</b> It is very important to close off a completed DataWriterEngine, as this ensures any output is written to the given location, and any resources are closed.
     * If this call is not made, the output document may be incomplete.
     * <p>
     * <b>NOTE:</b> If writing a footer is not supported, then the footer will be silently ignored
     * <p>
     * Close also validates that the last series key or group key has been completed.
     *
     * @param footer the footer
     */
    public void close(FooterMessage... footer);


    /**
     * Provides extra detail on close, used to inform users of things such as truncated messages, or errors while processing.
     */
    interface FooterMessage {
        /**
         * Mandatory Field - use to describe the error/warning
         *
         * @return code code
         */
        String getCode();

        /**
         * Optional - describes severity of problem
         *
         * @return severity severity
         */
        SEVERITY getSeverity();

        /**
         * Any text associated with the footer, there must be at least one text message
         *
         * @return footer text
         */
        List<TextTypeWrapper> getFooterText();

        /**
         * The enum Severity.
         */
        enum SEVERITY {
            /**
             * Error severity.
             */
            ERROR("Error"),
            /**
             * Information severity.
             */
            INFORMATION("Infomation"),
            /**
             * Warning severity.
             */
            WARNING("Warning");

            private String stringValue;

            private SEVERITY(String stringValue) {
                this.stringValue = stringValue;
            }

            @Override
            public String toString() {
                return stringValue;
            }
        }
    }
}
