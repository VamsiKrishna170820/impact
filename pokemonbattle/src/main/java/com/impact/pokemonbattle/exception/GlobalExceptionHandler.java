package com.impact.pokemonbattle.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.impact.pokemonbattle.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePokemonNotFound(PokemonNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(),LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
