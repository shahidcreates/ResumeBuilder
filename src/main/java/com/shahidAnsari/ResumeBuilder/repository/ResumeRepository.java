package com.shahidAnsari.ResumeBuilder.repository;

import com.shahidAnsari.ResumeBuilder.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<Resume>findByUserIdAndId(Long userId, Long id);

    long countByUserId(Long userId);

    Optional<Resume> findByShareToken(String shareToken);

}
