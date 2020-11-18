import org.junit.Test;
import xml.load.Client;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class ClientTest
{
    public ClientTest()
    {
    }

    @Test
    public void testCreateClient()
    {
        Client c = new Client();
        assertEquals(null, c.getInn());
        assertEquals(null, c.getFirstname());
        assertEquals(null, c.getLastname());
        assertEquals(null, c.getMiddlename());
    }

    @Test
    public void testCreateClientParam()
    {
        Client c = new Client(123l,"firstname","lastname","middlename");
        assertEquals(123, (long)c.getInn());
        assertEquals("firstname", c.getFirstname());
        assertEquals("lastname", c.getLastname());
        assertEquals("middlename", c.getMiddlename());

    }

    @Test
    public void testSetFirstname()
    {
        Client c = new Client();
        c.setFirstname("value");
        assertEquals("value", c.getFirstname());
    }

    @Test
    public void testSetLastname()
    {
        Client c = new Client();
        c.setLastname("value");
        assertEquals("value", c.getLastname());
    }

    @Test
    public void testSetMiddlename()
    {
        Client c = new Client();
        c.setMiddlename("value");
        assertEquals("value", c.getMiddlename());
    }

    @Test
    public void testSetInn()
    {
        Client c = new Client();
        c.setInn(1234567890l);
        assertEquals(1234567890l, (long) c.getInn());
    }

    @Test
    public void testIsMandatorySet()
    {
        Client c = new Client(1234l, "Fred", "Lucky", "Unlucky");
        assertTrue(c.isMandatorySet());
    }

}
