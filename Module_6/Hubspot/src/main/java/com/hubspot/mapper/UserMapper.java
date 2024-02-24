package com.hubspot.mapper;

import com.hubspot.dto.SignUpUserDto;
import com.hubspot.entity.SignUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    @Autowired
    private static PasswordEncoder encoder;

    public static SignUpUserDto mapToUserDto(SignUser user, SignUpUserDto userDto){
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        userDto.setStatus(user.getStatus());
        userDto.setPassword(encoder.encode(user.getPassword()));
        return userDto;
    }

    public static SignUser mapToUser(SignUpUserDto userDto, SignUser user){
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        user.setPassword(encoder.encode(userDto.getPassword()));
        return user;
    }


}
