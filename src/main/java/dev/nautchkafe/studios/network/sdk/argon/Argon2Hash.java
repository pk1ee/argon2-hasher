package dev.nautchkafe.studios.network.sdk.argon;

record Argon2Hash(
        String hash,
        String salt,
        Argon2Contract contract
) {

    static Argon2Hash of(final String hash, final String salt, final Argon2Contract contract) {
        return new Argon2Hash(hash, salt, contract);
    }
}
