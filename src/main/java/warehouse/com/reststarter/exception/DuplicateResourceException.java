package warehouse.com.reststarter.exception;

public class DuplicateResourceException extends ResourceException {

  public DuplicateResourceException(final String message) {
    super(message);
  }

  public DuplicateResourceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DuplicateResourceException(final String message, final Object[] args,
      final Throwable cause) {
    super(message, args, cause);
  }
}
