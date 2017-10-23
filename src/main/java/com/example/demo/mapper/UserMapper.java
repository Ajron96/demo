package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public class UserMapper {

    public static User from(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());

        return user;
    }
}
