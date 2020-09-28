package com.project.quizo.Repository;

import com.project.quizo.domain.testManagement.TestTake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTakeRepository extends JpaRepository<TestTake, Long> {
}
