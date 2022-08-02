package com.pagadala.atomikos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pagadala.atomikos.service.Roaster;

@SpringBootApplication
public class SpringBootAtomikosApplication 
implements CommandLineRunner
{

	@Autowired
    private Roaster roasterService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAtomikosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roasterService.addPlayer("allstars", "aaditya", 10);		
	}

}
