package br.qxd.jh.registry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 
 * @author João Henrique
 * 
 * DTO que recebe os dados da requisição para realizar o login.
 *
 */
public class LoginDTO {

	@NotBlank
	@Size(min=5, message="Username should have atleast 5 characters")
	private String username;
	
	@NotBlank
 	@Size(min=8, message="Password should have atleast 8 characters")
	private String password;

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
	
	
	
}
