package com.pagadala.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagadala.atomikos.model.team.Team;
import com.pagadala.atomikos.repository.team.TeamRepository;

@Service

public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void addTeam(String team) {
        Team t = new Team();
        t.setName(team);
        teamRepository.save(t);
    }

}
