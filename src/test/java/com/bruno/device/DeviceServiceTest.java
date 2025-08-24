package com.bruno.device;

import com.bruno.device.domain.*;
import com.bruno.device.exceptions.DeviceInUseException;
import com.bruno.device.exceptions.DeviceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    private DeviceRepository deviceRepository;
    private DeviceMapper deviceMapper;
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        deviceMapper = mock(DeviceMapper.class);
        deviceService = new DeviceService(deviceRepository, deviceMapper);
    }

    @Test
    void create_shouldSaveAndReturnMappedRecord() {
        DeviceRecord inputRecord = new DeviceRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        Device deviceEntity = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        Device savedEntity = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());

        when(deviceMapper.toEntity(inputRecord)).thenReturn(deviceEntity);
        when(deviceRepository.save(deviceEntity)).thenReturn(savedEntity);
        when(deviceMapper.toRecord(savedEntity)).thenReturn(inputRecord);

        DeviceRecord result = deviceService.create(inputRecord);

        assertEquals(inputRecord, result);
        verify(deviceRepository).save(deviceEntity);
    }

    @Test
    void findById_shouldReturnDeviceRecord_whenFound() throws DeviceNotFoundException {
        DeviceRecord record = new DeviceRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());

        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(record));

        DeviceRecord result = deviceService.findById(1L);

        assertEquals(record, result);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.findById(1L));
    }

    @Test
    void getAll_shouldReturnMappedDevices() {
        Device device1 = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        Device device2 = new Device(2L, "Device B", "Brand Y", DeviceState.IN_USE, LocalDate.now());
        DeviceRecord record1 = new DeviceRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        DeviceRecord record2 = new DeviceRecord(2L, "Device B", "Brand Y", DeviceState.IN_USE, LocalDate.now());

        when(deviceRepository.findAll()).thenReturn(List.of(device1, device2));
        when(deviceMapper.toRecord(device1)).thenReturn(record1);
        when(deviceMapper.toRecord(device2)).thenReturn(record2);

        List<DeviceRecord> result = deviceService.getAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(record1));
        assertTrue(result.contains(record2));
    }

    @Test
    void deleteById_shouldDelete_whenDeviceExistsAndNotInUse() throws Exception {
        Device device = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        deviceService.deleteById(1L);

        verify(deviceRepository).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowNotFound_whenDeviceDoesNotExist() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deleteById(1L));
    }

    @Test
    void deleteById_shouldThrowInUse_whenDeviceInUse() {
        Device device = new Device(1L, "Device A", "Brand X", DeviceState.IN_USE, LocalDate.now());

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        assertThrows(DeviceInUseException.class, () -> deviceService.deleteById(1L));
    }

    @Test
    void findAllByBrand_shouldDelegateToRepository() {
        List<DeviceRecord> expected = List.of(new DeviceRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now()));
        when(deviceRepository.findAllByBrand("Brand X")).thenReturn(expected);

        List<DeviceRecord> result = deviceService.findAllByBrand("Brand X");

        assertEquals(expected, result);
    }

    @Test
    void findAllByState_shouldDelegateToRepository() {
        List<DeviceRecord> expected = List.of(new DeviceRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now()));
        when(deviceRepository.findAllByState(DeviceState.AVAILABLE)).thenReturn(expected);

        List<DeviceRecord> result = deviceService.findAllByState(DeviceState.AVAILABLE);

        assertEquals(expected, result);
    }

    @Test
    void updateDevice_shouldUpdateAndReturnRecord_whenValid() throws Exception {
        DeviceUpdateRecord updateRecord = new DeviceUpdateRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE);
        Device existingDevice = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        Device updatedEntity = new Device(1L, "Device A", "Brand X", DeviceState.AVAILABLE, LocalDate.now());
        DeviceUpdateRecord expected = new DeviceUpdateRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        when(deviceMapper.fromUpdatetoEntity(updateRecord)).thenReturn(updatedEntity);
        when(deviceRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(deviceMapper.toUpdateRecord(updatedEntity)).thenReturn(expected);

        DeviceUpdateRecord result = deviceService.updateDevice(updateRecord);

        assertEquals(expected, result);
    }

    @Test
    void updateDevice_shouldThrowNotFound_whenDeviceDoesNotExist() {
        DeviceUpdateRecord updateRecord = new DeviceUpdateRecord(1L, "Device A", "Brand X", DeviceState.AVAILABLE);

        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateDevice(updateRecord));
    }

    @Test
    void updateDevice_shouldThrowInUse_whenChangingNameOrBrandAndDeviceInUse() {
        DeviceUpdateRecord updateRecord = new DeviceUpdateRecord(1L, "New Device", "Brand Z", DeviceState.IN_USE);
        Device existingDevice = new Device(1L, "Device A", "Brand X", DeviceState.IN_USE, LocalDate.now());

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));

        assertThrows(DeviceInUseException.class, () -> deviceService.updateDevice(updateRecord));
    }
}
