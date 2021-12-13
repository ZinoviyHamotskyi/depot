package com.depot.identity.service;

import com.depot.identity.model.User;
import com.depot.identity.model.UserType;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(long id) throws IllegalArgumentException;
    long createUser(String name, String password, UserType userType);
    void updateUser(long id, String name, String password, UserType userType)
            throws IllegalArgumentException;
    void deleteUser(long id);
}