package com.codebase.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codebase.domain.Role;
import com.codebase.domain.User;
import com.codebase.exception.CustomException;
import com.codebase.exception.ErrorConstants;
import com.codebase.repository.RoleRepository;
import com.codebase.repository.UserRepository;
import com.codebase.security.JwtProvider;
import com.codebase.web.LoginDto;

@Service
public class UserService {
	private static final Logger		LOGGER	= LoggerFactory.getLogger(UserService.class);

	private UserRepository			userRepository;

	private AuthenticationManager	authenticationManager;

	private RoleRepository			roleRepository;

	private PasswordEncoder			passwordEncoder;

	private JwtProvider				jwtProvider;

	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	}

	/**
	 * Sign in a user into the application, with JWT-enabled authentication
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return Optional of the Java Web Token, empty otherwise
	 */
	public Optional<String> signin(String username, String password) {
		LOGGER.info("New user attempting to sign in");
		Optional<String> token = Optional.empty();
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
			} catch (AuthenticationException e) {
				LOGGER.info("Log in failed for user {}", username);
			}
		}
		return token;
	}

	/**
	 * Create a new user in the database.
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param firstName
	 *            first name
	 * @param lastName
	 *            last name
	 * @return Optional of user, empty if the user already exists.
	 */
	public Optional<User> signup(LoginDto loginDto) {
		LOGGER.info("New user attempting to sign in");
		Optional<User> user = Optional.empty();
		if (!userRepository.findByUsername(loginDto.getUsername()).isPresent()) {
			Role role = roleRepository.findByRoleName(loginDto.getRole()).orElseThrow(() -> new CustomException(ErrorConstants.ROLE.NOT_PROVIDED, HttpStatus.BAD_REQUEST));
			user = Optional.of(
					userRepository.save(User.builder().username(loginDto.getUsername()).password(passwordEncoder.encode(loginDto.getPassword())).roles(Arrays.asList(role))
							.firstName(loginDto.getFirstName()).lastName(loginDto.getLastName()).build()));
		}
		return user;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
}