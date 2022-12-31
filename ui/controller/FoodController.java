package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodServiceImpl;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDto foodDto = foodServiceImpl.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse;

		foodDetailsResponse = FoodDetailsResponse.builder().foodId(foodDto.getFoodId()).foodCategory(foodDto.getFoodCategory()).foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();

		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		// Convert foodDetails to FoodDto
		FoodDto foodDto = FoodDto.builder().foodName(foodDetails.getFoodName()).foodCategory(foodDetails.getFoodCategory()).foodPrice(foodDetails.getFoodPrice()).build();
		foodServiceImpl.createFood(foodDto);  // Creating FoodEntity

		FoodDetailsResponse foodDetailsResponse;

		foodDetailsResponse = FoodDetailsResponse.builder().foodCategory(foodDto.getFoodCategory()).foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();

		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = FoodDto.builder().foodName(foodDetails.getFoodName()).foodCategory(foodDetails.getFoodCategory()).foodPrice(foodDetails.getFoodPrice()).build();

		foodServiceImpl.updateFoodDetails(id,foodDto);

		FoodDetailsResponse foodDetailsResponse;

		foodDetailsResponse = FoodDetailsResponse.builder().foodCategory(foodDto.getFoodCategory()).foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();
		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{

		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();
		foodServiceImpl.deleteFoodItem(id);  // Deleting the Entity

		return operationStatusModel;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto> listOfFoodDtos = foodServiceImpl.getFoods();

		List<FoodDetailsResponse> list = new ArrayList<>();

		for (FoodDto foodDto : listOfFoodDtos) {

			FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder().foodPrice(foodDto.getFoodPrice()).foodName(foodDto.getFoodName()).foodCategory(foodDto.getFoodCategory()).build();
			list.add(foodDetailsResponse);
		}
		return list;
	}
}
