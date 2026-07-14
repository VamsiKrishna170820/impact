package com.impact.pokemonbattle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import com.impact.pokemonbattle.entity.Pokemon;
import com.impact.pokemonbattle.exception.PokemonNotFoundException;
import com.impact.pokemonbattle.repository.PokemonRepo;
import com.impact.pokemonbattle.service.PokemonCatalog;

@ExtendWith(MockitoExtension.class)
class PokemonCatalogTest {

    @Mock
    private PokemonRepo pokemonRepo;

    @InjectMocks
    private PokemonCatalog pokemonCatalog;

    private Pokemon pikachu;

    @BeforeEach
    void setUp() {
        pikachu = new Pokemon();
        pikachu.setName("Pikachu");
    }
    
    
    /**
     * Verifies that getAllPokemon() returns a list of Pokémon
     * when the repository contains Pokémon records.
     */

    @Test
    void testGetAllPokemon_ReturnsPokemonList() {

        when(pokemonRepo.findAll()).thenReturn(Arrays.asList(pikachu));

        var result = pokemonCatalog.getAllPokemon();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pikachu", result.get(0).getName());

        verify(pokemonRepo, times(1)).findAll();
    }
    
    
    /**
     * Verifies that getAllPokemon() returns an empty list
     * when no Pokémon are available in the repository.
     */

    @Test
    void testGetAllPokemon_ReturnsEmptyList() {

        when(pokemonRepo.findAll()).thenReturn(Collections.emptyList());

        var result = pokemonCatalog.getAllPokemon();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(pokemonRepo).findAll();
    }
    
    /**
     * Verifies that getPokemonByName() returns the correct Pokémon
     * when a valid Pokémon name exists in the repository.
     */

    @Test
    void testGetPokemonByName_WhenPokemonExists() {

        when(pokemonRepo.findByName("Pikachu")).thenReturn(Optional.of(pikachu));

        Pokemon result = pokemonCatalog.getPokemonByName("Pikachu");

        assertNotNull(result);
        assertEquals("Pikachu", result.getName());

        verify(pokemonRepo).findByName("Pikachu");
    }
    
    
    
    /**
     * Verifies that getPokemonByName() throws
     * PokemonNotFoundException when the specified Pokémon
     * does not exist in the repository.
     */
    @Test
    void testGetPokemonByName_WhenPokemonNotFound() {

        when(pokemonRepo.findByName("Dragon"))
                .thenReturn(Optional.empty());

        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> pokemonCatalog.getPokemonByName("Dragon"));

        assertEquals("Pokemon not found: Dragon", exception.getMessage());

        verify(pokemonRepo).findByName("Dragon");
    }
    
    
    /**
     * Verifies that getPokemonByName() throws
     * PokemonNotFoundException when a null Pokémon name
     * is provided and no matching record exists.
     */
    @Test
    void testGetPokemonByName_WithNullName() {

        when(pokemonRepo.findByName(null))
                .thenReturn(Optional.empty());

        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> pokemonCatalog.getPokemonByName(null));

        assertEquals("Pokemon not found: null", exception.getMessage());

        verify(pokemonRepo).findByName(null);
    }
    
    
    /**
     * Verifies that getPokemonByName() throws
     * PokemonNotFoundException when an empty Pokémon name
     * is provided and no matching record exists.
     */
    @Test
    void testGetPokemonByName_WithEmptyString() {

        when(pokemonRepo.findByName(""))
                .thenReturn(Optional.empty());

        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> pokemonCatalog.getPokemonByName(""));

        assertEquals("Pokemon not found: ", exception.getMessage());

        verify(pokemonRepo).findByName("");
    }
    
 
}


