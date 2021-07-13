package com.example.persistence;

import com.example.domain.CloudBalance;
import com.example.domain.CloudProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CloudBalanceRepository {
    // There is only one planning, so there is only cloudBalanceId (= problemId).
    public static final Long SINGLETON_ID = 1L;

    @Autowired
    private CloudComputerRepository cloudComputerRepository;
    @Autowired
    private CloudProcessRepository cloudProcessRepository;
    @Autowired
    private CloudTypeRepository cloudTypeRepository;

    public CloudBalance findById(Long id) {
        if (!SINGLETON_ID.equals(id)) {
            throw new IllegalStateException("There is no cloudBalance with id (" + id + ").");
        }
        return new CloudBalance(
                cloudComputerRepository.findAll(),
                cloudProcessRepository.findAll(),
                cloudTypeRepository.findAll());
    }

    public void save(CloudBalance cloudBalance) {
        for (CloudProcess cloudProcess : cloudBalance.getProcessList()) {
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
            cloudProcessRepository.save(cloudProcess);
        }
    }

}
