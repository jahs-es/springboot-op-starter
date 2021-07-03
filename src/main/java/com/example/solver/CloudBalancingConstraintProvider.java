package com.example.solver;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sum;
import static org.optaplanner.core.api.score.stream.Joiners.equal;

import java.util.function.Function;

import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;

public class CloudBalancingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                requiredCpuPowerTotal(constraintFactory),
                requiredMemoryTotal(constraintFactory),
                requiredNetworkBandwidthTotal(constraintFactory),
                computerCost(constraintFactory)
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    Constraint requiredCpuPowerTotal(ConstraintFactory constraintFactory) {
        return constraintFactory.from(CloudProcess.class)
                .groupBy(CloudProcess::getComputer, sum(CloudProcess::getRequiredCpuPower))
                .filter((computer, requiredCpuPower) -> requiredCpuPower > computer.getCpuPower())
                .penalize("requiredCpuPowerTotal",
                        HardSoftScore.ONE_HARD,
                        (computer, requiredCpuPower) -> requiredCpuPower - computer.getCpuPower());
    }

    Constraint requiredMemoryTotal(ConstraintFactory constraintFactory) {
        return constraintFactory.from(CloudProcess.class)
                .groupBy(CloudProcess::getComputer, sum(CloudProcess::getRequiredMemory))
                .filter((computer, requiredMemory) -> requiredMemory > computer.getMemory())
                .penalize("requiredMemoryTotal",
                        HardSoftScore.ONE_HARD,
                        (computer, requiredMemory) -> requiredMemory - computer.getMemory());
    }

    Constraint requiredNetworkBandwidthTotal(ConstraintFactory constraintFactory) {
        return constraintFactory.from(CloudProcess.class)
                .groupBy(CloudProcess::getComputer, sum(CloudProcess::getRequiredNetworkBandwidth))
                .filter((computer, requiredNetworkBandwidth) -> requiredNetworkBandwidth > computer.getNetworkBandwidth())
                .penalize("requiredNetworkBandwidthTotal",
                        HardSoftScore.ONE_HARD,
                        (computer, requiredNetworkBandwidth) -> requiredNetworkBandwidth - computer.getNetworkBandwidth());
    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************

    Constraint computerCost(ConstraintFactory constraintFactory) {
        return constraintFactory.from(CloudComputer.class)
                .ifExists(CloudProcess.class, equal(Function.identity(), CloudProcess::getComputer))
                .penalize("computerCost",
                        HardSoftScore.ONE_SOFT,
                        CloudComputer::getCost);
    }

}
