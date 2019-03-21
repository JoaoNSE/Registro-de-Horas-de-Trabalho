package br.qxd.jh.registry.dto;

/**
 * DTO para a resposta padrão dos métodos da API
 * @author nobre
 *
 */
public class ApiResponseDTO {
	private boolean success;
	
	private String message;
	
	public ApiResponseDTO() {
		super();
	}
	
	public ApiResponseDTO(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}



	public boolean isSuccess() {
		return success;
	}

	public void setStatus(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
