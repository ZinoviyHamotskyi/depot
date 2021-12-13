package com.depot.bus.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class BusDto {
    String model;
    String number;
}