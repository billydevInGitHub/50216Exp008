package com.billydev.blib.service.users;

import java.util.List;

import com.billydev.blib.model.User;
import com.billydev.blib.model.UserDto;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
