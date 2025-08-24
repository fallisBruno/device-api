package com.bruno.device.domain;

import com.bruno.device.exceptions.DeviceInUseException;
import com.bruno.device.exceptions.DeviceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public DeviceUpdateRecord updateDevice(DeviceUpdateRecord deviceToUpdate)
            throws DeviceNotFoundException, DeviceInUseException {
        Optional<Device> deviceOptional = this.deviceRepository.findById(deviceToUpdate.id());

        if(deviceOptional.isEmpty())
            throw new DeviceNotFoundException(deviceToUpdate.id());

        Device deviceFound = deviceOptional.get();

        if(!Objects.equals(deviceToUpdate.brand(), deviceFound.getBrand())
                || !Objects.equals(deviceToUpdate.name(), deviceFound.getName()))
            isDeviceInUse(deviceFound);

        Device device = this.deviceRepository.save(this.deviceMapper.fromUpdatetoEntity(deviceToUpdate));
        return this.deviceMapper.toUpdateRecord(device);
    }

    private void isDeviceInUse(Device deviceFound) throws DeviceInUseException {
        if (DeviceState.IN_USE.equals(deviceFound.getState()))
            throw new DeviceInUseException("Cannot modify name and/or brand when Device IN USE");
    }
}
