package br.qxd.jh.registry.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class AuthControllerTests {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Rule
	public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.apply(documentationConfiguration(this.jUnitRestDocumentation))
				.build();
	}
	
	@Test
	public void loginShouldReturnError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"cabrito12\", \"password\":\"asdasfkajsd\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void loginShouldReturnOk() throws Exception{
		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"admin\", \"password\":\"admin123\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(document("auth/login", 
				requestFields(
						fieldWithPath("username").description("Nome de usuário."),
						fieldWithPath("password").description("Senha do usuário.")),
				responseFields(
						fieldWithPath("accessToken")
						.description("Token de acesso do usuário que foi logado. Isto deve ser informado nas requisições."))));
	}

}
