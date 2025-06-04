package com.group_6.book_store.controller;

import com.group_6.book_store.dto.AuthorDTO;
import com.group_6.book_store.form.AuthorCreateForm;
import com.group_6.book_store.form.AuthorUpdateForm;
import com.group_6.book_store.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorCreateForm form) {
        AuthorDTO authorDTO = authorService.createAuthor(form);
        return ResponseEntity.ok(authorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorUpdateForm form) {
        AuthorDTO authorDTO = authorService.updateAuthor(id, form);
        return ResponseEntity.ok(authorDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getAuthor(id);
        return ResponseEntity.ok(authorDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}