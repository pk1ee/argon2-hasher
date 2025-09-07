package dev.nautchkafe.studios.network.sdk.argon;

import java.util.function.BiFunction;

@FunctionalInterface
interface ArgonPasswordVerifier extends BiFunction<char[], Argon2Hash, Boolean> {
}
