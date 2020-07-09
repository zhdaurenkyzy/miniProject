package com.example.demo.model.mapper;

import com.example.demo.model.Article;
import com.example.demo.model.dto.ArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mappings({
            @Mapping(source = "list", target = "comments")
    })
    ArticleDto toDto(Article article);

    @Mappings({
            @Mapping(source = "comments", target = "list")
    })
    Article toArticle(ArticleDto articleDto);
}
