package tech.service.UserService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document (collection = "Addresses")
public class Address {

  @Id
  private Long addressId;
  private String street;
  private String city;
  private String state;
  private String zip;

}