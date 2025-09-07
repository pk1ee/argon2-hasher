package dev.nautchkafe.studios.network.sdk.argon;

interface ArgonHashCoordinator {

    Argon2Hash hashPassword(final char[] password);

    Argon2Hash hashPassword(final char[] password, final Argon2Contract contract);

    boolean verifyPassword(final char[] password, final Argon2Hash hash);

    void hashedPassword(final char[] password, final ArgonHashConsumer callAction);

    void hashedPassword(final char[] password, final Argon2Contract contract,
                        final ArgonHashConsumer callAction);

    void secureOperation(final char[] password, final ArgonSecure secure);
}
