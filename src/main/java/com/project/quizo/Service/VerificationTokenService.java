package com.project.quizo.Service;

import com.project.quizo.Domain.UserManagement.User;
import com.project.quizo.Domain.Verification.VerificationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public interface VerificationTokenService {

    VerificationToken createVerificationToken(User user);

    VerificationToken findByValue(UUID tokenValue);

    VerificationToken verifyToken(UUID tokenValue);

}