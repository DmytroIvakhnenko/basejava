package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class MainStreams {

    static boolean isEven(int i) {
        return i % 2 == 0;
    }

    static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    static List<Integer> oddOrEvenByStreams(List<Integer> integers) {
        return integers.stream()
                .collect(partitioningBy(MainStreams::isEven))
                .get(!isEven((integers.stream()
                        .mapToInt(Integer::intValue)
                        .sum())));
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> evenInts = new ArrayList<>();
        List<Integer> oddInts = new ArrayList<>();
        int sum = 0;
        for (Integer i : integers) {
            if (isEven(i)) {
                evenInts.add(i);
            } else {
                oddInts.add(i);
            }
            sum += i;
        }
        return isEven(sum) ? oddInts : evenInts;
    }

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9, 1, 5, 5, 3, 5, 2, 2}));
        System.out.println("result: " + oddOrEven(Arrays.asList(9, 1, 2, 5, 3, 5, 2, 4, 5, 8)));
        System.out.println("result: " + oddOrEven(Arrays.asList(9, 1, 2, 5, 3, 5, 2, 1, 11, 22)));
        System.out.println("result: " + oddOrEvenByStreams(Arrays.asList(9, 1, 2, 5, 3, 5, 2, 4, 5, 8)));
        System.out.println("result: " + oddOrEvenByStreams(Arrays.asList(9, 1, 2, 5, 3, 5, 2, 1, 11, 22)));
    }
}
