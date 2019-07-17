import java.util.Map;

/**
 * Represents the NYSE stock exchange
 * More code to come here that deals with
 *      *  1. dividing companies into small/mid/large cap values
 *      *  2. top 100 companies
 *      *  3. bottom 100 companies
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
     * A handy function that allows the user to access a particular stock
     * @param Code the key that is used to store the value
     * @return the stock ticker for that code
     */
    public Stock getAStock(String Code)
    {
        return Stock_Market.get(Code);
    }

}
