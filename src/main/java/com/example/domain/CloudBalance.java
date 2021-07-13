package com.example.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
public class CloudBalance {
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "computerRange")
    private List<CloudComputer> computerList;

    @ProblemFactCollectionProperty
    private List<CloudType> cloudTypeList;

    @PlanningEntityCollectionProperty
    private List<CloudProcess> processList;

    @PlanningScore
    private HardSoftScore score;

    // Ignored by OptaPlanner, used by the UI to display solve or stop solving button
    private SolverStatus solverStatus;


    public CloudBalance(List<CloudComputer> computerList, List<CloudProcess> processList, List<CloudType> cloudTypeList) {
        this.computerList = computerList;
        this.processList = processList;
        this.cloudTypeList = cloudTypeList;
    }

    public CloudBalance() {
    }

    public List<CloudComputer> getComputerList() {
        return computerList;
    }

    public void setComputerList(List<CloudComputer> computerList) {
        this.computerList = computerList;
    }

    public List<CloudProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<CloudProcess> processList) {
        this.processList = processList;
    }

    public List<CloudType> getCloudTypeList() {
        return cloudTypeList;
    }

    public void setCloudTypeList(List<CloudType> cloudTypeList) {
        this.cloudTypeList = cloudTypeList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }
}
