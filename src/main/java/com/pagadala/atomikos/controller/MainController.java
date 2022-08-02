package com.pagadala.atomikos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pagadala.atomikos.service.Roaster;

@RestController
@RequestMapping(path = "/demo")
public class MainController {

    @Autowired
    private Roaster roasterService;

    @PostMapping(path = "/player/add")
    public @ResponseBody String addNewPlayer(@RequestParam String team, @RequestParam String name, @RequestParam int age) {

        roasterService.addPlayer(team, name, age);
        return "Saved";
    }
}
