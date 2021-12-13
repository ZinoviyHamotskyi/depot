package com.depot.identity.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class UserDto {
    String name;
    String password;
    int userType;
}
