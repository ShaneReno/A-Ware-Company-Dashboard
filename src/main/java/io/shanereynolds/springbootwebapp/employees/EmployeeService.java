package io.shanereynolds.springbootwebapp.employees;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	List<Employee> topics = Arrays.asList(
			new Employee("JohnS336@gmail.com", "cherries22", "John Smith"),
			new Employee("BethHenderson@hotmail.com", "pineapple58", "Beth Henderson"),
			new Employee("tcrimson@outlook.ie", "apples72", "Timothy Crimson")
			);
	
	public List<Employee> getAllTopics() {
		return topics;
	}
	
	public Employee getEmployee(String employeeEmail) {
		return topics.stream().filter(t -> t.getEmployeeEmail().equals(employeeEmail)).findFirst().get();
	}
	


}
