package com.project.quizo.Service.ServiceImpl;

import com.project.quizo.domain.userManagement.User;
import com.project.quizo.domain.verification.VerificationToken;
import com.project.quizo.Exception.TokenIsInvalid;
import com.project.quizo.Repository.VerificationTokenRepository;
import com.project.quizo.Resource.ResourceNotFoundException;
import com.project.quizo.Service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    public static final String NO_SUCH_TOKEN_MESSAGE = "Verification token not found : ";

    @Value("${quizo.verification-token-expiration-time}")
    private Long expirationTimeInMinutes;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken createVerificationToken(User user) {
        return verificationTokenRepository.save(new VerificationToken(user, expirationTimeInMinutes));
    }

    @Override
    public VerificationToken verifyToken(UUID tokenValue) {
        VerificationToken existingToken = findByValue(tokenValue);

        if (existingToken.getExpired() || existingToken.getExpirationTime().isAfter(LocalDateTime.now())) {
            throw new TokenIsInvalid(tokenValue.toString());
        }

        existingToken.getUser().setVerified(Boolean.TRUE);
        existingToken.setExpired(Boolean.TRUE);

        return verificationTokenRepository.save(existingToken);
    }

    @Override
    public VerificationToken findByValue(UUID tokenValue) {
        Optional<VerificationToken> token = verificationTokenRepository.findByValue(tokenValue);
        if (!token.isPresent()) {
            throw new ResourceNotFoundException(NO_SUCH_TOKEN_MESSAGE + tokenValue);
        }

        return token.get();
    }
}