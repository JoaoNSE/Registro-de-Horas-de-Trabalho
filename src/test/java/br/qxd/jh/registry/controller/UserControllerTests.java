package br.qxd.jh.registry.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UserControllerTests {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Rule
	public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();
	
	private void createDeleteTestUser() {
		User us = new User("usertesteDelete", "passteeeste", "Teste Delete");
		userService.saveUser(us);
	}
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.apply(documentationConfiguration(this.jUnitRestDocumentation))
				.build();
		
		
		userService.deleteByUsername("userteste10");
	}
	
	@Test
	public void listAllUsersShouldReturnOk() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/users"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andDo(document("user/list-all-users",
				responseFields(
						fieldWithPath("[].id").description("Identificador do usuário."),
						fieldWithPath("[].name").description("Nome real do usuário."),
						fieldWithPath("[].username").description("Nome de usuário para realizar login."),
						fieldWithPath("[].hoursRecords").description("Registros de horas de trabalho do usuário."),
						fieldWithPath("[].hoursRecords.[].id").ignored(),
						fieldWithPath("[].hoursRecords.[].workedHours").ignored(),
						fieldWithPath("[].hoursRecords.[].date").ignored(),
						fieldWithPath("[].roles").description("Papeis do usuário."),
						fieldWithPath("[].roles.[].id").ignored(),
						fieldWithPath("[].roles.[].name").ignored())));
	}
	
	@Test
	public void createUserShouldReturnUnauthorized() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"userteste10\", \"password\":\"userteste\", \"name\":\"User Teste\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(username="joaodasilva")
	@Test
	public void createUserShouldReturnForbbiden() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"userteste10\", \"password\":\"userteste\", \"name\":\"User Teste\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden());
	}
	
	@WithMockUser(username="admin", roles= {"ADMIN"})
	@Test
	public void createUserShouldReturnOk() throws Exception {		
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.header("Authorization", "Bearer {access_token}")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"userteste10\", \"password\":\"userteste\", \"name\":\"User Teste\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(document("user/create-user",
				requestHeaders(headerWithName("Authorization").description("Token de acesso recebido no login.")),
				requestFields(
						fieldWithPath("username").description("Nome do usuário para ser usado no login."),
						fieldWithPath("password").description("Senha do usuário para ser usada no login."),
						fieldWithPath("name").description("Nome real do usuário.")),
				responseFields(
						fieldWithPath("success").description("Booleano que indica se a operação foi bem-sucedida ou não."),
						fieldWithPath("message").description("Mensagem da API sobre a requisição."))));
		
		userService.deleteByUsername("userteste10");
	}
	
	@WithMockUser(username="admin", roles= {"ADMIN"})
	@Test
	public void deleteUserByUsernameShouldReturnOk() throws Exception {
		createDeleteTestUser();
		
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"usertesteDelete\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
