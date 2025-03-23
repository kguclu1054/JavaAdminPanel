package com.example.demo.entity;


import jakarta.persistence.*;

@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    public Skills() {}

    public Skills(String skillName) {
        this.skillName = skillName;
    }

    // Getter - Setter
    public Long getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
