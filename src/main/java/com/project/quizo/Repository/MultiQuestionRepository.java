package com.project.quizo.Repository;

import com.project.quizo.Domain.NlpManagement.MultiChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiQuestionRepository extends JpaRepository<MultiChoiceQuestion, Long> {
}
