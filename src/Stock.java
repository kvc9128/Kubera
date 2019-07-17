/**
 * Used to represent a Stock ticker for a company
 */
public class Stock
{
    private String Code, Name, High, Low, Close, Volume, DivYield, Earning_per_share, Price_Earnings_to_growth_ratio, MarketCap;

    /**
     * Used to create a new Stock ticker for a new company on the Stock market
     * @param Code the NYSE approved Code
     * @param Name the NYSE approved Name
     * @param High the NYSE day's high for that stock
     * @param Low the NYSE day's low for that stock
     * @param Close the closing value for that day on the NYSE
     * @param Volume the number of publicly traded shares
     */
    public Stock(String Code, String Name, String High, String Low, String Close, String Volume, String DivYield, String Earning_per_share, String PEG, String marketCap)
    {
        this.Close = Close;
        this.Name = Name;
        this.Code = Code;
        this.High = High;
        this.Low = Low;
        this.Volume = Volume;
        this.DivYield = DivYield;
        this.Earning_per_share = Earning_per_share;
        this.Price_Earnings_to_growth_ratio = PEG;
        this.MarketCap = marketCap;
    }

    /**
     * Used to return the Dividend Yield
     * @return DivYield
     */
    public String getDivYield()
    {
       return this.DivYield;
    }

    /**
     * Used to return the Earning per Share
     * @return Earning_per_share
     */
    public String getEarning_per_share()
    {
        return this.Earning_per_share;
    }

    /**
     * Used to return the Price/Earnings to growth ratio
     * @return Price_Earnings to growth ratio
     */
    public String getPrice_Earnings_to_growth_ratio()
    {
        return this.Price_Earnings_to_growth_ratio;
    }

    /**
     * Used to return the company code
     * @return code
     */
    public String getCode()
    {
        return this.Code;
    }

    /**
     * Used to return the Closing value
     * @return close value
     */
    public String getClose()
    {
        return this.Close;
    }

    /**
     * Used to return the Highest value of the stock that day
     * @return high value
     */
    public String getHigh()
    {
        return this.High;
    }

    /**
     * Used to return the lowest value of the stock that day
     * @return low value
     */
    public String getLow()
    {
        return this.Low;
    }

    /**
     * Used to return the volume of stock available that day
     * @return available stock
     */
    public String getVolume()
    {
        return this.Volume;
    }

    /**
     * Used to return the Name of the Company
     * @return Company name
     */
    public String getName()
    {
        return this.Name;
    }

    /**
     * Used to return the Company's Market Capitalization.
     * @return Market Capitalization of the company
     */
    public String getMarketCap()
    {
        return this.MarketCap;
    }

    /**
     * A toString method to return the particulars of a given stock
     * @return a string representation of the stock ticker
     */
    @Override
    public String toString()
    {
        return String.format("Code : %s Name : %s High : %s Low : %s Close : %s Volume : %s DivYield : %s Earning_Per_Share : %s PEG ratio : %s Market_Capitalization : %s",
                Code, Name, High, Low, Close, Volume, DivYield, Earning_per_share, Price_Earnings_to_growth_ratio, MarketCap);
    }
}
