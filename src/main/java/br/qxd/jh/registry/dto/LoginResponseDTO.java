package br.qxd.jh.registry.dto;

/**
 * Classe de resposta à requisição de login.
 * 
 * @author nobre
 *
 */
public class LoginResponseDTO {
	
	private String accessToken;
	
	public LoginResponseDTO() {
		super();
	}
	
	public LoginResponseDTO(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
