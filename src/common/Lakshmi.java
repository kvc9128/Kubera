package common;

import Client.Observer;
import Server.Stock;

import java.util.LinkedList;
import java.util.List;
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
    public Map<String, Stock> Stock_Market;

    // a variable to depict status of software
    public Status status;

    // A standard constructor
    public Lakshmi(Map<String, Stock> NYSE)
    {
        this.Stock_Market = NYSE;
        this.observers = new LinkedList<>();
        this.status = Status.IN_USE;
    }

    /**
     * the observers of the model
     */
    private List<Observer<Lakshmi>> observers;

    /**
     * possible status of software
     */
    public enum Status
    {
        IN_USE, ERROR, STOP, STOCK_ADDED, STOCK_REMOVED
    }

    /**
     * The view calls this method to add themselves as an observer of the model
     */
    public void addObserver(Observer<Lakshmi> observer)
    {
        this.observers.add(observer);
    }

    /**
     * When the model changes, the observers are notified via their update method
     */
    private void alertObservers()
    {
        for(Observer<Lakshmi> obs: this.observers)
        {
            obs.update(this);
        }
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

    /**
     * to setup an error
     */
    public void error()
    {
        this.status = Status.ERROR;
        alertObservers();
    }

    /**
     * Used to display that the stock has been added to portfolio
     */
    public void stock_added()
    {
        this.status = Status.STOCK_ADDED;
        alertObservers();
        System.out.println("Stock added to portfolio");
    }

    /**
     * Used to display that the stock has been dropped from portfolio
     */
    public void stock_dropped()
    {
        this.status = Status.STOCK_REMOVED;
        alertObservers();
        System.out.println("Stock removed from portfolio");
    }

}
