package warehouse.com.reststarter.exception;

import org.springframework.http.HttpStatus;

public class InternalErrorException extends RestException {
  public InternalErrorException() {
    super(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public InternalErrorException(String messageId) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, messageId);
  }

  public InternalErrorException(String messageId, String... args) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, messageId, args);
  }

  public InternalErrorException(String messageId, Throwable throwable, String... args) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, messageId, throwable, args);
  }
}
