package io.shane.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "shiftswaps", schema="data")
public class ShiftSwapModel {
	
	//Mapping the table columns to variables
	@GeneratedValue(generator = "merge_id_seq2", strategy = GenerationType.AUTO)
	@Id
	@Column(name = "requestid")
	int requestId;
	
	@Column(name = "employeeemail")
	String employeeEmail;
	
	@Column(name = "recipientemail")
	String recipientEmail;
	
	@Column(name = "swapday")
	String swapDay;
	
	@Column(name = "forday")
	String forDay;
	
	@Column(name = "accepted")
	boolean accepted;

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public String getSwapDay() {
		return swapDay;
	}

	public void setSwapDay(String swapDay) {
		this.swapDay = swapDay;
	}

	public String getForDay() {
		return forDay;
	}

	public void setForDay(String forDay) {
		this.forDay = forDay;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	
	public ShiftSwapModel(int requestId, String employeeEmail, String recipientEmail, String swapDay, String forDay,
			boolean accepted) {
		super();
		this.requestId = requestId;
		this.employeeEmail = employeeEmail;
		this.recipientEmail = recipientEmail;
		this.swapDay = swapDay;
		this.forDay = forDay;
		this.accepted = accepted;
	}
	
	
	//Default constructor
	public ShiftSwapModel() {

	}

	
	@Override
	public String toString() {
		return "ShiftSwapModel [requestId=" + requestId + ", employeeEmail=" + employeeEmail + ", recipientEmail="
				+ recipientEmail + ", swapDay=" + swapDay + ", forDay=" + forDay + ", accepted=" + accepted + "]";
	}
	
	
	
	
	
	
}