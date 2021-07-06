package com.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private String surName;
    private List<Job> assignedJobs = new ArrayList<>();

    private int age;

    public Person(String name, String surName, int age) {
        this.name = name;
        this.surName = surName;
        this.age = age;
    }

    public boolean isSenior() {
        return (age > 65);
    }

    public void addJob(Job job) throws Exception {
        if (assertJobIsAssignable(job)) {
            throw new Exception("not assignable job due to age");
        }

        assignedJobs.add(job);
    }

    private boolean assertJobIsAssignable(Job job) {
        return (job.getRequiredMinAge() > age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Job> getAssignedJobs() {
        return assignedJobs;
    }

    public void setAssignedJobs(List<Job> assignedJobs) {
        this.assignedJobs = assignedJobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(surName, person.surName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surName, age);
    }
}
