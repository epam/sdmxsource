package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.util.stax.StaxUtil;
import org.sdmxsource.util.io.FileUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaxUtilTest {

    @Test
    public void shouldCheckSkipToEndNode() {
        InputStream fileInputStream = FileUtil.readFileAsStream("src/test/resources/queryResponse-estat-sts.xml");
        XMLInputFactory xif = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = xif.createXMLStreamReader(fileInputStream);
            assertTrue(StaxUtil.skipToEndNode(reader, "Header"));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCheckSkipToNode() {
        InputStream fileInputStream = FileUtil.readFileAsStream("src/test/resources/queryResponse-estat-sts.xml");
        XMLInputFactory xif = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = xif.createXMLStreamReader(fileInputStream);
            assertTrue(StaxUtil.skipToNode(reader, "Header"));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
