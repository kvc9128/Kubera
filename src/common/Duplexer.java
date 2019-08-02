package common;

import Server.Stock;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

/**
 * Class to handle a socket's input and output
 */
public class Duplexer implements AutoCloseable{
    /** The socket the duplexer acts on*/
    private Socket socket;
    /** Scanner to handle receiving of messages*/
    private Scanner in;
    /** PrintWriter to handle sending of message*/
    private PrintWriter out;

    public Duplexer(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Send a message by the socket
     * @param message: message to send
     */
    public void send(String message){
        out.println(message);
        out.flush();
    }

    /**
     * sends the hash map over the network
     * @param message STOCK_MARKET
     * @param NYSE
     */
    public void Special_send(String message, Map<String, Stock> NYSE)
    {
        out.println(message);
        try
        {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(NYSE);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        out.flush();
    }

    /**
     * Get a message from the socket
     * @return message in the socket's output buffer
     */
    public String receive(){
        return in.nextLine();
    }

    /**
     * Close the socket
     */
    public void close() throws IOException{
        socket.close();
    }
}