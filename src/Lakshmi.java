import java.util.Map;

/**
 * Represents the NYSE stock exchange
 */
public class Lakshmi
{
    // A representation of the NYSE as a Map stock code-stock
    private Map<String, Stock> NYSE;

    // A standard constructor
    public Lakshmi(Map<String, Stock> NYSE)
    {
        this.NYSE = NYSE;
    }

    /**
     * Returns the NYSE stock market
     * @return NYSE
     */
    public Map<String, Stock> getNYSE()
    {
        return NYSE;
    }

}
