package Server;

import Client.Portfolio;
import common.Duplexer;
import common.Kumbhakarna;
import common.Lakshmi;

import java.io.IOException;
import java.net.Socket;

import static common.Kumbhakarna.STOCK_ADDED;
import static common.Kumbhakarna.STOCK_DROPPED;

/**
 * A connection class used to represent an individual player on the server side.
 */
class IncomingConnection extends Thread
{
    private Duplexer connection;
    private Lakshmi lakshmi;
    private Portfolio portfolio;

    /**
     * A useful constructor called by Raavan to create a new user
     * @param connection
     */
    public IncomingConnection(Socket connection)
    {
        try
        {
            this.connection = new Duplexer(connection);
            this.lakshmi = Raavan.lakshmi;
            this.portfolio = new Portfolio(lakshmi);
        }
        catch (IOException io)
        {

        }
    }

    /**
     * while the user is using the software, it responds to client requests and
     * responds accordingly
     */
    public void run()
    {
        connection.send(Kumbhakarna.CONNECT);
        connection.Special_send(Kumbhakarna.STOCK_MARKET, Raavan.NYSE);
        //Primary loop, process incoming requests here
        try{
            while (true)
            {
                String message = connection.receive();
                System.out.println("Message: " + message);
                String[] tokens = message.split(" ");
                switch (tokens[0]){
                    case Kumbhakarna.ADD:
                        portfolio.add_to_portfolio(lakshmi.getAStock(tokens[1]));
                        connection.send(STOCK_ADDED);
                        break;
                    case Kumbhakarna.DROP:
                        portfolio.remove_from_portfolio(lakshmi.getAStock(tokens[1]));
                        connection.send(STOCK_DROPPED);
                        break;
                    case Kumbhakarna.STOP:
                        connection.close();
                        break;
                }
            }
        } catch (Exception ex)
        {
            connection.send(Kumbhakarna.ERROR);
        }
    }
}