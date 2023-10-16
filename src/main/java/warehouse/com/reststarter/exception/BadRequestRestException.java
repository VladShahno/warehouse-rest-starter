package warehouse.com.reststarter.exception;

import org.springframework.http.HttpStatus;

public class BadRequestRestException extends RestException {

  public BadRequestRestException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public BadRequestRestException(String messageId) {
    super(HttpStatus.BAD_REQUEST, messageId);
  }

  public BadRequestRestException(String messageId, String... args) {
    super(HttpStatus.BAD_REQUEST, messageId, args);
  }

  public BadRequestRestException(String messageId, Throwable throwable, String... args) {
    super(HttpStatus.BAD_REQUEST, messageId, throwable, args);
  }
}
