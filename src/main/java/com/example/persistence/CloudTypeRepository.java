package com.example.persistence;

import com.example.domain.CloudProcess;
import com.example.domain.CloudType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CloudTypeRepository extends PagingAndSortingRepository<CloudType, Long> {

    @Override
    List<CloudType> findAll();

}
