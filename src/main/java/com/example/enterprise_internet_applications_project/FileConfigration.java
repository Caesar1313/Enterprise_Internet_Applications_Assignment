package com.example.enterprise_internet_applications_project;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.repos.FilesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FileConfigration {
    @Bean
    CommandLineRunner commandLineRunner(FilesRepository filesRepository) {
        return args -> {
            MyFile test1 = new MyFile(
                    "test1",
                    true
            );
            MyFile test2 = new MyFile(
                    "test2",
                    false
            );
            MyFile test3 = new MyFile(
                    "test3",
                    false
            );
            filesRepository.saveAll(
                    List.of(
                            test1,
                            test2,
                            test3
                    )
            );
        };
    }
}
