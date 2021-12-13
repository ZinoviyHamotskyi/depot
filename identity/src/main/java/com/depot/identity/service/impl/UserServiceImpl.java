package com.depot.identity.service.impl;

import com.depot.identity.model.User;
import com.depot.identity.model.UserType;
import com.depot.identity.repo.UserRepository;
import com.depot.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) throws IllegalArgumentException {
        final Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new IllegalArgumentException("Invalid user ID");
    }

    @Override
    public long createUser(String name, String password, UserType userType) {
        final User user = new User(name, password, userType);
        final User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Override
    public void updateUser(long id, String name, String password, UserType userType)
            throws IllegalArgumentException {
        final Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new IllegalArgumentException("Invalid user ID");

        final User user = optionalUser.get();
        if (name != null && !name.isBlank()) user.setName(name);
        if (password != null && !password.isBlank()) user.setPassword(password);
        if (userType != null && userType != UserType.NULL) user.setType(userType);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
