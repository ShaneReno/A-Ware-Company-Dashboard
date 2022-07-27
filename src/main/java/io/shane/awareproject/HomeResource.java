package io.shane.awareproject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	
	@Autowired
	RosterRepository rosterRepository;
	
	
	/*
	@GetMapping("/getdata")
	@ResponseBody
	public List<UserModel> getUsers(){
		return fetchDataService.findAll();
		
	}
	*/
	
	@Autowired
	private RosterService rosterService;
	
	@RequestMapping("/employee-roster")
	public String getAll(Model model){
		List<RosterModel> roster = rosterService.getAll();
		model.addAttribute("roster", roster);
		return "employeeRoster";
	}
		
		
	
	
	
	@GetMapping("/employee-dashboard")
	public String employeeDashboard() {
		return "employeeDashboard";
	}
	
	@GetMapping("/employee-request-shift-swap")
	public String employeeRequestShiftSwap() {
		return "employeeShiftSwap";
	}
	
	@GetMapping("/employee-view-weather")
	public String employeeViewWeather() {
		return "employeeViewWeather";
	}
	
	@GetMapping("/employee-view-payslip")
	public String employeeViewPayslip() {
		return "employeeViewPayslip";
	}
	
	@GetMapping("/employee-request-holidays")
	public String employeeRequestHolidays() {
		return "employeeRequestHolidays";
	}
	
	
	
	
	//Mapping for the manager user account
	@GetMapping("/admin-dashboard")
	public String admin() {
		return "adminDashboard";
	}
	
	@GetMapping("/admin-create-roster")
	public String createRoster() {
		return "adminCreateRoster";
	}
	
	
	@RequestMapping("/saveData")
	@ResponseBody
	public String saveData(RosterModel rosterModel) {
		rosterRepository.save(rosterModel);
		return "Success";
	}
	
	
	
	

}
