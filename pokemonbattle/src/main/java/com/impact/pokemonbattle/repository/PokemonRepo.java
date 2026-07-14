package com.impact.pokemonbattle.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.impact.pokemonbattle.entity.Pokemon;

@Repository
public class PokemonRepo {
	
	
	private final Map<Integer, Pokemon> pokemonMap = new HashMap<>();


    public void save(Pokemon pokemon) {
    	
        pokemonMap.put(pokemon.getId(), pokemon);
    }


    public Optional<Pokemon> findById(int id) {
    	
        return Optional.ofNullable(pokemonMap.get(id));
    }


    public Optional<Pokemon> findByName(String name) {

        return pokemonMap.values()
                .stream()
                .filter(pokemon -> pokemon.getName()
                                   .equalsIgnoreCase(name))
                .findFirst();
    }


    public List<Pokemon> findAll() {

        return new ArrayList<>(pokemonMap.values());
    }


    public int count() {
    	
        return pokemonMap.size();
    }

}
