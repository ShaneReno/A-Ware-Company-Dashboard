
package io.shane.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roster", schema="data")
public class RosterModel {
	
	//Mapping the table columns to variables
	
	@GeneratedValue(generator = "merge_id_seq", strategy = GenerationType.AUTO)
	@Column(name = "employeeid")
	
	@Id
	int employeeId;
	
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

	@Column(name = "employeehours")
	int employeeHours;
	
	//Getters and setters for the roster
	
	
	
	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public int getEmployeeHours() {
		return employeeHours;
	}

	public void setEmployeeHours(int employeeHours) {
		this.employeeHours = employeeHours;
	}

	public RosterModel(int employeeId, String employeeEmail, String employeeName, int weekNo, String monHours,
			String tuesHours, String wedHours, String thursHours, String friHours, String satHours, String sunHours,
			String employeeDept, int employeeHours) {
		super();
		this.employeeId = employeeId;
		this.employeeEmail = employeeEmail;
		this.employeeName = employeeName;
		this.weekNo = weekNo;
		this.monHours = monHours;
		this.tuesHours = tuesHours;
		this.wedHours = wedHours;
		this.thursHours = thursHours;
		this.friHours = friHours;
		this.satHours = satHours;
		this.sunHours = sunHours;
		this.employeeDept = employeeDept;
		this.employeeHours = employeeHours;
	}

	public RosterModel() {
	}
	
	

	@Override
	public String toString() {
		return "RosterModel [employeeId=" + employeeId + ", employeeEmail=" + employeeEmail + ", employeeName="
				+ employeeName + ", weekNo=" + weekNo + ", monHours=" + monHours + ", tuesHours=" + tuesHours
				+ ", wedHours=" + wedHours + ", thursHours=" + thursHours + ", friHours=" + friHours + ", satHours="
				+ satHours + ", sunHours=" + sunHours + ", employeeDept=" + employeeDept + ", employeeHours="
				+ employeeHours + "]";
	}
	
	
	
	
	

}
