package com.example.securityWithJDBC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.securityWithJDBC.dao.EmployeeDao;
import com.example.securityWithJDBC.model.Employee;
import com.example.securityWithJDBC.service.EmployeeService;




@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public void insertEmployee(Employee emp) {
		
		employeeDao.insertEmployee(emp);
	}

	@Override
	public void insertEmployees(List<Employee> employees) {
		employeeDao.insertEmployees(employees);
		
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Override
	public void getEmployeeById(String empid) {
		Employee employee = employeeDao.getEmployeeById(empid);
		System.out.println(employee);
		
	}

	

}