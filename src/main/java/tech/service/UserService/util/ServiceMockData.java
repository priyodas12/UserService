package tech.service.UserService.util;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import tech.service.UserService.model.Address;
import tech.service.UserService.model.PhoneNumber;
import tech.service.UserService.model.User;

@Component
public class ServiceMockData {

  private final Faker faker;

  public ServiceMockData (Faker faker) {
    this.faker = faker;
  }

  public User createRandomUser () {
    return User.builder ()
        .userId (new Random ().nextLong (1, Long.MAX_VALUE))
        .userUniqueId (UUID.randomUUID ())
        .username (faker.funnyName ().name ())
        .emailId (faker.internet ().emailAddress ())
        .balance (new Random ().nextDouble (1000, 500000))
        .build ();
  }

  public Address createRandomAddress () {
    return Address.builder ()
        .addressId (new Random ().nextLong (1, Long.MAX_VALUE))
        .city (faker.address ().cityName ())
        .zip (faker.address ().zipCode ())
        .street (faker.address ().streetAddress ())
        .state (faker.address ().state ())
        .build ();
  }

  public PhoneNumber createRandomPhone () {
    return PhoneNumber.builder ()
        .type ("HOME")
        .number (faker.phoneNumber ().phoneNumber ())
        .phoneNumberUniqueId (new Random ().nextLong (1, Long.MAX_VALUE))
        .build ();
  }
}
