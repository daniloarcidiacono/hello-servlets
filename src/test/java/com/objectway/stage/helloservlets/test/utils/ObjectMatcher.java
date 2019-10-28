package com.objectway.stage.helloservlets.test.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * Custom ResultMatcher that deserializes the response body and compares it to a specified object.
 * @param <T> the type of the expected object
 */
public class ObjectMatcher<T> implements ResultMatcher {
	private static final ObjectMapper mapper = new ObjectMapper();
	private T expected;
	private TypeReference<T> typeReference;

	private ObjectMatcher(final T expected, final TypeReference<T> typeReference) {
		this.expected = expected;
		this.typeReference = typeReference;
	}

	@Override
	public void match(final MvcResult result) throws Exception {
		final byte[] serialized = result.getResponse().getContentAsByteArray();
		final T actual = mapper.readValue(serialized, typeReference);

		if (!actual.equals(expected)) {
			throw new AssertionError(String.format("Expected '%s' got '%s'", expected, actual));
		}
	}

	public static <T> ObjectMatcher<T> isEquals(final T object, final TypeReference<T> typeReference) {
		return new ObjectMatcher<>(object, typeReference);
	}
}
