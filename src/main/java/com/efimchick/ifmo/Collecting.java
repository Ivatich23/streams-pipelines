package com.efimchick.ifmo;

import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.Person;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {
    public int sum(IntStream intStream) {
        return intStream.reduce(0, Integer::sum);
    }

    public int production(IntStream intStream) {
        return intStream.reduce(1, (tempValue, value) -> tempValue * value);
    }

    public int oddSum(IntStream intStream) {
        return intStream.filter(value -> value % 2 != 0).reduce(0, Integer::sum);
    }

    /*public Map<Integer, Integer> sumByRemainder(IntStream intStream, final int divisor) {

        return intStream.collect(Collectors.groupingBy(Collectors.reducing((value,div)->value/divisor, Integer::sum));
    }*/
    public Map<Person, Double> totalScores(Stream<CourseResult> courseResultStream) {
        long sumOfSubjects = courseResultStream.map(courseResult -> courseResult
                .getTaskResults().values().size()).max(Integer::compareTo).get();
        return courseResultStream.collect(Collectors
                .toMap(person -> person.getPerson(), person -> person.getTaskResults()
                        .values().stream().map(value -> value.doubleValue())
                        .reduce(0.0, (value, sum) -> sum + value) / sumOfSubjects));


    }

    public Double averageTotalScore(Stream<CourseResult> courseResultStream) {
        int sumOfScores = Optional.of(courseResultStream.flatMap(value -> value
                .getTaskResults().values().stream())
                .reduce(0, (value, tempValue) -> tempValue + value)).get();
        long sumOfStudents = Stream.of(courseResultStream).count();
        long sumOfSubjects = Stream.of(courseResultStream).map(courseResult -> courseResult
                .getTaskResults().values().size()).max(Integer::compareTo).get();
        return (double) (sumOfScores / sumOfStudents/sumOfSubjects);


    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> courseResultStream) {
        long sumOfStudents = courseResultStream.count();
        Map<String, Double> mapOfResults = courseResultStream.flatMap(value -> value.getTaskResults().entrySet().stream())
                .collect(Collectors
                        .groupingBy(value -> value.getKey(), Collectors.summingDouble(value -> value.getValue())));
        mapOfResults.entrySet().forEach(value -> value.setValue(value.getValue() / sumOfStudents));
        return mapOfResults;
    }


    public Map<Person, String> defineMarks(Stream<CourseResult> courseResultStream) {
        return totalScores(courseResultStream).entrySet().stream()
                .collect(Collectors.toMap(key -> key.getKey(), entry -> fromDoubleToString(entry.getValue())));

    }

    public String fromDoubleToString(double value) {
        String result = "F";
        if (value <= 100 && value > 89) {
            return result = "A";
        } else if (value <= 89 && value > 82) {
            return result = "B";
        } else if (value <= 82 && value > 74) {
            return result = "C";
        } else if (value <= 74 && value > 67) {
            return result = "D";
        } else if (value <= 67 && value > 59) {
            return result = "E";
        }
        return result;
    }

    public String easiestTask(Stream<CourseResult> courseResultStream) {
        return courseResultStream.flatMap(result -> result.getTaskResults().entrySet().stream()
        ).max(Map.Entry.comparingByValue()).stream().findFirst().get().getKey();
    }



 /*       "%-15s%-15s%-10s%-10s%n-5s%n-5s%n", "| Student"
                , "| Phalanxing", "| Tercioing", "| Wedging", "| Total ", "| Mark |";*/

        Collector<CourseResult, StringBuilder, String> myCollector = new Collector<CourseResult, StringBuilder, String>() {
            @Override
            public Supplier<StringBuilder> supplier() {
                return StringBuilder::new;
            }

            @Override
            public BiConsumer<StringBuilder, CourseResult> accumulator() {

           /*     return ((stringBuilder, courseResult) -> stringBuilder.append(String.format("afasdad",courseResult.))

                        (courseResult.getPerson())
                        .append(courseResult.getTaskResults().get("Lab 1. Figures"))
                        .append(courseResult.getTaskResults().get("Lab 2. War and Peace"))
                        .append(courseResult.getTaskResults().get("Lab 3. File Tree"))
                        .append(courseResult.getTaskResults().values().stream().reduce(0,Integer::sum)/3)
                        .append(fromDoubleToString(courseResult.getTaskResults().values().stream().reduce(0,Integer::sum)/3))
                .append("\n"))
                        ;*/
                return null;
            }

            @Override
            public BinaryOperator<StringBuilder> combiner() {
                return null;
            }

            @Override
            public Function<StringBuilder, String> finisher() {

                return null;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return null;
            }
        };






                /*Collector.of(
                () -> new StringBuilder(), (s,c) -> s.append(c.getPerson().getLastName()),StringBuilder::toString);
courseResultStream.collect(Collectors.toList()).forEach(x-> System.out.printf("%-15s%-15s%-10s%-10s%n-5s%n-5s%n",
        x.getPerson().getLastName(),x.getTaskResults());*/


}
