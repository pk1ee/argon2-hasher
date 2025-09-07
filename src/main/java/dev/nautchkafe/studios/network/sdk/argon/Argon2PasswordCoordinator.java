package dev.nautchkafe.studios.network.sdk.argon;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

final class Argon2PasswordCoordinator {

    private final Argon2PasswordService passwordService;

    Argon2PasswordCoordinator(final Argon2PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    BiConsumer<char[], ArgonHashConsumer> hashAndConsume() {
        return passwordService::hashedPassword;
    }

    BiConsumer<char[], Argon2Contract> hashContractAndPrint() {
        return (password, contract) -> passwordService.hashedPassword(
                password,
                contract,
                hash -> {
                    Arrays.asList(">> Hash: " + hash.hash(),
                            ">> Salt: " + hash.salt(),
                            ">> Contract: " + contract).forEach(System.out::println);
                }
        );
    }

    Consumer<char[]> hashAndPrint() {
        return password -> passwordService.hashedPassword(password, hash -> {
            Arrays.asList(">> Hash: " + hash.hash(), ">> Salt: " + hash.salt()).forEach(System.out::println);
        });
    }
}
