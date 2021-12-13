package com.depot.appointment.service.impl;

import com.depot.appointment.dto.AppointmentDto;
import com.depot.appointment.dto.BusDto;
import com.depot.appointment.dto.UserDto;
import com.depot.appointment.dto.UserType;
import com.depot.appointment.model.Appointment;
import com.depot.appointment.repo.AppointmentRepository;
import com.depot.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final String userUrlAdress ="http://service-identity:8081/user";
    private final String busUrlAdress ="http://localhost:8082/bus";

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    private boolean checkUser(Long userId, UserType userType) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(userId);


        final ResponseEntity<UserDto> userResponse = restTemplate
                .exchange(userUrlAdress + "/dto/" + userId,
                        HttpMethod.GET, userRequest, UserDto.class);

        return userResponse.getStatusCode() != HttpStatus.NOT_FOUND
                && userResponse.getBody().getUserType() == userType.ordinal();
    }

    private boolean checkBus(Long busId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> busRequest = new HttpEntity<>(busId);


        final ResponseEntity<BusDto> busResponse = restTemplate
                .exchange(busUrlAdress + "/dto/" + busId,
                        HttpMethod.GET, busRequest, BusDto.class);

        return busResponse.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public Appointment getAppointmentById(long id) throws IllegalArgumentException {
        final Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isPresent())
            return optionalAppointment.get();
        else
            throw new IllegalArgumentException("Invalid appointment ID");
    }

    @Override
    public long createAppointment( Long adminId, Long driverId, Long busId) {
        final Appointment appointment = new Appointment(adminId, driverId, busId);

        if (!checkUser(adminId, UserType.ADMIN)
                || !checkUser(driverId, UserType.DRIVER)
                || !checkBus(busId)) {
            throw new IllegalArgumentException("Bad request");
        }
        else {
            final Appointment savedAppointment = appointmentRepository.save(appointment);
            return savedAppointment.getId();
        }
    }

    @Override
    public void updateAppointment(long id,  Long adminId, Long driverId, Long busId, Boolean accepted)
            throws IllegalArgumentException {
        final Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isEmpty())
            throw new IllegalArgumentException("Invalid appointment ID");
        final Appointment appointment = optionalAppointment.get();

        if (adminId != null && adminId != -1) {
            if (!checkUser(adminId, UserType.ADMIN)) {
                throw new IllegalArgumentException("Invalid admin id");
            }
            appointment.setAdminId(adminId);
        }

        if (driverId != null && driverId != -1) {
            if (!checkUser(driverId, UserType.DRIVER)) {
                throw new IllegalArgumentException("Invalid driver id");
            }
            appointment.setDriverId(driverId);
        }

        if (busId != null && busId != -1) {
            if (!checkBus(busId)) {
                throw new IllegalArgumentException("Invalid bus id");
            }
            appointment.setBusId(busId);
        }
        appointmentRepository.save(appointment);
    }


    private String getUserName(Long userId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(userId);

        final ResponseEntity<UserDto> userResponse = restTemplate
                .exchange(userUrlAdress + "/" +userId,
                        HttpMethod.GET, userRequest, UserDto.class);

        return userResponse.getBody().getName();
    }

    private BusDto getBusDto(Long busId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> busRequest = new HttpEntity<>(busId);

        final ResponseEntity<BusDto> busResponse = restTemplate
                .exchange(busUrlAdress + "/" + busId,
                        HttpMethod.GET, busRequest, BusDto.class);

        return busResponse.getBody();
    }

    public AppointmentDto appointmentToDto(Appointment appointment){
        String adminName = getUserName(appointment.getAdminId());
        String driverName = getUserName(appointment.getDriverId());
        BusDto busDto = getBusDto(appointment.getBusId());

        return new AppointmentDto(adminName, driverName, busDto.getModel(), busDto.getNumber(), Boolean.FALSE);
    }

    @Override
    public AppointmentDto getAppointmentDtoById(Long id) throws IllegalArgumentException {
        try {
            Appointment appointment = getAppointmentById(id);
            return appointmentToDto(appointment);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public List<AppointmentDto> getAllDto() {
        List<Appointment> appointments = getAll();
        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for(Appointment appointment: appointments) {
            appointmentDtos.add(appointmentToDto(appointment));
        }

        return appointmentDtos;
    }

    @Override
    public void deleteAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}
