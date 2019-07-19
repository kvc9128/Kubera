package common;

/**
 * A custom exception thrown from any of the
 * classes if something goes wrong.
 */
public class Indrajit extends Exception
{
    /**
     * Convenience constructor to create a new exception
     * with an error message.
     *
     * @param message The error message associated with the exception.
     */
    public Indrajit(String message) {
        super(message);
    }

    /**
     * Convenience constructor to create a new exception
     * as a result of some other exception.
     *
     * @param cause The root cause of the exception.
     */
    public Indrajit(Throwable cause) {
        super(cause);
    }

    /**
     * * Convenience constructor to create a new exception
     * as a result of some other exception.
     *
     * @param message The message associated with the exception.
     * @param cause The root cause of the exception.
     */
    public Indrajit(String message, Throwable cause) {
        super(message, cause);
    }
}
