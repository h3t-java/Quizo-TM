package com.project.quizo.Repository;

import com.project.quizo.Domain.TestManagement.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByCodeIgnoreCase(String code);
}
