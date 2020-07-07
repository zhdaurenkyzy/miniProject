package com.example.demo.model.mapper;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);
    @Mappings({
            @Mapping(source="article.user.userName", target="userName"),
            @Mapping(source="commentList", target="comments")
    })
    ArticleDto toDto(Article article);

    @Mappings({
            @Mapping(source="articleDto.userName", target="user.userName"),
            @Mapping(source="comments", target="commentList")
    })
    Article toArticle(ArticleDto articleDto);
}
