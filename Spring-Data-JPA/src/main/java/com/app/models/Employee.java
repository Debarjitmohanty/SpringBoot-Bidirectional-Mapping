package com.app.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false,unique=true)
	private String identifier;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable=false)
	private String lastName;
	
	@Column(nullable = false,unique=true)
	private String email;
	
	@Column(nullable=false)
	private LocalDate birthdate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private EmployeeRole role;
	
	@OneToOne
	@JoinColumn(name="address_id")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@ManyToMany
	@JoinTable(
			name="employee_mission",
			joinColumns=@JoinColumn(name="employee_id"),
			inverseJoinColumns=@JoinColumn(name="mission_id")
			)
	private List<Mission> missions;
}
