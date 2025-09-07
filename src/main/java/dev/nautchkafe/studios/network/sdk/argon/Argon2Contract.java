package dev.nautchkafe.studios.network.sdk.argon;

record Argon2Contract(
        int iterations,
        int memory,
        int parallelism,
        int hashLength,
        int saltLength,
        Argon2Selector selector
) {

    static Argon2Contract defaultContract() {
        return new Argon2Contract(
                10,
                65536,
                4,
                32,
                16,
                Argon2Selector.ARGON2ID);
    }

}
