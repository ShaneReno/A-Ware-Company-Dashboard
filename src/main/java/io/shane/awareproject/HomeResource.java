package io.shane.awareproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeResource {
	
	
	@GetMapping
	public String home() {
		return "index";
	}
	
	
	@GetMapping("/employee-dashboard")
	public String user() {
		return "employeeDashboard";
	}
	
	@GetMapping("/employee-roster")
	public String roster() {
		return "employeeRoster";
	}
	
	
	
	
	@GetMapping("/admin")
	public String admin() {
		return("<h1>Welcome Admin</h1>");
	}

}
