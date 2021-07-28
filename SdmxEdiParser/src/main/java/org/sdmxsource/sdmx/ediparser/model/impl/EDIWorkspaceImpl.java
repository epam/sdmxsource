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
package org.sdmxsource.sdmx.ediparser.model.impl;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.factory.WriteableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;
import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.engine.EdiParseEngine;
import org.sdmxsource.sdmx.ediparser.engine.reader.EDIStructureReaderEngine;
import org.sdmxsource.sdmx.ediparser.engine.reader.impl.EDIDataReaderEngineImpl;
import org.sdmxsource.sdmx.ediparser.model.EDIWorkspace;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDataDocument;
import org.sdmxsource.sdmx.ediparser.model.document.EDIDocumentPosition;
import org.sdmxsource.sdmx.ediparser.model.document.EDIMetadata;
import org.sdmxsource.sdmx.ediparser.model.document.EDIStructureDocument;
import org.sdmxsource.sdmx.ediparser.model.document.impl.EDIDataDocumentImpl;
import org.sdmxsource.sdmx.ediparser.model.document.impl.EDIStructureDocumentImpl;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIDataReader;
import org.sdmxsource.sdmx.ediparser.model.reader.FileReader;
import org.sdmxsource.sdmx.ediparser.model.reader.impl.FileReaderImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * The type Edi workspace.
 */
public class EDIWorkspaceImpl implements EDIWorkspace {

    private final WriteableDataLocationFactory writeableDataLocationFactory;

    private List<EDIDataDocument> dataDocuments = new ArrayList<EDIDataDocument>();
    private List<SdmxBeans> beans = new ArrayList<SdmxBeans>();

    private Set<MaintainableRefBean> keyFamilyReferences = new HashSet<MaintainableRefBean>();

    private EDIMetadata ediMetadata;
    private HeaderBean headerBean;

    /**
     * Instantiates a new Edi workspace.
     *
     * @param writeableDataLocationFactory the writeable data location factory
     * @param structureReaderEngine        the structure reader engine
     * @param ediParseEngine               the edi parse engine
     * @param ediDocument                  the edi document
     */
    public EDIWorkspaceImpl(
            final WriteableDataLocationFactory writeableDataLocationFactory,
            final EDIStructureReaderEngine structureReaderEngine,
            final EdiParseEngine ediParseEngine,
            final ReadableDataLocation ediDocument) {
        this.writeableDataLocationFactory = Objects.requireNonNull(writeableDataLocationFactory, "writeableDataLocationFactory");
        ediMetadata = Objects.requireNonNull(ediParseEngine, "ediParseEngine").parseEDIMessage(ediDocument);
        FileReader fr = new FileReaderImpl(ediDocument, EDI_CONSTANTS.END_OF_LINE_REG_EX, EDI_CONSTANTS.CHARSET_ENCODING);

        int idx = 0;
        try {
            for (EDIDocumentPosition documentPosition : ediMetadata.getDocumentIndex()) {
                int start = documentPosition.getStartLine();
                WriteableDataLocation wdl = writeableDataLocationFactory.getTemporaryWriteableDataLocation();

                OutputStreamWriter osw;
                try {
                    osw = new OutputStreamWriter(wdl.getOutputStream(), EDI_CONSTANTS.CHARSET_ENCODING);
                } catch (UnsupportedEncodingException e1) {
                    throw new RuntimeException("Unable to open: ", e1);
                }
                while (fr.moveNext()) {
                    idx = fr.getLineNumber();
                    // If this next line is within the bounds of the start and end location then write the current line to the writer
                    if (idx >= start) {
                        String line = fr.getCurrentLine();
                        write(osw, line + EDI_CONSTANTS.END_TAG);
//						writeLineSeparator(osw);

                        // Keep going until we encounter the end segment indicator
                        if (line.startsWith(EDI_PREFIX.END_MESSAGE_ADMINISTRATION.toString())) {
                            break;
                        }
                    }
                }
                try {
                    osw.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close: ", e);
                }

                if (documentPosition.isData()) {
                    EDIDataDocument dataDocument = new EDIDataDocumentImpl(wdl, documentPosition, ediMetadata);
                    dataDocuments.add(dataDocument);
                    keyFamilyReferences.add(dataDocument.getDataReader().getDatasetHeaderBean().getDataStructureReference().getStructureReference().getMaintainableReference());
                } else {
                    EDIStructureDocument doc = new EDIStructureDocumentImpl(structureReaderEngine, wdl, documentPosition, ediMetadata);
                    try {
                        beans.add(doc.getSdmxBeans());
                    } finally {
                        //No longer need ReadableFile location as we have built Beans
                        wdl.close();
                    }
                }
            }
            createHeader();
        } finally {
            fr.close();
        }

    }

