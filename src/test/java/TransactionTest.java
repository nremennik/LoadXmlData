import org.junit.Test;
import xml.load.Client;
import xml.load.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest
{
    public TransactionTest()
    {
    }

    @Test
    public void testCreateTransaction()
    {
        Transaction t = new Transaction();
        assertEquals(null, t.getPlace());
        assertEquals(null, t.getAmount());
        assertEquals(null, t.getCurrency());
        assertEquals(null, t.getCard());
        assertEquals(null, t.getInn());

    }

    @Test
    public void testCreateTransactionParam()
    {
        Transaction t = new Transaction(1l, "place", 1.2f, "currency", "12**", 123l);

        assertEquals(1, (long) t.getId());
        assertEquals("place", t.getPlace());
        assertEquals(1.2, (float) t.getAmount(), 0.0001);
        assertEquals("currency", t.getCurrency());
        assertEquals("12**", t.getCard());
        assertEquals(123, (long) t.getInn());

    }

    @Test
    public void testSetId()
    {
        Transaction t = new Transaction();
        t.setId(111);
        assertEquals(111, t.getId());
    }

    @Test
    public void testSetPlace()
    {
        Transaction t = new Transaction();
        t.setPlace("new place");
        assertEquals("new place", t.getPlace());
    }

    @Test
    public void testSetAmount()
    {
        Transaction t = new Transaction();
        t.setAmount(1.2f);
        assertEquals(1.2, (float) t.getAmount(), 0.0001);
    }

    @Test
    public void testSetCurrency()
    {
        Transaction t = new Transaction();
        t.setCurrency("new currency");
        assertEquals("new currency", t.getCurrency());
    }

    @Test
    public void testSetCard()
    {
        Transaction t = new Transaction();
        t.setCard("11**");
        assertEquals("11**", t.getCard());
    }

    @Test
    public void testSetInn()
    {
        Transaction t = new Transaction();
        t.setInn(111l);
        assertEquals(111, (long) t.getInn());
    }

    @Test
    public void testIsMandatorySet()
    {
        Transaction t = new Transaction(1l, "place", 1.2f, "currency", "12**", 123l);
        assertTrue(t.isMandatorySet());
    }

}
