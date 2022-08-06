//The main controller class that maps models to MVC and dynamic HTML interaction
//@author Shane Reynolds

package io.shane.awareproject;


//All the library imports and dependencies for different functions.
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import io.shane.models.EmployeeModel;
import io.shane.models.RoleModel;
import io.shane.models.RosterModel;
import io.shane.models.ShiftSwapModel;
import io.shane.services.EmployeeServiceImpl;
import io.shane.services.RoleService;
import io.shane.services.RosterService;
import io.shane.services.ShiftSwapService;


//Multi view controller for mapping different requests and passing models to seperate HTML files
@Controller
public class MainController {

	//When the user logs in, depending on their role: direct them to the employee or admin dashboard 
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
	
	
	//Adding the favicon to the HTML tabs
	@RequestMapping("favicon.ico")
    String favicon() {
    return "forward:/favicon.ico";
}
	
	
	
	// Mapping for the employee user account
	//Note: As a lot of the method bodies are repeated throughout the controller, some will be commented only once.
	@RequestMapping("/employee-dashboard/employee-roster")
	public String getAll(Model model) { //passing model into the method
		//Initialise a list of object 'RosterModel' called roster, populate it with all the rostered employees in the roster service
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		//Add the list to the MVC as a model, with the attribute name 'roster' 
		model.addAttribute("roster", roster);
		//Return the string 'employeeRoster' - when this method is executed, the 'employeeRoster' HTML file is called and the user is directed there.
		return "employeeRoster";
	}

