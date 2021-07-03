package com.example.solver;/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

import com.example.domain.CloudBalance;
import com.example.solver.CloudBalanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "optaplanner.solver.environment-mode=FULL_ASSERT", // Use FULL_ASSERT only for testing, it slows down solving
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"})
public class CloudBalanceServiceTest {

    @Autowired
    private CloudBalanceService cloudBalanceService;

    @Test(timeout = 600_000)
    public void solveDemoDataUntilFeasible() throws InterruptedException {
        cloudBalanceService.solve();
        CloudBalance cloudBalance = cloudBalanceService.getBalance();
        while (cloudBalance.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            // Quick polling (not a Test Thread Sleep anti-pattern)
            // Test is still fast on fast machines and doesn't randomly fail on slow machines.
            Thread.sleep(100L);
            cloudBalance = cloudBalanceService.getBalance();
        }
        assertTrue(cloudBalance.getProcessList().size() > 1);
        assertTrue(cloudBalance.getScore().isFeasible());
    }
}
