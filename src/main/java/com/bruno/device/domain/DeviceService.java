package com.bruno.device.domain;

import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper){
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public DeviceRecord create(DeviceRecord deviceRecord){
        Device device = this.deviceMapper.toEntity(deviceRecord);
        Device createdDevice = this.deviceRepository.save(device);
        return this.deviceMapper.toRecord(createdDevice);
    }

    public DeviceRecord findById(long id){
        return this.deviceRepository.findDeviceById(id);
    }

}
