package io.tornimo.spring.autoconfigure;

public interface TornimoEnvironmentData {

    default String getEnvironmentString() {
        return "";
    }
}
