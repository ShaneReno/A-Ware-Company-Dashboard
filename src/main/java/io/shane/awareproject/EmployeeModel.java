
package io.shane.awareproject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema="data")
public class EmployeeModel {
	
	//Mapping the table columns to variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "username")
	String username;
	
	@Column(name = "password")
	String password;

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

	public EmployeeModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	//Default constructor with no parameters.
	public EmployeeModel() {
	}

	@Override
	public String toString() {
		return "employeeModel [username=" + username + ", password=" + password + "]";
	}
	
	
	
	
	
}

