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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class RecordControllerTests {

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
	public void getRecordByUserIdShouldReturnOk() throws Exception {
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/records/user/{id}", 3))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andDo(document("record/get-by-user-id",
				pathParameters(parameterWithName("id")
						.description("Indetificador do usuário que se deseja ver os registros.")),
				responseFields(fieldWithPath("[].id").description("Indentificador do registro."),
						fieldWithPath("[].workedHours").description("Quantidade de horas trabalhadas"),
						fieldWithPath("[].date").description("Data em que as horas foram trabalhadas."))));
	}
	
	@WithAnonymousUser
	@Test
	public void insertRecordAsAnonumousShouldReturnUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/records")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":\"5\", \"date\":\"2019-03-12\", \"workedHours\":12}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
		
	}
	
	@WithMockUser(username="joaodasilva")
	@Test
	public void insertRecordAuthenticatedShouldReturnOk() throws Exception {
		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/records")
				.header("Authorization", "Bearer <<Authorization>>")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":\"5\", \"date\":\"2019-03-12\", \"workedHours\":12}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andDo(document("record/insert-record",
				requestHeaders(headerWithName("Authorization").description("Token JWT de autorização recebido no login.")),
				requestFields (
						fieldWithPath("userId").description("Indentificador do usuário no qual será inserido o novo registro de horas."),
						fieldWithPath("date").description("Data na qual as horas foram trabalhadas."),
						fieldWithPath("workedHours").description("Quantidade de horas trabalhadas.")),
				responseFields(fieldWithPath("success").description("Booleano que indica se a operação foi sucedida ou não"),
						fieldWithPath("message").description("Mensagem resultante da operação"))));
		
	}
	
	
	@WithMockUser(username="joaodasilva")
	@Test
	public void inserInvalidRecordShouldReturnBadRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/records")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":5, \"workedHours\":12}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
}
