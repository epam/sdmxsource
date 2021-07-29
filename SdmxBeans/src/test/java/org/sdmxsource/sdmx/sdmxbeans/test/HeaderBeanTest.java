package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ContactBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ContactMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextTypeWrapperMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderBeanTest {

    private static List<PartyBean> buildReceivers() {
        List<TextTypeWrapper> receiverNames = singletonList(new TextTypeWrapperImpl("en", "receiver name", null));
        var mutableContactReceiver = new ContactMutableBeanImpl();
        mutableContactReceiver.addEmail("smith@example.com");
        List<ContactBean> receiverContacts = singletonList(new ContactBeanImpl(mutableContactReceiver));
        String timeZone = "+02:00";
        return singletonList(new PartyBeanImpl(receiverNames, "RC0", receiverContacts, timeZone));
    }

    private static PartyBean buildSender(String senderId) {
        var senderContact1Mutable = new ContactMutableBeanImpl();
        senderContact1Mutable.addDepartment(new TextTypeWrapperMutableBeanImpl("en", "A department"));
        senderContact1Mutable.addName(new TextTypeWrapperMutableBeanImpl("en", "a contact name"));
        senderContact1Mutable.addName(new TextTypeWrapperMutableBeanImpl("it", "a contact name"));
        senderContact1Mutable.addEmail("sender@example.com");
        senderContact1Mutable.addTelephone("+12 (0)34567890");
        var senderContact2Mutable = new ContactMutableBeanImpl();
        senderContact2Mutable.addRole(new TextTypeWrapperMutableBeanImpl("en", "A role"));
        senderContact2Mutable.addRole(new TextTypeWrapperMutableBeanImpl("it", "A role"));
        senderContact1Mutable.addEmail("sender2@example.com");

        List<ContactBean> senderContacts = Arrays.asList(new ContactBeanImpl(senderContact1Mutable),
                new ContactBeanImpl(senderContact2Mutable));
        return new PartyBeanImpl(null, senderId, senderContacts, null);
    }

    @Test
    public void shouldCheckHeaderWithBaseParams() {
        String senderId = "TestSender";
        var receivers = buildReceivers();
        var sender = buildSender(senderId);
        Date prepared = Date.from(Instant.now());
        var reportingBegin = new Date(2000, 1, 1);
        var reportingEnd = new Date(2010, 12, 31);
        HeaderBean header = new HeaderBeanImpl("TEST", prepared, reportingBegin, reportingEnd, receivers, sender, true);

        header.addStructure(new DatasetStructureReferenceBeanImpl(
                new StructureReferenceBeanImpl("DSD_AGENCY", "DSD_ID", "1.0", SDMX_STRUCTURE_TYPE.DSD)));

        assertEquals("TEST", header.getId());
        assertEquals(senderId, header.getSender().getId());
        assertEquals(1, header.getReceiver().size());
    }

    @Test
    public void shouldCheckHeaderWithMinimumParams() {
        String headerId = "TEST";
        String senderId = "ZZ";
        HeaderBean header = new HeaderBeanImpl(headerId, senderId);
        assertEquals(headerId, header.getId());
        assertEquals(senderId, header.getSender().getId());
    }
}
