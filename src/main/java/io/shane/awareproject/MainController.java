package io.shane.awareproject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import io.shane.models.RoleModel;
import io.shane.models.RosterModel;
import io.shane.services.EmployeeServiceImpl;
import io.shane.services.RoleService;
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

	// Mapping for the employee user account

	@Autowired
	RosterRepository rosterRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private RosterService rosterService;
	@Autowired
	private EmployeeServiceImpl employeeService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/employee-dashboard/employee-roster")
	public String getAll(Model model) {
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

	

	@GetMapping("/employee-dashboard/employee-view-payslip")
	public String employeeViewPayslip() {
		return "employeeViewPayslip";
	}

	@GetMapping("/employee-dashboard/employee-request-holidays")
	public String employeeRequestHolidays() {
		return "employeeRequestHolidays";
	}
	
	@RequestMapping("/employee-dashboard/employee-view-weather")
	public String employeeViewWeather(Model model) throws IOException, ParseException {
		URL url;
		try {
			url = new URL ("http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=brnuJdH3fQ0kG6Vz6cfgshueYWOdeXnb&details=true");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();

			if(responseCode != 200) {
				return "error503";
				//throw new RuntimeException("HTTP Response code: " + responseCode);
				
			}
			else {
				StringBuilder detailString = new StringBuilder();
				Scanner scan = new Scanner(url.openStream());
				
				while(scan.hasNext()) {
					detailString.append(scan.nextLine());
					
				}
				scan.close();
				//System.out.println(detailString);
				
				JSONParser parser = new JSONParser();
				JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(detailString));
				
				//System.out.println(dataObject.get(0));
				
				//Navigating through JSONs
				JSONObject dataCountry = (JSONObject) dataObject.get(0);
				JSONObject dataCountry2 = (JSONObject) dataCountry.get("Temperature");
				JSONObject dataCountry3 = (JSONObject) dataCountry2.get("Metric");
				
				JSONObject dataCountryWindSpeed = (JSONObject) dataCountry.get("Wind");
				JSONObject dataCountryWindSpeed2 = (JSONObject) dataCountryWindSpeed.get("Speed");
				JSONObject dataCountryWindSpeed3 = (JSONObject) dataCountryWindSpeed2.get("Metric");
				//String weather = dataCountry3.get("Value");
				
				
				//Strings for testing
				//System.out.println("Temperature in Dublin is: " + dataCountry3.get("Value") + "C");
				//System.out.println("Raining in Dublin: " + dataCountry.get("HasPrecipitation"));
				model.addAttribute("weather", dataCountry3.get("Value"));
				model.addAttribute("weatherRain", dataCountry.get("HasPrecipitation"));
				model.addAttribute("weatherWindSpeed", dataCountryWindSpeed3.get("Value"));
				
				
				//if it is raining in dublin, display rain picture. Vice versa for sun.
				if(dataCountry.get("HasPrecipitation").toString() == "true") {
					model.addAttribute("image", "/images/rain.png");
				}
				else {
					model.addAttribute("image", "/images/sun.png");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("ERRORRRR");
		}
		
		return "employeeViewWeather";
	}
	
	
	
	
	
	
	
	

	// Mapping for the manager user account
	@GetMapping("/admin-dashboard")
	public String admin() {
		return "adminDashboard";
	}

	@GetMapping("/admin-dashboard/admin-add-employee-to-roster")
	public String createRoster(Model model) {
		RosterModel roster = new RosterModel();
		model.addAttribute("roster", roster);
		return "adminAddEmployeeToRoster";
	}

	@PostMapping("/saveRosteredEmployee")
	public String saveRosteredEmployee(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard";
	}

	@PostMapping("/saveRosteredEmployee2")
	public String saveRosteredEmployee2(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard/admin-view-create-roster";
	}

	@GetMapping("/admin-dashboard/admin-add-new-employee-hire")
	public String addHire(Model model) {
		EmployeeModel employee = new EmployeeModel();
		model.addAttribute("employee", employee);
		return "adminAddNewEmployeeHire";
	}

	@GetMapping("/admin-dashboard/admin-add-new-employee-hire-confirm-role")
	public String addRole(Model model) {
		RoleModel role = new RoleModel();
		model.addAttribute("role", role);
		return "adminAddNewEmployeeHireConfirmRole";
	}

	// Setting the credentials of new registered employee
	@PostMapping("/saveEmployeeHire")
	public String saveEmployeeHire(@ModelAttribute("employee") EmployeeModel employee) {
		employeeService.saveEmployeeHire(employee);
		return "redirect:/admin-dashboard/admin-add-new-employee-hire-confirm-role";
	}

	// Setting the role authority of new registered employee
	@PostMapping("/saveRoleHire")
	public String saveRoleHire(@ModelAttribute("role") RoleModel role) {
		roleService.saveRole(role);
		return "redirect:/admin-dashboard";

	}

	@GetMapping("/admin-dashboard/admin-view-create-roster")
	public String createEditRoster(Model model) {
		RosterModel roster = new RosterModel();
		List<RosterModel> rosterList = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster); // key value pair for form binding
		model.addAttribute("roster", rosterList);
		return "adminCreateEditRoster";
	}

	@GetMapping("/saveCreateEditRoster/{id}")
	public String saveCreateEditRoster(@PathVariable(value = "id") int id, Model model) {
		// update method
		RosterModel roster = rosterService.getEmployeeByemployeeId(id);
		model.addAttribute("roster", roster);
		return "adminCreateEditRosterInfo";
	}

	@GetMapping("/deleteCreateEditRoster/{id}")
	public String deleteRosteredEmployee(@PathVariable(value = "id") int id) {
		// delete method
		this.rosterService.deleteRosteredEmployeeById(id);
		return "adminDashboard";
	}

	@GetMapping("/admin-dashboard/admin-fire-employee")
	public String editEmployee(Model model) {
		EmployeeModel employee = new EmployeeModel();
		List<EmployeeModel> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employee", employee); // key value pair for form binding
		model.addAttribute("employee", employeeList);
		return "adminFireEmployee";
	}

	@GetMapping("/updateEmployee/{id}")
	public String updateEmployee(@PathVariable(value = "id") String username, Model model) {
		// update method
		EmployeeModel employee = employeeService.getEmployeeByUsername(username);
		model.addAttribute("employee", employee);
		return "adminFireEmployeeInfo";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") String username) {
		// delete method
		if (username.toString() == "sreynolds") {
			System.out.println("Cannot delete the master account.");
		}
		else if(username.toString() != "sreynolds"){
			this.employeeService.deleteEmployeeByUsername(username);
		}

		return "adminDashboard";
	}

	@PostMapping("/saveEmployeeRecord")
	public String saveEmployeeRecord(@ModelAttribute("employee") EmployeeModel employee) {
		// As the username dupliates in the string due to it being a string primary key,
		// the returned username must be split by comma

		String user = employee.getUsername();
		String[] parts = user.split(",");
		employee.setUsername(parts[0]);
		employeeService.saveEmployeeHire(employee);

		return "redirect:/admin-dashboard/admin-fire-employee";
	}

	/*
	 * @PostMapping("/saveCreateEditRoster") public String
	 * saveCreateEditRoster(@ModelAttribute("roster") RosterModel roster) {
	 * rosterService.saveRosteredEmployee(roster); return null;
	 * 
	 * }
	 */

	@GetMapping("/admin-dashboard/admin-view-employee-records")
	public String viewAllEmployees(Model model) {
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);

		List<EmployeeModel> employee = employeeService.getAll();
		model.addAttribute("employee", employee);

		List<RoleModel> role = roleService.getAllRoles();
		model.addAttribute("role", role);
		return "adminViewEmployeeRecords";
	}

}
