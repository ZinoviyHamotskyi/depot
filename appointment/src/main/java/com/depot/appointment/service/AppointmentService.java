package com.depot.appointment.service;

import com.depot.appointment.dto.AppointmentDto;
import com.depot.appointment.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll();
    Appointment getAppointmentById(long id) throws IllegalArgumentException;
    long createAppointment(Long adminId, Long driverId, Long busId);
    void updateAppointment(long id, Long adminId, Long driverId, Long busId, Boolean accepted)
            throws IllegalArgumentException;
    void deleteAppointment(long id);
    List<AppointmentDto> getAllDto();
    AppointmentDto getAppointmentDtoById(Long id) throws IllegalArgumentException;
}
