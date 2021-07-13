package com.example;

import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.domain.CloudType;
import com.example.persistence.CloudComputerRepository;
import com.example.persistence.CloudProcessRepository;
import com.example.persistence.CloudTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Bean
    public CommandLineRunner demoData(
            CloudComputerRepository cloudComputerRepository,
            CloudTypeRepository cloudTypeRepository,
            CloudProcessRepository cloudProcessRepository) {
        return (args) -> {
            CloudComputer computer1 = new CloudComputer(10, 10, 10, 2);
            CloudComputer computer2 = new CloudComputer(5, 5, 5, 4);

            cloudComputerRepository.save(computer1);
            cloudComputerRepository.save(computer2);

            CloudType type1 = new CloudType("T1");
            CloudType type2 = new CloudType("T2");

            cloudTypeRepository.save(type1);
            cloudTypeRepository.save(type2);

            CloudProcess process1 = new CloudProcess(2, 1, 1,type2);
            CloudProcess process2 = new CloudProcess(2, 1, 1,type1);
            CloudProcess process3 = new CloudProcess(2, 1, 1,type2);
            CloudProcess process4 = new CloudProcess(2, 1, 1,type1);

            cloudProcessRepository.save(process1);
            cloudProcessRepository.save(process2);
            cloudProcessRepository.save(process3);
            cloudProcessRepository.save(process4);
        };
    }
}
