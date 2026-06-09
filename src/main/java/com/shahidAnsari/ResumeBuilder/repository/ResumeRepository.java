package com.shahidAnsari.ResumeBuilder.repository;

import com.shahidAnsari.ResumeBuilder.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<Resume>findByUserIdAndId(Long userId, Long id);

    long countByUserId(Long userId);

    Optional<Resume> findByShareToken(String shareToken);

    Optional<Resume> findByShareSlug(String slug);

    boolean existsByShareSlug(String slug);

    @Query("""
       SELECT DATE(r.createdAt) as date,
              COUNT(r) as count
       FROM Resume r
       WHERE MONTH(r.createdAt) = :month
       GROUP BY DATE(r.createdAt)
       ORDER BY DATE(r.createdAt)
       """)
    List<Object[]> getResumeTrendByMonth(@Param("month") Integer month);

}
