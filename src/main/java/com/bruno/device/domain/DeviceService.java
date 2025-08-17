package com.bruno.device.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<DeviceRecord> getAll(){
        List<DeviceRecord> result = new ArrayList<>();
        Iterable<Device> devices = this.deviceRepository.findAll();
        devices.forEach(device -> result.add(this.deviceMapper.toRecord(device)));
        return result;
    }

    public void deleteById(Long id) {
        this.deviceRepository.deleteById(id);
    }

    public List<DeviceRecord> findAllByBrand(String brand) {
        return this.deviceRepository.findAllByBrand(brand);
    }

    public List<DeviceRecord> findAllByState(DeviceState state) {
        return this.deviceRepository.findAllByState(state);
    }

    public DeviceUpdateRecord updateDevice(DeviceUpdateRecord deviceRecord) throws DeviceNotFoundException {
        if(!this.deviceRepository.existsById(deviceRecord.id())) throw new DeviceNotFoundException(deviceRecord.id());
        Device device = this.deviceRepository.save(this.deviceMapper.fromUpdatetoEntity(deviceRecord));
        return this.deviceMapper.toUpdateRecord(device);
    }
}
