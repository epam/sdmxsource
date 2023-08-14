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
package org.sdmxsource.sdmx.structureparser.manager.parsing.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.*;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexStructureQuery;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.builder.query.QueryBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.query.impl.QueryBeanBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.parsing.QueryParsingManager;
import org.sdmxsource.sdmx.structureparser.workspace.QueryWorkspace;
import org.sdmxsource.sdmx.structureparser.workspace.impl.QueryWorkspaceImpl;
import org.sdmxsource.sdmx.util.exception.ParseException;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.springutil.xml.XMLParser;
import org.sdmxsource.util.log.LoggingUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The type Query parsing manager.
 */
//JAVADOC missing
public class QueryParsingManagerImpl implements QueryParsingManager {
    private final QueryBeanBuilder queryBeanBuilder = new QueryBeanBuilderImpl();
    private Logger log = LoggerFactory.getLogger(QueryParsingManagerImpl.class);

    @Override
    public QueryWorkspace parseQueries(ReadableDataLocation dataLocation) throws ParseException {
        LoggingUtil.debug(log, "Parse Structure request, for xml at location: " + dataLocation.toString());
        InputStream stream = null;
        try {
            SDMX_SCHEMA schemaVersion = SdmxMessageUtil.getSchemaVersion(dataLocation);
            LoggingUtil.debug(log, "Schema Version Determined to be : " + schemaVersion);
            XMLParser.validateXML(dataLocation, schemaVersion);
            LoggingUtil.debug(log, "XML VALID");
            stream = dataLocation.getInputStream();

            MESSAGE_TYPE messageType = SdmxMessageUtil.getMessageType(dataLocation);

            if (schemaVersion == SDMX_SCHEMA.VERSION_ONE || schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
                if (messageType == MESSAGE_TYPE.QUERY) {
                    return processQueryMessage(stream, schemaVersion);
                } else if (messageType == MESSAGE_TYPE.REGISTRY_INTERFACE) {
                    REGISTRY_MESSAGE_TYPE registryMessageType = SdmxMessageUtil.getRegistryMessageType(dataLocation);
                    if (registryMessageType.isQueryRequest()) {
                        try {
                            return processRegistryQueryMessage(stream, schemaVersion, registryMessageType);
                        } catch (Throwable th) {
                            throw new ParseException(th, DATASET_ACTION.INFORMATION, false, registryMessageType.getArtifactType());
                        }
                    } else {
                        //TODO should this be IllegalArgumentException?
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Expected query message - type found : " + registryMessageType);
                    }
                } else {
                    throw new IllegalArgumentException("Expecting a message type " + MESSAGE_TYPE.QUERY + ". Got a " + messageType);
                }
            } else if (schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                if (messageType == MESSAGE_TYPE.REGISTRY_INTERFACE) {
                    REGISTRY_MESSAGE_TYPE registryMessageType = SdmxMessageUtil.getRegistryMessageType(dataLocation);
                    return processRegistryQueryMessage(stream, schemaVersion, registryMessageType);
                } else {
                    List<QUERY_MESSAGE_TYPE> queryMessageTypes = SdmxMessageUtil.getQueryMessageTypes(dataLocation);
                    QUERY_MESSAGE_TYPE queryMessageType = queryMessageTypes.get(0); // Only one *Where element according to 2.1 Schema
                    if (messageType != MESSAGE_TYPE.QUERY) {
                        //TODO should this be IllegalArgumentException?
                        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Not a structure query message:" + queryMessageType);
                    }
                    return processQueryMessage(stream, queryMessageType);
                }
            } else {
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
            }

        } catch (XmlException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * processes all v2.1 structure Query messages and build the {@link ComplexStructureQuery}
     *
     * @param is
     * @param queryMessageType
     * @return a parsed {@link ComplexStructureQuery} set in the {@link QueryWorkspace}
     * @throws XmlException
     * @throws IOException
     */
    private QueryWorkspace processQueryMessage(InputStream is, QUERY_MESSAGE_TYPE queryMessageType) throws XmlException, IOException {
        ComplexStructureQuery complexQuery = null;
        switch (queryMessageType) {
            case STRUCTURES_WHERE:
                StructuresQueryDocument structuresQueryDocument = StructuresQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(structuresQueryDocument.getStructuresQuery());
                break;
            case DATAFLOW_WHERE:
                DataflowQueryDocument dataflowQueryDocument = DataflowQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(dataflowQueryDocument.getDataflowQuery());
                break;
            case METADATAFLOW_WHERE:
                MetadataflowQueryDocument metadataflowQueryDocument = MetadataflowQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(metadataflowQueryDocument.getMetadataflowQuery());
                break;
            case DSD_WHERE:
                DataStructureQueryDocument dataStructureQueryDocument = DataStructureQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(dataStructureQueryDocument.getDataStructureQuery());
                break;
            case MDS_WHERE:
                MetadataStructureQueryDocument metadataStructureQueryDocument = MetadataStructureQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(metadataStructureQueryDocument.getMetadataStructureQuery());
                break;
            case CATEGORY_SCHEME_WHERE:
                CategorySchemeQueryDocument categorySchemeQueryDocument = CategorySchemeQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(categorySchemeQueryDocument.getCategorySchemeQuery());
                break;
            case CONCEPT_SCHEME_WHERE:
                ConceptSchemeQueryDocument conceptSchemeQueryDocument = ConceptSchemeQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(conceptSchemeQueryDocument.getConceptSchemeQuery());
                break;
            case CODELIST_WHERE:
                CodelistQueryDocument codelistQueryDocument = CodelistQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(codelistQueryDocument.getCodelistQuery());
                break;
            case HCL_WHERE:
                HierarchicalCodelistQueryDocument hierarchicalCodelistQueryDocument = HierarchicalCodelistQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(hierarchicalCodelistQueryDocument.getHierarchicalCodelistQuery());
                break;
            case ORGANISATION_SCHEME_WHERE:
                OrganisationSchemeQueryDocument organisationSchemeQueryDocument = OrganisationSchemeQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(organisationSchemeQueryDocument.getOrganisationSchemeQuery());
                break;
            case REPORTING_TAXONOMY_WHERE:
                ReportingTaxonomyQueryDocument reportingTaxonomyQueryDocument = ReportingTaxonomyQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(reportingTaxonomyQueryDocument.getReportingTaxonomyQuery());
                break;
            case STRUCTURE_SET_WHERE:
                StructureSetQueryDocument structureSetQueryDocument = StructureSetQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(structureSetQueryDocument.getStructureSetQuery());
                break;
            case PROCESS_WHERE:
                ProcessQueryDocument processQueryDocument = ProcessQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(processQueryDocument.getProcessQuery());
                break;
            case CATEGORISATION_WHERE:
                CategorisationQueryDocument categorisationQueryDocument = CategorisationQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(categorisationQueryDocument.getCategorisationQuery());
                break;
            case PROVISION_AGREEMENT_WHERE:
                ProvisionAgreementQueryDocument provisionAgreementQueryDocument = ProvisionAgreementQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(provisionAgreementQueryDocument.getProvisionAgreementQuery());
                break;
            case CONSTRAINT_WHERE:
                ConstraintQueryDocument constraintQueryDocument = ConstraintQueryDocument.Factory.parse(is);
                complexQuery = queryBeanBuilder.build(constraintQueryDocument.getConstraintQuery());
                break;
            default:
                throw new IllegalArgumentException("Not a structure query message:" + queryMessageType);
        }

        return new QueryWorkspaceImpl(complexQuery);
    }

    /**
     * Processes a query message which is a QueryMessage Document
     *
     * @param is
     * @param schemaVersion
     * @return
     * @throws IOException
     * @throws XmlException
     */
    private QueryWorkspace processQueryMessage(InputStream is, SDMX_SCHEMA schemaVersion) throws IOException, XmlException {
        List<StructureReferenceBean> structureReferences = null;

        switch (schemaVersion) {
            case VERSION_ONE:
                QueryMessageDocument docV1 = QueryMessageDocument.Factory.parse(is);
                structureReferences = queryBeanBuilder.build(docV1.getQueryMessage());
                break;
            case VERSION_TWO:
                org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageDocument docV2 = org.sdmx.resources.sdmxml.schemas.v20.message.QueryMessageDocument.Factory.parse(is);
                structureReferences = queryBeanBuilder.build(docV2.getQueryMessage());
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
        return new QueryWorkspaceImpl(null, null, structureReferences, false);
    }


    /**
     * Processes a query message which is a RegistryInterface Document
     *
     * @param is                  - the stream containing the SDMX
     * @param schemaVersion       - the schema version that the SDMX is in
     * @param registryMessageType - the type of query message, provision, registration or structure
     * @return
     */
    private QueryWorkspace processRegistryQueryMessage(InputStream is, SDMX_SCHEMA schemaVersion, REGISTRY_MESSAGE_TYPE registryMessageType) throws IOException, XmlException {
        switch (registryMessageType) {
            case QUERY_PROVISION_REQUEST:
                return processRegistryQueryMessageForProvision(is, schemaVersion);
            case QUERY_REGISTRATION_REQUEST:
                return processRegistryQueryMessageForRegistration(is, schemaVersion);
            case QUERY_STRUCTURE_REQUEST:
                return processRegistryQueryMessageForStructures(is, schemaVersion);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, registryMessageType);
        }
    }

    private QueryWorkspace processRegistryQueryMessageForStructures(InputStream is, SDMX_SCHEMA schemaVersion) throws IOException, XmlException {
        switch (schemaVersion) {
            case VERSION_TWO:
                RegistryInterfaceDocument doc = RegistryInterfaceDocument.Factory.parse(is);
                List<StructureReferenceBean> structureReferences = queryBeanBuilder.build(doc.getRegistryInterface().getQueryStructureRequest());
                boolean resolveRefernces = doc.getRegistryInterface().getQueryStructureRequest().getResolveReferences();
                return new QueryWorkspaceImpl(null, null, structureReferences, resolveRefernces);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
    }

    private QueryWorkspace processRegistryQueryMessageForProvision(InputStream is, SDMX_SCHEMA schemaVersion) throws IOException, XmlException {
        switch (schemaVersion) {
            case VERSION_TWO:
                RegistryInterfaceDocument doc = RegistryInterfaceDocument.Factory.parse(is);
                StructureReferenceBean provisionReferences = queryBeanBuilder.build(doc.getRegistryInterface().getQueryProvisioningRequest());
                return new QueryWorkspaceImpl(provisionReferences, null, null, false);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
    }

    private QueryWorkspace processRegistryQueryMessageForRegistration(InputStream is, SDMX_SCHEMA schemaVersion) throws IOException, XmlException {
        StructureReferenceBean registrationReferences = null;
        switch (schemaVersion) {
            case VERSION_TWO:
                RegistryInterfaceDocument doc = RegistryInterfaceDocument.Factory.parse(is);
                registrationReferences = queryBeanBuilder.build(doc.getRegistryInterface().getQueryRegistrationRequest());
                break;
            case VERSION_TWO_POINT_ONE:
                org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument doc2_1 = org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument.Factory.parse(is);
                registrationReferences = queryBeanBuilder.build(doc2_1.getRegistryInterface().getQueryRegistrationRequest());
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
        return new QueryWorkspaceImpl(null, registrationReferences, null, false);
    }
}
