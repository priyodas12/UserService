package tech.service.UserService.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.service.UserService.model.User;
import tech.service.UserService.model.UserDetails;
import tech.service.UserService.service.UserService;
import tech.service.UserService.util.ServiceMockData;

@Log4j2
@RequestMapping ("/api/v2")
@RestController
public class UserController {

  private final UserService userService;

  private final ServiceMockData serviceMockData;

  public UserController (UserService userService, ServiceMockData serviceMockData) {
    this.userService = userService;
    this.serviceMockData = serviceMockData;
  }

  @PostMapping ("/users")
  public Mono<User> saveUser (@RequestBody User user) {

    var randomUser = serviceMockData.createRandomUser ();
    var address = serviceMockData.createRandomAddress ();
    var phoneNumber = serviceMockData.createRandomPhone ();

    return userService.saveUser (randomUser, address, phoneNumber);
  }

  @GetMapping ("/users")
  public Flux<UserDetails> getAllUsers () {
    log.info ("fetching all users");
    return userService.fetchAllUsers ();
  }

  @GetMapping ("/users/{userId}")
  public Mono<UserDetails> getUserById (@PathVariable ("userId") Long userId) {
    log.info ("fetching userId: {}", userId);
    return userService.fetchUserById (userId);
  }
}
