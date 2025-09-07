package dev.nautchkafe.studios.network.sdk.argon;

import java.util.function.Function;

final class Argon2PasswordService implements ArgonHashCoordinator {

    private final ArgonPasswordHasher passwordHasher;
    private final ArgonPasswordVerifier passwordVerifier;
    private final Argon2Contract defaultContract;

    Argon2PasswordService(final ArgonPasswordHasher passwordHasher, final ArgonPasswordVerifier passwordVerifier) {
        this(passwordHasher, passwordVerifier, Argon2Contract.defaultContract());
    }

    Argon2PasswordService(final ArgonPasswordHasher passwordHasher, final ArgonPasswordVerifier passwordVerifier,
                          final Argon2Contract defaultContract) {
        this.passwordHasher = passwordHasher;
        this.passwordVerifier = passwordVerifier;
        this.defaultContract = defaultContract;
    }

    @Override
    public Argon2Hash hashPassword(final char[] password) {
        return passwordHasher.apply(password, defaultContract);
    }

    @Override
    public Argon2Hash hashPassword(final char[] password, final Argon2Contract contract) {
        return passwordHasher.apply(password, contract);
    }

    @Override
    public boolean verifyPassword(final char[] password, final Argon2Hash hash) {
        return passwordVerifier.apply(password, hash);
    }

    @Override
    public void hashedPassword(final char[] password, final ArgonHashConsumer callAction) {
        hashedPassword(password, defaultContract, callAction);
    }

    @Override
    public void hashedPassword(final char[] password, final Argon2Contract contract, final ArgonHashConsumer callAction) {
        try {
            final Argon2Hash hash = hashPassword(password, contract);
            callAction.accept(hash);
        } finally {
            clearPassword(password);
        }
    }

    @Override
    public void secureOperation(final char[] password, final ArgonSecure secure) {
        try {
            secure.accept(password);
        } finally {
            clearPassword(password);
        }
    }

    <TYPE>Function<char[], TYPE> hashAndThen(final Function<Argon2Hash, TYPE> mapper) {
        return ArgonSafeExecution.peekPasswordCleanup(password -> mapper.apply(hashPassword(password)));
    }

    void clearPassword(final char[] password) {
        if (password == null) {
            return;
        }

        ArgonSafeExecution.fill(password);
    }
}
