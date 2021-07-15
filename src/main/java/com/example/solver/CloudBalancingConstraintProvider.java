package com.example.solver;

import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.domain.CloudType;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.countDistinct;
import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sum;
import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class CloudBalancingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                requiredCpuPowerTotal(constraintFactory),
                requiredMemoryTotal(constraintFactory),
                requiredNetworkBandwidthTotal(constraintFactory),
                computerCost(constraintFactory),
                typeBalanced(constraintFactory),
                allTypesInEveryComputer(constraintFactory)
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

    protected Constraint typeBalanced(ConstraintFactory factory) {
        return factory.fromUniquePair(CloudProcess.class, equal(CloudProcess::getComputer))
                .filter((process1, process2) -> !process1.getType().equals(process2.getType()))
                .reward("type diversification",HardSoftScore.ONE_SOFT);
    }

    protected Constraint allTypesInEveryComputer(ConstraintFactory factory) {
        return factory.from(CloudComputer.class)
                .join(CloudType.class)
                .ifNotExists(CloudProcess.class,
                        equal((computer, type) ->computer , CloudProcess::getComputer),
                        equal((computer, type) ->type , CloudProcess::getType)
                        )
                .penalize("atLeastOneTypePerComputer",HardSoftScore.ONE_HARD);
    }
}
