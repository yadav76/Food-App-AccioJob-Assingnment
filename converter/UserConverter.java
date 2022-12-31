package com.driver.converter;

import com.driver.io.entity.UserEntity;
import com.driver.shared.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass    // All methods are static and called using class name
public class UserConverter {

    public static UserDto entityToDto(UserEntity userEntity) {
        return UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId()).firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
    }
}
