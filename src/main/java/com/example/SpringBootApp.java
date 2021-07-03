package com.example;

import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.persistence.CloudComputerRepository;
import com.example.persistence.CloudProcessRepository;
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
            CloudProcessRepository cloudProcessRepository) {
        return (args) -> {
            CloudComputer computer1 = new CloudComputer(1, 1, 1, 2);
            CloudComputer computer2 = new CloudComputer(2, 2, 2, 4);

            cloudComputerRepository.save(computer1);
            cloudComputerRepository.save(computer2);

            CloudProcess process1 = new CloudProcess(1, 1, 1);
            CloudProcess process2 = new CloudProcess(1, 1, 1);
            CloudProcess process3 = new CloudProcess(1, 1, 1);

            cloudProcessRepository.save(process1);
            cloudProcessRepository.save(process2);
            cloudProcessRepository.save(process3);
        };
    }
}
