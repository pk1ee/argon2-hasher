package dev.nautchkafe.studios.network.sdk.argon;

import java.util.Arrays;

/*
> Task :dev.nautchkafe.studios.network.sdk.argon.Argon2App.main()
>> before hash: guzdsldgzuzk21lmd
>> Hash: $argon2id$v=19$m=65536,t=10,p=4$Pjkh5MY1t7htfwPWP7wETA$8wQO6hFHUmCIJgN89/0T69np7OwRpIlJ78Wuer2xT9Q
>> Salt: Pjkh5MY1t7htfwPWP7wETA
>> Password valid: true
 */
public final class Argon2App {

    private static final String HASHED_PASSWORD = "guzdsldgzuzk21lmd";

    public static void main(final String[] args) {
        final Argon2PasswordService passwordService = Argon2PasswordCreator.createDefault();

        runWithoutCustomContract(passwordService);
        runWithHashAndConsumeScore(passwordService);
        runWithoutCustomContract(passwordService);
    }

    private static void runWithoutCustomContract(final Argon2PasswordService passwordService) {
        final char[] password = HASHED_PASSWORD.toCharArray();

        passwordService.hashedPassword(password, hash -> {
            Arrays.asList(">> Before hash: " + HASHED_PASSWORD, ">> Hash: " + hash.hash(), ">> Salt: " + hash.salt()).forEach(System.out::println);

            final char[] testPassword = HASHED_PASSWORD.toCharArray();
            boolean isValid = passwordService.verifyPassword(testPassword, hash);
            System.out.println(">> Password valid: " + isValid);

            passwordService.clearPassword(testPassword);
        });
    }

    private static void runWithHashAndConsumeScore(final Argon2PasswordService passwordService) {
        final Argon2PasswordCoordinator passwordCoordinator = new Argon2PasswordCoordinator(passwordService);

        // run hash and consume score
        final char[] pass = "drowssap".toCharArray();
        passwordCoordinator.hashAndConsume().accept(pass, hash -> {
            System.out.println(">> Hasched: " + hash.hash());
        });

        // run hash with custom contract
        final char[] customPassword = "passwod12".toCharArray();
        final Argon2Contract customContract = new Argon2Contract(15, 8192, 2, 24, 12, Argon2Selector.ARGON2I);
        passwordCoordinator.hashContractAndPrint().accept(customPassword, customContract);

        // run with safe operation on hash
        passwordService.secureOperation(customPassword, password -> {
            System.out.println(">> Password length: " + password.length);
        });
    }
}