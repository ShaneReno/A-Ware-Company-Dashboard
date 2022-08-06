package io.shane.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Map the attributes of the 'authorities' table in the 'data' schema data.sql file to the following variables
//This model class will assign different authority roles to each user - manager(admin) and employee(user)
@Entity
@Table(name = "authorities", schema="data")
public class RoleModel {
	
	//Primary key - using the column 'username', do not auto generate one.
	@Id
	@Column(name = "username")
	String username;
	
	//String for authority type
	@Column(name = "authority")
	String authority;

	//Getters and setters generated
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	//Constructor with parameters geenrated
	public RoleModel(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	
	//Default constructor
	public RoleModel() {
	}

	//Overriden to string method for the RoleModel class. This will prevent the printing of a hash value when called on the object.
	@Override
	public String toString() {
		return "RoleModel [username=" + username + ", authority=" + authority + "]";
	}
	
	

}
