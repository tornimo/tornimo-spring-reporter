package io.tornimo.spring.autoconfigure;

interface InstanceIdProvider {

    default String getInstanceId() {
        return "";
    }
}
