package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "service")
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ServiceID")
	private int serviceId;
	
	@Column(nullable=false)
	private String name;
	
	private String type;
	private int minutes;
	private int sms;
	private int gigabytes;
	private Float minutesFee;
	private Float smsFee;
	private Float gigabytesFee;
	
	
	
	public Service() {}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	

	public int getGigabytes() {
		return gigabytes;
	}

	public void setGigabytes(int gigabytes) {
		this.gigabytes = gigabytes;
	}

	public Float getGigabytesFee() {
		return gigabytesFee;
	}

	public void setGigabytesFee(Float gigabytesFee) {
		this.gigabytesFee = gigabytesFee;
	}

	public Float getMinutesFee() {
		return minutesFee;
	}

	public void setMinutesFee(Float minutesFee) {
		this.minutesFee = minutesFee;
	}

	public Float getSmsFee() {
		return smsFee;
	}

	public void setSmsFee(Float smsFee) {
		this.smsFee = smsFee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
