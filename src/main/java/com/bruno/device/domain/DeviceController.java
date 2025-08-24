package com.bruno.device.domain;

import com.bruno.device.exceptions.DeviceInUseException;
import com.bruno.device.exceptions.DeviceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @PostMapping
    public DeviceRecord createDevice(@RequestBody DeviceRecord deviceRecord){
        return this.deviceService.create(deviceRecord);
    }

    @PutMapping
    public DeviceUpdateRecord updateDevice(@RequestBody DeviceUpdateRecord deviceRecord)
            throws DeviceNotFoundException, DeviceInUseException {
        return this.deviceService.updateDevice(deviceRecord);
    }

    @GetMapping("/{id}")
    public DeviceRecord getDeviceById(@PathVariable Long id) throws DeviceNotFoundException {
        return this.deviceService.findById(id);
    }

    @GetMapping("/all")
    public List<DeviceRecord> getAllDevices(){
        return this.deviceService.getAll();
    }

    @GetMapping("/all-by-brand")
    public List<DeviceRecord> getAllDevicesByBrand(@RequestParam(name = "brand") String brand) {
        return this.deviceService.findAllByBrand(brand);
    }

    @GetMapping("/all-by-state")
    public List<DeviceRecord> getAllDevicesByState(@RequestParam(name = "state") DeviceState state) {
        return this.deviceService.findAllByState(state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeviceById(@PathVariable Long id)
            throws DeviceNotFoundException, DeviceInUseException {
        this.deviceService.deleteById(id);
        return ResponseEntity.ok("Device deleted");
    }

}
