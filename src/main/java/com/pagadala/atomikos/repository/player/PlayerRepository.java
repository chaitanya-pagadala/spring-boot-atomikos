package com.pagadala.atomikos.repository.player;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagadala.atomikos.model.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    
}
