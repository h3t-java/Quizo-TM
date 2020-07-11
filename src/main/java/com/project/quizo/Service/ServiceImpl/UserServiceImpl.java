package com.project.quizo.Service.ServiceImpl;

import com.project.quizo.Domain.UserManagement.Role;
import com.project.quizo.Domain.UserManagement.User;
import com.project.quizo.Exception.UserAlreadyExistsException;
import com.project.quizo.Repository.UserRepository;
import com.project.quizo.Resource.PasswordDoesNotMatchException;
import com.project.quizo.Resource.ResourceNotFoundException;
import com.project.quizo.Resource.UserChangePassDTO;
import com.project.quizo.Resource.UserRegisterDTO;
import com.project.quizo.Service.RoleService;
import com.project.quizo.Service.UserService;
import com.project.quizo.Service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenServIce;

    public static final String USER_NOT_FOUND_FORMAT = "User not found for %s %s.";
    public static final String PASSWORDS_MUST_BE_DIFFERENT = "New password must be different than old password.";
    public static final String USER_ALREADY_EXIST = "Username or email is taken.";

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_FORMAT, "id", id)));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_FORMAT, "email", email)));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_FORMAT, "username", username)));
    }

    @Override
    @Transactional
    public User registerUser(UserRegisterDTO userRegisterDTO) {
//        userRepository.findByUsernameOrEmail(userRegisterDTO.getUsername(), userRegisterDTO.getEmail())
//				.orElseThrow(() -> new UserAlreadyExistsException(USER_ALREADY_EXIST));
        List<Role> userRoles = roleService.findAllByIds(userRegisterDTO.getRolesIds());
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword())   );
        User user = new User(userRegisterDTO, userRoles);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User changePassword(UserChangePassDTO userResetPassDTO) {

        User user = userRepository.findByUsernameIgnoreCase(userResetPassDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userResetPassDTO.getNewPassword().equalsIgnoreCase(userResetPassDTO.getOldPassword())) {
            throw new IllegalArgumentException(PASSWORDS_MUST_BE_DIFFERENT);
        }

        if (!passwordEncoder.matches(userResetPassDTO.getOldPassword(), user.getPassword())) {
            throw new PasswordDoesNotMatchException();
        }

        user.setPassword(passwordEncoder.encode(userResetPassDTO.getNewPassword()));

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}