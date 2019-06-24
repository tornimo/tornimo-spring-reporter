package io.tornimo;

public class TornimoStaticEnvironmentData implements TornimoEnvironmentData {

    private final String data;

    public TornimoStaticEnvironmentData(String data) {
        this.data = data;
    }

    public String getEnvironmentString() {
        return data;
    }

    public static TornimoEnvironmentData empty() {
        return new TornimoStaticEnvironmentData("");
    }
}
