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
	
	@Column(name = "employee_email")
	String employeeEmail;
	
	@Column(name = "employee_name")
	String employeeName;
	
	@Column(name = "week_no")
	int weekNo;
	
	@Column(name = "mon_hours")
	String monHours;
	
	@Column(name = "tues_hours")
	String tuesHours;
	
	@Column(name = "wed_hours")
	String wedHours;
	
	@Column(name = "thurs_hours")
	String thursHours;
	
	@Column(name = "fri_hours")
	String friHours;
	
	@Column(name = "sat_hours")
	String satHours;
	
	@Column(name = "sun_hours")
	String sunHours;
	
	@Column(name = "employee_dept")
	String employeeDept;

}
