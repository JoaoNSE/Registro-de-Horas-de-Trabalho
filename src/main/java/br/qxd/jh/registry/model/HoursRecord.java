package br.qxd.jh.registry.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class HoursRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private double workedHours;
	
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy")	
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonBackReference
	private User user;
	
	public HoursRecord() {
		super();
		this.workedHours = 0;
		this.date = new Date();	
	}

	public HoursRecord(Long id, @NotNull double workedHours, @NotNull Date date, User user) {
		super();
		this.id = id;
		this.workedHours = workedHours;
		this.date = date;
		this.user = user;
	}

	public HoursRecord(@NotNull double workedHours, @NotNull Date date, User user) {
		super();
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
