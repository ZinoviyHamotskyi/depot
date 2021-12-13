package com.depot.identity.api;

import com.depot.identity.dto.UserDto;
import com.depot.identity.model.User;
import com.depot.identity.model.UserType;
import com.depot.identity.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        final List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        try {
            User user = userService.getUserById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<UserDto> getDtoById(@PathVariable long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = new UserDto(user.getName(), user.getPassword(),
                     user.getType().ordinal());

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody UserDto userDto) {
        final String name = userDto.getName();
        final String password = userDto.getPassword();
        final int userType = userDto.getUserType();

        final long userId = userService.createUser(name, password,
                UserType.values()[userType]);
        final String userUri = "/user" + userId;

        return ResponseEntity.created(URI.create(userUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody UserDto userDto) {
        final String name = userDto.getName();
        final String password = userDto.getPassword();
        final UserType userType = UserType.values()[userDto.getUserType()];

        try {
            userService.updateUser(id, name, password, userType);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}