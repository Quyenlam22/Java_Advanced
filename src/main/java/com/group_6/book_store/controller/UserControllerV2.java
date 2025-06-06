package com.group_6.book_store.controller;

import com.group_6.book_store.dto.UserDTO;
import com.group_6.book_store.form.UserUpdateForm;
import com.group_6.book_store.service.UserServiceV2;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class UserControllerV2 {

    private final UserServiceV2 userServiceV2;

    public UserControllerV2(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> users = userServiceV2.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam String searchTerm, Pageable pageable) {
        Page<UserDTO> users = userServiceV2.searchUsers(searchTerm, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userServiceV2.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateForm form) {
        UserDTO user = userServiceV2.updateUser(id, form);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceV2.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}