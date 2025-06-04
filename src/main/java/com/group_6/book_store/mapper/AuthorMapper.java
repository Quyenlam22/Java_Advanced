package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.AuthorDTO;
import com.group_6.book_store.entity.Author;
import com.group_6.book_store.form.AuthorCreateForm;
import com.group_6.book_store.form.AuthorUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
    Author toEntity(AuthorCreateForm form);
    void updateEntityFromForm(AuthorUpdateForm form, @MappingTarget Author author);
}