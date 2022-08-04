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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import io.shane.models.ShiftSwapModel;
import io.shane.services.EmployeeServiceImpl;
import io.shane.services.RoleService;
import io.shane.services.RosterService;
import io.shane.services.RosterServiceImpl;
import io.shane.services.ShiftSwapService;

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
	RosterRepository2 rosterRepository2;
	@Autowired
	ShiftSwapRepository shiftSwapRepository;

	@Autowired
	private RosterService rosterService;
	@Autowired
	private EmployeeServiceImpl employeeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ShiftSwapService shiftSwapService;

	@RequestMapping("/employee-dashboard/employee-roster")
	public String getAll(Model model) {
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		return "employeeRoster";
	}

	@GetMapping("/employee-dashboard")
	public String employeeDashboard(Model model) {
		List<ShiftSwapModel> shiftSwap = shiftSwapService.getAllShiftSwapRequests();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean glowBool = false;
		for(int i = 0; i < shiftSwap.size(); i++) {
			if(auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())){
				glowBool = true;
				model.addAttribute("glowBool", glowBool);
			}
		}
		return "employeeDashboard";
	}

	@RequestMapping("/employee-dashboard/employee-request-shift-swap")
	public String employeeRequestShiftSwap(Model model) {
		List<ShiftSwapModel> shiftSwap = shiftSwapService.getAllShiftSwapRequests();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String youHaveARequest = "";
		for(int i = 0; i < shiftSwap.size(); i++) {
			if(auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())){
				youHaveARequest = "You have a request to swap.";
				model.addAttribute("youHaveARequest", youHaveARequest);
			}
		}
		model.addAttribute("shiftSwap", shiftSwap);
		return "employeeShiftSwap";
	}

	@GetMapping("/employee-dashboard/employee-view-payslip")
	public String employeeViewPayslip(Model model) {
		// RosterModel roster = rosterService.getEmployeeByemployeeId(id);
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();

		model.addAttribute("roster", roster);
		// double wages = (roster.getEmployeeHours() * 11.75);
		// model.addAttribute("wages", wages);#
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		for (int i = 0; i < roster.size(); i++) {
			RosterModel roster2 = rosterService.getEmployeeByemployeeEmail(roster.get(i).getEmployeeEmail());
			if (auth.getName().toString().equals(roster2.getEmployeeEmail())) {
				model.addAttribute("weekNo", roster.get(i).getWeekNo());
				model.addAttribute("hours", roster.get(i).getEmployeeHours());
				double grossWages = (roster.get(i).getEmployeeHours() * 11.75);
				grossWages = Math.round(grossWages*100); //Rounding to 2 decimals
				grossWages = grossWages/100;
				model.addAttribute("grossWages", grossWages);
				
				double netWages = grossWages * .92; //Calculating 'tax'
				netWages = Math.round(netWages*100); //Rounding to 2 decimals
				netWages = netWages/100;
				model.addAttribute("netWages", netWages);
				return "employeeViewPayslip";
			}

		}
		System.out.println("Error occurred");
		return "error";

	}

	@GetMapping("/employee-dashboard/employee-request-holidays")
	public String employeeRequestHolidays() {
		return "employeeRequestHolidays";
	}

	@RequestMapping("/employee-dashboard/employee-view-weather")
	public String employeeViewWeather(Model model) throws IOException, ParseException {
		URL url;
		try {
			// Different API keys for when the calls have exceeded for the day - daily limit
			// on free account
			// url = new URL
			// ("http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=brnuJdH3fQ0kG6Vz6cfgshueYWOdeXnb&details=true");
			url = new URL(
					"http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=KjwhD3BGkYQRYyKEp0j2Q3i3O4fHwg6o&details=true");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();

			if (responseCode != 200) {
				return "error503";
				// throw new RuntimeException("HTTP Response code: " + responseCode);

			} else {
				StringBuilder detailString = new StringBuilder();
				Scanner scan = new Scanner(url.openStream());

				while (scan.hasNext()) {
					detailString.append(scan.nextLine());

				}
				scan.close();
				// System.out.println(detailString);

				JSONParser parser = new JSONParser();
				JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(detailString));

				// System.out.println(dataObject.get(0));

				// Navigating through JSONs
				JSONObject dataCountry = (JSONObject) dataObject.get(0);
				JSONObject dataCountry2 = (JSONObject) dataCountry.get("Temperature");
				JSONObject dataCountry3 = (JSONObject) dataCountry2.get("Metric");

				JSONObject dataCountryWindSpeed = (JSONObject) dataCountry.get("Wind");
				JSONObject dataCountryWindSpeed2 = (JSONObject) dataCountryWindSpeed.get("Speed");
				JSONObject dataCountryWindSpeed3 = (JSONObject) dataCountryWindSpeed2.get("Metric");
				// String weather = dataCountry3.get("Value");

				// Strings for testing
				// System.out.println("Temperature in Dublin is: " + dataCountry3.get("Value") +
				// "C");
				// System.out.println("Raining in Dublin: " +
				// dataCountry.get("HasPrecipitation"));
				model.addAttribute("weather", dataCountry3.get("Value"));
				model.addAttribute("weatherRain", dataCountry.get("HasPrecipitation"));
				model.addAttribute("weatherWindSpeed", dataCountryWindSpeed3.get("Value"));

				// if it is raining in dublin, display rain picture. Vice versa for sun.
				if (dataCountry.get("HasPrecipitation").toString() == "true") {
					model.addAttribute("image", "/images/rain.png");
				} else {
					model.addAttribute("image", "/images/sun.png");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("ERROR");
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
		
		List<EmployeeModel> employeeList = employeeService.getAllEmployees();
		System.out.println(employeeList);
		model.addAttribute("roster", roster);
		model.addAttribute("employeeList", employeeList);
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
		
		RoleModel person = this.roleService.getRoleByUsername(username);
		
		EmployeeModel employee = employeeService.getEmployeeByUsername(username);
		model.addAttribute("employee", employee);
		
		//After user has been updated, if the user was an administrator with 'sreynolds' name,
		//Set them always to admin. This is to disallow changing of the master account to a normal employee account.
		//This will result in a total lockout of the admin dashboard.
		if(username.toString().equals("sreynolds")) {
			person.setAuthority("ROLE_ADMIN");
			person.setUsername("sreynolds"); //Name is static.
		}
		
		
		return "adminFireEmployeeInfo";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") String username) {
		// delete method - if the account being deleted is the master account, do not delete
		if (username.toString().equals("sreynolds")) {
			System.out.println("Cannot delete the master account.");
			return "masterAccountDeleteError";
		} else {
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
