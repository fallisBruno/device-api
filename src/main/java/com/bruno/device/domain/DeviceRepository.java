package com.bruno.device.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {

    DeviceRecord findDeviceById(Long id);

    List<DeviceRecord> findAllByBrand(String brand);

    List<DeviceRecord> findAllByState(DeviceState state);

}
