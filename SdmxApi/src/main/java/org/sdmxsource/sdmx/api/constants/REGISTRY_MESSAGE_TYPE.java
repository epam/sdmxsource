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
package org.sdmxsource.sdmx.api.constants;

/**
 * Contains a list of all the Registry Message Types as found under the RegistryInterfaceDocument XML node
 * <p>
 * This Enum offers the ability to retrieve the underlying <b>ARTIFACT_TYPE</b> and XML node names
 *
 * @author Matt Nelson
 * @see ARTIFACT_TYPE
 */
public enum REGISTRY_MESSAGE_TYPE {
    /**
     * Submit structure request registry message type.
     */
//REQUESTS
    SUBMIT_STRUCTURE_REQUEST(ARTIFACT_TYPE.STRUCTURE, "SubmitStructureRequest", null),
    /**
     * Submit provision request registry message type.
     */
    SUBMIT_PROVISION_REQUEST(ARTIFACT_TYPE.PROVISION, "SubmitProvisioningRequest", null),
    /**
     * Submit registration request registry message type.
     */
    SUBMIT_REGISTRATION_REQUEST(ARTIFACT_TYPE.REGISTRATION, "SubmitRegistrationRequest", "SubmitRegistrationsRequest"),
    /**
     * Submit subscription request registry message type.
     */
    SUBMIT_SUBSCRIPTION_REQUEST(ARTIFACT_TYPE.SUBSCRIPTION, "SubmitSubscriptionRequest", "SubmitSubscriptionsRequest"),

    /**
     * Query structure request registry message type.
     */
    QUERY_STRUCTURE_REQUEST(ARTIFACT_TYPE.STRUCTURE, "QueryStructureRequest", null),
    /**
     * Query provision request registry message type.
     */
    QUERY_PROVISION_REQUEST(ARTIFACT_TYPE.PROVISION, "QueryProvisioningRequest", null),
    /**
     * Query registration request registry message type.
     */
    QUERY_REGISTRATION_REQUEST(ARTIFACT_TYPE.REGISTRATION, "QueryRegistrationRequest", null),

    /**
     * Submit structure response registry message type.
     */
//RESPONSES
    SUBMIT_STRUCTURE_RESPONSE(ARTIFACT_TYPE.STRUCTURE, "SubmitStructureResponse", null),
    /**
     * Submit provision response registry message type.
     */
    SUBMIT_PROVISION_RESPONSE(ARTIFACT_TYPE.PROVISION, "SubmitProvisioningResponse", null),
    /**
     * Submit registration response registry message type.
     */
    SUBMIT_REGISTRATION_RESPONSE(ARTIFACT_TYPE.REGISTRATION, "SubmitRegistrationResponse", "SubmitRegistrationsResponse"),
    /**
     * Submit subscription response registry message type.
     */
    SUBMIT_SUBSCRIPTION_RESPONSE(ARTIFACT_TYPE.SUBSCRIPTION, "SubmitSubscriptionResponse", "SubmitSubscriptionsResponse"),

    /**
     * Query provision response registry message type.
     */
    QUERY_PROVISION_RESPONSE(ARTIFACT_TYPE.PROVISION, "QueryProvisioningResponse", null),
    /**
     * Query registration response registry message type.
     */
    QUERY_REGISTRATION_RESPONSE(ARTIFACT_TYPE.REGISTRATION, "QueryRegistrationResponse", null),
    /**
     * Query structure response registry message type.
     */
    QUERY_STRUCTURE_RESPONSE(ARTIFACT_TYPE.STRUCTURE, "QueryStructureResponse", null),

    /**
     * Notify registry event registry message type.
     */
//NOTIFICATIONS
    NOTIFY_REGISTRY_EVENT(ARTIFACT_TYPE.NOTIFICATION, "NotifyRegistryEvent", null);

    private ARTIFACT_TYPE artifactType;
    private String type;
    private String twoPointOneType;

