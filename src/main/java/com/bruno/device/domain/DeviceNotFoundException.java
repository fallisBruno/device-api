package com.bruno.device.domain;

public class DeviceNotFoundException extends Exception {

    public DeviceNotFoundException(Long id) {
        super("No device found for id: " + id);
    }
}
