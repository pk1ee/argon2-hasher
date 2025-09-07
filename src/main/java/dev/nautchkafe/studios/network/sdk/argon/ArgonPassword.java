package dev.nautchkafe.studios.network.sdk.argon;

import java.util.function.Function;

@FunctionalInterface
interface ArgonPassword extends Function<char[], Argon2Hash> {
}
