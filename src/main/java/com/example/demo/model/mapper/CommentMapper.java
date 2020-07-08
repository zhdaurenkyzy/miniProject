package com.example.demo.model.mapper;

import com.example.demo.model.Article;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
   CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mappings({
            @Mapping(source="comment.author.userName", target="authorName"),
            @Mapping(source="comment.article.articleId", target="articleId")
    })
    CommentDto toDto(Comment comment);

    @Mappings({
            @Mapping(source="commentDto.authorName", target="author.userName"),
            @Mapping(source="commentDto.articleId", target="article.articleId")
    })
    Comment toComment(CommentDto commentDto);
}
