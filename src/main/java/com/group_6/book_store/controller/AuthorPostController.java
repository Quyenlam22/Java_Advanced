package com.group_6.book_store.controller;

import com.group_6.book_store.dto.AuthorPostDTO;
import com.group_6.book_store.form.AuthorPostCreateForm;
import com.group_6.book_store.form.AuthorPostUpdateForm;
import com.group_6.book_store.service.AuthorPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author-posts")
public class AuthorPostController {
    @Autowired
    private AuthorPostService authorPostService;

    @PostMapping
    public ResponseEntity<AuthorPostDTO> createAuthorPost(@Valid @RequestBody AuthorPostCreateForm form) {
        AuthorPostDTO authorPostDTO = authorPostService.createAuthorPost(form);
        return ResponseEntity.ok(authorPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorPostDTO> updateAuthorPost(@PathVariable Long id, @Valid @RequestBody AuthorPostUpdateForm form) {
        AuthorPostDTO authorPostDTO = authorPostService.updateAuthorPost(id, form);
        return ResponseEntity.ok(authorPostDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorPostDTO> getAuthorPost(@PathVariable Long id) {
        AuthorPostDTO authorPostDTO = authorPostService.getAuthorPost(id);
        return ResponseEntity.ok(authorPostDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuthorPostDTO>> getAllAuthorPosts() {
        List<AuthorPostDTO> authorPosts = authorPostService.getAllAuthorPosts();
        return ResponseEntity.ok(authorPosts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorPost(@PathVariable Long id) {
        authorPostService.deleteAuthorPost(id);
        return ResponseEntity.noContent().build();
    }
}