package io.shane.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities", schema="data")
public class RoleModel {
	
	@Id
	
	@Column(name = "username")
	String username;
	
	@Column(name = "authority")
	String authority;

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

	public RoleModel(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	
	//Default
	public RoleModel() {
	}

	@Override
	public String toString() {
		return "RoleModel [username=" + username + ", authority=" + authority + "]";
	}
	
	

}
