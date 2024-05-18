package com.acm.casemanagement.steps;



import com.acm.casemanagement.dto.UserDto;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRegistrationSteps {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonSteps commonSteps;

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUrl = "http://localhost:8080/api/users";
    private ResponseEntity<String> response;
    private UserDto userDto;

    @Given("a user with firstname {string}, lastname {string}, email {string}, username {string}, and password {string}")
    public void aUserWithUsernameAndPassword(String firstname, String lastname, String email, String username, String password) {
        userDto = UserDto.builder()
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .build();
    }

    @And("the user {string} is already registered")
    public void theUserIsAlreadyRegistered(String username) {
        UserDto existingUser = UserDto.builder()
                .username(username)
                .password("password")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(existingUser, headers);
        restTemplate.postForEntity(baseUrl + "/register", entity, String.class);
    }

    @When("the user registers")
    public void theUserRegisters() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        response = restTemplate.postForEntity(baseUrl + "/register", entity, String.class);
        commonSteps.setResponse(response);
    }
}