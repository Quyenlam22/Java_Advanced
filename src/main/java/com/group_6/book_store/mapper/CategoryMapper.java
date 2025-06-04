package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.CategoryDTO;
import com.group_6.book_store.entity.Category;
import com.group_6.book_store.form.CategoryCreateForm;
import com.group_6.book_store.form.CategoryUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryCreateForm form);
    void updateEntityFromForm(CategoryUpdateForm form, @MappingTarget Category category);
}