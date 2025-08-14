package com.bruno.device.domain;

public enum DeviceState {
    AVAILABLE("available"),
    IN_USE("in-use"),
    INACTIVE("inactive");

    private final String state;

    DeviceState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
