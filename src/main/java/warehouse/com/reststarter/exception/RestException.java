package warehouse.com.reststarter.exception;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RestException extends ResponseStatusException {

  private final String[] args;

  public RestException(HttpStatus status) {
    super(status);
    this.args = null;
  }

  public RestException(HttpStatus status, String messageId) {
    super(status, messageId);
    this.args = null;
  }

  public RestException(HttpStatus status, String messageId, String... args) {
    super(status, messageId);
    this.args = args;
  }

  public RestException(HttpStatus status, String messageId, Throwable throwable, String... args) {
    super(status, messageId, throwable);
    this.args = args;
  }

  public String[] getArgs() {
    return Optional.ofNullable(args)
        .map(String[]::clone)
        .orElse(null);
  }
}
