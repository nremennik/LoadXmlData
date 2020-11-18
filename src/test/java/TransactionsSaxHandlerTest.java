import org.junit.Test;
import org.xml.sax.SAXException;
import xml.load.Client;
import xml.load.DataUpdater;
import xml.load.Transaction;
import xml.load.TransactionsSaxHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransactionsSaxHandlerTest
{
    public TransactionsSaxHandlerTest()
    {
    }


    class TestDataUpdater extends DataUpdater
    {
        @Override
        public void insert()
        {
        }
    }

    @Test
    public void testParseXml()
    {
        final String xmlTestSample = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "        <soap:Body>\n" +
                "                <ns2:GetTransactionsResponse xmlns:ns2=\"http://dbo.qulix.com/ukrsibdbo\">\n" +
                "                  <transactions>\n" +
                "                    <transaction>\n" +
                "                      <place>A PLACE 1</place>\n" +
                "                      <amount>10.01</amount>\n" +
                "                      <currency>UAH</currency>\n" +
                "                      <card>123456****1234</card>\n" +
                "                      <client>\n" +
                "                        <firstName>Ivan</firstName>\n" +
                "                        <lastName>Ivanoff</lastName>\n" +
                "                        <middleName>Ivanoff</middleName>\n" +
                "                        <inn>1234567890</inn>\n" +
                "                      </client>\n" +
                "                    </transaction>\n" +
                "                  </transactions>\n" +
                "                </ns2:GetTransactionsResponse>\n" +
                "        </soap:Body>\n" +
                "</soap:Envelope>";
        Client client = null;
        Transaction transaction = null;
        try
        {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            parser = saxParserFactory.newSAXParser();
            DataUpdater dataUpdater = new TestDataUpdater();
            TransactionsSaxHandler handler = new TransactionsSaxHandler(dataUpdater);
            InputStream inputStream = new ByteArrayInputStream(xmlTestSample.getBytes(StandardCharsets.UTF_8));

/*
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }
            System.out.printf("[%s]\n", stringBuilder);
            inputStream = new ByteArrayInputStream(xmlTestSample.getBytes(StandardCharsets.UTF_8));
*/

            parser.parse(inputStream, handler);
            client = dataUpdater.getClient();
            transaction = dataUpdater.getTransaction();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        assertNotEquals(null, client);
        assertNotEquals(null, transaction);
        assertEquals("Ivan", client.getFirstname());
    }
}
