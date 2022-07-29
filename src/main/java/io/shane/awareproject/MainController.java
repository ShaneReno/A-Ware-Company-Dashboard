package io.shane.awareproject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.shane.models.EmployeeModel;
import io.shane.models.RosterModel;
import io.shane.services.EmployeeService;
import io.shane.services.RosterService;
import io.shane.services.RosterServiceImpl;

@Controller
public class MainController {
	
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
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@Autowired
	private RosterService rosterService;
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/employee-dashboard/employee-roster")
	public String getAll(Model model){
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		return "employeeRoster";
	}
		
		
	
	
	
	@GetMapping("/employee-dashboard")
	public String employeeDashboard() {
		return "employeeDashboard";
	}
	
	@GetMapping("/employee-dashboard/employee-request-shift-swap")
	public String employeeRequestShiftSwap() {
		return "employeeShiftSwap";
	}
	
	@GetMapping("/employee-dashboard/employee-view-weather")
	public String employeeViewWeather() {
		return "employeeViewWeather";
	}
	
	@GetMapping("/employee-dashboard/employee-view-payslip")
	public String employeeViewPayslip() {
		return "employeeViewPayslip";
	}
	
	@GetMapping("/employee-dashboard/employee-request-holidays")
	public String employeeRequestHolidays() {
		return "employeeRequestHolidays";
	}
	
	
	
	
	//Mapping for the manager user account
	@GetMapping("/admin-dashboard")
	public String admin() {
		return "adminDashboard";
	}
	
	@GetMapping("/admin-dashboard/admin-create-roster")
	public String createRoster(Model model) {
		RosterModel roster = new RosterModel();
		model.addAttribute("roster", roster);
		return "adminCreateRoster";
	}
	
	@PostMapping("/saveRosteredEmployee")
	public String saveRosteredEmployee(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard";
	}
	
	
	
	
	@GetMapping("/admin-dashboard/admin-view-employee-records")
	public String viewAllEmployees(Model model) {
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		
		List<EmployeeModel> employee = employeeService.getAll();
		model.addAttribute("employee", employee);
		return "adminViewEmployeeRecords";
	}
	
	@GetMapping("/admin-dashboard/admin-add-new-employee-hire")
	public String addHire() {
		return "adminAddNewEmployeeHire";
	}
	

}
