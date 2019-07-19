package Client;

import common.Indrajit;
import common.Kumbhakarna;
import common.Lakshmi;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
    private Lakshmi stock_market;
    /** decides whether the user wants to stop or not*/
    private Boolean STOP;
    /** A variable to represent a portfolio*/
    private Portfolio portfolio;

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
     * @param lakshmi stock market
     * @throws Indrajit custom error
     */
    public User(String host, int port, Lakshmi lakshmi) throws Indrajit
    {
        try
        {
            this.clientSocket = new Socket(host, port);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.stock_market = lakshmi;
            this.STOP = true;
            this.portfolio = new Portfolio(stock_market);

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

            if (Kumbhakarna.ERROR.equals(request)) {
                error();
            } else {
                System.err.println("Unrecognized request: " + request);
                this.stop();
            }
        }
        this.close();
    }


}
