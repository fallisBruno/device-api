package com.bruno.device.domain;

public record DeviceUpdateRecord(Long id, String name, String brand, DeviceState state) {
}
