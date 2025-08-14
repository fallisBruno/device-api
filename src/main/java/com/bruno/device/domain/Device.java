package com.bruno.device.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Device {

    @Id
    private long id;
    private String name;
    private String brand;
    private DeviceState state;
    private LocalDate creationTime;

}
