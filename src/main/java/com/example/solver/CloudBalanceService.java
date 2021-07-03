package com.example.solver;

import com.example.domain.CloudBalance;
import com.example.persistence.CloudBalanceRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloudBalance")
public class CloudBalanceService {

    @Autowired
    private CloudBalanceRepository cloudBalanceRepository;
    @Autowired
    private SolverManager<CloudBalance, Long> solverManager;
    @Autowired
    private ScoreManager<CloudBalance, HardSoftScore> scoreManager;

    // To try, GET http://localhost:8080/cloudBalance
    @GetMapping()
    public CloudBalance getBalance() {
        CloudBalance solution = cloudBalanceRepository.findById(CloudBalanceRepository.SINGLETON_ID);
        scoreManager.updateScore(solution); // Sets the score
        solution.setSolverStatus(getSolverStatus());
        return solution;
    }

    @PostMapping("/solve")
    public void solve() {
        solverManager.solveAndListen(CloudBalanceRepository.SINGLETON_ID,
                cloudBalanceRepository::findById,
                cloudBalanceRepository::save);
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(CloudBalanceRepository.SINGLETON_ID);
    }

    @PostMapping("/stopSolving")
    public void stopSolving() {
        solverManager.terminateEarly(CloudBalanceRepository.SINGLETON_ID);
    }

}
