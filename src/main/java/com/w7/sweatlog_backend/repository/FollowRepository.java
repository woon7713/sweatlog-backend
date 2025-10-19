package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.Follow;
import com.w7.sweatlog_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.following = :user")
    Long countFollowers(@Param("user") User user);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower = :user")
    Long countFollowing(@Param("user") User user);

    @Query("SELECT f.follower FROM Follow f WHERE f.following = :user")
    Page<User> findFollowers(@Param("user") User user, Pageable pageable);

    @Query("SELECT f.following FROM Follow f WHERE f.follower = :user")
    Page<User> findFollowing(@Param("user") User user, Pageable pageable);

    void deleteByFollowerAndFollowing(User follower, User following);
}