package ${package}.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	private Tracer sleuthTracer;

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

	private ErrorResponse createErrorResponse(Throwable ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
//		addNewSpan();
		tagError(ex.getMessage(), ex);
		return new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
	}

//	private void addNewSpan() {
//		log.info("I'm in the original span");
//
//		Span newSpan = sleuthTracer.nextSpan().name("newSpan").start();
//		try (SpanInScope ws = sleuthTracer.withSpan(newSpan.start())) {
//			sleuthTracer.currentSpan().tag("rtr", "yo");
//			try {
//				Thread.sleep(1000L);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			log.info("I'm in the new span doing some cool work that needs its own span");
//		} finally {
//			newSpan.end();
//		}
//
//		log.info("I'm in the original span");
//	}

	private void tagError(String message, Throwable ex) {
		if (sleuthTracer != null) {
			Span span = sleuthTracer.currentSpan();
			span.event("ERROR: " + message);
			span.tag("errorTrace", getStackTrace(ex));
		}
	}

	public String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
