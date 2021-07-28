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

import java.io.Serializable;

/**
 * Containing all the general exception codes used.
 *
 * @author Matt Nelson
 */
public class ExceptionCode implements Serializable {

    private static final long serialVersionUID = 5049272520940964141L;
    //SDMX STRUCTURE ERRORS
    public static ExceptionCode STRUCTURE_URN_MALFORMED = new ExceptionCode("001");
    public static ExceptionCode STRUCTURE_URN_MALFORMED_MISSING_PREFIX = new ExceptionCode("002");
    public static ExceptionCode STRUCTURE_URN_MALFORMED_UNKOWN_PREFIX = new ExceptionCode("003");
    public static ExceptionCode USTRUCTURE_URN_MALFORMED_PART_VERSION_INFOMATION_SUPPLIED = new ExceptionCode("004");
    public static ExceptionCode STRUCTURE_IDENTIFIABLE_MISSING_URN = new ExceptionCode("005");
    public static ExceptionCode STRUCTURE_IDENTIFIABLE_MISSING_ID = new ExceptionCode("006");
    public static ExceptionCode STRUCTURE_IDENTIFIABLE_MISSING_NAME = new ExceptionCode("007");
    public static ExceptionCode STRUCTURE_NOT_FOUND = new ExceptionCode("008");
    public static ExceptionCode STRUCTURE_CROSS_REFERENCE_MISSING = new ExceptionCode("009");
    public static ExceptionCode DUPLICATE_URN = new ExceptionCode("010");
    public static ExceptionCode STRUCTURE_URN_UNEXPECTED_PREFIX = new ExceptionCode("011");
    public static ExceptionCode STRUCTURE_MAINTAINABLE_MISSING_AGENCY = new ExceptionCode("012");
    public static ExceptionCode STRUCTURE_INVALID_VERSION = new ExceptionCode("013");
    public static ExceptionCode STRUCTURE_INVALID_ID = new ExceptionCode("014");
    public static ExceptionCode STRUCTURE_URI_MALFORMED = new ExceptionCode("015");
    public static ExceptionCode STRUCTURE_INVALID_ID_START_ALPHA = new ExceptionCode("016");
    public static ExceptionCode STRUCTURE_INVALID_ORGANISATION_SCHEME_NO_CONTENT = new ExceptionCode("017");
    //SECURITY ERRORS
    public static ExceptionCode SECURITY_UNAUTHORISED_REFERENCE = new ExceptionCode("101");
    public static ExceptionCode SECURITY_UNAUTHORISED = new ExceptionCode("102");
    public static ExceptionCode SECURITY_AUTH_LEVEL_REGISTRY_OWNER_REQUIRED = new ExceptionCode("103");
    public static ExceptionCode SECURITY_INVALID_LOGIN = new ExceptionCode("104");
    public static ExceptionCode SECURITY_NO_USER_LOGGED_IN = new ExceptionCode("105");
    public static ExceptionCode SECURITY_INCORRECT_PASSWORD = new ExceptionCode("106");
    public static ExceptionCode SECURITY_SESSION_LIMIT_REACHED = new ExceptionCode("107");
    public static ExceptionCode SECURITY_INVALID_TOKEN = new ExceptionCode("108");
    public static ExceptionCode SECURITY_ACCOUNT_INACTIVE = new ExceptionCode("109");
    public static ExceptionCode SECURITY_NO_CRITERIA_SUPPLIED = new ExceptionCode("110");
    //WEB SERVICE ERRORS
    public static ExceptionCode WEB_SERVICE_CONFIGURATION_MISSING = new ExceptionCode("201");
    public static ExceptionCode WEB_SERVICE_REQUEST_MISSING = new ExceptionCode("202");
    public static ExceptionCode WEB_SERVICE_URL_MISSING = new ExceptionCode("203");
    public static ExceptionCode WEB_SERVICE_PROTOCOL_MISSING = new ExceptionCode("204");
    public static ExceptionCode WEB_SERVICE_BAD_RESPONSE = new ExceptionCode("205");
    public static ExceptionCode WEB_SERVICE_BAD_CONNECTION = new ExceptionCode("206");
    public static ExceptionCode WEB_SERVICE_ENDPOINT_TYPE_INVALID = new ExceptionCode("207");
    public static ExceptionCode WEB_SERVICE_UNSUPPORTED_PROTOCOL = new ExceptionCode("208");
    public static ExceptionCode WEB_SERVICE_SOCKET_TIMEOUT = new ExceptionCode("209");
    public static ExceptionCode WEB_SERVICE_INVALID_GET_DATA = new ExceptionCode("210");
    public static ExceptionCode WEB_SERVICE_INVALID_GET_SCHEMA = new ExceptionCode("211");
    //JAVA OBJECT STATE ERRORS
    public static ExceptionCode JAVA_REQUIRED_OBJECT_NULL = new ExceptionCode("301");
    public static ExceptionCode JAVA_UNEXPECTED_ARGUMENT = new ExceptionCode("302");
    public static ExceptionCode JAVA_COLLECTION_EMPTY = new ExceptionCode("303");
    public static ExceptionCode JAVA_PROPERTY_NOT_FOUND = new ExceptionCode("304");
    public static ExceptionCode JAVA_IO_EXCEPTION = new ExceptionCode("305");
    //GENERAL ERRORS
    public static ExceptionCode START_DATE_AFTER_END_DATE = new ExceptionCode("401");
    public static ExceptionCode END_DATE_BEFORE_START_DATE = new ExceptionCode("402");
    public static ExceptionCode EMAIL_INVALID_FORMAT = new ExceptionCode("403");
    public static ExceptionCode INVALID_DATE_FORMAT = new ExceptionCode("404");
    public static ExceptionCode UNSUPPORTED = new ExceptionCode("405");
    public static ExceptionCode UNSUPPORTED_TRANSFORM = new ExceptionCode("406");
    //DATABASE ERRORS
    public static ExceptionCode DATABASE_SQL_QUERY_ERROR = new ExceptionCode("501");
    //REFERENCE ERRORS
    public static ExceptionCode REFERENCE_ERROR = new ExceptionCode("601");
    public static ExceptionCode REFERENCE_ERROR_MISSING_PARAMETERS = new ExceptionCode("602");
    public static ExceptionCode REFERENCE_ERROR_UNRESOLVABLE = new ExceptionCode("603");
    public static ExceptionCode REFERENCE_ERROR_NO_TYPE = new ExceptionCode("604");
    public static ExceptionCode REFERENCE_ERROR_MULTIPLE_RESPONSES_EXPECTED_ONE = new ExceptionCode("605");
    public static ExceptionCode REFERENCE_ERROR_UNSUPPORTED_QUERY_FOR_STRUCTURE = new ExceptionCode("606");
    public static ExceptionCode REFERENCE_ERROR_UNEXPECTED_RESULTS_COUNT = new ExceptionCode("607");
    public static ExceptionCode REFERENCE_ERROR_UNEXPECTED_STRUCTURE = new ExceptionCode("608");
    //XML INVALID DUE TO BUSINESS ERROR
    public static ExceptionCode FAIL_VALIDATION = new ExceptionCode("701");
    public static ExceptionCode DUPLICATE_CONCEPT = new ExceptionCode("702");
    public static ExceptionCode KEY_FAMILY_GROUP_ATTRIBUTE_MISSING_GROUPID = new ExceptionCode("703");
    public static ExceptionCode KEY_FAMILY_XS_MEASURE_REFERENCE_INCORRECT_DIMENSION_TYPE = new ExceptionCode("704");
    public static ExceptionCode CAN_NOT_RESOLVE_PARENT = new ExceptionCode("705");
    public static ExceptionCode PARENT_RECURSIVE_LOOP = new ExceptionCode("706");
    public static ExceptionCode MAINTAINABLE_REF_INCOMPLETE = new ExceptionCode("707");
    public static ExceptionCode DUPLICATE_LANGUAGE = new ExceptionCode("708");
    public static ExceptionCode DUPLICATE_ALIAS = new ExceptionCode("709");
    public static ExceptionCode DUPLICATE_CODE_REF = new ExceptionCode("710");
    public static ExceptionCode CODE_REF_MISSING_CODE_ID = new ExceptionCode("711");
    public static ExceptionCode CODE_REF_MISSING_CODE_REFERENCE = new ExceptionCode("712");
    public static ExceptionCode CODE_REF_CONSTAINS_URN_AND_CODELIST_ALIAS = new ExceptionCode("713");
    public static ExceptionCode HCL_DUPLICATE_CODE_REFERENCE = new ExceptionCode("714");
    public static ExceptionCode CODE_REF_REFERENCED_TWICE = new ExceptionCode("715");
    public static ExceptionCode KEY_FAMILY_DUPLICATE_GROUP_ID = new ExceptionCode("716");
    public static ExceptionCode KEY_FAMILY_XS_MEASURE_REFERENCE_UNCODED_DIMENSION = new ExceptionCode("717");
    public static ExceptionCode DUPLICATE_REFERENCE = new ExceptionCode("718");
    public static ExceptionCode PARTIAL_TARGET_ID_DUPLICATES_FULL_TARGET_ID = new ExceptionCode("719");
    public static ExceptionCode DUPLICATE_ORGANISATION_ROLE_ID = new ExceptionCode("720");
    public static ExceptionCode EXTERNAL_STRUCTURE_MISSING_URI = new ExceptionCode("721");
    public static ExceptionCode EXTERNAL_STRUCTURE_NOT_FOUND_AT_URI = new ExceptionCode("722");
    public static ExceptionCode GROUP_CANNOT_REFERENCE_TIME_DIMENSION = new ExceptionCode("723");
    public static ExceptionCode REPORT_STRUCTURE_INVALID_IDENTIFIER_REFERENCE = new ExceptionCode("724");
    //PARSE ERRORS
    public static ExceptionCode XML_PARSE_EXCEPTION = new ExceptionCode("800");
    public static ExceptionCode EDI_PARSE_EXCEPTION_DIM_MISSING_CODELIST = new ExceptionCode("801");
    public static ExceptionCode PARSE_ERROR_NOT_XML_OR_EDI = new ExceptionCode("802");
    public static ExceptionCode PARSE_ERROR_NOT_XML = new ExceptionCode("803");
    public static ExceptionCode PARSE_ERROR_NOT_SDMX = new ExceptionCode("804");
    //INVALID DATA MESSAGES
    public static ExceptionCode DATASET_MISSING_VALUE_FOR_DIMENSION = new ExceptionCode("901");
    public static ExceptionCode DSD_MISSING_TIME_DIMENSION = new ExceptionCode("902");
    public static ExceptionCode DSD_MISSING_MEASURE_DIMENSION = new ExceptionCode("903");
    public static ExceptionCode DATASET_INVALID_SERIES_KEY = new ExceptionCode("904");
    public static ExceptionCode DATASET_UNDEFINED_DIMENSION = new ExceptionCode("905");
    public static ExceptionCode DATASET_SERIES_MISSING_ATTRIBUTE = new ExceptionCode("906");
    public static ExceptionCode DATASET_SERIES_UNDEFINED_ATTRIBUTE = new ExceptionCode("907");
    public static ExceptionCode DATASET_GROUP_MISSING_ATTRIBUTE = new ExceptionCode("908");
    public static ExceptionCode DATASET_GROUP_UNDEFINED_ATTRIBUTE = new ExceptionCode("909");
    public static ExceptionCode DATASET_OBS_MISSING_ATTRIBUTE = new ExceptionCode("910");
    public static ExceptionCode DATASET_OBS_UNDEFINED_ATTRIBUTE = new ExceptionCode("911");
    public static ExceptionCode DATASET_INVALID_GROUP = new ExceptionCode("912");
    public static ExceptionCode DATASET_GROUP_UNDEFINED = new ExceptionCode("913");
    public static ExceptionCode DATASET_MISSING_GROUP_KEY_CONCEPT = new ExceptionCode("914");
    public static ExceptionCode DATASET_UNDEFINED_GROUP_KEY_CONCEPT = new ExceptionCode("915");
    public static ExceptionCode DATASET_UNKNOWN_NODE = new ExceptionCode("916");
    public static ExceptionCode UNSUPPORTED_DATATYPE = new ExceptionCode("917");
    public static ExceptionCode DATASET_SERIES_KEY_ORDER_INCORRECT = new ExceptionCode("918");
    //DATA QUERY ERRORS
    public static ExceptionCode QUERY_SELECTION_MISSING_CONCEPT = new ExceptionCode("1001");
    public static ExceptionCode QUERY_SELECTION_MISSING_CONCEPT_VALUE = new ExceptionCode("1002");
    public static ExceptionCode QUERY_SELECTION_ILLEGAL_AND_CODES_IN_SAME_DIMENSION = new ExceptionCode("1003");
    public static ExceptionCode QUERY_SELECTION_ILLEGAL_AND_KEYFAMILY = new ExceptionCode("1004");
    public static ExceptionCode QUERY_SELECTION_MULTIPLE_DATE_FROM = new ExceptionCode("1005");
    public static ExceptionCode QUERY_SELECTION_MULTIPLE_DATE_TO = new ExceptionCode("1006");
    public static ExceptionCode QUERY_SELECTION_ILLEGAL_AND_AGENCY_ID = new ExceptionCode("1007");
    public static ExceptionCode QUERY_SELECTION_ILLEGAL_OPERATOR = new ExceptionCode("1008");
    //REGISTRY EXCEPTIONS
    public static ExceptionCode REGISTRY_ATTEMPT_TO_UPDATE_FINAL_STRUCTURE = new ExceptionCode("1101");
    public static ExceptionCode REGISTRY_ATTEMPT_TO_DELETE_FINAL_STRUCTURE = new ExceptionCode("1102");
    public static ExceptionCode REGISTRY_ATTEMPT_TO_INSERT_LOWER_VERSION_UNDER_FINAL = new ExceptionCode("1103");
    public static ExceptionCode REGISTRY_INSERTION_DELETES_CROSS_REFERENCED_STRUCTURE = new ExceptionCode("1104");
    public static ExceptionCode REGISTRY_ATTEMPT_TO_DELETE_CROSS_REFERENCED_STRUCTURE = new ExceptionCode("1105");
    public static ExceptionCode REGISTRY_DATASOURCE_CAN_NOT_BE_REACHED = new ExceptionCode("1106");
    public static ExceptionCode REGISTRY_ATTEMPT_TO_DELETE_PROVISION_WITH_REGISTRATIONS = new ExceptionCode("1107");
    public static ExceptionCode REGISTRY_SUBSCRIPTION_NOTIFICATION_EXISTS = new ExceptionCode("1108");
    public static ExceptionCode REGISTRY_SUBSCRIPTION_MULTIPLE_RESPONSE_NOT_SUPPORTED = new ExceptionCode("1109");
    public static ExceptionCode REGISTRY_NO_QUERIES_FOUND = new ExceptionCode("1110");
    public static ExceptionCode REGISTRY_ATTEMPT_TO_DELETE_NON_EXISTANT_STRUCTURE = new ExceptionCode("1111");
    public static ExceptionCode REGISTRY_METADATAFLOW_MUST_REFERENCE_DSD = new ExceptionCode("1112");
    public static ExceptionCode REGISTRY_DATAFLOW_MUST_REFERENCE_DSD = new ExceptionCode("1113");
    //BEAN CONSTRUCUTION EXCEPTIONS
    public static ExceptionCode BEAN_PARTIAL_REFERENCE = new ExceptionCode("1201");
    public static ExceptionCode IDENTIFIER_COMPONENT_REP_SCHEME_NO_TYPE = new ExceptionCode("1202");
    public static ExceptionCode IDENTIFIER_UNKNOWN_REP_SCH_TYPE = new ExceptionCode("1203");
    public static ExceptionCode IDENTIFIER_UNSUPPORTED_REP_SCH_TYPE = new ExceptionCode("1204");
    public static ExceptionCode IDENTIFIER_EXPECTED_EXTERNAL_OR_TYPE = new ExceptionCode("1205");
    public static ExceptionCode BEAN_INCOMPLETE_REFERENCE = new ExceptionCode("1206");
    public static ExceptionCode BEAN_STRUCTURE_CONSTRUCTION_ERROR = new ExceptionCode("1207");
    public static ExceptionCode BEAN_MISSING_REQUIRED_ATTRIBUTE = new ExceptionCode("1208");
    public static ExceptionCode BEAN_MISSING_REQUIRED_ELEMENT = new ExceptionCode("1209");
    public static ExceptionCode BEAN_MUTUALLY_EXCLUSIVE = new ExceptionCode("1210");
    private String code;
    private ExceptionCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExceptionCode) {
            ExceptionCode that = (ExceptionCode) obj;
            return this.code.equals(that.code);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Exception Code " + code;
    }

}
