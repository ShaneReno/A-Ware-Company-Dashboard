package io.shanereynolds.springbootwebapp.employees;

public class Employee {
	
	private String employeeEmail;
	private String employeePass;
	private String employeeName;
	
	
	
	
	//Constructors
	//Default constructor
	public Employee() {
		
	}
	
	//Parameter Constructor
	public Employee(String employeeEmail, String employeePass, String employeeName) {
		super();
		this.employeeEmail = employeeEmail;
		this.employeePass = employeePass;
		this.employeeName = employeeName;
	}
	
	
	
	//Getters and Setters
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeePass() {
		return employeePass;
	}
	public void setEmployeePass(String employeePass) {
		this.employeePass = employeePass;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	
	
	
	
	
	

}
