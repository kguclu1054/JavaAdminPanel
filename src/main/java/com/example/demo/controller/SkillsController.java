package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Skills;
import com.example.demo.service.SkillsService;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillsController {
    private final SkillsService skillService;

    public SkillsController(SkillsService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skills> getSkills() {
        return skillService.getAllSkills();
    }

    @PostMapping("/add")
    public Skills addSkill(@RequestBody Skills skill) {
        return skillService.addSkill(skill);
    }

    @PutMapping("/{id}")
    public Skills updateSkill(@PathVariable Long id, @RequestBody Skills skill) {
        return skillService.updateSkill(id, skill);
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}

