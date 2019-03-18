package br.qxd.jh.registry.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.qxd.jh.registry.entity.HoursRecord;

public class UserDTO {
	
private Long id;
	
	@NotNull
	@Size(min=5, message="Username should have atleast 5 characters")
	private String username;
	
	@NotNull
 	@Size(min=8, message="Password should have atleast 8 characters")
	private String password;

	@NotNull
	private String name;
	
	private List<HoursRecord> hoursRecords;
	
	public UserDTO() {
		super();
		this.username = "";
		this.password = "";
		this.name = "";
	}

	public UserDTO(Long id, String username, String password, String name, List<HoursRecord> hoursRecords) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.hoursRecords = hoursRecords;
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

	public List<HoursRecord> getHoursRecords() {
		return hoursRecords;
	}

	public void setHoursRecords(List<HoursRecord> hoursRecords) {
		this.hoursRecords = hoursRecords;
	}
	
	
}
