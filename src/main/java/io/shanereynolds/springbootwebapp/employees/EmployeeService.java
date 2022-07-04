package io.shanereynolds.springbootwebapp.employees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	static List<Employee> employees = new ArrayList<>(Arrays.asList(
			new Employee("johns336@gmail.com", "cherries22", "John Smith"),
			new Employee("bethhenderson@hotmail.com", "pineapple58", "Beth Henderson"),
			new Employee("tcrimson@outlook.ie", "apples72", "Timothy Crimson")
			));
	
	public List<Employee> getAllTopics() {
		return employees;
	}
	
	public Employee getEmployee(String employeeEmail) {
		return employees.stream().filter(t -> t.getEmployeeEmail().equals(employeeEmail)).findFirst().get();
	}

	public void addEmployee(Employee employee) {	
		employees.add(employee);
	}

	public void updateEmployee(String email, Employee employee) {
		for(int i = 0; i < employees.size(); i++) {
			Employee e = employees.get(i);
			if(e.getEmployeeEmail().equals(email)) {
				employees.set(i, employee);
				return;
			}
		}
		
	}

	public Employee deleteEmployee(String email) {
		employees.removeIf(e -> e.getEmployeeEmail().equals(email));
		return null;
	}
	


}
