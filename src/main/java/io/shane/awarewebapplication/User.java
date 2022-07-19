package io.shane.awarewebapplication;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "users", schema="aware")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="user_name")
	private String username;
	@Column(name="passphrase")
	private String password;
	@Column(name="roles")
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getRoles() {
		return role;
	}
	public void setRoles(String roles) {
		this.role = roles;
	}
	public boolean isActive() {
		return false;
	}
	
	
}
