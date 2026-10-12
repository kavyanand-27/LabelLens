package com.labellens.repository;

import com.labellens.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Analysis> findTop10ByOrderByCreatedAtDesc();
}
