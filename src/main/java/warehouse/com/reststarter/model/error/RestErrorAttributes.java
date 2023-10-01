package warehouse.com.reststarter.model.error;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;
import warehouse.com.reststarter.exception.RestException;

@Slf4j
@RequiredArgsConstructor
public class RestErrorAttributes extends DefaultErrorAttributes {

  private static final String MESSAGE = "message";
  private static final String ERROR_TYPE = "errorType";

  private final InformationDisclosureHandlingConfig informationDisclosureHandlingConfig;

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest,
      ErrorAttributeOptions options) {

    var errorAttributes = super.getErrorAttributes(webRequest, options);

    var error = super.getError(webRequest);
    if (error instanceof RestException) {
      updateMessage(errorAttributes, (RestException) error);
      errorAttributes.put(ERROR_TYPE,
          ((RestException) error).getReason());  // The reason is the raw untranslated string.
      addErrorsMap(errorAttributes);
    } else if (informationDisclosureHandlingConfig.isEnabled()) {
      if (error != null) {
        log.error("Replaced error msg {}", error.getMessage());
      }
      errorAttributes.replace(MESSAGE, informationDisclosureHandlingConfig.getDisplayedMessage());
    }
    return errorAttributes;
  }

  private void updateMessage(Map<String, Object> errorAttributes, RestException restException) {
    String[] args = restException.getArgs();
    if (args == null) {
      return;
    }
    String message = (String) errorAttributes.get(MESSAGE);
    errorAttributes.put(MESSAGE, MessageFormat.format(message, args));
  }

  private void addErrorsMap(Map<String, Object> errorAttributes) {
    Map<String, Object> errors = new HashMap<>();
    String message = (String) errorAttributes.get(MESSAGE);
    errors.put("defaultMessage", message);

    errorAttributes.put("errors", errors);
  }
}

