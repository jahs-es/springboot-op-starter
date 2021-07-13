package com.example.domain;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CloudType {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    public CloudType(String type) {
        this.type = type;
    }

    public CloudType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public CloudType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
