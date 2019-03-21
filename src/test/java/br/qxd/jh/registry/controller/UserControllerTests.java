package br.qxd.jh.registry.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UserControllerTests {

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
	public void listAllUsersShouldReturnOk() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/users"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
}
