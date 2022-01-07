package com.os.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.os.user.entity.UserEntity;

public class RestTemplateUtil {
	@Autowired
	private RestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port + "/user";
	}

	
	public void testGetAllUserEntitys() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/users", HttpMethod.GET, entity,
				String.class);
		System.out.println(response);
	}

	
	public void testGetUserEntityById() {
		UserEntity employee = restTemplate.getForObject(getRootUrl() + "/users/1", UserEntity.class);
//		System.out.println(employee.getFirstName());

	}

	
	public void testCreateUserEntity() {
		UserEntity employee = new UserEntity();
//		employee.setEmailId("admin@gmail.com");
//		employee.setFirstName("admin");
//		employee.setLastName("admin");
		ResponseEntity<UserEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", employee,
				UserEntity.class);
	}

	
	public void testUpdateUserEntity() {
		int id = 1;
		UserEntity employee = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
//		employee.setFirstName("admin1");
//		employee.setLastName("admin2");
		restTemplate.put(getRootUrl() + "/users/" + id, employee);
		UserEntity updatedUserEntity = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
	}

	public void testDeleteUserEntity() {
		int id = 2;
		UserEntity employee = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		restTemplate.delete(getRootUrl() + "/users/" + id);
		try {
			employee = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		} catch (final HttpClientErrorException e) {
		}
	}
}