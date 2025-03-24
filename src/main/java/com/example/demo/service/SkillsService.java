package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Skills;
import com.example.demo.repository.SkillsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SkillsService {
    private final SkillsRepository skillRepository;

    public SkillsService(SkillsRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skills> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skills addSkill(Skills skill) {
        return skillRepository.save(skill);
    }

    public Skills updateSkill(Long id, Skills newSkill) {
        Optional<Skills> existingSkill = skillRepository.findById(id);
        if (existingSkill.isPresent()) {
            Skills skill = existingSkill.get();
            skill.setSkillName(newSkill.getSkillName());
            skill.setPercentage(newSkill.getPercentage());
            return skillRepository.save(skill);
        }
        return null;
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}

