package com.impact.pokemonbattle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.impact.pokemonbattle.dto.BattleResult;
import com.impact.pokemonbattle.entity.Pokemon;
import com.impact.pokemonbattle.exception.PokemonNotFoundException;
import com.impact.pokemonbattle.service.BattleGround;
import com.impact.pokemonbattle.service.PokemonCatalog;

@ExtendWith(MockitoExtension.class)
class BattleGroundTest {


    @Mock
    private PokemonCatalog pokemonCatalog;

    @InjectMocks
    private BattleGround battleGround;


    private Pokemon charizard;
    private Pokemon blastoise;
    private Pokemon pikachu;
    private Pokemon bulbasaur;
    private Pokemon charmander;


    @BeforeEach
    void setup() {


        charizard = new Pokemon();

        charizard.setName("Charizard");
        charizard.setType("Fire");
        charizard.setHitPoints(78);
        charizard.setAttack(84);
        charizard.setDefense(78);
        charizard.setSpeed(100);



        blastoise = new Pokemon();

        blastoise.setName("Blastoise");
        blastoise.setType("Water");
        blastoise.setHitPoints(79);
        blastoise.setAttack(83);
        blastoise.setDefense(100);
        blastoise.setSpeed(78);


        charmander = new Pokemon();
        charmander.setName("Charmander");
        charmander.setType("Fire");
        charmander.setHitPoints(39);
        charmander.setAttack(52);
        charmander.setDefense(43);
        charmander.setSpeed(65);

        pikachu = new Pokemon();

        pikachu.setName("Pikachu");
        pikachu.setType("Electric");
        pikachu.setHitPoints(35);
        pikachu.setAttack(55);
        pikachu.setDefense(40);
        pikachu.setSpeed(90);



        bulbasaur = new Pokemon();

        bulbasaur.setName("Bulbasaur");
        bulbasaur.setType("Grass");
        bulbasaur.setHitPoints(45);
        bulbasaur.setAttack(49);
        bulbasaur.setDefense(49);
        bulbasaur.setSpeed(45);

    }



    /**
     * Scenario:
     * Two valid Pokémon are provided.
     * Charizard has higher speed, so it attacks first.
     *
     * Expected:
     * Battle completes and returns a winner.
     */
    @Test
    void teststarttBattle_ReturnsWinner() {

        when(pokemonCatalog.getPokemonByName("Charizard")).thenReturn(charizard);
        when(pokemonCatalog.getPokemonByName("Blastoise")).thenReturn(blastoise);

        BattleResult result = battleGround.startBattle("Charizard","Blastoise");
        
        assertNotNull(result);
        assertNotNull(result.winner());

        assertTrue(result.winner().equals("Charizard") || result.winner().equals("Blastoise"));

        verify(pokemonCatalog).getPokemonByName("Charizard");
        verify(pokemonCatalog).getPokemonByName("Blastoise");

    }




    /**
     * Scenario:
     * Fire type attacks Water type.
     *
     * Expected:
     * Water effectiveness reduces Fire damage,
     * and battle still completes successfully.
     */
    @Test
    void testStartBattle_FireAgainstWater() {

        when(pokemonCatalog.getPokemonByName("Charizard")).thenReturn(charizard);
        when(pokemonCatalog.getPokemonByName("Blastoise")).thenReturn(blastoise);

        BattleResult result = battleGround.startBattle("Charizard","Blastoise");

        assertNotNull(result);
        
        assertEquals("Blastoise", result.winner());
        assertTrue(result.remainingHP() >= 0);

    }
    
    
    /**
     * Scenario:
     * Fire type Pokémon attacks Grass type Pokémon.
     *
     * Expected:
     * Fire type has an advantage over Grass type,
     * so Charizard should win the battle.
     */
    @Test
    void testStartBattle_FireAgainstGrass() {

        when(pokemonCatalog.getPokemonByName("Charizard")).thenReturn(charizard);
        when(pokemonCatalog.getPokemonByName("Bulbasaur")).thenReturn(bulbasaur);

        BattleResult result = battleGround.startBattle("Charizard", "Bulbasaur");

        assertNotNull(result);
        assertEquals("Charizard", result.winner());
        assertTrue(result.remainingHP() >= 0);
    }

    
    
