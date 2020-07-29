package com.codebase.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

import com.codebase.util.CodebaseUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ControllerAdvice("com.codebase")
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleException(CustomException ex) {
		LOGGER.error("Handled Custom Exception", ex);
		ObjectNode errorJsonNode = CodebaseUtil.createErrorJsonNode(ex.getHttpStatus(), ex.getMessage());
		return new ResponseEntity<>(errorJsonNode, ex.getHttpStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse res) throws IOException {
		LOGGER.error("Handled Access denied Exception", ex);
		ObjectNode errorJsonNode = CodebaseUtil.createErrorJsonNode(HttpStatus.FORBIDDEN, "Access denied");
		return new ResponseEntity<>(errorJsonNode, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<?> handleHttpServerErrorException(HttpServerErrorException ex, HttpServletResponse res) throws IOException {
		LOGGER.error("Handled Http Server Error Exception Exception", ex);
		ObjectNode errorJsonNode = CodebaseUtil.createErrorJsonNode(ex.getStatusCode(), ex.getMessage());
		return new ResponseEntity<>(errorJsonNode, ex.getStatusCode());
	}

	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<?> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex, HttpServletResponse res) throws IOException {
		LOGGER.error("Handled Insufficient Authentication Exception", ex);
		ObjectNode errorJsonNode = CodebaseUtil.createErrorJsonNode(HttpStatus.FORBIDDEN, "Insufficient Authentication");
		return new ResponseEntity<>(errorJsonNode, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> handleException(Exception ex) {
		LOGGER.error("Handled Internal Error Exception", ex);
		ObjectNode errorJsonNode = CodebaseUtil.createErrorJsonNode(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(errorJsonNode, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}