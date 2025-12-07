package globalexceptions;
/**
 * exception thrown when there is an error parsing
 */
public class InvalidParseException extends RuntimeException {
    /**
     * exception which is thrown if an error is occurred while parsing
     *
     * @param message error message
     */
    public InvalidParseException(String message) {
        super(message);
    }
}
