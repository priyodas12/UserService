package tech.service.UserService.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import tech.service.UserService.model.Address;

public interface AddressRepository extends ReactiveMongoRepository<Address, Long> {
}
