package warehouse.com.reststarter.exception;

public class CustomException extends Exception {

  public CustomException(final String message) {
    super(message);
  }

  public CustomException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
