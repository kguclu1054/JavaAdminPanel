package com.example.demo.entity;


import jakarta.persistence.*;

@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private int percentage;

    public Skills() {}

    public Skills(String skillName, int percentage) {
        this.skillName = skillName;
        this.percentage = percentage;
    }

    public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
