package com.driver.service.impl;

import com.driver.converter.UserConverter;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(user.getUserId());

        if (userEntity == null) {
            UserEntity userEntity1 = UserEntity.builder().userId(user.getUserId()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
            userRepository.save(userEntity1);
            return user;
        }else {
            System.out.println("User Already Exists");
            return null;
        }
    }

    @Override
    public UserDto getUser(String email) throws Exception {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            System.out.println("User Doesn't Exist");
            return null;
        }else {
            UserDto userDto=UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId()).firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
            return userDto;

        }
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId((userId));

        if (userEntity == null) {
            System.out.println("User Not Found");
            return null;
        }else {
            UserDto userDto=UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId()).firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
            return userDto;
        }
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            System.out.println("User Not Found");
            return null;
        }else {
            userRepository.updateUserEntity(userEntity.getId(), user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail());
            UserDto updatedUserDto=user;
            updatedUserDto.setId(userEntity.getId());
            return updatedUserDto;
        }
    }

    @Override
    public void deleteUser(String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) System.out.println("User Not Found to Delete");
        else userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> listOfUserEntities= (List<UserEntity>) userRepository.findAll();
        List<UserDto> listOfUserDtos=new ArrayList<>();
        for(UserEntity user :listOfUserEntities) listOfUserDtos.add(UserConverter.entityToDto(user));
        return listOfUserDtos;
    }
}