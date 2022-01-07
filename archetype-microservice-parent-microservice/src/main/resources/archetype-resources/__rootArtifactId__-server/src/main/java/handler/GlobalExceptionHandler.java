package ${package}.handler;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleConflict(IllegalArgumentException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(createErrorResponse(ex, request));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> resourceNotFoundException(IllegalArgumentException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(ex, request));
	}

	@ExceptionHandler({ Throwable.class, Exception.class, RuntimeException.class })
	public ResponseEntity<?> handleThrowable(Throwable ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse(ex, request));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(createErrorResponse(ex, request));
	}

	private static ErrorResponse createErrorResponse(Throwable ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
	}
}
