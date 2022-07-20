package io.shane.awareproject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeResource {
	
	@RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin-dashboard/";
        }
        return "redirect:/employee-dashboard/";
    }
	
	
	@GetMapping
	public String home() {
		return "index";
	}
	

	//Mapping for the employee user account
	@GetMapping("/employee-dashboard")
	public String user() {
		return "employeeDashboard";
	}
	
	@GetMapping("/employee-roster")
	public String roster() {
		return "employeeRoster";
	}
	
	
	
	//Mapping for the manager user account
	@GetMapping("/admin-dashboard")
	public String admin() {
		return "adminDashboard";
	}
	
	@GetMapping("/create-roster")
	public String createRoster() {
		return "createRoster";
	}
	
	
	
	@Autowired
	FetchDataService fetchDataService;
	
	@GetMapping(path = "getdata")
	List<UserModel> getUsers(){
		return fetchDataService.findAll();
		
	}

}
