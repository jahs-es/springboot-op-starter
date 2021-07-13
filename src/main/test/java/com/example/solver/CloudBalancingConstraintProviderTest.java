/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.solver;

import com.example.domain.CloudBalance;
import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.domain.CloudType;
import org.junit.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;

public class CloudBalancingConstraintProviderTest {

    private final ConstraintVerifier<CloudBalancingConstraintProvider, CloudBalance> constraintVerifier =
            ConstraintVerifier.build(new CloudBalancingConstraintProvider(), CloudBalance.class, CloudProcess.class);

    @Test
    public void requiredCpuPowerTotal() {
        CloudType type1 = new CloudType("T1");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);
        CloudProcess unassignedProcess = new CloudProcess(0L,1, 1, 1, null,type1);
        // Total = 2, available = 1.
        CloudProcess process1 = new CloudProcess(1L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 2L,1, 1, 1, computer1,type1);
        // Total = 1, available = 2.
        CloudProcess process3 = new CloudProcess( 3L,1, 1, 1, computer2,type1);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::requiredCpuPowerTotal)
                .given(unassignedProcess, process1, process2, process3)
                .penalizesBy(1); // Only the first computer.
    }

    @Test
    public void requiredMemoryTotal() {
        CloudType type1 = new CloudType("T1");

        CloudComputer computer1 = new CloudComputer(0L, 1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);
        CloudProcess unassignedProcess = new CloudProcess( 0L,1, 1, 1, null,type1);
        // Total = 2, available = 1.
        CloudProcess process1 = new CloudProcess( 0L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 1L,1, 1, 1, computer1,type1);
        // Total = 1, available = 2.
        CloudProcess process3 = new CloudProcess( 2L,1, 1, 1, computer2,type1);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::requiredMemoryTotal)
                .given(unassignedProcess, process1, process2, process3)
                .penalizesBy(1); // Only the first computer.
    }

    @Test
    public void requiredNetworkBandwidthTotal() {
        CloudType type1 = new CloudType("T1");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);
        CloudProcess unassignedProcess = new CloudProcess( 0L,1, 1, 1, null,type1);
        // Total = 2, available = 1.
        CloudProcess process1 = new CloudProcess( 1L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 2L,1, 1, 1, computer1,type1);
        // Total = 1, available = 2.
        CloudProcess process3 = new CloudProcess( 3L,1, 1, 1, computer2,type1);
        process3.setComputer(computer2);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::requiredNetworkBandwidthTotal)
                .given(unassignedProcess, process1, process2, process3)
                .penalizesBy(1); // Only the first computer.
    }

    @Test
    public void computerCost() {
        CloudType type1 = new CloudType("T1");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);
        CloudProcess unassignedProcess = new CloudProcess( 0L,1, 1, 1,null,type1);
        CloudProcess process = new CloudProcess( 1L,1, 1, 1, computer1,type1);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::computerCost)
                .given(computer1, computer2, unassignedProcess, process)
                .penalizesBy(2);
    }

    @Test
    public void typeDiversification() {
        CloudType type1 = new CloudType("T1");
        CloudType type2 = new CloudType("T2");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);

        CloudProcess process1 = new CloudProcess( 1L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 2L,1, 1, 1, computer1,type2);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::typeDiversification)
                .given(computer1, computer2,process1,process2)
                .rewardsWith(1);
    }

    @Test
    public void atLeastOneTypeInComputerPenalized() {
        CloudType type1 = new CloudType("T1");
        CloudType type2 = new CloudType("T2");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);

        CloudProcess process1 = new CloudProcess( 1L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 2L,1, 1, 1, computer1,type1);
        CloudProcess process3 = new CloudProcess( 3L,1, 1, 1, computer1,type2);
        CloudProcess process4 = new CloudProcess( 4L,1, 1, 1, computer2,type2);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::atLeastOneInComputer)
                .given(computer1, computer2,process1,process2,process3,process4, type1, type2)
                .penalizesBy(1);
    }

    @Test
    public void atLeastOneTypeInComputerUnpenalized() {
        CloudType type1 = new CloudType("T1");
        CloudType type2 = new CloudType("T2");

        CloudComputer computer1 = new CloudComputer( 0L,1, 1, 1, 2);
        CloudComputer computer2 = new CloudComputer( 1L,2, 2, 2, 4);

        CloudProcess process1 = new CloudProcess( 1L,1, 1, 1, computer1,type1);
        CloudProcess process2 = new CloudProcess( 2L,1, 1, 1, computer1,type2);
        CloudProcess process3 = new CloudProcess( 3L,1, 1, 1, computer2,type1);
        CloudProcess process4 = new CloudProcess( 4L,1, 1, 1, computer2,type2);

        constraintVerifier.verifyThat(CloudBalancingConstraintProvider::atLeastOneInComputer)
                .given(computer1, computer2,process1,process2,process3,process4, type1, type2)
                .penalizesBy(0);
    }
}
