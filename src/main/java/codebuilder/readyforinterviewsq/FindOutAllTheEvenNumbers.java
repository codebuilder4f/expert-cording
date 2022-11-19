package codebuilder.readyforinterviewsq;


import codebuilder.util.Util;

import java.util.stream.Collectors;

/**
 * Given a list of integers, find out all the even numbers exist in the list using Stream functions?
 */
public class FindOutAllTheEvenNumbers {
    public static void main(String[] args) {
        Util.integerList
                .stream()
                .filter(number -> number % 2 == 0)
                .sorted()
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}