	//GetMapping is similar to RequestMapping.
	@GetMapping("/employee-dashboard")
	public String employeeDashboard(Model model) {
		//List of ShiftSwapModel objects - this is for when a shift swap request has been placed for this user, they will receive a red notification
		List<ShiftSwapModel> shiftSwap = shiftSwapService.getAllShiftSwapRequests();
		//Get the currently signed in user name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Boolean for the glowing of the shift swap button - a notification that will notify the user of an incoming request
		boolean glowBool = false;
		//Iterate through every element in the shiftSwap list and:
		for (int i = 0; i < shiftSwap.size(); i++) {
			//if the signed in user email equals the shift swap request recipient email - basically if the user has a pending request:
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
				//boolean is true so the button glows red.
				glowBool = true;
				//Add this to the MVC. This will allow the HTML to access the boolean for dynamic changing of values
				model.addAttribute("glowBool", glowBool);
			}
		}
		//Direct to employee dashboard
		return "employeeDashboard";
	}

	//Method for shift swap
	@RequestMapping("/employee-dashboard/employee-request-shift-swap")
	public String employeeRequestShiftSwap(Model model) {
		List<ShiftSwapModel> shiftSwap = shiftSwapService.getAllShiftSwapRequests();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();//Same as previous method
		String youHaveARequest = ""; //String for holding the 'you have a request' message on HTML screen
		//Iterate through every element in the shiftSwap list and:
		for (int i = 0; i < shiftSwap.size(); i++) {
			//if the signed in user email equals the shift swap request recipient email - basically if the user has a pending request:
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
				youHaveARequest = "You have a request to swap"; //Update the string with the message that will be sent to the MVC below
				model.addAttribute("youHaveARequest", youHaveARequest);
			}
		}
		//Add the shiftSwap list to the MVC. This will happen regardless if the user has a pending request or not. Can view all users requests.
		model.addAttribute("shiftSwap", shiftSwap);
		//Direct to this page.
		return "employeeShiftSwap";
	}

	@RequestMapping("/employee-dashboard/employee-shift-swap-request")
	//Same again for the request part of the shift swap method:
	public String getShiftSwapRequest(Model model) {
		ShiftSwapModel shiftSwapModel = new ShiftSwapModel();
		model.addAttribute("shiftSwapModel", shiftSwapModel);
		List<ShiftSwapModel> shiftSwapList = shiftSwapService.getAllShiftSwapRequests();
		model.addAttribute("shiftSwapList", shiftSwapList);
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		return "employeeShiftSwapRequest";
	}

	//Postmapping takes what the input is from the HTML URL below and sends it to this method:
	@PostMapping("/employee-dashboard/employee-shift-swap-request-sent")
	public String saveShiftSwapRequest(@ModelAttribute("shiftSwapModel") ShiftSwapModel model) {
		shiftSwapService.saveShiftSwapRequest(model); //The model object that is passed through as a parameter is saved to the shiftSwap Table in the service
		return "redirect:/employee-dashboard"; //redirect user to page
	}


	//Initialise the list of objects outside of the method so that when the method is called, it is not just resetting the list every time.
	LinkedHashSet<ShiftSwapModel> shiftSwapInstance = new LinkedHashSet<ShiftSwapModel>();
	@RequestMapping("/employee-dashboard/employee-shift-swap-response")
	public String employeeRequestShiftSwapResponse(Model model) {
		List<ShiftSwapModel> shiftSwap2 = shiftSwapService.getAllShiftSwapRequests();
		
		List<ShiftSwapModel> shiftSwap = new ArrayList<ShiftSwapModel>(shiftSwap2);
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //get current user email
		String employeeEmails = ""; 

		//For the length of the list:
		for (int i = 0; i < shiftSwap.size(); i++) {
			//if the signed in user email equals the shift swap request recipient email - basically if the user has a pending request:
			if (auth.getName().toString().equals(shiftSwap.get(i).getRecipientEmail())) {
				//Get the object and add it to the new list. Send to MVC
				shiftSwapInstance.add(shiftSwap.get(i));
				model.addAttribute("shiftSwapInstance", shiftSwap);

				//Add to the employeeEmails string everytime the current user has a request directed towards them. MVC add
				employeeEmails += shiftSwap.get(i).getEmployeeEmail() + ", ";
				model.addAttribute("employeeEmails", employeeEmails);
			}
		}
		
		//Debug 
		//System.out.println("Total Shift Swap Requests: " + shiftSwap.size());
		//System.out.println("Shift Swap for this user: " + shiftSwapInstance.size());

		return "employeeShiftSwapResponse"; //Redirect
	}
	
	
	

	@GetMapping("/employee-dashboard/accept-shift-swap-request/{id}")
	public String accecptShiftSwapRequest(@PathVariable(value = "id") int id, Model model) {
		// update method
		ShiftSwapModel shiftSwapInstance = shiftSwapService.getShiftSwapRequestById(id);
		shiftSwapInstance.setAccepted(true); //Set the 'accepted boolean' of this object to true
		model.addAttribute("shiftSwapInstance", shiftSwapInstance); //pass this model
		// Swapping around the instance variables so that the shift swap can be
		// reflected on the roster:
		shiftSwapService.saveShiftSwapRequest(shiftSwapInstance);

		//Define 2 new objects of RosterModel class, equalling to the current shift swap object's employee email and recipient email attributes.
		RosterModel person1 = this.rosterService.getEmployeeByemployeeEmail(shiftSwapInstance.getEmployeeEmail());
		RosterModel person2 = this.rosterService.getEmployeeByemployeeEmail(shiftSwapInstance.getRecipientEmail());
		
		
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
		return "employeeDashboard"; //Direct back to the main employee dashboard HTML page.
	}


	//Method for calculating payslip
	@GetMapping("/employee-dashboard/employee-view-payslip")
	public String employeeViewPayslip(Model model) {

		//list of all objects for roster
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		//MVC. Get current user email
		model.addAttribute("roster", roster);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//For the length of the roster list, execute:
		for (int i = 0; i < roster.size(); i++) {
			//define a new RosterModel called roster2, initialising it as the roster object's employee email at the ith element in the list
			RosterModel roster2 = rosterService.getEmployeeByemployeeEmail(roster.get(i).getEmployeeEmail());
			if (auth.getName().toString().equals(roster2.getEmployeeEmail())) {
				//Send these attributes to the MVC
				model.addAttribute("weekNo", roster.get(i).getWeekNo());
				model.addAttribute("hours", roster.get(i).getEmployeeHours());
				double grossWages = (roster.get(i).getEmployeeHours() * 11.75); //Calculating the wages
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
	//Method for calculating employee holiday hours accrued
	@RequestMapping("/employee-dashboard/employee-holidays")
	public String employeeHolidays(Model model) {
		
		//List of objects, add to MVC and get current user email
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//For length of roster list:
		for (int i = 0; i < roster.size(); i++) {
			//define a new RosterModel called roster2, initialising it as the roster object's employee email at the ith element in the list
			RosterModel roster2 = rosterService.getEmployeeByemployeeEmail(roster.get(i).getEmployeeEmail());
			if (auth.getName().toString().equals(roster2.getEmployeeEmail())) {
				double hoursAccrued = (roster.get(i).getEmployeeHours() * 0.04);
				hoursAccrued = Math.round(hoursAccrued * 100); //Calculating the amount of holiday hours gained this week, rounding to 2 decimals
				hoursAccrued = hoursAccrued / 100;
				model.addAttribute("hoursAccrued", hoursAccrued); //Calculating finished and added to MVC
				return "employeeHolidays";
			}
		}
		
		//Error handling: If user isn't found for some reason:
		System.out.println("Error occurred");
		return "error";
		
	}

	//Method for viewing the weather live API - Note: only has up to 50 calls a day
	@RequestMapping("/employee-dashboard/employee-view-weather")
	public String employeeViewWeather(Model model) throws IOException, ParseException {
		URL url; //Define a URL using the imported library
		try {
			// Different API keys for when the calls have exceeded for the day - daily limit
			// on free account
			// url = new URL
			// ("http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=brnuJdH3fQ0kG6Vz6cfgshueYWOdeXnb&details=true");
			
			//API keys are unique to the person that set up the account on Accuweather.
			url = new URL(
					"http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=KjwhD3BGkYQRYyKEp0j2Q3i3O4fHwg6o&details=true");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Define a new connection
			connection.setRequestMethod("GET");
			connection.connect(); //Establish the connection using the aforementioned details
			int responseCode = connection.getResponseCode();

			//If the maximum calls for the day are reached - error handling 
			if (responseCode != 200) {
				return "error503";
				// throw new RuntimeException("HTTP Response code: " + responseCode);

			} else {
				//If the API is within its limits for the day:
				StringBuilder detailString = new StringBuilder();
				Scanner scan = new Scanner(url.openStream());

				//Open up a scanning stream and read every line
				while (scan.hasNext()) {
					detailString.append(scan.nextLine());

				}
				scan.close();
				// System.out.println(detailString);

				//Parse the JSON into readable strings from objects
				JSONParser parser = new JSONParser();
				JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(detailString));

				// System.out.println(dataObject.get(0));

				// Navigating through JSONs. It is a tiered hierarchy of nested parameters.
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
		} catch (MalformedURLException e) { //Any other URL errors:
			e.printStackTrace();
			System.out.println("ERROR");
		}

		return "employeeViewWeather"; //Go to the view weather page for employee account
	}

	
	
	
	//end of employee mapping
	//================================================
	
	
	
	
	
	// Mapping for the manager user account
	@GetMapping("/admin-dashboard")
	public String admin() {
		return "adminDashboard";
	}

	//Adding an employee to the roster
	@GetMapping("/admin-dashboard/admin-add-employee-to-roster")
	public String createRoster(Model model) {
		RosterModel roster = new RosterModel();

		List<EmployeeModel> employeeList = employeeService.getAllEmployees(); //List of objects of employees
		System.out.println(employeeList);
		//RosterModel and EmployeeModel are different: roster being the users that are scheduled to work, and employee being the hired employees records in general
		model.addAttribute("roster", roster);
		model.addAttribute("employeeList", employeeList);
		return "adminAddEmployeeToRoster"; //Go to page
	}

	//Post mapping accepting the model returned from HTML page via Thymeleaf and saving it to the roster table in the SQL database.
	@PostMapping("/employee-dashboard/save-rostered-employee")
	public String saveRosteredEmployee(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard"; //Go back to the dashboard
	}

	//Same as before except for a different page
	@PostMapping("/employee-dashboard/save-rostered-employee-2")
	public String saveRosteredEmployee2(@ModelAttribute("roster") RosterModel roster) {
		rosterService.saveRosteredEmployee(roster);
		return "redirect:/admin-dashboard/admin-view-create-roster";
	}

	//Creating a new employee account for a new hire to the company.
	@GetMapping("/admin-dashboard/admin-add-new-employee-hire")
	public String addHire(Model model) {
		EmployeeModel employee = new EmployeeModel();
		model.addAttribute("employee", employee);
		return "adminAddNewEmployeeHire";
	}

	//Confirm the role of the new added user - if employee then ROLE_USER, if admin manager then ROLE_ADMIN
	@GetMapping("/admin-dashboard/admin-add-new-employee-hire-confirm-role")
	public String addRole(Model model) {
		RoleModel role = new RoleModel();

		model.addAttribute("role", role);

		return "adminAddNewEmployeeHireConfirmRole"; //Go to confirmation
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
		RosterModel roster = rosterService.getEmployeeByemployeeId(id); //Update the employee in the roster with matching ID, with different details
		model.addAttribute("roster", roster);
		return "adminCreateEditRosterInfo";
	}

	@GetMapping("/employee-dashboard/delete-rostered-employee/{id}")
	public String deleteRosteredEmployee(@PathVariable(value = "id") int id) {
		// delete method
		this.rosterService.deleteRosteredEmployeeById(id); //Delete the record in roster with the returned ID
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

	//To view all the employee records, the roster service, employee service and role service must all be called and utilised in harmony.
	@GetMapping("/admin-dashboard/admin-view-employee-records")
	public String viewAllEmployees(Model model) {
		List<RosterModel> roster = rosterService.getAllRosteredEmployees();
		model.addAttribute("roster", roster);

		List<EmployeeModel> employee = employeeService.getAll();
		model.addAttribute("employee", employee);

		List<RoleModel> role = roleService.getAllRoles();
		model.addAttribute("role", role);
		return "adminViewEmployeeRecords"; //show this HTML page.
	}

}
