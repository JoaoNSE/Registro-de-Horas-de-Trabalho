package br.qxd.jh.registry.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private double workedHours;
	
	public User() {
		super();
		this.username = "";
		this.password = "";
		this.name = "";
		this.workedHours = 0;
	}

	public User(Long id, @NotNull String username, @NotNull String password, @NotNull String name,
			@NotNull double workedHours) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.workedHours = workedHours;
	}
	
	public User(@NotNull String username, @NotNull String password, @NotNull String name,
			@NotNull double workedHours) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.workedHours = workedHours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(double workedHours) {
		this.workedHours = workedHours;
	}
	
	
	
}
