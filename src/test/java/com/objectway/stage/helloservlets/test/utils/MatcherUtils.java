package com.objectway.stage.helloservlets.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MatcherUtils {
	private MatcherUtils() {
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
