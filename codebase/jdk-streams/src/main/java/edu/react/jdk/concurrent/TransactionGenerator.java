package edu.react.jdk.concurrent;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

import edu.react.jdk.concurrent.flow.Messages.Mutation;
import edu.react.jdk.concurrent.flow.Messages.UnValidatedTransaction;

public final class TransactionGenerator implements Supplier<UnValidatedTransaction> {

    private static final Random RANDOMIZER = new Random();

    // Keep amounts between 10 and 100
    private static final int FLOOR = 10;
    private static final int CEILING = 101;

    @Override
    public UnValidatedTransaction get() {
        return new UnValidatedTransaction(account(), amount(), mutation());
    }

    private static String account() {
        return UUID.randomUUID().toString();
    }

    private static BigDecimal amount() {
        return BigDecimal.valueOf(RANDOMIZER.nextInt(CEILING - FLOOR) + FLOOR);
    }

    private static Mutation mutation() {
        return (RANDOMIZER.nextInt(CEILING - FLOOR) + FLOOR) % 2 == 0 ? Mutation.values()[0] : Mutation.values()[1];
    }
}
