package com.objectway.stage.helloservlets.utils;

import java.util.Objects;

/**
 * Represents a pair of elements.
 * @param <S> the type of the first element
 * @param <T> the type of the second element.
 */
public class Pair<S, T> {
	private final S first;
	private final T second;

	private Pair(final S first, final T second) {
		this.first = first;
		this.second = second;
	}

	public static <S, T> Pair<S, T> of(final S first, final T second) {
		return new Pair<>(first, second);
	}

	public S getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(first, pair.first) &&
				Objects.equals(second, pair.second);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public String toString() {
		return "Pair{" +
				"first=" + first +
				", second=" + second +
				'}';
	}
}
