package com.pagadala.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagadala.atomikos.model.player.Player;
import com.pagadala.atomikos.repository.player.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public void addPlayer(String name, int age) {
        Player n = new Player();
        n.setName(name);
        n.setAge(age);
        playerRepository.save(n);

    }
}
