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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
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
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/records/3"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andDo(document("record/get-by-user-id"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.post("/records")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":\"5\", \"date\":\"2019-03-12\", \"workedHours\":12}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
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
