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

    /**
     * A @Transactional with no transaction manager qualifier injects the primary transaction manager.
     * Since the teamTransactionManager in defined as Primary, 
     * any exception adding the player is rolling back the associated transaction.
     * In this case since the team transaction manager is defined as primary it is rolling back adding the team.
     * 
     *  A @Transactional(transactionManager = "playerTransactionManager") injects the player transaction manager.
     * This does not roll back adding the team when a exception occurs adding a player.
     * 
     * This will require us to use a jta transaction manager.
     * 
     * After wiring atomikos, change the @Primary in the config to test rollback
     * 
     * 
     * @param team
     * @param name
     * @param age
     */
    @Transactional
    public void addPlayer(String team, String name, int age) {

        teamService.addTeam(team);
        playerService.addPlayer(name, age);
    }

}
