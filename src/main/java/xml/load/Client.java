package xml.load;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT", schema = "NATA")
public class Client
{
    @Id
    @Column(name = "INN", nullable = false, precision = 0)
    private Long inn;

    @Basic
    @Column(name = "FIRSTNAME", nullable = false, length = 200)
    private String firstname;

    @Basic
    @Column(name = "LASTNAME", nullable = false, length = 200)
    private String lastname;

    @Basic
    @Column(name = "MIDDLENAME", nullable = false, length = 200)
    private String middlename;

    public Long getInn()
    {
        return (inn);
    }

    public void setInn(Long inn)
    {
        this.inn = inn;
    }

    public String getFirstname()
    {
        return (firstname);
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return (lastname);
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getMiddlename()
    {
        return (middlename);
    }

    public void setMiddlename(String middlename)
    {
        this.middlename = middlename;
    }

    public Client()
    {
        this.inn = null;
        this.firstname = null;
        this.lastname = null;
        this.middlename = null;
    }

    public Client(Long inn, String firstname, String lastname, String middlename)
    {
        this.inn = inn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
    }

    @Transient
    public boolean isMandatorySet()
    {
        if (getInn() == null ||
                getFirstname() == null ||
                getLastname() == null ||
                getMiddlename() == null)
        {
            return (false);
        }
        return (true);
    }

    @Override
    public String toString()
    {
        return ("Client:\n" +
                "\tInn: \"" + getInn() + "\"\n" +
                "\tFirstName: \"" + getFirstname() + "\"\n" +
                "\tlastName: \"" + getLastname() + "\"\n" +
                "\tmiddleName: \"" + getMiddlename() + "\"");
    }
}

