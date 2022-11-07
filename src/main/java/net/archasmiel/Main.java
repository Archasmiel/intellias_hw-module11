package net.archasmiel;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		List<String> names = List.of("Kolya", "Tolya", "Dolya", "Volya", "Rolya", "Lolya");
		String[] array = {"1, 2, 0", "4, 5"};
		String [] arr = {"0,2,1","4,5","123, 88"};
		Stream<Integer> str1 = Stream.of(1, 2, 3, 4, 5);
		Stream<Integer> str2 = Stream.of(6, 7, 8, 9, 10);

		System.out.println(StreamHelper.oddNames(names));
		System.out.println(StreamHelper.sortList(names));
		System.out.println(StreamHelper.parseArray(array));
		System.out.println(StreamHelper.parseArray(arr));
		System.out.println(StreamHelper.iterateLKG(25214903917L, 11L, (long) Math.pow(2, 48)).limit(10).toList());
		System.out.println(StreamHelper.zip(str1, str2).toList());
	}



}
