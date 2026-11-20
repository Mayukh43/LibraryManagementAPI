package org.mckv.librarymanagement.controller;

import java.util.List;

import org.mckv.librarymanagement.entity.Student;
import org.mckv.librarymanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    // Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // Update student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}