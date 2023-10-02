package warehouse.com.reststarter.exception.handler;


import static warehouse.com.reststarter.common.Constants.INVALID_FIELD_FORMAT_MESSAGE;
import static warehouse.com.reststarter.common.Constants.INVALID_SPECIAL_CHARACTERS_MESSAGE;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import warehouse.com.reststarter.model.error.ErrorResponse;

@ControllerAdvice
public class ValidationExceptionHandler {

  private static final Pattern COMPILED_GENERIC_PARAMETER_PATTERN = Pattern.compile("<.*>");

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleFieldValidationException(BindException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleParameterValidationException(
      ConstraintViolationException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidFormatOfInputParameterException(
      InvalidFormatException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      final MethodArgumentTypeMismatchException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(JsonParseException.class)
  public ResponseEntity<ErrorResponse> handleJsonParseException(
      final JsonParseException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MismatchedInputException.class)
  public ResponseEntity<ErrorResponse> handleMismatchedInputException(
      MismatchedInputException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception), HttpStatus.BAD_REQUEST);
  }

  private ErrorResponse buildErrorResponse(MismatchedInputException exception) {
    var references = exception.getPath();
    var fieldName = references.isEmpty() ? null : references.get(0).getFieldName();

    return new ErrorResponse(List.of(ErrorResponse.ErrorDetail
        .builder()
        .field(fieldName)
        .message(String.format(INVALID_FIELD_FORMAT_MESSAGE, fieldName))
        .build()));
  }

  private ErrorResponse buildErrorResponse(MethodArgumentTypeMismatchException exception) {
    return new ErrorResponse(List.of(ErrorResponse.ErrorDetail
        .builder()
        .field(exception.getName())
        .message(String.format("Failed to convert value of \"%s\" to required type",
            exception.getName()))
        .build()));
  }

  private ErrorResponse buildErrorResponse(JsonParseException exception) {
    return new ErrorResponse(List.of(ErrorResponse.ErrorDetail
        .builder()
        .message(exception.getOriginalMessage())
        .type("JSON")
        .build()));
  }

  private ErrorResponse buildErrorResponse(BindException exception) {
    var errors = exception
        .getAllErrors()
        .stream()
        .map(this::buildErrorDetail)
        .collect(Collectors.toList());

    return new ErrorResponse(errors);
  }

  private ErrorResponse.ErrorDetail buildErrorDetail(final ObjectError error) {
    var message = INVALID_SPECIAL_CHARACTERS_MESSAGE.equals(error.getDefaultMessage())
        ? buildMessage(error)
        : error.getDefaultMessage();

    var fieldName = error instanceof FieldError ? ((FieldError) error).getField() : null;

    var type = getErrorType(getMessageTemplate(error));

    return ErrorResponse.ErrorDetail
        .builder()
        .field(fieldName)
        .message(message)
        .type(type)
        .build();
  }

  private String buildMessage(ObjectError error) {
    return String.format(INVALID_SPECIAL_CHARACTERS_MESSAGE, ((FieldError) error).getField());
  }

  private ErrorResponse buildErrorResponse(ConstraintViolationException exception) {
    var errors = exception
        .getConstraintViolations()
        .stream()
        .map(this::buildErrorDetail)
        .collect(Collectors.toList());

    return new ErrorResponse(errors);
  }

  private ErrorResponse.ErrorDetail buildErrorDetail(
      final ConstraintViolation<?> constraintViolation) {

    var message = INVALID_SPECIAL_CHARACTERS_MESSAGE.equals(constraintViolation.getMessage())
        ? buildMessage(constraintViolation)
        : constraintViolation.getMessage();

    var fieldName = Optional
        .ofNullable(constraintViolation.getPropertyPath())
        .map(Path::toString)
        .orElse(null);

    return ErrorResponse
        .ErrorDetail
        .builder()
        .field(fieldName)
        .message(message)
        .type(getErrorType(constraintViolation.getMessageTemplate()))
        .build();
  }

  private String buildMessage(ConstraintViolation<?> constraintViolation) {
    var pathParts = constraintViolation.getPropertyPath().toString().split("\\.");
    var fieldName = isGenericParameter(pathParts)
        ? pathParts[pathParts.length - 2]
        : pathParts[pathParts.length - 1];
    return String.format(INVALID_SPECIAL_CHARACTERS_MESSAGE, fieldName);
  }

  private boolean isGenericParameter(String[] pathParts) {
    return COMPILED_GENERIC_PARAMETER_PATTERN.matcher(pathParts[pathParts.length - 1]).find()
        && pathParts.length > 1;
  }

  private String getMessageTemplate(final ObjectError error) {
    try {
      return error.unwrap(ConstraintViolationImpl.class).getMessageTemplate();
    } catch (Exception e) {
      return null;
    }
  }

  private String getErrorType(String messageTemplate) {
    return StringUtils.strip(messageTemplate, "{}");
  }

  private ErrorResponse buildErrorResponse(InvalidFormatException exception) {
    var error = buildErrorDetail(exception);
    return new ErrorResponse(List.of(error));
  }

  private ErrorResponse.ErrorDetail buildErrorDetail(InvalidFormatException exception) {
    var references = exception.getPath();
    var fieldName = references.isEmpty() ? null : references.get(0).getFieldName();

    var message = String.format(INVALID_FIELD_FORMAT_MESSAGE, fieldName);

    var type = exception.getTargetType().getSuperclass().getSimpleName();

    return ErrorResponse
        .ErrorDetail
        .builder()
        .field(fieldName)
        .message(message)
        .type(type)
        .build();
  }
}
