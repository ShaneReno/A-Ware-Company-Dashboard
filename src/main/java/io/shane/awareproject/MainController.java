package io.shane.awareproject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
	
	
	@RequestMapping("favicon.ico")
    String favicon() {
    return "forward:/favicon.ico";
}

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
		for (int i = 0; i < shiftSwap.size(); i++) {
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
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
		for (int i = 0; i < shiftSwap.size(); i++) {
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
				youHaveARequest = "You have a request to swap";
				model.addAttribute("youHaveARequest", youHaveARequest);
			}
		}
		model.addAttribute("shiftSwap", shiftSwap);
		return "employeeShiftSwap";
	}

	@RequestMapping("/employee-dashboard/employee-shift-swap-request")
	public String getShiftSwapRequest(Model model) {
		ShiftSwapModel shiftSwapModel = new ShiftSwapModel();
		model.addAttribute("shiftSwapModel", shiftSwapModel);
		List<ShiftSwapModel> shiftSwapList = shiftSwapService.getAllShiftSwapRequests();
		model.addAttribute("shiftSwapList", shiftSwapList);
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		return "employeeShiftSwapRequest";
	}

	@PostMapping("/employee-dashboard/employee-shift-swap-request-sent")
	public String saveShiftSwapRequest(@ModelAttribute("shiftSwapModel") ShiftSwapModel model) {
		shiftSwapService.saveShiftSwapRequest(model);
		return "redirect:/employee-dashboard";
	}

	//List<ShiftSwapModel> shiftSwapInstance = new ArrayList<ShiftSwapModel>();
	LinkedHashSet<ShiftSwapModel> shiftSwapInstance = new LinkedHashSet<ShiftSwapModel>();
	@RequestMapping("/employee-dashboard/employee-shift-swap-response")
	public String employeeRequestShiftSwapResponse(Model model) {
		List<ShiftSwapModel> shiftSwap2 = shiftSwapService.getAllShiftSwapRequests();
		
		List<ShiftSwapModel> shiftSwap = new ArrayList<ShiftSwapModel>(shiftSwap2);
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String employeeEmails = "";

		for (int i = 0; i < shiftSwap.size(); i++) {
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
				shiftSwapInstance.add(shiftSwap.get(i));
				model.addAttribute("shiftSwapInstance", shiftSwap);

				employeeEmails += shiftSwap.get(i).getEmployeeEmail() + ", ";
				model.addAttribute("employeeEmails", employeeEmails);
			}
		}
		
		//Debug 
		//System.out.println("Total Shift Swap Requests: " + shiftSwap.size());
		//System.out.println("Shift Swap for this user: " + shiftSwapInstance.size());

		return "employeeShiftSwapResponse";
	}
	
	
	

	@GetMapping("/employee-dashboard/accept-shift-swap-request/{id}")
	public String accecptShiftSwapRequest(@PathVariable(value = "id") int id, Model model) {
		// update method
		ShiftSwapModel shiftSwapInstance = shiftSwapService.getShiftSwapRequestById(id);
		shiftSwapInstance.setAccepted(true);
		model.addAttribute("shiftSwapInstance", shiftSwapInstance);
		System.out.println("Here1");
		// Swapping around the instance variables so that the shift swap can be
		// reflected on the roster:
		shiftSwapService.saveShiftSwapRequest(shiftSwapInstance);
		/*
		 * String swapping1 = shiftSwapInstance.getEmployeeEmail(); String swapping2 =
		 * shiftSwapInstance.getRecipientEmail(); String tempSwap = swapping1; swapping1
		 * = swapping2; swapping2 = tempSwap;
		 * 
		 * String swappingDay1 = shiftSwapInstance.getSwapDay(); String swappingDay2 =
		 * shiftSwapInstance.getForDay(); String tempSwapDay = swappingDay1;
		 * swappingDay1 = swappingDay2; swappingDay2 = tempSwapDay;
		 * 
		 * 
		 * System.out.println("swapping1: " + swapping1);
		 * System.out.println("swapping2: " + swapping2);
		 * 
		 * System.out.println("swapday1: " + swappingDay1);
		 * System.out.println("swapday2: " + swappingDay2);
		 */

		RosterModel person1 = this.rosterService.getEmployeeByemployeeEmail(shiftSwapInstance.getEmployeeEmail());

		RosterModel person2 = this.rosterService.getEmployeeByemployeeEmail(shiftSwapInstance.getRecipientEmail());

		System.out.println("person1: " + person1);
		System.out.println("person2: " + person2);
		System.out.println(shiftSwapInstance.getEmployeeEmail().toString());
		System.out.println(shiftSwapInstance.getRecipientEmail().toString());
		
		
		
		//ALL 49 POSSIBLE DAY SWAP COMBINATIONS - RELEVANT SWITCHES ARE MADE HERE TO THE ROSTER
		// Swapping employee's Monday with:
		if (shiftSwapInstance.getSwapDay().toString().equals("Monday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP MONDAY FOR MONDAY");
				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getMonHours());
				person2.setMonHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP MONDAY FOR TUESDAY");
				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP MONDAY FOR WEDNESDAY");

				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getWedHours());
				person2.setWedHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP MONDAY FOR THURSDAY");

				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getThursHours());
				person2.setThursHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP MONDAY FOR FRIDAY");

				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getFriHours());
				person2.setFriHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP MONDAY FOR SATURDAY");

				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getSatHours());
				person2.setSatHours(tempHours);
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP MONDAY FOR SUNDAY");

				String tempHours = person1.getMonHours();
				person1.setMonHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}

		// Swapping employee's Tuesday with:
		} else if (shiftSwapInstance.getSwapDay().toString().equals("Tuesday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP TUESDAY FOR MONDAY");
				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP TUESDAY FOR TUESDAY");
				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP TUESDAY FOR WEDNESDAY");

				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP TUESDAY FOR THURSDAY");

				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP TUESDAY FOR FRIDAY");

				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP TUESDAY FOR SATURDAY");

				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP TUESDAY FOR SUNDAY");

				String tempHours = person1.getTuesHours();
				person1.setTuesHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}

		// Swapping employee's Wednesday with:
		} else if (shiftSwapInstance.getSwapDay().toString().equals("Wednesday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP WEDNESDAY FOR MONDAY");
				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP WEDNESDAY FOR TUESDAY");
				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP WEDNESDAY FOR WEDNESDAY");

				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP WEDNESDAY FOR THURSDAY");

				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP WEDNESDAY FOR FRIDAY");

				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP WEDNESDAY FOR SATURDAY");

				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP WEDNESDAY FOR SUNDAY");

				String tempHours = person1.getWedHours();
				person1.setWedHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}

		// Swapping employee's Thursday with:
		} else if (shiftSwapInstance.getSwapDay().toString().equals("Thursday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP THURSDAY FOR MONDAY");
				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP THURSDAY FOR TUESDAY");
				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP THURSDAY FOR WEDNESDAY");

				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP THURSDAY FOR THURSDAY");

				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP THURSDAY FOR FRIDAY");

				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP THURSDAY FOR SATURDAY");

				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP THURSDAY FOR SUNDAY");

				String tempHours = person1.getThursHours();
				person1.setThursHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}

			
		// Swapping employee's Friday with:
		} else if (shiftSwapInstance.getSwapDay().toString().equals("Friday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP FRIDAY FOR MONDAY");
				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP FRIDAY FOR TUESDAY");
				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP FRIDAY FOR WEDNESDAY");

				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP FRIDAY FOR THURSDAY");

				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP FRIDAY FOR FRIDAY");

				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP FRIDAY FOR SATURDAY");

				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP FRIDAY FOR SUNDAY");

				String tempHours = person1.getFriHours();
				person1.setFriHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}

		

		// Swapping employee's Saturday with:
		} else if (shiftSwapInstance.getSwapDay().toString().equals("Saturday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP SATURDAY FOR MONDAY");
				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP SATURDAY FOR TUESDAY");
				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP SATURDAY FOR WEDNESDAY");

				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP SATURDAY FOR THURSDAY");

				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP SATURDAY FOR FRIDAY");

				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP SATURDAY FOR SATURDAY");

				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP SATURDAY FOR SUNDAY");

				String tempHours = person1.getSatHours();
				person1.setSatHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}
		}
		
		// Swapping employee's Sunday with:
		else if (shiftSwapInstance.getSwapDay().toString().equals("Sunday")) {
			// Monday
			if (shiftSwapInstance.getForDay().toString().equals("Monday")) {
				System.out.println("SWAP SATURDAY FOR MONDAY");
				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getMonHours());
				person2.setMonHours(tempHours);
				// Tuesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Tuesday")) {
				System.out.println("SWAP SATURDAY FOR TUESDAY");
				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getTuesHours());
				person2.setTuesHours(tempHours);
				// Wednesday
			} else if (shiftSwapInstance.getForDay().toString().equals("Wednesday")) {
				System.out.println("SWAP SATURDAY FOR WEDNESDAY");

				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getWedHours());
				person2.setWedHours(tempHours);
				// Thursday
			} else if (shiftSwapInstance.getForDay().toString().equals("Thursday")) {
				System.out.println("SWAP SATURDAY FOR THURSDAY");

				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getThursHours());
				person2.setThursHours(tempHours);
				// Friday
			} else if (shiftSwapInstance.getForDay().toString().equals("Friday")) {
				System.out.println("SWAP SATURDAY FOR FRIDAY");

				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getFriHours());
				person2.setFriHours(tempHours);
				// Saturday
			} else if (shiftSwapInstance.getForDay().toString().equals("Saturday")) {
				System.out.println("SWAP SATURDAY FOR SATURDAY");

				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getSatHours());
				person2.setSatHours(tempHours);
				// Sunday
			} else if (shiftSwapInstance.getForDay().toString().equals("Sunday")) {
				System.out.println("SWAP SATURDAY FOR SUNDAY");

				String tempHours = person1.getSunHours();
				person1.setSunHours(person2.getSunHours());
				person2.setSunHours(tempHours);
			}
		}
		
		//Error handling
		else {
			System.out.println("Error occurred");
			return "error";
		}
		
		//END OF THE IF ELSE CONDITIONS FOR SHIFT SWAPPING.
		

		//Add the new objects to the model MVC.
		model.addAttribute("person1", person1);
		model.addAttribute("person2", person2);
		//Delete the shift swap request as the swap has been made.
		shiftSwapService.deleteShiftSwapRequestById(id);
		return "employeeDashboard";
	}

	/*
	 * @PostMapping("/employee-dashboard/accept-shift-swap-request2/{id}") public
	 * String saveAccecptShiftSwapRequest(@ModelAttribute("shiftSwapInstance")
	 * ShiftSwapModel shiftSwapInstance) { shiftSwapInstance.setAccepted(true);
	 * shiftSwapService.saveShiftSwapRequest(shiftSwapInstance);
	 * System.out.println("Here2"); return "redirect:/employee-dashboard"; }
	 */

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
				grossWages = Math.round(grossWages * 100); // Rounding to 2 decimals
				grossWages = grossWages / 100;
				model.addAttribute("grossWages", grossWages);

				double netWages = grossWages * .92; // Calculating 'tax'
				netWages = Math.round(netWages * 100); // Rounding to 2 decimals
				netWages = netWages / 100;
				model.addAttribute("netWages", netWages);
				return "employeeViewPayslip";
			}

		}
		
		//Error handling: If user isn't found for some reason:
		System.out.println("Error occurred");
		return "error";

	}

	@RequestMapping("/employee-dashboard/employee-request-holidays")
	public String employeeRequestHolidays(Model model) {
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		for (int i = 0; i < roster.size(); i++) {
			RosterModel roster2 = rosterService.getEmployeeByemployeeEmail(roster.get(i).getEmployeeEmail());
			if (auth.getName().toString().equals(roster2.getEmployeeEmail())) {
				double hoursAccrued = (roster.get(i).getEmployeeHours() * 0.04);
				hoursAccrued = Math.round(hoursAccrued * 100); //Calculating the amount of holiday hours gained this week, rounding to 2 decimals
				hoursAccrued = hoursAccrued / 100;
				model.addAttribute("hoursAccrued", hoursAccrued);
				return "employeeRequestHolidays";
			}
		}
		
		//Error handling: If user isn't found for some reason:
		System.out.println("Error occurred");
		return "error";
		
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

	@PostMapping("/employee-dashboard/save-rostered-employee")
	public String saveRosteredEmployee(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard";
	}

	@PostMapping("/employee-dashboard/save-rostered-employee-2")
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
	@PostMapping("/employee-dashboard/save-employee-hire")
	public String saveEmployeeHire(@ModelAttribute("employee") EmployeeModel employee) {
		employeeService.saveEmployeeHire(employee);
		return "redirect:/admin-dashboard/admin-add-new-employee-hire-confirm-role";
	}

	// Setting the role authority of new registered employee
	@PostMapping("/employee-dashboard/save-role-hire")
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

	@GetMapping("/employee-dashboard/save-create-edit-roster/{id}")
	public String saveCreateEditRoster(@PathVariable(value = "id") int id, Model model) {
		// update method
		RosterModel roster = rosterService.getEmployeeByemployeeId(id);
		model.addAttribute("roster", roster);
		return "adminCreateEditRosterInfo";
	}

	@GetMapping("/employee-dashboard/delete-rostered-employee/{id}")
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

	@GetMapping("/employee-dashboard/update-employee/{id}")
	public String updateEmployee(@PathVariable(value = "id") String username, Model model) {
		// update method

		RoleModel person = this.roleService.getRoleByUsername(username);

		EmployeeModel employee = employeeService.getEmployeeByUsername(username);
		model.addAttribute("employee", employee);

		// After user has been updated, if the user was an administrator with
		// 'sreynolds' name,
		// Set them always to admin. This is to disallow changing of the master account
		// to a normal employee account.
		// This will result in a total lockout of the admin dashboard.
		if (username.toString().equals("sreynolds")) {
			person.setAuthority("ROLE_ADMIN");
			person.setUsername("sreynolds"); // Name is static.
		}

		return "adminFireEmployeeInfo";
	}

	@GetMapping("/employee-dashboard/delete-employee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") String username) {
		// delete method - if the account being deleted is the master account, do not
		// delete
		if (username.toString().equals("sreynolds")) {
			System.out.println("Cannot delete the master account.");
			return "masterAccountDeleteError";
		} else {
			this.employeeService.deleteEmployeeByUsername(username);
		}

		return "adminDashboard";
	}

	@PostMapping("/employee-dashboard/save-employee-record/{id}")
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
