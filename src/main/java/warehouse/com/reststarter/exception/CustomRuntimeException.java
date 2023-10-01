package warehouse.com.reststarter.exception;

public class CustomRuntimeException extends RuntimeException {

  public CustomRuntimeException(final String message) {
    super(message);
  }

  public CustomRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