    private void write(OutputStreamWriter outputStreamWriter, String value) {
        try {
            outputStreamWriter.write(value);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write the string: " + value, e);
        }
    }

    private void createHeader() {
        List<DatasetStructureReferenceBean> structure = new ArrayList<DatasetStructureReferenceBean>();
        List<PartyBean> receiver = new ArrayList<PartyBean>();
        PartyBean sender = null;
        Set<String> allreceivers = new HashSet<String>();
        for (EDIDataDocument currentReader : dataDocuments) {
            EDIDataReader reader = currentReader.getDataReader();
            DatasetHeaderBean header = reader.getDatasetHeaderBean();
            structure.add(header.getDataStructureReference());
            sender = reader.getSendingAgency();
            String recievingAgency = reader.getRecievingAgency();
            if (!allreceivers.contains(recievingAgency)) {
                allreceivers.add(recievingAgency);
                receiver.add(new PartyBeanImpl(null, recievingAgency, null, null));
            }

        }

        headerBean = new HeaderBeanImpl(ediMetadata.getInterchangeReference(),
                ediMetadata.getDateOfPreparation(),
                ediMetadata.getReportingBegin(),
                ediMetadata.getReportingEnd(),
                receiver,
                sender,
                ediMetadata.isTest());

        if (ediMetadata.getMessageName() != null) {
            headerBean.addName(new TextTypeWrapperImpl("en", ediMetadata.getMessageName(), null));
        }
    }

    @Override
    public HeaderBean getHeader() {
        return headerBean;
    }

    @Override
    public List<DatasetHeaderBean> getDatasetHeaders() {
        List<DatasetHeaderBean> returnList = new ArrayList<DatasetHeaderBean>();
        for (EDIDataDocument currentReader : dataDocuments) {
            EDIDataReader reader = currentReader.getDataReader();
            DatasetHeaderBean header = reader.getDatasetHeaderBean();
            returnList.add(header);
        }
        return returnList;
    }

    @Override
    public DataReaderEngine getDataReader(SdmxBeanRetrievalManager beanRetrieval) {
        List<EDIDataReader> engines = new ArrayList<EDIDataReader>();
        for (EDIDataDocument dataDocument : dataDocuments) {
            engines.add(dataDocument.getDataReader());
        }
        return new EDIDataReaderEngineImpl(headerBean, beanRetrieval, engines);
    }

    @Override
    public DataReaderEngine getDataReader(DataStructureBean keyFamily, DataflowBean dataflowBean) {
        if (!hasData()) {
            throw new IllegalArgumentException("Attempting to read data from an EDI file that does not contain data");
        }
        if (!keyFamilyReferences.contains(keyFamily.asReference().getMaintainableReference())) {
            return null;
        }
        List<EDIDataReader> engines = new ArrayList<EDIDataReader>();
        for (EDIDataDocument dataDocument : dataDocuments) {
            if (dataDocument.getDataReader().getDatasetHeaderBean().getDataStructureReference().getStructureReference().getMaintainableReference().equals(keyFamily.asReference().getMaintainableReference())) {
                engines.add(dataDocument.getDataReader());
            }
        }
        return new EDIDataReaderEngineImpl(headerBean, dataflowBean, keyFamily, engines);
    }

    /**
     * Gets edi data document.
     *
     * @return the edi data document
     */
    public List<EDIDataDocument> getEDIDataDocument() {
        return new ArrayList<EDIDataDocument>(dataDocuments);
    }

    @Override
    public List<SdmxBeans> getBeans() {
        return new ArrayList<SdmxBeans>(beans);
    }

    @Override
    public SdmxBeans getMergedBeans() {
        if (!hasStructures()) {
            throw new RuntimeException("There are no structures within this EDI file.");
        }
        if (beans.size() == 1) {
            return beans.get(0);
        }
        SdmxBeans returnBeans = new SdmxBeansImpl(headerBean);
        for (SdmxBeans currentBeans : beans) {
            returnBeans.merge(currentBeans);
        }
        return returnBeans;
    }

    @Override
    public boolean hasData() {
        return dataDocuments.size() > 0;
    }

    @Override
    public boolean hasStructures() {
        return beans.size() > 0;
    }
}
