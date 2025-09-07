package dev.nautchkafe.studios.network.sdk.argon;

import de.mkammerer.argon2.Argon2;

import java.util.function.Function;

final class Argon2PasswordVerifier implements ArgonPasswordVerifier {

    private final Function<Argon2Selector, Argon2> argonCreator;

    Argon2PasswordVerifier() {
        argonCreator = Argon2PasswordHasher.argonReference();
    }

    @Override
    public Boolean apply(final char[] password, final Argon2Hash hash) {
        final Argon2 argon2 = argonCreator.apply(hash.contract().selector());

        try {
            return argon2.verify(hash.hash(), password);
        } finally {
            argon2.wipeArray(password);
        }
    }
}
