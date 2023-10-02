package warehouse.com.reststarter.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {
  public NotFoundException() {
    super(HttpStatus.NOT_FOUND);
  }

  public NotFoundException(String messageId) {
    super(HttpStatus.NOT_FOUND, messageId);
  }

  public NotFoundException(String messageId, String... args) {
    super(HttpStatus.NOT_FOUND, messageId, args);
  }

  public NotFoundException(String messageId, Throwable throwable, String... args) {
    super(HttpStatus.NOT_FOUND, messageId, throwable, args);
  }
}
