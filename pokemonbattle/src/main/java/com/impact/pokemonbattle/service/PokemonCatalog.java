package com.impact.pokemonbattle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.impact.pokemonbattle.entity.Pokemon;
import com.impact.pokemonbattle.exception.PokemonNotFoundException;
import com.impact.pokemonbattle.repository.PokemonRepo;


@Service
public class PokemonCatalog {
		
	private final PokemonRepo pokemonRepo;


    public PokemonCatalog(PokemonRepo pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }



    public List<Pokemon> getAllPokemon() {
    	
    	return pokemonRepo.findAll();
    	
    	
    	/**
        return pokemonRepo.findAll()
        				  .stream()
        				  .map(Pokemon::getName)
        				  .toList();
        */
    }



    public Pokemon getPokemonByName(String name) {

        return pokemonRepo.findByName(name).orElseThrow(()->new PokemonNotFoundException("Pokemon not found: " + name));
                		 
    }

}
