package tech.service.UserService.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import tech.service.UserService.model.PhoneNumber;

public interface PhoneNumberRepository extends ReactiveMongoRepository<PhoneNumber, Long> {
}