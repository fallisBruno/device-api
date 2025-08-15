package com.bruno.device.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target = "id", ignore = true)
    Device toEntity(DeviceRecord deviceRecord);

    DeviceRecord toRecord(Device device);

}
