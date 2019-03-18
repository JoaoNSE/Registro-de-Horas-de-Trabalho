package br.qxd.jh.registry.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;

import br.qxd.jh.registry.entity.User;

public class HoursRecordDTO {

	private Long id;
	
	@NotNull
	private double workedHours;
	
	@NotNull
	private Date date;
	
	@NotNull
	private User user;
	
	public HoursRecordDTO() {
		super();
		this.workedHours = 0;
		this.date = new Date();
		this.user = new User();
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
