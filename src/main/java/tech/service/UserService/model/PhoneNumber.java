package tech.service.UserService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
@Document (collection = "PhoneNumbers")
public class PhoneNumber {

  @Id
  private Long phoneNumberUniqueId;
  private String number;
  private String type;
}