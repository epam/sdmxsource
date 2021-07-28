package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Header iterator.
 */
public class HeaderIterator extends AbstractIterator {
    private String id;
    private Date prepared;
    private Boolean test;

    private PartyIterator senderIt;
    private PartyIterator recieverIt;

    /**
     * Instantiates a new Header iterator.
     *
     * @param jReader the j reader
     */
    public HeaderIterator(JsonReader jReader) {
        super(jReader);
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public HeaderBean getHeader() {
        PartyBean sender = senderIt != null ? senderIt.getParty() : null;
        PartyBean reciever = recieverIt != null ? recieverIt.getParty() : null;
        List<PartyBean> recievers = new ArrayList<PartyBean>();
        if (reciever != null) {
            recievers.add(reciever);
        }
        return new HeaderBeanImpl(id, prepared, null, null, recievers, sender, test);
    }

    @Override
    public JsonReader.Iterator start(String fieldName, boolean isObject) {
        if ("sender".equals(fieldName)) {
            senderIt = new PartyIterator(jReader);
            return senderIt;
        }
        //TODO Other fields (reciever)
        return null;
    }

    @Override
    public void next(String fieldName) {
        if ("id".equals(fieldName)) {
            id = jReader.getValueAsString();
        } else if ("prepared".equals(fieldName)) {
            prepared = jReader.getValueAsDate();
        } else if ("test".equals(fieldName)) {
            test = jReader.getValueAsBoolean();
        }
    }

    private class PartyIterator extends AbstractIterator {
        private String id;
        private String name;

        /**
         * Instantiates a new Party iterator.
         *
         * @param jReader the j reader
         */
        public PartyIterator(JsonReader jReader) {
            super(jReader);
        }

        @Override
        public void next(String fieldName) {
            if ("id".equals(fieldName)) {
                id = jReader.getValueAsString();
            } else if ("name".equals(fieldName)) {
                name = jReader.getValueAsString();
            }
        }

        /**
         * Gets party.
         *
         * @return the party
         */
        public PartyBean getParty() {
            List<TextTypeWrapper> nameList = new ArrayList<TextTypeWrapper>();
            nameList.add(new TextTypeWrapperImpl("en", name, null));
            List<ContactBean> contactList = new ArrayList<ContactBean>();
            return new PartyBeanImpl(nameList, id, contactList, null);  //TODO CONTACTS
        }

        @Override
        public org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator start(String fieldName, boolean isObject) {
            //TODO Contact
            return JsonReader.SKIP_OBJECT_ITERATOR;
        }
    }
}
