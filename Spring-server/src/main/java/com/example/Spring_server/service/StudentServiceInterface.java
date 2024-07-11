package com.example.Spring_server.service;

import java.util.List;

import com.example.Spring_server.model.Student;


public interface StudentServiceInterface {
    Student addStudent(Student student);
    List<Student> getStudents();
    Student updateStudent(Student student, Long id);
    Student getStudentById(Long id);
    void deleteStudent(Long id);
}
