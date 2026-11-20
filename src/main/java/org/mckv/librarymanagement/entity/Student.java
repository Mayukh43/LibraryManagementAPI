package org.mckv.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "student_id")
    private int studentId;

    @NotBlank(message = "Student name cannot be blank")
    @Size(min = 2, max = 100, message = "Student name should be between 2 to 100 characters")
    @Column(name = "student_name", length = 100, nullable = false)
    private String studentName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Department cannot be blank")
    @Size(min = 2, max = 100, message = "Department should be between 2 to 100 characters")
    @Column(name = "department", length = 100, nullable = false)
    private String department;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$",
             message = "Phone number must contain exactly 10 digits")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    public Student() {
    	
    }
    
    // Parameterized constructor

	public Student(
			@NotBlank(message = "Student name cannot be blank") @Size(min = 2, max = 100, message = "Student name should be between 2 to 100 characters") String studentName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Please enter a valid email address") String email,
			@NotBlank(message = "Department cannot be blank") @Size(min = 2, max = 100, message = "Department should be between 2 to 100 characters") String department,
			@NotBlank(message = "Phone number cannot be blank") String phoneNumber) {
		super();
		this.studentName = studentName;
		this.email = email;
		this.department = department;
		this.phoneNumber = phoneNumber;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", email=" + email + ", department="
				+ department + ", phoneNumber=" + phoneNumber + "]";
	}
    
	
}
