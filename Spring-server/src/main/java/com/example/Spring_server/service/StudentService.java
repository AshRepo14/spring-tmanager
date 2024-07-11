package com.example.Spring_server.service;

import com.example.Spring_server.exception.StudentAlreadyExistsException;
import com.example.Spring_server.exception.StudentNotFoundException;
import com.example.Spring_server.model.Student;
import com.example.Spring_server.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentServiceInterface{
    private final StudentRepo studentRepo;

    @Override
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }
    @Override
    public Student addStudent(Student student) {
        if (studentAlreadyExists(student.getEmail())){
            throw  new StudentAlreadyExistsException(student.getEmail()+ " already exists!");
        }
        return studentRepo.save(student);
    }


    @Override
    public Student updateStudent(Student student, Long id) {
        return studentRepo.findById(id).map(st -> {
            st.setFirstName(student.getFirstName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());
            st.setDepartment(student.getDepartment());
            return studentRepo.save(st);
        }).orElseThrow(() -> new StudentNotFoundException("Sorry, this student could not be found"));
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Sorry, no student found with the Id :" +id));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)){
            throw new StudentNotFoundException("Sorry, student not found");
        }
        studentRepo.deleteById(id);
    }
    private boolean studentAlreadyExists(String email) {
        return studentRepo.findByEmail(email).isPresent();
    }
}
