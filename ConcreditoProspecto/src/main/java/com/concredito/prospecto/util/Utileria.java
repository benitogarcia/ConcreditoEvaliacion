package com.concredito.prospecto.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con diferentes funciones simples.
 * 
 * @author benito
 *
 */
public class Utileria {

	private static final Logger log = LoggerFactory.getLogger(Utileria.class);

	/**
	 * Generar un numero de 1 a 10;
	 * 
	 * @return int de entre 1 a 10
	 */
	public static int num() {
		return (int) Math.floor(Math.random() * (1 - 10) + 10);
	}
	
	public static List<String> messagesBindingResult(BindingResult result) {
		
		List<String> messages = result.getFieldErrors().stream().map(error -> {

			return error.getDefaultMessage();
		
		}).collect(Collectors.toList());
		
		return messages;
	}

	public static String errorMessages(BindingResult result) {

		List<Map<String, String>> messages = result.getFieldErrors().stream().map(error -> {
			Map<String, String> _error = new HashMap<>();

			_error.put(error.getField(), error.getDefaultMessage());

			return _error;
		}).collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonString;
	}

	public static String errorMessages(List<Map<String, String>> messages) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonString;
	}

	public static String errorMessagesMap(Map<String, Object> messages) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonString;
	}

}
