package tech.service.UserService.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDetails {

  private User user;
  private Address address;
  private PhoneNumber phoneNumber;

}
