package br.qxd.jh.registry.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;


/**
 * 
 * @author João Henrique
 * 
 * DTO para receber os dados de requisições sobre registros de horas.
 *
 */
public class HoursRecordDTO {

	private Long id;
	
	@NotNull
	private double workedHours;
	
	@NotNull
	private Date date;
	
	@NotNull
	private Long userId;
	
	public HoursRecordDTO() {
		super();
	}
	
	public HoursRecordDTO(Long id, double workedHours, Date date, Long userId) {
		super();
		this.id = id;
		this.workedHours = workedHours;
		this.date = date;
		this.userId = userId;
	}
	
	public HoursRecordDTO(double workedHours, Date date, Long userId) {
		super();
		this.workedHours = workedHours;
		this.date = date;
		this.userId = userId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
