package warehouse.com.reststarter.exception;

import java.util.ArrayList;
import java.util.List;

public class MultipleIllegalArgumentsException extends IllegalArgumentException {

  private final List<String> errorMessages = new ArrayList<>();

  public MultipleIllegalArgumentsException(final String message) {
    super(message);
  }

  public MultipleIllegalArgumentsException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public MultipleIllegalArgumentsException(final List<String> messages) {
    this.errorMessages.addAll(messages);
  }

  public List<String> getAllErrors() {
    return errorMessages;
  }

  private String getFormattedErrorMessage() {
    StringBuilder sb = new StringBuilder("Validation failed for arguments.");
    for (String errorMsg : this.errorMessages) {
      sb.append("[").append(errorMsg).append("] ");
    }
    return sb.toString();
  }

  @Override
  public String getMessage() {
    if (!errorMessages.isEmpty()) {
      return getFormattedErrorMessage();
    } else {
      return super.getMessage();
    }
  }
}
