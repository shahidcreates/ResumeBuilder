package com.shahidAnsari.ResumeBuilder.repository;

import com.shahidAnsari.ResumeBuilder.entity.Role;
import com.shahidAnsari.ResumeBuilder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<User>findByVerificationToken(String verificationToken);

    Long countByRole(Role role);

    @Query(value = """
        SELECT
            u.id,
            u.name,
            u.email,
            COUNT(r.id) AS resumeCount
        FROM users u
        LEFT JOIN resume r
            ON u.id = r.user_id
        GROUP BY u.id, u.name, u.email
        """, nativeQuery = true)
    List<Object[]> getUserResumeStats();
}
