package com.impact.pokemonbattle.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impact.pokemonbattle.dto.AttackRequest;
import com.impact.pokemonbattle.dto.BattleResult;
import com.impact.pokemonbattle.service.BattleGround;

@RestController
public class BattleController {

	private final BattleGround battleGround;
	
	public BattleController(BattleGround battleGround) {
		this.battleGround = battleGround;
	}
	
	@PostMapping("/attack")
    public ResponseEntity<BattleResult> attack(@RequestBody AttackRequest request) {
		
        BattleResult result = battleGround.startBattle(request.pokemon1(),request.pokemon2());
        return ResponseEntity.ok(result);
    }
}
