package com.example.persistence;

import java.util.List;

import com.example.domain.CloudComputer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CloudComputerRepository extends PagingAndSortingRepository<CloudComputer, Long> {

    @Override
    List<CloudComputer> findAll();

}
