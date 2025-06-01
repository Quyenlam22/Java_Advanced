package com.group_6.book_store.repository;

import com.group_6.book_store.entity.AuthorPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorPostRepository extends JpaRepository<AuthorPost, Long> {
}