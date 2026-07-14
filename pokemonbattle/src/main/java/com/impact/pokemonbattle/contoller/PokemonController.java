package com.impact.pokemonbattle.contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.impact.pokemonbattle.entity.Pokemon;
import com.impact.pokemonbattle.service.PokemonCatalog;


@RestController
public class PokemonController {
		
	private final PokemonCatalog pokemonCatalog;

    public PokemonController(PokemonCatalog pokemonCatalog) {
        this.pokemonCatalog = pokemonCatalog;
    }
   
    @GetMapping("/pokemons")
    public ResponseEntity<List<Pokemon>> getAllPokemon() {

        return ResponseEntity.ok(pokemonCatalog.getAllPokemon());
    }
    
    @GetMapping("/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name) {
    	
        return ResponseEntity.ok(pokemonCatalog.getPokemonByName(name));
    }


}
