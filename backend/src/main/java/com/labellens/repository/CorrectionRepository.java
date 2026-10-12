package com.labellens.repository;

import com.labellens.model.Correction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CorrectionRepository extends JpaRepository<Correction, Long> {
    List<Correction> findByStatusOrderByCreatedAtDesc(String status);
}
