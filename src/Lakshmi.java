import java.util.Map;

/**
 * Represents the NYSE stock exchange
 */
public class Lakshmi
{
    // A representation of the NYSE as a Map <stock code listing of stock>
    private Map<String, Stock> Stock_Market;

    // A standard constructor
    public Lakshmi(Map<String, Stock> NYSE)
    {
        this.Stock_Market = NYSE;
    }

    /**
     * Returns the stock market
     * @return stock market
     */
    public Map<String, Stock> getNYSE()
    {
        return this.Stock_Market;
    }

    /**
     * More code to come here that deals with
     *  1. dividing companies into small/mid/large cap values
     *  2. top 100 companies
     *  3. bottom 100 companies
     *  4. return a individual stock
     */

}
