package com.pagadala.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Roaster {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    @Transactional
    public void addPlayer(String team, String name, int age) {

        teamService.addTeam(team);
        playerService.addPlayer(name, age);
    }

}
