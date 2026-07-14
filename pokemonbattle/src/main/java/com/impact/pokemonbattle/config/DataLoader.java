package com.impact.pokemonbattle.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.impact.pokemonbattle.entity.Pokemon;
import com.impact.pokemonbattle.repository.PokemonRepo;

import jakarta.annotation.PostConstruct;



@Component
public class DataLoader {
	
	//private final PokemonParser parser;
    private final PokemonRepo repo;


    public DataLoader( PokemonRepo repo) {

        this.repo = repo;
    }


    public List<Pokemon> parse() {

        List<Pokemon> pokemonList = new ArrayList<>();


        try {

            ClassPathResource resource = new ClassPathResource("pokemon.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(",");
                Pokemon pokemon = new Pokemon(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Integer.parseInt(fields[4]),
                        Integer.parseInt(fields[5]),
                        Integer.parseInt(fields[6]),
                        Integer.parseInt(fields[7]),
                        Integer.parseInt(fields[8]),
                        Integer.parseInt(fields[9]),
                        Integer.parseInt(fields[10]),
                        Boolean.parseBoolean(fields[11])
                );
                pokemonList.add(pokemon);
            }
            reader.close();
        } 
        catch (Exception e) {

            throw new RuntimeException("Failed to parse pokemon.csv",e);
        }


        return pokemonList;
    }
    
    

    @PostConstruct
    public void loadPokemonData() {
    	
        List<Pokemon> pokemonList = parse();
        pokemonList.forEach(repo::save);
        System.out.println("Loaded Pokemon count: "+ repo.count());
    }

}
