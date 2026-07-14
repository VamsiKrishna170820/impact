package com.impact.pokemonbattle.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.impact.pokemonbattle.dto.BattleResult;
import com.impact.pokemonbattle.entity.Pokemon;

@Service
public class BattleGround {
	
	 private final PokemonCatalog pokemonCatalog;
	 private final Random random = new Random();
	 
	 public BattleGround(PokemonCatalog pokemonCatalog) {
		 
		 this.pokemonCatalog = pokemonCatalog;
	 }
	 
	 public BattleResult startBattle(String pokemon1Name,String pokemon2Name) {
		 
		 Pokemon pokemon1 = pokemonCatalog.getPokemonByName(pokemon1Name);
	     Pokemon pokemon2 = pokemonCatalog.getPokemonByName(pokemon2Name);


	        // Create battle copies because HP changes during battle
	        double pokemon1HP = pokemon1.getHitPoints();
	        double pokemon2HP = pokemon2.getHitPoints();

	        Pokemon attacker;
	        Pokemon defender;


	        // Determine first attacker
	        if (pokemon1.getSpeed() > pokemon2.getSpeed()) {
	        	
	        	attacker = pokemon1;
	            defender = pokemon2;

	        } 
	        else if (pokemon2.getSpeed() > pokemon1.getSpeed()) {

	            attacker = pokemon2;
	            defender = pokemon1;

	        } 
	        else {
	            if (random.nextBoolean()) {

	                attacker = pokemon1;
	                defender = pokemon2;

	            } 
	            else {

	                attacker = pokemon2;
	                defender = pokemon1;
	            }
	        }

	        while (pokemon1HP > 0 && pokemon2HP > 0) {

	            double damage = calculateDamage(attacker,defender);
	            if (defender.getName().equalsIgnoreCase(pokemon1.getName())) {

	                pokemon1HP -= damage;
	            } 
	            else {

	                pokemon2HP -= damage;
	            }
	            // Swap attacker and defender
	            Pokemon temp = attacker;
	            attacker = defender;
	            defender = temp;
	        }



	        String winner;
	        double remainingHP;

	        if (pokemon1HP > 0) {

	            winner = pokemon1.getName();
	            remainingHP = pokemon1HP;

	        } 
	        else {

	            winner = pokemon2.getName();
	            remainingHP = pokemon2HP;
	        }

	        return new BattleResult(winner,(int)remainingHP);
	        
	    }

	 private double calculateDamage(Pokemon attacker, Pokemon defender) {
		 
		 double effectiveness = getEffectivenessModifier(attacker.getType(),defender.getType());
		 double damage = 5 *((double) attacker.getAttack()/defender.getDefense()) * effectiveness;
		 
		 return Math.max(1,damage);
		
	 }

	 private double getEffectivenessModifier(String attackerType, String defenderType) {

	        return switch (attackerType.toLowerCase()) {
	        
	            case "fire" -> {
	                if (defenderType.equalsIgnoreCase("grass")) {
	                    yield 2.0;
	                }
	                if (defenderType.equalsIgnoreCase("water")) {
	                    yield 0.5;
	                }
	                yield 1.0;
	            }

	            case "water" -> {
	                if (defenderType.equalsIgnoreCase("fire")) {
	                    yield 2.0;
	                }
	                if (defenderType.equalsIgnoreCase("electric")) {
	                    yield 0.5;
	                }
	                yield 1.0;
	            }

	            case "grass" -> {
	                if (defenderType.equalsIgnoreCase("electric")) {
	                    yield 2.0;
	                }
	                if (defenderType.equalsIgnoreCase("fire")) {
	                    yield 0.5;
	                }
	                yield 1.0;
	            }

	            case "electric" -> {
	                if (defenderType.equalsIgnoreCase("water")) {
	                    yield 2.0;
	                }
	                if (defenderType.equalsIgnoreCase("grass")) {
	                    yield 0.5;
	                }
	                yield 1.0;
	            }
	            default -> 1.0;
	        };
	    }
	 

}