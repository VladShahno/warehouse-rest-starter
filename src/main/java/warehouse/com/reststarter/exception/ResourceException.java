package warehouse.com.reststarter.exception;

import java.util.Optional;

public abstract class ResourceException extends Exception {

  private final transient Object[] args;

  public ResourceException(final String message) {
    super(message);
    this.args = new Object[0];
  }

  public ResourceException(final String message, final Throwable cause) {
    super(message, cause);
    this.args = new Object[0];
  }

  public ResourceException(final String message, final Object[] args, final Throwable cause) {
    super(message, cause);
    this.args = Optional.ofNullable(args)
        .map(Object[]::clone)
        .orElse(new Object[0]);
  }

  public Object[] getArgs() {
    return args.clone();
  }
}
