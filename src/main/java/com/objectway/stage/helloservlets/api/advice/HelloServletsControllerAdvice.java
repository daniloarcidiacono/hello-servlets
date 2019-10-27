package com.objectway.stage.helloservlets.api.advice;

import com.objectway.stage.helloservlets.books.dto.ErrorDTO;
import com.objectway.stage.helloservlets.books.dto.ValidationErrorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Please note that ResponseEntityExceptionHandler is a convenient base class for @ControllerAdvice classes that wish to
 * provide centralized exception handling across all @RequestMapping methods through @ExceptionHandler methods.
 *
 * ResponseEntityExceptionHandler does bring extra handlers but you don't necessarily need to extend it.
 * The key is to have @RestControllerAdvice and write your own @ExceptionHandler annotated methods.
 */
@RestControllerAdvice
// This injects a ResourcePropertySource into Environment, which is also looked up by @Value (through AutowiredAnnotationBeanPostProcessor),
// which in turn uses the Environment because it is contaqined in AbstractBeanFactory#embeddedValueResolvers
// that is used by AbstractBeanFactory#resolveEmbeddedValue()
//
// @PropertySource --> ResourcePropertySource added to Environment
// @Value processed by AutowiredAnnotationBeanPostProcessor --> invoke AutowiredFieldElement#inject --> ... -> AbstractBeanFactory#resolveEmbeddedValue()
@PropertySource("WEB-INF/spring/application.properties")
public class HelloServletsControllerAdvice /* extends ResponseEntityExceptionHandler */ {
	@Value("${error.reporting.includeStackTrace:false}")
	private boolean includeStackTrace;

	@ExceptionHandler(Exception.class)
	public ErrorDTO handleException(final Exception ex) {
		return new ErrorDTO(ex.getMessage(), includeStackTrace ? ex.getStackTrace() : null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorDTO handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
		return new ValidationErrorDTO("Validation error", exception.getBindingResult());
	}
}
