package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Skills;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
}

