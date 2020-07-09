package com.example.demo.model.mapper;

import com.example.demo.model.User;
import com.example.demo.model.dto.AdminUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminUserMapper {
    AdminUserMapper INSTANCE = Mappers.getMapper(AdminUserMapper.class);

    AdminUserDto toDto(User user);

    User toUser(AdminUserMapper adminUserMapper);
}
