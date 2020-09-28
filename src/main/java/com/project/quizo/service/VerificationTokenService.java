package com.project.quizo.service;

import com.project.quizo.domain.userManagement.User;
import com.project.quizo.domain.verification.VerificationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public interface VerificationTokenService {

    VerificationToken createVerificationToken(User user);

    VerificationToken findByValue(UUID tokenValue);

    VerificationToken verifyToken(UUID tokenValue);

}