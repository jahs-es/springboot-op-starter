package com.example.domain;

public class Job {
    private String name;
    private int requiredMinAge;

    public Job(String name, int requiredMinAge) {
        this.name = name;
        this.requiredMinAge = requiredMinAge;
    }

    public int getRequiredMinAge() {
        return requiredMinAge;
    }

    public void setRequiredMinAge(int requiredMinAge) {
        this.requiredMinAge = requiredMinAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
