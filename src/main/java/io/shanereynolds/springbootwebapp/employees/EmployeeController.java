package io.shanereynolds.springbootwebapp.employees;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("employees")
	public List<Employee> getAllTopics() {
		return employeeService.getAllTopics();
	}
	
	@RequestMapping("/employees/{email}")
	public Employee getTopic(@PathVariable String email) {
		return employeeService.getEmployee(email);
	}
	
	@PostMapping(value="/employees")
	public void addEmployee(@RequestBody Employee employee) {
		employeeService.addEmployee(employee);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employees/{email}")
	public void updateEmployee(@RequestBody Employee employee, @PathVariable String email) {
		employeeService.updateEmployee(email, employee);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employees/{email}")
	public Employee deleteTopic(@PathVariable String email) {
		return employeeService.deleteEmployee(email);
	}
	
	

}
