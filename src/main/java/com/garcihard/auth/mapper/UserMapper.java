package com.garcihard.auth.mapper;

import com.garcihard.auth.model.dto.NewUserDTO;
import com.garcihard.auth.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toEntity(NewUserDTO loginDTO);
}
