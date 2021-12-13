package com.depot.appointment.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class AppointmentDto {
    String adminName;
    String driverName;
    String busModel;
    String busNumber;
    Boolean accepted;
}
