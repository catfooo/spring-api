package com.catfood.store.mappers;

import com.catfood.store.dtos.UserDto;
import com.catfood.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
