package com.objectway.stage.helloservlets.books.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.Objects;

/**
 * DTO for an API error.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {
	private String message;
	private StackTraceElement[] stackTrace;

	public ErrorDTO() {
	}

	public ErrorDTO(String message) {
		this.message = message;
	}

	public ErrorDTO(String message, StackTraceElement[] stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ErrorDTO errorDTO = (ErrorDTO) o;
		return Objects.equals(message, errorDTO.message) &&
				Arrays.equals(stackTrace, errorDTO.stackTrace);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(message);
		result = 31 * result + Arrays.hashCode(stackTrace);
		return result;
	}

	@Override
	public String toString() {
		return "ErrorDTO{" +
				"message='" + message + '\'' +
				", stackTrace=" + Arrays.toString(stackTrace) +
				'}';
	}
}
