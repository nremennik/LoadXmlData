package xml.load;

import org.apache.log4j.Level;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

public class TransactionsSaxHandler extends DefaultHandler
{
    public TransactionsSaxHandler(DataUpdater updater)
    {
        super();
        this.dataUpdater = updater;
    }

    Transaction transaction = null;
    Client client = null;
    final LinkedList<String> tagsStack = new LinkedList<>(); // Current tags path - rom first to last

    DataUpdater dataUpdater;

    static final Logger log = Logger.getLogger("Main");

    public CharSequence getTagsPathAsString()
    {
        StringBuilder res = new StringBuilder();
        final AtomicBoolean first = new AtomicBoolean(true);
        tagsStack.forEach(el ->
        {
            if (!first.get())
            {
                res.append(";");
            }
            res.append(el);
            first.set(false);
        });
        return (res);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        tagsStack.add(qName);
        log.log(Level.DEBUG, "Got tag " + qName);
        log.log(Level.DEBUG, getTagsPathAsString());
        switch (qName)
        {
            case "transaction": // Create new transaction if found, maybe need to check the whole path?
                log.log(Level.DEBUG, "Creating new Transaction object");
                transaction = new Transaction();
                client = null;
                break;
            case "client": // Create new client if found, maybe need to check the whole path?
                log.log(Level.DEBUG, "Creating new Client object");
                client = new Client();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        log.log(Level.DEBUG, "Tag " + qName + " closed");

        if (qName.equals("transaction")) // Transaction end, map it to the db
        {
            log.log(Level.DEBUG, "Storing current Client object");

            log.log(Level.INFO, client.toString());
            log.log(Level.INFO, transaction.toString());

            transaction.setInn(client.getInn());

            dataUpdater.setClient(client);
            dataUpdater.setTransaction(transaction);
            dataUpdater.insert();

            client = null;
            transaction = null;
        }
        tagsStack.removeLast();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String value = new StringBuilder().append(ch, start, length).toString().trim(); // Read tag content to string and trim it

        String curentTag = tagsStack.peekLast();

        if (value.length() > 0 && getTagsPathAsString().toString().matches("^.*transactions;transaction;[^;]+$"))
        {
            log.log(Level.DEBUG, "Processing data for tag '" + curentTag + "'");
            switch (curentTag)
            {
                case "place":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Transaction object");
                    transaction.setPlace(value);
                    break;
                case "amount":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Transaction object");
                    try
                    {
                        transaction.setAmount(Float.parseFloat(value));
                    }
                    catch (Exception e)
                    {
                        // On parsing exception do nothing or raise an error?
                    }
                    break;
                case "currency":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Transaction object");
                    transaction.setCurrency(value);
                    break;
                case "card":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Transaction object");
                    transaction.setCard(value);
                    break;
            }
        }

        if (value.length() > 0 && getTagsPathAsString().toString().matches("^.*transactions;transaction;client;[^;]+$"))
        {
            log.log(Level.DEBUG, "Processing data for tag '" + curentTag + "'");
            switch (curentTag)
            {
                case "inn":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Client object");
                    try
                    {
                        client.setInn(Long.parseLong(value));
                    }
                    catch (Exception e)
                    {
                        // On parsing exception do nothing or raise an error?
                    }
                    break;
                case "firstName":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Client object");
                    client.setFirstname(value);
                    break;
                case "lastName":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Client object");
                    client.setLastname(value);
                    break;
                case "middleName":
                    log.log(Level.DEBUG, "Storing data for tag " + curentTag + " in the Client object");
                    client.setMiddlename(value);
                    break;
            }
        }
    }

    public DataUpdater getDataUpdater()
    {
        return (dataUpdater);
    }

    public void setDataUpdater(DataUpdater dataUpdater)
    {
        this.dataUpdater = dataUpdater;
    }
}
