package dev.nautchkafe.studios.network.sdk.argon;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.function.Function;

final class Argon2PasswordHasher implements ArgonPasswordHasher {

    private final Function<Argon2Selector, Argon2> argonCreator;

    Argon2PasswordHasher() {
        argonCreator = argonReference();
    }

    @Override
    public Argon2Hash apply(final char[] password, final Argon2Contract contract) {
        final Argon2 argon2 = argonCreator.apply(contract.selector());

        try {
            final String hash = argon2.hash(
                    contract.iterations(),
                    contract.memory(),
                    contract.parallelism(),
                    password);

            return Argon2Hash.of(hash, extractSaltFromHash(hash), contract);
        } finally {
            argon2.wipeArray(password);
        }
    }

    private String extractSaltFromHash(final String hash) {
        return hash.split("\\$")[4];
    }

    static Function<Argon2Selector, Argon2> argonReference() {
        final Function<Argon2Selector, Argon2> argonCreator;

        argonCreator = selector -> switch (selector) {
            case ARGON2D -> selectArgon2d();
            case ARGON2I -> selectArgon2i();
            case ARGON2ID -> selectArgon2id();
        };

        return argonCreator;
    }

    private static Argon2 selectArgon2id() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    private static Argon2 selectArgon2d() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
    }

    private static Argon2 selectArgon2i() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
    }
}
