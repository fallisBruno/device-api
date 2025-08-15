package com.bruno.device.domain;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @PostMapping
    public DeviceRecord createDevice(@RequestBody DeviceRecord deviceRecord){
        return this.deviceService.create(deviceRecord);
    }

    @GetMapping("/{id}")
    public DeviceRecord getDevice(@PathVariable long id) {
        return this.deviceService.findById(id);
    }

}
