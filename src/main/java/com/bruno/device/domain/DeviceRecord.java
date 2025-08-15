package com.bruno.device.domain;

import java.time.LocalDate;

public record DeviceRecord(Long id, String name, String brand, DeviceState state, LocalDate creationTime) {
}
