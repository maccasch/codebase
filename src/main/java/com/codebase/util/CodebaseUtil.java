package com.codebase.util;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CodebaseUtil {

	public static ObjectNode createErrorJsonNode(HttpStatus status, String message) {
		ObjectNode jsonNode = createJsonNode();
		jsonNode.putPOJO("timestamp", new Date());
		jsonNode.put("status", status.value());
		jsonNode.putPOJO("error", status.getReasonPhrase());
		jsonNode.put("message", message);

		return jsonNode;
	}

	public static ObjectNode createJsonNode() {
		return JsonNodeFactory.instance.objectNode();
	}

}
