package com.depot.appointment.api;

import com.depot.appointment.dto.AppointmentDto;
import com.depot.appointment.model.Appointment;
import com.depot.appointment.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/appointment")
@RestController
public class AppointmentController {

        private final AppointmentServiceImpl appointmentService;

        @GetMapping
        public ResponseEntity<List<AppointmentDto>> getAll() {
            final List<AppointmentDto> appointments = appointmentService.getAllDto();
            return ResponseEntity.ok(appointments);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Appointment> getById(@PathVariable long id) {
            try {
                Appointment appointment = appointmentService.getAppointmentById(id);

                return ResponseEntity.ok(appointment);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }


        @PostMapping("/create")
        public ResponseEntity<Void> create(@RequestBody Appointment appointment) {
            final Long adminId = appointment.getAdminId();
            final Long driverId = appointment.getDriverId();
            final Long busId = appointment.getBusId();

            final long appointmentId = appointmentService.createAppointment(adminId, driverId, busId);
            final String appointmentUri = "/appointment" + appointmentId;

            return ResponseEntity.created(URI.create(appointmentUri)).build();
        }

        @PatchMapping("/{id}")
        public ResponseEntity<Void> change(@PathVariable long id, @RequestBody Appointment appointment) {
            final Long adminId = appointment.getAdminId();
            final Long driverId = appointment.getDriverId();
            final Long busId = appointment.getBusId();
            final Boolean accepted = appointment.getAccepted();

            try {
                appointmentService.updateAppointment(id, adminId, driverId, busId, accepted);

                return ResponseEntity.noContent().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable long id) {
            appointmentService.deleteAppointment(id);

            return ResponseEntity.noContent().build();
        }
}
        
