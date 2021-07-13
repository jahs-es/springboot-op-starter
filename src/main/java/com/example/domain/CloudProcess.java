package com.example.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@PlanningEntity
@Entity
public class CloudProcess {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CloudType type;

    private int requiredCpuPower;
    private int requiredMemory;
    private int requiredNetworkBandwidth;

    @PlanningVariable(valueRangeProviderRefs = {"computerRange"})
    @ManyToOne
    private CloudComputer computer;

    public CloudProcess(Long id, int requiredCpuPower, int requiredMemory, int requiredNetworkBandwidth, CloudComputer computer, CloudType type) {
        this.id = id;
        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
        this.computer = computer;
        this.type = type;
    }

    public CloudProcess(int requiredCpuPower, int requiredMemory, int requiredNetworkBandwidth, CloudType type) {
        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
        this.type = type;
    }

    public CloudProcess() {

    }

    @Override
    public String toString() {
        return "(" + id + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRequiredCpuPower() {
        return requiredCpuPower;
    }

    public void setRequiredCpuPower(int requiredCpuPower) {
        this.requiredCpuPower = requiredCpuPower;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public int getRequiredNetworkBandwidth() {
        return requiredNetworkBandwidth;
    }

    public void setRequiredNetworkBandwidth(int requiredNetworkBandwidth) {
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    public CloudComputer getComputer() {
        return computer;
    }

    public void setComputer(CloudComputer computer) {
        this.computer = computer;
    }

    public CloudType getType() {
        return type;
    }

    public void setType(CloudType type) {
        this.type = type;
    }
}
