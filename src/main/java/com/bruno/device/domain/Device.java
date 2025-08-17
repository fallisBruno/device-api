package com.bruno.device.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;

    @Enumerated(EnumType.STRING)
    private DeviceState state;
    private LocalDate creationTime;

}
