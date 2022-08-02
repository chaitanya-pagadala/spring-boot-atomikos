package com.pagadala.atomikos.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagadala.atomikos.model.team.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    
}
