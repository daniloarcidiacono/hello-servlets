package com.objectway.stage.helloservlets.books.dto;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO sent when a validation error occurs.
 * @see ErrorDTO
 */
public class ValidationErrorDTO extends ErrorDTO {
	private final Map<String, String> errors;

	public ValidationErrorDTO(final String message, final Set<ConstraintViolation<Object>> violations) {
		super(message);
		errors = violations.stream()
			.collect(
				Collectors.toMap(
					x -> x.getPropertyPath().toString(),
					ConstraintViolation::getMessage
				)
			);
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}
