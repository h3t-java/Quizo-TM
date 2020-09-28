package com.project.quizo.service.ServiceImpl;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

import com.project.quizo.domain.userManagement.Role;
import com.project.quizo.domain.userManagement.User;
import com.project.quizo.repository.UserRepository;
import com.project.quizo.resource.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {

		User entity = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		return new LoginUser(entity.getId(), entity.getUsername(), entity.getPassword(), getUserAuthorities((Collection<? extends Role>) entity.getRoles()));
	}

	private Set<GrantedAuthority> getUserAuthorities(Collection<? extends Role> userAuthorities) {
		return userAuthorities.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(toSet());
	}

}