package common;

/**
 * The interface provides constants for all of the messages that
 * are communicated between the server and the clients.
 *
 * @author srikamal
 *
 */
public interface Kumbhakarna
{
    /**
     * Request sent from the server to the client after the client initially
     * opens a connection to the server. This is the first part of the
     * handshake used to establish that the client understand the protocol
     */
    public static final String CONNECT = "CONNECT";

    /**
     * Request sent from the client to the server when client wants to add a
     * stock ticker to his portfolio.
     *
     * The request will also include the code of the company. eg: ADD XYZ
     */
    public static final String ADD = "ADD";

    /**
     * Request sent from the client to the server when the client wants to drop
     * a stock ticker from his portfolio.
     *
     * The request will also include the code of the company. eg: DROP XYZ
     */
    public static final String DROP = "DROP";

    /**
     *  Request sent from the server to the client when any kind of error has
     *  resulted from a bad client response. No response is expected from the
     *  client and the connection is terminated (as is the game).
     */
    public static final String ERROR = "ERROR";

    /**
     * Request sent from the client to the server when the client wants to stop trading for the day
     */
    public static final String STOP = "STOP";

    /**
     * Response from server to client acknowledging that stock has been added
     */
    public static final String STOCK_ADDED = "STOCK_ADDED";

    /**
     * Response from server to client acknowledging that stock has been dropped
     */
    public static final String STOCK_DROPPED = "STOCK_DROPPED";

}
