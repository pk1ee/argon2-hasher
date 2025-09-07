
## Use case: 

```java

    private static final String HASHED_PASSWORD = "guzdsldgzuzk21lmd";

    void main(final String[] args) {
        final Argon2PasswordService passwordService = Argon2PasswordCreator.createDefault();

        runWithoutCustomContract(passwordService);
        runWithHashAndConsumeScore(passwordService);
    }

    void runWithoutCustomContract(final Argon2PasswordService passwordService) {
        final char[] password = HASHED_PASSWORD.toCharArray();

        passwordService.hashedPassword(password, hash -> {
            Arrays.asList(">> Before hash: " + HASHED_PASSWORD, ">> Hash: " + hash.hash(), ">> Salt: " + hash.salt()).forEach(System.out::println);

            final char[] testPassword = HASHED_PASSWORD.toCharArray();
            boolean isValid = passwordService.verifyPassword(testPassword, hash);
            System.out.println(">> Password valid: " + isValid);

            passwordService.clearPassword(testPassword);
        });
    }

    void runWithHashAndConsumeScore(final Argon2PasswordService passwordService) {
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
```
