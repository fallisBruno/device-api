package com.bruno.device.domain;

import com.bruno.device.exceptions.DeviceInUseException;
import com.bruno.device.exceptions.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DeviceNotFoundException.class})
    public ResponseEntity<Object> handleDeviceNotFoundException(DeviceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({DeviceInUseException.class})
    public ResponseEntity<Object> handleDeviceInUseException(DeviceInUseException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
