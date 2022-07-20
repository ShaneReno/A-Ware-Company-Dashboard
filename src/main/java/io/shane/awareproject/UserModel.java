
package io.shane.awareproject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roster", schema="aware")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "employeeemail")
	String employeeEmail;
	
	@Column(name = "employeename")
	String employeeName;
	
	@Column(name = "weekno")
	int weekNo;
	
	@Column(name = "monhours")
	String monHours;
	
	@Column(name = "tueshours")
	String tuesHours;
	
	@Column(name = "wedhours")
	String wedHours;
	
	@Column(name = "thurshours")
	String thursHours;
	
	@Column(name = "frihours")
	String friHours;
	
	@Column(name = "sathours")
	String satHours;
	
	@Column(name = "sunhours")
	String sunHours;
	
	@Column(name = "employeedept")
	String employeeDept;

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getWeekNo() {
		return weekNo;
	}

	public void setWeekNo(int weekNo) {
		this.weekNo = weekNo;
	}

	public String getMonHours() {
		return monHours;
	}

	public void setMonHours(String monHours) {
		this.monHours = monHours;
	}

	public String getTuesHours() {
		return tuesHours;
	}

	public void setTuesHours(String tuesHours) {
		this.tuesHours = tuesHours;
	}

	public String getWedHours() {
		return wedHours;
	}

	public void setWedHours(String wedHours) {
		this.wedHours = wedHours;
	}

	public String getThursHours() {
		return thursHours;
	}

	public void setThursHours(String thursHours) {
		this.thursHours = thursHours;
	}

	public String getFriHours() {
		return friHours;
	}

	public void setFriHours(String friHours) {
		this.friHours = friHours;
	}

	public String getSatHours() {
		return satHours;
	}

	public void setSatHours(String satHours) {
		this.satHours = satHours;
	}

	public String getSunHours() {
		return sunHours;
	}

	public void setSunHours(String sunHours) {
		this.sunHours = sunHours;
	}

	public String getEmployeeDept() {
		return employeeDept;
	}

	public void setEmployeeDept(String employeeDept) {
		this.employeeDept = employeeDept;
	}
	
	
	
	

}
