package Client;

import Server.Stock;
import common.Indrajit;
import common.Kumbhakarna;
import common.Lakshmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class User
{
    /** client socket to communicate with server */
    private Socket clientSocket;
    /** used to read requests from the server */
    private Scanner networkIn;
    /** Used to write responses to the server. */
    private PrintStream networkOut;
    /** the model which keeps track of the stock market */
    public Lakshmi stock_market;
    /** decides whether the user wants to stop or not*/
    private Boolean STOP;
    /** A variable to represent a portfolio*/
    private Portfolio portfolio;
    /** A variable to represent a stock market*/
    private Map<String, Stock> S_M = new HashMap<>();
    /** A object input stream to accept the hashmap*/
    private ObjectInputStream is;

    /**
     * Called when the server sends an ERROR message
     *
     */
    public void error()
    {
        User.dPrint( '!' + Kumbhakarna.ERROR + "!");
        dPrint( "Fatal error: ");
        this.stock_market.error();
        this.stop();
    }

    /**
     * Print method that does something only if DEBUG is true
     *
     * @param logMsg the message to log
     */
    private static void dPrint( Object logMsg )
    {
        System.out.println( logMsg );
    }

    /**
     * Used by the GUI to connect with the server
     * @param host host name
     * @param port port number
     * @throws Indrajit custom error
     */
    public User(String host, int port) throws Indrajit
    {
        try
        {
            this.clientSocket = new Socket(host, port);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.STOP = true;
            this.stock_market = new Lakshmi();
            this.is = new ObjectInputStream(clientSocket.getInputStream());

            String request = this.networkIn.next();
            if (!request.equals(Kumbhakarna.CONNECT ))
            {
                throw new Indrajit("Expected CONNECT from server");
            }
            User.dPrint("Connected to Server " + this.clientSocket);
        }catch (IOException io)
        {
            throw new Indrajit(io);
        }
    }

    /**
     * Called from the GUI when it is ready to start receiving messages
     * from the server.
     */
    public void startListener() {
        new Thread(() -> this.run()).start();
    }

    /**
     * This method should be called at the end of the game to
     * close the client connection.
     */
    public void close()
    {
        try {
            this.clientSocket.close();
        }
        catch( IOException ioe ) {
            // squash
        }
    }

    /**
     * UI wants to add a new stock to portfolio.
     *
     * @param code the code of the stock
     */
    public void ADD(String code)
    {
        this.networkOut.println( Kumbhakarna.ADD + " " + code );
        portfolio.add_to_portfolio(stock_market.getAStock(code));
    }

    /**
     * UI wants to remove a stock from portfolio.
     *
     * @param code the code of the stock
     */
    public void DROP(String code)
    {
        this.networkOut.println( Kumbhakarna.DROP + " " + code );
        portfolio.remove_from_portfolio(stock_market.getAStock(code));
    }

    /**
     * UI wants to stop the program execution
     *
     */
    public void stop()
    {
        this.STOP = false;
        this.networkOut.println( Kumbhakarna.STOP );
    }

    /**
     * Run the main client loop. Intended to be started as a separate
     * thread internally. This method is made private so that no one
     * outside will call it or try to start a thread on it.
     */
    private void run() throws NoSuchElementException {
        while (this.STOP)
        {
            String request = this.networkIn.next();
            User.dPrint( "Net message in = \"" + request + '"' );

            if (Kumbhakarna.ERROR.equals(request))
            {
                error();
            }
            else if (Kumbhakarna.STOCK_ADDED.equals(request))
            {
                this.stock_market.stock_added();
            }
            else if (Kumbhakarna.STOCK_DROPPED.equals(request))
            {
                this.stock_market.stock_dropped();
            }
            else if (Kumbhakarna.STOCK_MARKET.equals(request))
            {
                try
                {
                    System.out.println("getting stocks");
                    Object object = is.readObject();
                    this.S_M = (HashMap<String, Stock>) object;
                    this.stock_market.addStockMarket(S_M);
                    System.out.println(this.stock_market.toString());
                    this.portfolio = new Portfolio(stock_market);

                }
                catch (IOException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.err.println("Unrecognized request: " + request);
                this.stop();
            }
        }
        this.close();
    }


}
