package net.archasmiel;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamHelper {

	private static final Random RANDOM = new Random();

	private StreamHelper() {

	}

	public static String oddNames(List<String> names) {
		return IntStream
			.range(0, names.size())
			.filter(i -> i % 2 == 1)
			.mapToObj(e -> e + ". " + names.get(e))
			.collect(Collector.of(
				StringBuilder::new,
				(builder, o) -> builder.append(o).append(", "),
				StringBuilder::append,
				builder -> {
					if (!builder.isEmpty()) {
						builder.delete(builder.length()-2, builder.length());
					}
					return builder.append("...").toString();
				}
			));
	}

	public static List<String> sortList(List<String> names) {
		return names.stream()
			.map(String::toUpperCase)
			.sorted(Comparator.reverseOrder())
			.toList();
	}

	public static String parseArray(String[] numbers) {
		return Stream.of(numbers)
			.flatMap(Pattern.compile(", *")::splitAsStream)
			.map(Integer::parseInt)
			.sorted()
			.collect(Collector.of(
				StringBuilder::new,
				(builder, o) -> builder.append(o).append(", "),
				StringBuilder::append,
				builder -> {
					if (!builder.isEmpty()) {
						builder.delete(builder.length()-2, builder.length());
					}
					return builder.toString();
				}
			));
	}

	public static Stream<Long> iterateLKG(long a, long c, long m) {
		Iterable<Long> iterable = () -> new Iterator<>() {
			final long seed = RANDOM.nextLong();
			long prev;
			boolean started;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Long next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				if (started)
					prev = (a * prev * c) % m;
				else {
					prev = seed;
					started = true;
				}
				return prev;
			}
		};

		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
		Iterator<T> iterator1 = Objects.requireNonNull(first).iterator();
		Iterator<T> iterator2 = Objects.requireNonNull(second).iterator();

		Iterable<T> iterable = () -> new Iterator<>() {
			boolean isFirst = true;

			@Override
			public boolean hasNext() {
				return (iterator1.hasNext() && iterator2.hasNext()) ||
					(!iterator1.hasNext() && iterator2.hasNext() && !isFirst);
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				T ret = (isFirst ? iterator1 : iterator2).next();
				isFirst = !isFirst;
				return ret;
			}
		};

		return StreamSupport.stream(iterable.spliterator(), false);
	}


}
