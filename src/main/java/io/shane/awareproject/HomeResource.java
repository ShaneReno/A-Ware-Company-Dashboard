package io.shane.awareproject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.shane.models.EmployeeModel;
import io.shane.models.RosterModel;
import io.shane.services.EmployeeService;
import io.shane.services.RosterService;


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
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	/*
	@GetMapping("/getdata")
	@ResponseBody
	public List<UserModel> getUsers(){
		return fetchDataService.findAll();
		
	}
	*/
	
	@Autowired
	private RosterService rosterService;
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/employee-dashboard/employee-roster")
	public String getAll(Model model){
		List<RosterModel> roster = rosterService.getAll();
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
	public String createRoster() {
		return "adminCreateRoster";
	}
	
	@GetMapping("/admin-dashboard/admin-view-employee-records")
	public String viewAllEmployees(Model model) {
		List<RosterModel> roster = rosterService.getAll();
		model.addAttribute("roster", roster);
		
		List<EmployeeModel> employee = employeeService.getAll();
		model.addAttribute("employee", employee);
		return "adminViewEmployeeRecords";
	}
	
	
	
	@RequestMapping("/update-data/{employeeEmail}")
	public ModelAndView showEdit(@PathVariable(name="employeeEmail")String employeeEmail) {
		ModelAndView mav = new ModelAndView("adminCreateRoster");
		RosterModel roster = rosterService.get(employeeEmail);
		mav.addObject("roster", roster);
		return mav;
		
	}
	
	
	
	
	
	
	
	
	
	/*
	@RequestMapping("/saveData")
	@ResponseBody
	public String saveData(RosterModel rosterModel) {
		rosterRepository.save(rosterModel);
		return "Success";
	}
	*/
	
	/*
	@RequestMapping("/update-data")
	@ResponseBody
	public String update(RosterModel rosterModel) {
		RosterModel updateRoster = rosterRepository.findByemployeeEmail(rosterModel.getEmployeeEmail());
		updateRoster.setEmployeeName(rosterModel.getEmployeeName());
		updateRoster.setWeekNo(rosterModel.getWeekNo());
		updateRoster.setMonHours(rosterModel.getMonHours());
		updateRoster.setTuesHours(rosterModel.getTuesHours());
		updateRoster.setWedHours(rosterModel.getWedHours());
		updateRoster.setThursHours(rosterModel.getThursHours());
		updateRoster.setFriHours(rosterModel.getFriHours());
		updateRoster.setSatHours(rosterModel.getSatHours());
		updateRoster.setSunHours(rosterModel.getSunHours());
		updateRoster.setEmployeeDept(rosterModel.getEmployeeDept());
		rosterRepository.save(updateRoster);
		return "adminCreateRoster";
	}
	*/
	
	/*
	@RequestMapping(value="/update-data", method = {RequestMethod.PUT, RequestMethod.GET})
	@ResponseBody
	public String update(RosterModel rosterModel) {
		rosterService.update(rosterModel);
		return "adminCreateRoster";
	}
	*/

}
