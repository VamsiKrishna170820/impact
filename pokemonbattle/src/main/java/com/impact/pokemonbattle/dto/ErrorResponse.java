package com.impact.pokemonbattle.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String message,LocalDateTime timestamp) {

}
