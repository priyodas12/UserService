package tech.service.UserService.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import tech.service.UserService.model.User;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {

  Mono<User> findByUserId (Long id);
}
