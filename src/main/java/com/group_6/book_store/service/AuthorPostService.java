package com.group_6.book_store.service;

import com.group_6.book_store.dto.AuthorPostDTO;
import com.group_6.book_store.entity.Author;
import com.group_6.book_store.entity.AuthorPost;
import com.group_6.book_store.form.AuthorPostCreateForm;
import com.group_6.book_store.form.AuthorPostUpdateForm;
import com.group_6.book_store.mapper.AuthorPostMapper;
import com.group_6.book_store.repository.AuthorPostRepository;
import com.group_6.book_store.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorPostService {
    @Autowired
    private AuthorPostRepository authorPostRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorPostMapper authorPostMapper;

    public AuthorPostDTO createAuthorPost(AuthorPostCreateForm form) {
        Author author = authorRepository.findById(form.getAuthorId())
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + form.getAuthorId()));
        AuthorPost authorPost = authorPostMapper.toEntity(form);
        authorPost.setAuthor(author);
        authorPost = authorPostRepository.save(authorPost);
        return authorPostMapper.toDTO(authorPost);
    }

    public AuthorPostDTO updateAuthorPost(Long id, AuthorPostUpdateForm form) {
        AuthorPost authorPost = authorPostRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AuthorPost not found with id: " + id));
        authorPostMapper.updateEntityFromForm(form, authorPost);
        authorPost = authorPostRepository.save(authorPost);
        return authorPostMapper.toDTO(authorPost);
    }

    public AuthorPostDTO getAuthorPost(Long id) {
        AuthorPost authorPost = authorPostRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AuthorPost not found with id: " + id));
        return authorPostMapper.toDTO(authorPost);
    }

    public List<AuthorPostDTO> getAllAuthorPosts() {
        return authorPostRepository.findAll().stream()
            .map(authorPostMapper::toDTO)
            .collect(Collectors.toList());
    }

    public void deleteAuthorPost(Long id) {
        if (!authorPostRepository.existsById(id)) {
            throw new RuntimeException("AuthorPost not found with id: " + id);
        }
        authorPostRepository.deleteById(id);
    }
}