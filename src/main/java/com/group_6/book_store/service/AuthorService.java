package com.group_6.book_store.service;

import com.group_6.book_store.dto.AuthorDTO;
import com.group_6.book_store.entity.Author;
import com.group_6.book_store.form.AuthorCreateForm;
import com.group_6.book_store.form.AuthorUpdateForm;
import com.group_6.book_store.mapper.AuthorMapper;
import com.group_6.book_store.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public AuthorDTO createAuthor(AuthorCreateForm form) {
        Author author = authorMapper.toEntity(form);
        author = authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorUpdateForm form) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        authorMapper.updateEntityFromForm(form, author);
        author = authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    public AuthorDTO getAuthor(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return authorMapper.toDTO(author);
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
            .map(authorMapper::toDTO)
            .collect(Collectors.toList());
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
}