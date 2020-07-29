package com.codebase.exception;

public final class ErrorConstants {

	public final static class USER {
		public static final String	LOGIN_FAILED	= "Login Failed";
		public static final String	ALREADY_EXIST	= "User already exists";
	}

	public final static class ROLE {
		public static final String NOT_PROVIDED = "Role is not provided for that user";
	}
}
