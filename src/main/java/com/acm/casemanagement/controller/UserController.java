package com.acm.casemanagement.controller;


import com.acm.casemanagement.dto.LoginDto;
import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @Operation(summary = "Get all users")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Found the users",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = User.class)) }),
//            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
//    })
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

//    @Operation(summary = "Get user by ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Found the user",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = User.class)) }),
//            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
//    })
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        log.info("Registering user: {}", userDto.getUsername());
        User createdUser = userService.registerUser(userDto);
        URI location = URI.create(String.format("/api/users/%s", createdUser.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
    }

//    @Operation(summary = "Delete a user by ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "User deleted"),
//            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        if (user.isPresent()) {
//            userService.deleteUserById(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @Operation(summary = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginDto loginDto) {
        log.info("Logging in user: {}", loginDto.getUsername());
        User authenticatedUser = userService.loginUser(loginDto);
        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }
    @Operation(summary = "UPDATE a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Updated",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

        return userService.updateUserById(id,userDto);

    }
}


