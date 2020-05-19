package edu.react.rx;

import java.util.UUID;

public class Functions {

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
