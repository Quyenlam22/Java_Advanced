package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.AuthorDTO;
import com.group_6.book_store.entity.Author;
import com.group_6.book_store.form.AuthorCreateForm;
import com.group_6.book_store.form.AuthorUpdateForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "profileImage", target = "profileImage")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Author toEntity(AuthorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Author toEntity(AuthorCreateForm form);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntityFromForm(AuthorUpdateForm form, @MappingTarget Author author);
}