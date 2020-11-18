package xml.load;

import javax.persistence.*;

@Entity
@Table(name = "TRANSACTIONS", schema = "NATA")
public class Transaction
{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "PLACE", nullable = false, length = 500)
    private String place;


    @Basic
    @Column(name = "AMOUNT", nullable = false, precision = 0)
    private Float amount;


    @Basic
    @Column(name = "CURRENCY", nullable = false, length = 500)
    private String currency;


    @Basic
    @Column(name = "CARD", nullable = false, length = 100)
    private String card;

    @Basic
    @Column(name = "INN", nullable = false, precision = 0)
    private Long inn;

    public Long getInn()
    {
        return (inn);
    }

    public void setInn(Long inn)
    {
        this.inn = inn;
    }

    public long getId()
    {
        return (id);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getPlace()
    {
        return (place);
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public Float getAmount()
    {
        return (amount);
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }

    public String getCurrency()
    {
        return (currency);
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getCard()
    {
        return (card);
    }

    public void setCard(String card)
    {
        this.card = card;
    }

    public Transaction()
    {
        this.place = null;
        this.amount = null;
        this.currency = null;
        this.card = null;
        this.inn=null;
    }

    public Transaction(Long id, String place, Float amount, String currency, String card,Long inn)
    {
        this.id=id;
        this.place = place;
        this.amount = amount;
        this.currency = currency;
        this.card = card;
        this.inn=inn;
    }

    @Transient
    public boolean isMandatorySet()
    {
        if (getAmount() == null ||
                getCard() == null ||
                getCurrency() == null ||
                getPlace() == null||
                getInn() == null)
        {
            return (false);
        }
        return (true);
    }

    @Override
    public String toString()
    {
        return ("Transaction:\n" +
                "\tPlace: \"" + getPlace() + "\"\n" +
                "\tAmount: \"" + getAmount() + "\"\n" +
                "\tCurrency: \"" + getCurrency() + "\"\n" +
                "\tCard: \"" + getCard() + "\"");
    }
}
