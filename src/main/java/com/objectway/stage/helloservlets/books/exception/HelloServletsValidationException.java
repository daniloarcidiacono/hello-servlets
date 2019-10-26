package com.objectway.stage.helloservlets.books.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Exception thrown when a validation error occurs.
 */
public class HelloServletsValidationException extends Exception {
	/**
	 * The validation errors.
	 */
	private final Set<ConstraintViolation<Object>> violations;

	public HelloServletsValidationException(final String message, final Set<ConstraintViolation<Object>> violations) {
		super(message);
		this.violations = violations;
	}

	public Set<ConstraintViolation<Object>> getViolations() {
		return violations;
	}
}
