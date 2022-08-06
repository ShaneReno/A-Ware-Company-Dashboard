//Employee model class for creating objects of employee
package io.shane.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Map the attributes of the 'users' table in the 'data' schema data.sql file to the following variables
@Entity
@Table(name = "users", schema="data")
public class EmployeeModel {
	
	//Mapping the table columns to variables
	@Id //Primary key - not generated
	@Column(name = "username")
	String username;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "enabled")
	boolean enabled;
	
	
	//Getters and setters generated

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	//Generated constructor using all parameters
	public EmployeeModel(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}


	//Default constructor with no parameters.
	public EmployeeModel() {
	}

	
	
	//to String method
	@Override
	public String toString() {
		return "EmployeeModel [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}
	
	
	
	
	
}

