package Client;

import Server.*;
import common.*;

import java.util.ArrayList;
import java.util.List;

public class Portfolio
{
    public List<Stock> Noted_Stocks;

    /**
     * A constructor that allows each new user to create a personal portfolio
     * @param SM a stock market
     */
    public Portfolio(Lakshmi SM)
    {
        this.Noted_Stocks = new ArrayList<>();
    }

    /**
     * A function that allows the user to add a stock to their portfolio
     * @param stock
     */
    public void add_to_portfolio(Stock stock)
    {
        Noted_Stocks.add(stock);
    }

    public void remove_from_portfolio(Stock stock)
    {
        if (Noted_Stocks.contains(stock))
        {
            System.out.println("ERROR");
        }
        else
        {
            Noted_Stocks.remove(stock);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder stocks = new StringBuilder();
        for (Stock stock: Noted_Stocks)
        {
            stocks.append(stock.toString()).append("\n");
        }
        return stocks.toString();
    }

    public boolean Is_in_portfolio(Stock stock)
    {
        return Noted_Stocks.contains(stock);
    }

    public List<Stock> return_all_stocks()
    {
        return Noted_Stocks;
    }
}
