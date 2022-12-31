package com.driver.service.impl;

import com.driver.converter.FoodConverter;
import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    // Function for adding Entitiy to DB
    @Override
    public FoodDto createFood(FoodDto food) {

        FoodEntity foodEntity = FoodEntity.builder().foodId(food.getFoodId()).foodName(food.getFoodName()).foodPrice(food.getFoodPrice()).foodCategory(food.getFoodCategory()).build();
        foodRepository.save(foodEntity);

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

        if (foodEntity == null) {
            System.out.println("FoodId Not Found");
            return null;
        }
        else {
            // convert FoodEntity to FoodDto

            FoodDto foodDto = FoodDto.builder().id(foodEntity.getId()).foodId(foodEntity.getFoodId()).foodName(foodEntity.getFoodName()).foodCategory(foodEntity.getFoodCategory()).foodPrice(foodEntity.getFoodPrice()).build();
            return foodDto;
        }

    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

        if (foodEntity == null) {
            System.out.println("FoodId not Found");
            return null;
        }else {

            foodRepository.updateFoodEntity(foodEntity.getId(), foodDetails.getFoodId(), foodDetails.getFoodName(), foodDetails.getFoodPrice(), foodDetails.getFoodCategory());
            FoodDto updatedFoodDto = foodDetails;
            updatedFoodDto.setId(foodEntity.getId());

            return updatedFoodDto;

        }
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(id);

        if (foodEntity == null) System.out.println("Record Not found");
        else {
            foodRepository.delete(foodEntity);
        }
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> listOfFoodEntities = (List<FoodEntity>) foodRepository.findAll();
        List<FoodDto> listOfFoodDtos = new ArrayList<>();

        for (FoodEntity foodEntity : listOfFoodEntities) {
            listOfFoodDtos.add(FoodConverter.entityToDto(foodEntity));
        }

        return listOfFoodDtos;
    }
}