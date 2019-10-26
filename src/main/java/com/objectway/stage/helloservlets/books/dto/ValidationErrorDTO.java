package com.objectway.stage.helloservlets.books.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.objectway.stage.helloservlets.utils.Pair;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO sent when a validation error occurs.
 * @see ErrorDTO
 */
public class ValidationErrorDTO extends ErrorDTO {
	private final Map<String, String> errors;

	/**
	 * Used for deserialization in tests
	 * @param message a generic error message
	 * @param errors the detailed field errors
	 * @see <a href="https://stackoverflow.com/questions/21920367/why-when-a-constructor-is-annotated-with-jsoncreator-its-arguments-must-be-ann">StackOverflow</a>
	 */
	@JsonCreator
	public ValidationErrorDTO(@JsonProperty("message") final String message, @JsonProperty("errors") final Map<String, String> errors) {
		super(message);
		this.errors = errors;
	}

	public ValidationErrorDTO(final String message, final List<Pair<String, String>> errors) {
		super(message);
		this.errors = errors.stream()
			.collect(
				Collectors.toMap(
					Pair::getFirst,
					Pair::getSecond
				)
			);
	}

	public ValidationErrorDTO(final String message, final BindingResult violations) {
		super(message);
		errors = violations.getFieldErrors().stream()
			.collect(
				Collectors.toMap(
					FieldError::getField,
					FieldError::getDefaultMessage
				)
			);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ValidationErrorDTO that = (ValidationErrorDTO) o;
		return Objects.equals(errors, that.errors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), errors);
	}

	@Override
	public String toString() {
		return "ValidationErrorDTO{" +
				"errors=" + errors +
				'}';
	}
}
