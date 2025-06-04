package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.AuthorPostDTO;
import com.group_6.book_store.entity.AuthorPost;
import com.group_6.book_store.form.AuthorPostCreateForm;
import com.group_6.book_store.form.AuthorPostUpdateForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface AuthorPostMapper {
    @Mapping(source = "author.id", target = "authorId")
    AuthorPostDTO toDTO(AuthorPost authorPost);

    @Mapping(source = "authorId", target = "author.id")
    AuthorPost toEntity(AuthorPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    AuthorPost toEntity(AuthorPostCreateForm form);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntityFromForm(AuthorPostUpdateForm form, @MappingTarget AuthorPost authorPost);
}