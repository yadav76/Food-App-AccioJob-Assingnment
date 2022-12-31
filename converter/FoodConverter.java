package com.driver.converter;

import com.driver.io.entity.FoodEntity;
import com.driver.shared.dto.FoodDto;
import lombok.experimental.UtilityClass;

@UtilityClass    // All methods are static and called using class name
public class FoodConverter {

    public static FoodDto entityToDto(FoodEntity foodEntity) {
        return FoodDto.builder().id(foodEntity.getId()).foodId(foodEntity.getFoodId()).foodCategory(foodEntity.getFoodCategory()).foodName(foodEntity.getFoodName()).foodPrice(foodEntity.getFoodPrice()).foodId(foodEntity.getFoodId()).build();

    }
}
