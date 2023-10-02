package warehouse.com.reststarter.exception.handler;

import static warehouse.com.reststarter.common.Constants.INVALID_SPECIAL_CHARACTERS_MESSAGE;

import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import warehouse.com.reststarter.exception.BadRequestException;
import warehouse.com.reststarter.exception.CustomException;
import warehouse.com.reststarter.exception.CustomRuntimeException;
import warehouse.com.reststarter.exception.DuplicateResourceException;
import warehouse.com.reststarter.exception.MultipleIllegalArgumentsException;
import warehouse.com.reststarter.exception.ResourceNotFoundException;
import warehouse.com.reststarter.exception.RestException;
import warehouse.com.reststarter.exception.UnAuthorizedException;
import warehouse.com.reststarter.model.error.ErrorDetails;
import warehouse.com.reststarter.model.error.InformationDisclosureHandlingConfig;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String SEPARATOR = ": ";
  private static final String EMPTY_STRING = "";

  private final MessageSource messageSource;
  private final InformationDisclosureHandlingConfig informationDisclosureHandlingConfig;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers, final HttpStatusCode status,
      final WebRequest request) {
    log.error(ex.getMessage(), ex);

    BindingResult result = ex.getBindingResult();
    List<String> errorMessages = result.getAllErrors()
        .stream()
        .map(objectError -> getErrorMessage(request.getLocale(), objectError))
        .collect(Collectors.toList());
    return new ResponseEntity<>(new ErrorDetails(errorMessages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(
      final ResourceNotFoundException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), ex.getArgs());
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({DuplicateResourceException.class})
  public ResponseEntity<Object> handleDuplicateResourceException(
      final DuplicateResourceException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), ex.getArgs());
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.CONFLICT);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessedDeniedException(final AccessDeniedException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), null, true);
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), null, true);
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), ex.getArgs());
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({UnAuthorizedException.class})
  public ResponseEntity<Object> handleUnAuthorizedException(final UnAuthorizedException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), ex.getArgs());
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({UnsupportedOperationException.class})
  public ResponseEntity<Object> handleUnsupportedOperationException(
      final UnsupportedOperationException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), null, true);
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler({CustomRuntimeException.class, CustomException.class})
  public ResponseEntity<Object> handleCustomExceptions(final Exception ex, final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), null);
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MultipleIllegalArgumentsException.class})
  public ResponseEntity<Object> handleMultipleIllegalArgumentException(
      final MultipleIllegalArgumentsException ex,
      final Locale locale) {
    logger.error("API Multiple Illegal Argument Error ", ex);
    List<String> errorMessages = ex.getAllErrors()
        .stream()
        .map(errorMsg -> getMessage(locale, errorMsg, null))
        .collect(Collectors.toList());
    return new ResponseEntity<>(new ErrorDetails(errorMessages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolationException(
      final ConstraintViolationException ex,
      final Locale locale) {
    logger.error("API Constraint Violation Exception Error ", ex);
    List<String> errorMessages = ex.getConstraintViolations()
        .stream()
        .map(constraintViolation -> getErrorMessage(locale, constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()))
        .collect(Collectors.toList());
    return new ResponseEntity<>(new ErrorDetails(errorMessages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MultipartException.class})
  public ResponseEntity<Object> handleFileUploadException(final Exception ex, final Locale locale) {
    logger.error("API File Upload Error ", ex);
    String errorMessage = getMessage(locale, ex.getMessage(), null, true);
    return new ResponseEntity<>(new ErrorDetails(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({RestException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(final RestException ex,
      final Locale locale) {
    log.error(ex.getMessage(), ex);
    String errorMessage = getMessage(locale, ex.getMessage(), ex.getArgs());
    return new ResponseEntity<>(new ErrorDetails(errorMessage), ex.getStatusCode());
  }

  private String getMessage(Locale locale, String message, Object[] o) {
    return getMessage(locale, message, o, false);
  }

  private String getMessage(Locale locale, String message, Object[] o,
      boolean enableInformationDisclosureHandling) {
    try {
      if (enableInformationDisclosureHandling && informationDisclosureHandlingConfig.isEnabled()) {
        return informationDisclosureHandlingConfig.getDisplayedMessage();
      }
      return messageSource.getMessage(message, o, locale);
    } catch (NoSuchMessageException e) {
      return message;
    }
  }

  private String getErrorMessage(Locale locale, ObjectError objectError) {
    String errorField =
        objectError instanceof FieldError ? ((FieldError) objectError).getField() : null;
    return getErrorMessage(locale, objectError.getDefaultMessage(), errorField, false);
  }

  private String getErrorMessage(Locale locale, String errorMessage, String errorPath) {
    String errorField = String.valueOf(errorPath.split("\\.", 2)[1]);
    String message = getErrorMessage(locale, errorMessage, errorField, true);
    logger.warn(errorPath + SEPARATOR + message);
    return message;
  }

  private String getErrorMessage(
      Locale locale,
      String errorMessage,
      String errorField,
      boolean enableInformationDisclosureHandling) {
    try {
      if (enableInformationDisclosureHandling && informationDisclosureHandlingConfig.isEnabled()) {
        return informationDisclosureHandlingConfig.getDisplayedMessage();
      }
      if (INVALID_SPECIAL_CHARACTERS_MESSAGE.equals(errorMessage) && StringUtils.isNotEmpty(
          errorField)) {
        return String.format(errorMessage, errorField);
      }
      return StringUtils.isNotEmpty(errorMessage) ? messageSource.getMessage(errorMessage, null,
          locale) : EMPTY_STRING;
    } catch (NoSuchMessageException e) {
      return errorMessage;
    }
  }
}
