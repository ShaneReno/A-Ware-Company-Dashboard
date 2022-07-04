package io.shanereynolds.springbootwebapp.employees;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService topicService;
	
	@RequestMapping("topics")
	public List<Employee> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	@RequestMapping("/topics/{foo}")
	public Employee getTopic(@PathVariable("foo") String id) {
		return topicService.getEmployee(id);
	}

}
