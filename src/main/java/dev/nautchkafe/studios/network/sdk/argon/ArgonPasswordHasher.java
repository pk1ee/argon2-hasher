package dev.nautchkafe.studios.network.sdk.argon;

import java.util.function.BiFunction;

@FunctionalInterface
interface ArgonPasswordHasher extends BiFunction<char[], Argon2Contract, Argon2Hash> {
}
