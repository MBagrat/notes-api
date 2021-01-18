package net.kreddo.notes.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.kreddo.notes.controller.exception.InvalidInputException;
import net.kreddo.notes.exception.model.NotesApiError;
import net.kreddo.notes.service.exception.BusinessException;
import net.kreddo.notes.service.exception.EntityAlreadyExistException;
import net.kreddo.notes.service.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
   * is missing.
   *
   * @param ex MissingServletRequestParameterException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the NotesApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String errorMessage = ex.getParameterName() + " parameter is missing";
    return buildResponseEntity(new NotesApiError(BAD_REQUEST, errorMessage, ex));
  }

  /**
   * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
   *
   * @param ex HttpMediaTypeNotSupportedException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the NotesApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    String errorMessage = builder.substring(0, builder.length() - 2);
    return buildResponseEntity(new NotesApiError(UNSUPPORTED_MEDIA_TYPE, errorMessage, ex));
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the NotesApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    NotesApiError NotesApiError = new NotesApiError(BAD_REQUEST);
    NotesApiError.setMessage("Validation error");
    NotesApiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    NotesApiError.addValidationError(ex.getBindingResult().getGlobalErrors());
    return buildResponseEntity(NotesApiError);
  }

  /**
   * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
   *
   * @param ex HttpMessageNotReadableException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the NotesApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    log.info(
        "{} to {}",
        servletWebRequest.getHttpMethod(),
        servletWebRequest.getRequest().getServletPath());
    String error = "Malformed JSON request";
    return buildResponseEntity(new NotesApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

  /**
   * Handle HttpMessageNotWritableException.
   *
   * @param ex HttpMessageNotWritableException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the NotesApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "Error writing JSON output";
    return buildResponseEntity(new NotesApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
  }

  /**
   * Handle NoHandlerFoundException.
   *
   * @param ex
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    NotesApiError NotesApiError = new NotesApiError(BAD_REQUEST);
    NotesApiError.setMessage(
        String.format(
            "Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
    NotesApiError.setDebugMessage(ex.getMessage());
    return buildResponseEntity(NotesApiError);
  }

  /**
   * Handle InvalidInputException. Triggered when an invalid request parameter provided.
   *
   * @param ex InvalidInputException
   * @return the NotesApiError object
   */
  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntity(new NotesApiError(BAD_REQUEST, errorMessage, ex));
  }

  /**
   * Handle EntityAlreadyExistException. Triggered when an entity already exists in DB.
   *
   * @param ex EntityAlreadyExistException
   * @return the NotesApiError object
   */
  @ExceptionHandler(EntityAlreadyExistException.class)
  public ResponseEntity<Object> handleEntityExistException(EntityAlreadyExistException ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntity(new NotesApiError(CONFLICT, errorMessage, ex));
  }

  /**
   * Handle BusinessException. Triggered when a business rule in application is violated.
   *
   * @param ex BusinessException
   * @return the NotesApiError object
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntity(new NotesApiError(BAD_REQUEST, errorMessage, ex));
  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the NotesApiError object
   */
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    NotesApiError NotesApiError = new NotesApiError(BAD_REQUEST);
    NotesApiError.setMessage("Validation error");
    NotesApiError.addValidationErrors(ex.getConstraintViolations());
    return buildResponseEntity(NotesApiError);
  }

  /**
   * Handles EntityNotFoundException. Created to encapsulate errors with more detail than
   * javax.persistence.EntityNotFoundException.
   *
   * @param ex the EntityNotFoundException
   * @return the NotesApiError object
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    NotesApiError NotesApiError = new NotesApiError(NOT_FOUND);
    NotesApiError.setMessage(ex.getMessage());
    return buildResponseEntity(NotesApiError);
  }

  /**
   * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
   *
   * @param ex the DataIntegrityViolationException
   * @return the NotesApiError object
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(
          new NotesApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
    }
    return buildResponseEntity(new NotesApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the NotesApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    NotesApiError NotesApiError = new NotesApiError(BAD_REQUEST);
    NotesApiError.setMessage(
        String.format(
            "The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
    NotesApiError.setDebugMessage(ex.getMessage());
    return buildResponseEntity(NotesApiError);
  }

  private ResponseEntity<Object> buildResponseEntity(NotesApiError notesApiError) {
    return new ResponseEntity<>(notesApiError, notesApiError.getStatus());
  }
}
