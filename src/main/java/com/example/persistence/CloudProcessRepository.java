package com.example.persistence;

import java.util.List;

import com.example.domain.CloudProcess;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CloudProcessRepository extends PagingAndSortingRepository<CloudProcess, Long> {

    @Override
    List<CloudProcess> findAll();

}
