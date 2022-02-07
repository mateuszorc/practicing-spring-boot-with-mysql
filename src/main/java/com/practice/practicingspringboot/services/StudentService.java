package com.practice.practicingspringboot.services;

import com.practice.practicingspringboot.model.Student;
import com.practice.practicingspringboot.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId );
    }

    @Transactional
    public Student updateStudent(Student student) {

        Student studentEdited = studentRepository.findById(student.getId())
                .orElseThrow(() -> new IllegalStateException("student with id " + student + " does not exist"));

        String name = student.getName();
        String email = student.getEmail();

        if (name != null && name.length() > 0 && !Objects.equals(studentEdited.getName(), name)) {
            studentEdited.setName(student.getName());
        }

        if(email != null && email.length() > 0 && !Objects.equals(studentEdited.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                 throw new IllegalStateException("email taken");
            }
            studentEdited.setEmail(student.getEmail());
        }


        return studentEdited;

    }
}
