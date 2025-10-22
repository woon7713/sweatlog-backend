package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {


    // 삭제되지 않은 전체 게시물 조회
    @Query("SELECT p FROM Post p JOIN FETCH p.details pd JOIN FETCH p.user u WHERE p.deleted = false ORDER BY p.id DESC")
    Page<Post> findAllActive(Pageable pageable);

    //  단일 사용자의 삭제되지않은 게시물 조회
    @Query("SELECT p FROM Post p JOIN FETCH p.details pd JOIN FETCH p.user u WHERE p.user.id = :userId AND p.deleted = false ORDER BY p.id DESC")
    Page<Post> findByUserIdAndNotDeleted(@Param("userId") Long userId, Pageable pageable);

    // 단일 게시물 조회
    Optional<Post> findByIdAndDeletedFalse(Long postId);

    @Query("""
    SELECT p FROM Post p
    WHERE p.deleted = false
      AND LOWER(p.title) LIKE LOWER(CONCAT(:kw, '%'))
  """)
    Page<Post> findByTitlePrefix(@Param("kw") String kw, Pageable pageable);

    @Query("""
    SELECT p FROM Post p
    WHERE p.deleted = false
      AND LOWER(p.title) LIKE LOWER(CONCAT('%', :kw, '%'))
  """)
    Page<Post> findByTitleContains(@Param("kw") String kw, Pageable pageable);

    @Query("""
    SELECT p FROM Post p
    WHERE p.deleted = false
      AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :kw, '%'))
        OR LOWER(COALESCE(p.memo, '')) LIKE LOWER(CONCAT('%', :kw, '%')))
  """)
    Page<Post> findByTitleOrMemoContains(@Param("kw") String kw, Pageable pageable);



}