    private REGISTRY_MESSAGE_TYPE(ARTIFACT_TYPE artifactType, String type, String twoPointOneType) {
        this.artifactType = artifactType;
        this.type = type;
        this.twoPointOneType = twoPointOneType;
        if (this.twoPointOneType == null) {
            this.twoPointOneType = type;
        }
    }

    /**
     * Returns the REGISTRY_MESSAGE_TYPE for the SDMX Node
     *
     * @param XMLNode the xml node
     * @return message type
     */
    public static REGISTRY_MESSAGE_TYPE getMessageType(String XMLNode) {
        for (REGISTRY_MESSAGE_TYPE currentType : REGISTRY_MESSAGE_TYPE.values()) {
            if (currentType.getType().equals(XMLNode) || currentType.twoPointOneType.equals(XMLNode)) {
                return currentType;
            }
        }
        throw new IllegalArgumentException("Unknown node :" + XMLNode);
    }

    /**
     * Gets artifact type.
     *
     * @return the artifact type
     */
    public ARTIFACT_TYPE getArtifactType() {
        return artifactType;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns true if the message type is a notification event (NOTIFY_REGISTRY_EVENT):
     *
     * @return true if the message type is a notification event (NOTIFY_REGISTRY_EVENT):
     */
    public boolean isNotifyEvent() {
        return this == NOTIFY_REGISTRY_EVENT;
    }

    /**
     * Returns true if the message type is a submission request (one of the following):
     * <ul>
     *   <li>SUBMIT_STRUCTURE_REQUEST</li>
     *   <li>SUBMIT_PROVISION_REQUEST</li>
     *   <li>SUBMIT_REGISTRATION_REQUEST</li>
     *   <li>SUBMIT_SUBSCRIPTION_REQUEST</li>
     * </ul>
     *
     * @return is submission request
     */
    public boolean isSubmissionRequest() {
        return this == SUBMIT_STRUCTURE_REQUEST ||
                this == SUBMIT_PROVISION_REQUEST ||
                this == SUBMIT_REGISTRATION_REQUEST ||
                this == SUBMIT_SUBSCRIPTION_REQUEST;
    }

    /**
     * Returns true if the message type is a submission response (one of the following):
     * <ul>
     *   <li>SUBMIT_STRUCTURE_RESPONSE</li>
     *   <li>SUBMIT_PROVISION_RESPONSE</li>
     *   <li>SUBMIT_REGISTRATION_RESPONSE</li>
     *   <li>SUBMIT_SUBSCRIPTION_RESPONSE</li>
     * </ul>
     *
     * @return is submission response
     */
    public boolean isSubmissionResponse() {
        return this == SUBMIT_STRUCTURE_RESPONSE ||
                this == SUBMIT_PROVISION_RESPONSE ||
                this == SUBMIT_REGISTRATION_RESPONSE ||
                this == SUBMIT_SUBSCRIPTION_RESPONSE;
    }

    /**
     * Returns true if the message type is a query request (one of the following):
     * <ul>
     *   <li>QUERY_PROVISION_REQUEST</li>
     *   <li>QUERY_REGISTRATION_REQUEST</li>
     *   <li>QUERY_STRUCTURE_REQUEST</li>
     * </ul>
     *
     * @return is query request
     */
    public boolean isQueryRequest() {
        return this == QUERY_PROVISION_REQUEST ||
                this == QUERY_REGISTRATION_REQUEST ||
                this == QUERY_STRUCTURE_REQUEST;
    }

    /**
     * Returns true if the message type is a query response (one of the following):
     * <ul>
     *   <li>QUERY_PROVISION_RESPONSE</li>
     *   <li>QUERY_REGISTRATION_RESPONSE</li>
     *   <li>QUERY_STRUCTURE_RESPONSE</li>
     *   <li>QUERY_STRUCTURE_RESPONSE</li>
     * </ul>
     *
     * @return is query response
     */
    public boolean isQueryResponse() {
        return this == QUERY_PROVISION_RESPONSE ||
                this == QUERY_REGISTRATION_RESPONSE ||
                this == QUERY_STRUCTURE_RESPONSE;
    }

}
