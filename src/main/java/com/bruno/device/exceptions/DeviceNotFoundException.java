package com.bruno.device.exceptions;

public class DeviceNotFoundException extends Exception {

    public DeviceNotFoundException(Long id) {
        super("No device found for id: " + id);
    }
}
