package com.dsidak.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;

public interface Testable {
    long MAX_TIMEOUT = 5000;

    static void waitFor(@NotNull BooleanSupplier condition) throws TimeoutException {
        waitFor(condition, MAX_TIMEOUT);
    }

    static void waitFor(@NotNull BooleanSupplier condition, long timeout) throws TimeoutException {
        long start = System.currentTimeMillis();
        while (!condition.getAsBoolean()) {
            if (System.currentTimeMillis() - start > timeout) {
                throw new TimeoutException(String.format("Could not wait for condition within %s ms", timeout));
            }
        }
    }
}
