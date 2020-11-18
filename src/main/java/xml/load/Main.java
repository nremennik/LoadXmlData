package xml.load;

import org.apache.log4j.Level;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;


public class Main
{
    private static final SessionFactory ourSessionFactory;

    static
    {
        try
        {
            // Set in log4j instead
            // java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        }
        catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception
    {
        try
        {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            for (int i = 0; i < args.length; i++)
            {
                SAXParser parser = saxParserFactory.newSAXParser();
                TransactionsSaxHandler handler = new TransactionsSaxHandler(new DataUpdater());
                parser.parse(args[i], handler);
                /* Or this
                SAXReader saxReader = new SAXReader();
                XMLReader xmlReader = saxReader.getXMLReader();
                xmlReader.setContentHandler(new TransactionsSaxHandler());
                xmlReader.parse(args[i]);
*/
            }
        }
        catch (Exception e)
        {
            Logger.getLogger("Main").log(Level.ERROR, "Exception: " + e.getMessage());
        }
    }
}
