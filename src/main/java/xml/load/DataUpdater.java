package xml.load;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

public class DataUpdater
{
    Client client;
    Transaction transaction;
    static final Logger log = Logger.getLogger("Main");

    public DataUpdater()
    {
    }

    public void insert()
    {
        if (client != null && transaction != null)
        {
            log.log(Level.DEBUG, "Storing current Client object");
            final Session session = Main.getSession();

            try
            {
                // if(client.isMandatorySet()) // Check mandatory fields set
                session.beginTransaction();
                session.save(client);
                session.getTransaction().commit();
            }
            catch (Exception e)
            {
                if (!(e.getCause() instanceof ConstraintViolationException)) // Do nothing if client already exists
                {
                    log.log(Level.ERROR, "Exception while inserting new client: " + e.getMessage());
                    log.log(Level.ERROR, client.toString());
                    log.log(Level.ERROR, transaction.toString());
                }
                session.getTransaction().rollback();
            }

            try
            {
                log.log(Level.DEBUG, "Storing current Transaction object");
                transaction.setInn(client.getInn());
                // if(transaction.isMandatorySet()) // Check mandatory fields set
                session.beginTransaction();
                session.save(transaction);
                session.getTransaction().commit();
            }
            catch (Exception e)
            {
                session.getTransaction().rollback();
                log.log(Level.ERROR, "Exception while inserting new transaction: " + e.getMessage() + " : ");
                log.log(Level.ERROR, client.toString());
                log.log(Level.ERROR, transaction.toString());
            }
            session.close();
        }
        else
        {
            log.log(Level.ERROR, "Client or Transaction is null");
        }
        //Reset to prevent duplicate insertion
        setClient(null);
        setTransaction(null);
    }

    public Client getClient()
    {
        return (client);
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Transaction getTransaction()
    {
        return (transaction);
    }

    public void setTransaction(Transaction transaction)
    {
        this.transaction = transaction;
    }
}
