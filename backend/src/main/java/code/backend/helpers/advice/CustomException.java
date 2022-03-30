package code.backend.helpers.advice;

public class CustomException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6659457952746574355L;

	public CustomException(String message) {
        super(message);
    }
}
