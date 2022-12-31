package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{

		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse;

		userResponse = UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();

		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{

		UserDto userDto = UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName()).lastName(userDetails.getLastName()).build();

		UserResponse userResponse = UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();

		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{

		UserDto userDto = UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName()).lastName(userDetails.getLastName()).build();

		userService.updateUser(id,userDto);   // Updating user

		UserResponse userResponse = UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{

		OperationStatusModel operationStatusModel = OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();
		userService.deleteUser(id);
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){

		List<UserDto> listOfUserDtos = userService.getUsers();
		List<UserResponse> list = new ArrayList<>();

		for (UserDto userDto : listOfUserDtos) {
			UserResponse userResponse = UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();

			list.add(userResponse);
		}
		return list;
	}
	
}
