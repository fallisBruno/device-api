package com.bruno.device.domain;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    Device toEntity(DeviceRecord deviceRecord);

    DeviceRecord toRecord(Device device);

    Device fromUpdatetoEntity(DeviceUpdateRecord deviceUpdateRecord);

    DeviceUpdateRecord toUpdateRecord(Device device);

}
