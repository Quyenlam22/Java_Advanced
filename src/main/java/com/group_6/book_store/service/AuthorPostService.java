package com.group_6.book_store.service;

import com.group_6.book_store.dto.AuthorPostDTO;
import com.group_6.book_store.entity.Author;
import com.group_6.book_store.entity.AuthorPost;
import com.group_6.book_store.form.AuthorPostCreateForm;
import com.group_6.book_store.form.AuthorPostUpdateForm;
import com.group_6.book_store.mapper.AuthorPostMapper;
import com.group_6.book_store.repository.AuthorPostRepository;
import com.group_6.book_store.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorPostService {

    private final AuthorPostRepository authorPostRepository;
    private final AuthorRepository authorRepository;
    private final AuthorPostMapper authorPostMapper;

    public AuthorPostService(AuthorPostRepository authorPostRepository, AuthorRepository authorRepository,
                             AuthorPostMapper authorPostMapper) {
        this.authorPostRepository = authorPostRepository;
        this.authorRepository = authorRepository;
        this.authorPostMapper = authorPostMapper;
    }

    public Page<AuthorPostDTO> getAllPosts(Pageable pageable) {
        return authorPostRepository.findAll(pageable)
                .map(authorPostMapper::toDTO);
    }

    public Page<AuthorPostDTO> searchPosts(String searchTerm, Pageable pageable) {
        return authorPostRepository.findByTitleContainingIgnoreCase(searchTerm, pageable)
                .map(authorPostMapper::toDTO);
    }


    public AuthorPostDTO getPost(Long id) {
        AuthorPost post = authorPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return authorPostMapper.toDTO(post);
    }

    public AuthorPostDTO createPost(AuthorPostCreateForm form) {
        Author author = authorRepository.findById(form.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + form.getAuthorId()));

        // Kiểm tra quyền: Chỉ ADMIN hoặc AUTHOR có role AUTHOR
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!hasRole("ADMIN") && !hasRole("AUTHOR")) {
            throw new AccessDeniedException("Only ADMIN or AUTHOR can create posts");
        }

        AuthorPost post = authorPostMapper.toEntity(form);
        post.setAuthor(author);
        post = authorPostRepository.save(post);
        return authorPostMapper.toDTO(post);
    }

    public AuthorPostDTO updatePost(Long id, AuthorPostUpdateForm form) {
        AuthorPost post = authorPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        // Kiểm tra quyền: Chỉ ADMIN hoặc AUTHOR của bài viết
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!hasRole("ADMIN")) {
            // Giả định: Liên kết Author với User qua username hoặc logic khác
            // Ở đây, tạm kiểm tra role AUTHOR
            if (!hasRole("AUTHOR")) {
                throw new AccessDeniedException("Only ADMIN or post's AUTHOR can update this post");
            }
        }

        authorPostMapper.updateEntityFromForm(form, post);
        post = authorPostRepository.save(post);
        return authorPostMapper.toDTO(post);
    }

    public void deletePost(Long id) {
        AuthorPost post = authorPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        // Kiểm tra quyền: Chỉ ADMIN hoặc AUTHOR của bài viết
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!hasRole("ADMIN")) {
            if (!hasRole("AUTHOR")) {
                throw new AccessDeniedException("Only ADMIN or post's AUTHOR can delete this post");
            }
        }

        authorPostRepository.deleteById(id);
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + role));
    }

}