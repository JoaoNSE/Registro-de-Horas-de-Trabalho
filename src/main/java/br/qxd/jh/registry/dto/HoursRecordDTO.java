package br.qxd.jh.registry.dto;

import java.util.Date;
import br.qxd.jh.registry.entity.User;

public class HoursRecordDTO {

	private Long id;
	
	private double workedHours;
	
	private Date date;
	
	private User user;
	
	public HoursRecordDTO() {
		super();
	}
	
	public HoursRecordDTO(Long id, double workedHours, Date date, User user) {
		super();
		this.id = id;
		this.workedHours = workedHours;
		this.date = date;
		this.user = user;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(double workedHours) {
		this.workedHours = workedHours;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
