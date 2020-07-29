package com.codebase.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codebase.domain.User;
import com.codebase.exception.CustomException;
import com.codebase.exception.ErrorConstants;
import com.codebase.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
	public String login(@RequestBody @Validated LoginDto loginDto) {
       return userService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(()->
		new CustomException(ErrorConstants.USER.LOGIN_FAILED, HttpStatus.FORBIDDEN));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
	public User signup(@RequestBody @Validated LoginDto loginDto) {
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
				loginDto.getLastName()).orElseThrow(() -> new CustomException(ErrorConstants.USER.ALREADY_EXIST, HttpStatus.BAD_REQUEST));
    }

    @GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

}