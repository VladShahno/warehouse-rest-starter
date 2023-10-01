package warehouse.com.reststarter.exception;

public class BadRequestException extends ResourceException {

  public BadRequestException(final String message) {
    super(message);
  }

  public BadRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(final String message, final Object[] args, final Throwable cause) {
    super(message, args, cause);
  }
}
