package com.os.user.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.os.user.entity.UserEntity;

public class SpringRestClient {

	private static final String GET_UserEntityS_ENDPOINT_URL = "http://localhost:8080/api/v1/UserEntitys";
	private static final String GET_UserEntity_ENDPOINT_URL = "http://localhost:8080/api/v1/UserEntitys/{id}";
	private static final String CREATE_UserEntity_ENDPOINT_URL = "http://localhost:8080/api/v1/UserEntitys";
	private static final String UPDATE_UserEntity_ENDPOINT_URL = "http://localhost:8080/api/v1/UserEntitys/{id}";
	private static final String DELETE_UserEntity_ENDPOINT_URL = "http://localhost:8080/api/v1/UserEntitys/{id}";
	private static RestTemplate restTemplate = new RestTemplate();

	public static void mainssss(String[] args) {
		SpringRestClient springRestClient = new SpringRestClient();

		// Step1: first create a new UserEntity
		springRestClient.createUserEntity();

		// Step 2: get new created UserEntity from step1
		springRestClient.getUserEntityById();

		// Step3: get all UserEntitys
		springRestClient.getUserEntitys();

		// Step4: Update UserEntity with id = 1
		springRestClient.updateUserEntity();

		// Step5: Delete UserEntity with id = 1
		springRestClient.deleteUserEntity();
	}

	private void getUserEntitys() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_UserEntityS_ENDPOINT_URL, HttpMethod.GET, entity,
				String.class);

		System.out.println(result);
	}

	private void getUserEntityById() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");

		RestTemplate restTemplate = new RestTemplate();
		UserEntity result = restTemplate.getForObject(GET_UserEntity_ENDPOINT_URL, UserEntity.class, params);

		System.out.println(result);
	}

	private void createUserEntity() {

		UserEntity newUserEntity = new UserEntity(1L);

		RestTemplate restTemplate = new RestTemplate();
		UserEntity result = restTemplate.postForObject(CREATE_UserEntity_ENDPOINT_URL, newUserEntity, UserEntity.class);

		System.out.println(result);
	}

	private void updateUserEntity() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		UserEntity updatedUserEntity = new UserEntity(2L);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(UPDATE_UserEntity_ENDPOINT_URL, updatedUserEntity, params);
	}

	private void deleteUserEntity() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(DELETE_UserEntity_ENDPOINT_URL, params);
	}
}