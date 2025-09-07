package dev.nautchkafe.studios.network.sdk.argon;

final class Argon2PasswordCreator {

    private Argon2PasswordCreator() {
    }

    static Argon2PasswordService createDefault() {
        return new Argon2PasswordService(
                new Argon2PasswordHasher(),
                new Argon2PasswordVerifier());
    }

    static Argon2PasswordService createWithContract(final Argon2Contract contract) {
        return new Argon2PasswordService(
                new Argon2PasswordHasher(),
                new Argon2PasswordVerifier(),
                contract);
    }
}