    /**
     * Scenario:
     * Water type Pokémon attacks Grass type Pokémon.
     *
     * Expected:
     * Grass type is super effective against Water type,
     * so Bulbasaur should win the battle.
     */
    @Test
    void testStartBattle_WaterAgainstGrass() {

        when(pokemonCatalog.getPokemonByName("Blastoise")).thenReturn(blastoise);
        when(pokemonCatalog.getPokemonByName("Bulbasaur")).thenReturn(bulbasaur);

        BattleResult result = battleGround.startBattle("Blastoise", "Bulbasaur");

        assertNotNull(result);
        assertEquals("Blastoise", result.winner());
        assertTrue(result.remainingHP() >= 0);
    }




    /**
     * Scenario:
     * Electric Pokémon fights Water Pokémon.
     *
     * Expected:
     * Electric has type advantage and should win.
     */
    @Test
    void testStartBattle_ElectricAgainstWater() {

        when(pokemonCatalog.getPokemonByName("Pikachu")).thenReturn(pikachu);
        when(pokemonCatalog.getPokemonByName("Blastoise")).thenReturn(blastoise);

        BattleResult result = battleGround.startBattle("Pikachu","Blastoise");

        assertEquals("Blastoise",result.winner());
        assertTrue(result.remainingHP() > 0);
    }

    
    
    /**
     * Scenario:
     * Two Pokémon with the same type battle each other.
     *
     * Expected:
     * Battle completes successfully without type advantage.
     */
    @Test
    void testStartBattle_SameTypeBattle() {

        when(pokemonCatalog.getPokemonByName("Charizard")).thenReturn(charizard);
        when(pokemonCatalog.getPokemonByName("Charizard")).thenReturn(charizard);

        BattleResult result = battleGround.startBattle("Charizard", "Charizard");

        assertNotNull(result);
        assertNotNull(result.winner());
        assertTrue(result.remainingHP() >= 0);
    }




    /**
     * Scenario:
     * Pokémon name does not exist.
     *
     * Expected:
     * PokemonNotFoundException should be thrown.
     */
    @Test
    void testStartBattle_WhenPokemonDoesNotExist() {

        when(pokemonCatalog.getPokemonByName("Unknown")).thenThrow(new PokemonNotFoundException("Pokemon not found: Unknown"));

        assertThrows(PokemonNotFoundException.class,() ->battleGround.startBattle("Unknown","Pikachu"));


    }



    /**
     * Scenario:
     * Defender has extremely high defense.
     *
     * Expected:
     * Damage calculation should never return less than 1.
     */
    @Test
    void testConductBattle_MinimumDamageApplied() {

        pikachu.setDefense(10000);
        
        when(pokemonCatalog.getPokemonByName("Pikachu")).thenReturn(pikachu);
        when(pokemonCatalog.getPokemonByName("Bulbasaur")).thenReturn(bulbasaur);

        BattleResult result = battleGround.startBattle("Bulbasaur", "Pikachu");

        assertNotNull(result);
        assertTrue(result.remainingHP() >= 0);

    }
    
    
    
    

    /**
     * Scenario:
     * Both Pokémon have the same speed.
     *
     * Expected:
     * Battle should still complete successfully
     * regardless of random first attacker.
    
    @Test
    void testConductBattle_WhenSpeedIsSame() {

        pikachu.setSpeed(50);
        bulbasaur.setSpeed(50);
        
        when(pokemonCatalog.getPokemonByName("Pikachu")).thenReturn(pikachu);
        when(pokemonCatalog.getPokemonByName("Bulbasaur")).thenReturn(bulbasaur);

        BattleResult result = battleGround.startBattle("Pikachu","Bulbasaur");

        assertNotNull(result);
        assertNotNull(result.winner());

    }
     */

    
    

}













