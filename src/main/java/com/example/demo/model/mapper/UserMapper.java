package com.example.demo.model.mapper;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mappings({
            @Mapping(source="articleList", target="articles"),
            @Mapping(source="commentList", target="comments")
    })
    UserDto toDto(User user);
    @Mappings({
            @Mapping(source="articles", target="articleList"),
            @Mapping(source="comments", target="commentList")
    })
    User toUser(UserDto userDto);
}
