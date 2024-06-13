package com.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.models.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor 
public class EmployeeSearchDao {

	private final EntityManager em;
	
	public List<Employee> findAllBySimpleQuery(String firstname, String lastname, String email) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		
		// Select * from Employee
		Root<Employee> root = criteriaQuery.from(Employee.class);
		
		// Prepare where clause
		// Where firstname like '%ali%'
		Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
		Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
		Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + email + "%");
		Predicate orPredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate);
		
		// Final query ==> select * from Employee where firstname like '%ali%' or lastname like '%ali%' and email like '%email%'
		Predicate andEmailPredicate = criteriaBuilder.and(orPredicate, emailPredicate);
		criteriaQuery.where(andEmailPredicate);
		TypedQuery<Employee> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	public List<Employee> findAllByCriteria(SearchRequest request) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		List<Predicate> predicates = new ArrayList<>();
		
		// Select from Employee
		Root<Employee> root = criteriaQuery.from(Employee.class);
		
		if (request.getFirstname() != null) {
			Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + request.getFirstname() + "%");
			predicates.add(firstnamePredicate);
		}
		
		if (request.getLastname() != null) {
			Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + request.getLastname() + "%");
			predicates.add(lastnamePredicate);
		}
		
		if (request.getEmail() != null) {
			Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%");
			predicates.add(emailPredicate);
		}
		
		criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
		TypedQuery<Employee> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}
}
