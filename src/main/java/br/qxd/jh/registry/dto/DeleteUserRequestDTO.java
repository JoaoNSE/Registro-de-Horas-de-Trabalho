package br.qxd.jh.registry.dto;

import javax.validation.constraints.NotBlank;

/**
 * DTO para realizar a requisição de deleção de usuário por username
 * @author nobre
 *
 */
public class DeleteUserRequestDTO {

	@NotBlank
	private String username;
	
	public DeleteUserRequestDTO() {
		super();
	}

	public DeleteUserRequestDTO(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
