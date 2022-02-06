package com.practice.practicingspringboot.config;

import com.practice.practicingspringboot.model.Student;
import com.practice.practicingspringboot.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {

        return args -> {
            Student mateusz = new Student(
                    "Mateusz",
                    "kek.kek@o2.pl",
                    LocalDate.of(2000, Month.APRIL, 5)
            );

            Student zsuetam = new Student(
                    "Zsuetam",
                    "lek.lek@o2.pl",
                    LocalDate.of(2004, Month.APRIL, 5)
            );

            studentRepository.saveAll(
                    List.of(mateusz, zsuetam)
            );
        };
    }
}
