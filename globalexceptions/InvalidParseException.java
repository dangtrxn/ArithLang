package globalexceptions;
/**
 * exception thrown when there is an error parsing
 */
public class InvalidParse extends RuntimeException {
    /**
     * exception which is thrown if an error is occurred while parsing
     *
     * @param message error message
     */
    public InvalidParse(String message) {
        super(message);
    }
}
