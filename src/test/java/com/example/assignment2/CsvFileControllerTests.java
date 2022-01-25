package com.example.assignment2;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class CsvFileControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void isUploadOkTest() throws Exception {
		String mockData = "name,description,price,brand\n" +
				"iphone,mobile,999,apple\n" +
				"macbook m1,laptop,999,apple\n" +
				"g402 mouse,peripheral device,40,logitech";

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
				"file",
				"products.csv",
				"text/csv",
				mockData.getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/csv/upload")
						.file(mockMultipartFile))
						.andExpect(status().isOk());
	}

	@Test
	public void shouldAcceptCorrectHeaders() throws Exception {

		String mockData = "names,prescription,prices,brandy\n" +
				"iphone,mobile,999,apple\n" +
				"macbook m1,laptop,999,apple";

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
				"file",
				"products.csv",
				"text/csv",
				mockData.getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/csv/upload")
						.file(mockMultipartFile))
						.andExpect(status().isExpectationFailed());

	}

	@Test
	public void shouldAcceptCsvFileOnly() throws Exception{

		String mockData = "name,description,price,brand\n" +
				"iphone,mobile,999,apple";

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
				"file",
				"products.csv",
				"multipart/form-data",
				mockData.getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/csv/upload")
						.file(mockMultipartFile))
						.andExpect(status().isBadRequest());
	}


	@Test
	public void getAllAfterUploadTest() throws Exception{
		isUploadOkTest();
		mockMvc.perform(get("/api/csv/products"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getExpensiveAfterUploadTest() throws Exception{
		isUploadOkTest();
		mockMvc.perform(get("/api/csv/products/expensive?priceFilter=300"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
