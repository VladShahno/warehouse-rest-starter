package warehouse.com.reststarter.exception;

import org.springframework.http.HttpStatus;

public class EntityExistsException extends RestException {

  public EntityExistsException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public EntityExistsException(String messageId) {
    super(HttpStatus.BAD_REQUEST, messageId);
  }

  public EntityExistsException(String messageId, String... args) {
    super(HttpStatus.BAD_REQUEST, messageId, args);
  }

  public EntityExistsException(String messageId, Throwable throwable, String... args) {
    super(HttpStatus.BAD_REQUEST, messageId, throwable, args);
  }

}
