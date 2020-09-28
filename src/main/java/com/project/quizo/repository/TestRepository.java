package com.project.quizo.repository;

import com.project.quizo.domain.testManagement.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByCodeIgnoreCase(String code);
}
