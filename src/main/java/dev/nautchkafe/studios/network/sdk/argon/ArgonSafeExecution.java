package dev.nautchkafe.studios.network.sdk.argon;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* This functional execution for java try catch mistake */
final class ArgonSafeExecution {

    private ArgonSafeExecution() {
    }

    static <TYPE, RESULT> Function<TYPE, RESULT> peekCleanup(
            final Function<TYPE, RESULT> operation,
            final Consumer<TYPE> cleanup) {

        return type -> {
            try {
                return operation.apply(type);
            } finally {
                cleanup.accept(type);
            }
        };
    }

    static <TYPE, UNIT, RESULT> BiFunction<TYPE, UNIT, RESULT> peekBiCleanup(
            final BiFunction<TYPE, UNIT, RESULT> operation,
            final Consumer<TYPE> cleanup) {

        return ((type, unit) -> {
            try {
                return operation.apply(type, unit);
            } finally {
                cleanup.accept(type);
            }
        });
    }

    static <TYPE, RESULT> BiFunction<char[], TYPE, RESULT> peekPasswordCleanup(final BiFunction<char[], TYPE, RESULT> operation) {
        return (password, parametrs) -> {
            try {
                return operation.apply(password, parametrs);
            } finally {
                fill(password);
            }
        };
    }

    static <RESULT> Function<char[], RESULT> peekPasswordCleanup(final Function<char[], RESULT> operation) {
        return peekCleanup(operation, ArgonSafeExecution::fill);
    }

    static void fill(final char[] password) {
        Arrays.fill(password, '\0');
    }
}
