package tech.service.UserService.model;


import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document (collection = "Users")
public class User {

  @Id
  private Long userId;
  private UUID userUniqueId;
  private String username;
  private String emailId;
  private Double balance;
  private Long addressId;
  private Long phoneNumberUniqueId;
}
