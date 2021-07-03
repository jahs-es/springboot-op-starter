package com.example.persistence;

import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.solver.CloudBalanceService;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProblemChangedRepositoryEventListener {

    @Autowired
    private CloudBalanceService cloudBalanceService;

    // TODO Future work: Give the CRUD operations "right of way", by calling something like this:
    // before: solverManager.freeze(TIME_TABLE_ID);
    // after: reloadProblem(TIME_TABLE_ID, cloudBalanceRepository::findById);

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    private void timeslotCreateSaveDelete(CloudProcess cloudProcess) {
        assertNotSolving();
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    private void roomCreateSaveDelete(CloudComputer cloudComputer) {
        assertNotSolving();
    }

    public void assertNotSolving() {
        // TODO Race condition: if a cloudBalanceSolverService.solve() call arrives concurrently,
        // the solver might start before the CRUD transaction completes. That's not very harmful, though.
        if (cloudBalanceService.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            throw new IllegalStateException("The solver is solving. Please stop solving first.");
        }
    }

}
