package ${package};

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.entity.${service-alias}Entity;

@SpringBootTest(properties="spring.main.lazy-initialization=true")
@AutoConfigureMockMvc
//@ActiveProfiles("itg-test") 
//@PropertySource("classpath:application-itg-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ${service-alias}ServerApplicationIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@DisplayName("Initial Context Load Test")
	void contextLoads() {
	}

	@Test
	@DisplayName("Success - ${service-alias} findAll")
	@Order(3)
	public void whenValidDepartmentName_thenDepartmentShouldFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/${resource-alias}s").accept(MediaType.APPLICATION_JSON))// .andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(200))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[*]").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].${resource-alias}Id").isNotEmpty());

	}

	@Test
	@DisplayName("Success - ${service-alias} findById")
	@Order(2)
	public void get${service-alias}ByIdAPI() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/${resource-alias}s/{${resource-alias}Id}", 1).accept(MediaType.APPLICATION_JSON)) // .andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").isNotEmpty())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id", Matchers.equalTo(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").value(1));

	}

	@Test
	@DisplayName("Success - ${service-alias} save")
	@Order(1)
	public void create${service-alias}API() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/${resource-alias}s")
				.content(asJsonString(${service-alias}Entity.builder().${resource-alias}Id(null).build()))
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id", Matchers.equalTo(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").value(1));

	}

	@Test
	@DisplayName("Success - ${service-alias} update")
	@Order(4)
	public void update${service-alias}API() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/${resource-alias}s/{${resource-alias}Id}", 1)
				.content(asJsonString(${service-alias}Entity.builder().${resource-alias}Id(1L).build())).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id", Matchers.equalTo(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.${resource-alias}Id").value(1));
//				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("New Name")));

	}

	public String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Success - ${service-alias} deleteById")
	@Order(5)
	public void delete${service-alias}API() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/${resource-alias}s/{${resource-alias}Id}", 1))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
//				.andExpect(MockMvcResultMatchers.content().string("Deleted"))
				.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("Deleted")))
//				.andExpect(MockMvcResultMatchers.header().string("Content-Length", "11"))
				.andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/plain;charset=UTF-8"));

	}

}
