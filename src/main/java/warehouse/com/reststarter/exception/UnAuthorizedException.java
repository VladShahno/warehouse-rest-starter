package warehouse.com.reststarter.exception;

public class UnAuthorizedException extends ResourceException {

  public UnAuthorizedException(final String message) {
    super(message);
  }

  public UnAuthorizedException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UnAuthorizedException(final String message, final Object[] args, final Throwable cause) {
    super(message, args, cause);
  }
}
