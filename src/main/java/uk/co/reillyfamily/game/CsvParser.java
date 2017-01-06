package uk.co.reillyfamily.game;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Primitives;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by stuart on 12/12/16.
 */
public class CsvParser {
    public Float[] parse(File file) throws IOException {
        return Files.lines(file.toPath())
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> Arrays.asList(line.split(",")))
                .map(row -> row.stream()
                        .map(Float::parseFloat)
                        .collect(Collectors.toList())
                        .toArray(new Float[0]))
                .reduce(this::concat)
                .orElseThrow(() -> new RuntimeException("Failed to parse file!"));
    }

    private Float[] concat(Float[] arr, Float[] arr2) {
        Float[] result = new Float[arr.length + arr2.length];
        System.arraycopy(arr, 0, result, 0, arr.length);
        System.arraycopy(arr2, 0, result, arr.length, arr2.length);
        return result;
    }
}
