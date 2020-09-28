package com.project.quizo.Repository;

import com.project.quizo.domain.testManagement.SingleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleQuestionRepository extends JpaRepository<SingleChoiceQuestion, Long> {
}
