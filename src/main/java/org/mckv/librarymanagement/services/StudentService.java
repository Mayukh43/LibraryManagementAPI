package org.mckv.librarymanagement.services;

import java.util.List;

import org.mckv.librarymanagement.entity.Student;
import org.mckv.librarymanagement.exceptions.DuplicateResourceException;
import org.mckv.librarymanagement.exceptions.InvalidOperationException;
import org.mckv.librarymanagement.exceptions.ResourceNotFoundException;
import org.mckv.librarymanagement.repository.BookIssueRepository;
import org.mckv.librarymanagement.repository.StudentRepository;
import org.mckv.librarymanagement.repository.UniversalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {
	private final UniversalRepository universalRepository;
	private final StudentRepository studentRepository;
	private final BookIssueRepository bookIssueRepository;

// Constructor
public StudentService(UniversalRepository universalRepository,
                      StudentRepository studentRepository,
                      BookIssueRepository bookIssueRepository) {

    this.universalRepository = universalRepository;
    this.studentRepository = studentRepository;
    this.bookIssueRepository = bookIssueRepository;
}

// Add a new student
public Student addStudent(Student student) {

    if (studentRepository.existsByEmail(student.getEmail())) {
        throw new DuplicateResourceException(
                "A student with this email already exists");
    }

    return universalRepository.save(student);
}

// Get all students
@Transactional(readOnly = true)
public List<Student> getAllStudents() {

    return universalRepository.findAll(Student.class);
}

// Get student by ID
@Transactional(readOnly = true)
public Student getStudentById(int studentId) {

    Student student = universalRepository.findById(
            Student.class, studentId);

    if (student == null) {
        throw new ResourceNotFoundException(
                "Student not found with ID: " + studentId);
    }

    return student;
}

// Update student
public Student updateStudent(int studentId, Student updatedStudent) {

    Student existingStudent = getStudentById(studentId);

    if (!existingStudent.getEmail().equals(updatedStudent.getEmail())
            && studentRepository.existsByEmail(updatedStudent.getEmail())) {

        throw new DuplicateResourceException(
                "A student with this email already exists");
    }

    existingStudent.setStudentName(updatedStudent.getStudentName());
    existingStudent.setEmail(updatedStudent.getEmail());
    existingStudent.setDepartment(updatedStudent.getDepartment());
    existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());

    return universalRepository.save(existingStudent);
}

// Delete student
public void deleteStudent(int studentId) {

    Student student = getStudentById(studentId);

    long issueHistory =
            bookIssueRepository.countByStudentStudentId(studentId);

    if (issueHistory > 0) {
        throw new InvalidOperationException(
                "Cannot delete this student because book issue history exists");
    }

    universalRepository.delete(student);
}
}

