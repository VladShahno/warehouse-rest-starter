package warehouse.com.reststarter.exception;

public class ResourceNotFoundException extends ResourceException {

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(final String message, final Object[] args,
      final Throwable cause) {
    super(message, args, cause);
  }
}
