package tech.service.UserService.service;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.service.UserService.model.Address;
import tech.service.UserService.model.PhoneNumber;
import tech.service.UserService.model.User;
import tech.service.UserService.model.UserDetails;
import tech.service.UserService.repository.AddressRepository;
import tech.service.UserService.repository.PhoneNumberRepository;
import tech.service.UserService.repository.UserRepository;

@Log4j2
@Service
public class UserService {

  private final UserRepository userRepository;
  private final AddressRepository addressRepository;
  private final PhoneNumberRepository phoneNumberRepository;


  public UserService (
      UserRepository userRepository, AddressRepository addressRepository,
      PhoneNumberRepository phoneNumberRepository
                     ) {
    this.userRepository = userRepository;
    this.addressRepository = addressRepository;
    this.phoneNumberRepository = phoneNumberRepository;
  }

  public Mono<User> saveUser (User user, Address address, PhoneNumber phoneNumber) {

    var addressMono = addressRepository.save (address);

    var phoneNumberMono = phoneNumberRepository.save (phoneNumber);

    //var userMono = userRepository.save (user);

    return Mono.zip (addressMono, phoneNumberMono)
        .flatMap (tuple -> {
          Address savedAddress = tuple.getT1 ();
          PhoneNumber savedPhoneNumber = tuple.getT2 ();

          // Set address ID and phone number IDs in the user object
          user.setAddressId (savedAddress.getAddressId ());
          user.setPhoneNumberUniqueId (savedPhoneNumber.getPhoneNumberUniqueId ());

          // Save the user
          return userRepository.save (user);
        });
  }

  public Flux<UserDetails> fetchAllUsers () {
    return userRepository.findAll ().flatMap (user -> {
      log.info (
          "*fetchUsers:: user : {}, addressId: {}, phoneNumberId: {}",
          user,
          user.getAddressId (),
          user.getPhoneNumberUniqueId ()
               );
      var addressMono = addressRepository.findById (user.getAddressId ()).switchIfEmpty (
          Mono.just (Address.builder ().build ()));
      var phoneNumberMono =
          phoneNumberRepository.findById (user.getPhoneNumberUniqueId ()).switchIfEmpty (
              Mono.just (PhoneNumber.builder ().build ()));

      return Mono.zip (Mono.just (user), addressMono, phoneNumberMono)
          .map (tuple3 -> {
            log.info (
                "***fetchUsers:: user : {}, address: {}, phoneNumber: {}",
                tuple3.getT1 (),
                tuple3.getT2 (),
                tuple3.getT3 ()
                     );
            return UserDetails.builder ()
                .user (tuple3.getT1 ())
                .address (tuple3.getT2 ())
                .phoneNumber (tuple3.getT3 ())
                .build ();
          }).onErrorResume (error -> {

            log.error ("fetchAllUsers::Error while fetching data:  {}", error.getMessage ());
            return Mono.empty ();
          });
    });
  }

  public Mono<UserDetails> fetchUserById (Long id) {
    return userRepository.findByUserId (id).flatMap (user -> {
      log.info (
          "fetchUserById: user : {}, addressId: {}, phoneNumberId: {}",
          user,
          user.getAddressId (),
          user.getPhoneNumberUniqueId ()
               );
      var addressMono = addressRepository.findById (user.getAddressId ()).switchIfEmpty (
          Mono.empty ());
      var phoneNumberMono =
          phoneNumberRepository.findById (user.getPhoneNumberUniqueId ()).switchIfEmpty (
              Mono.empty ());

      return Mono.zip (Mono.just (user), addressMono, phoneNumberMono)
          .map (tuple3 -> {
            return UserDetails.builder ()
                .user (tuple3.getT1 ())
                .address (tuple3.getT2 ())
                .phoneNumber (tuple3.getT3 ())
                .build ();
          }).onErrorResume (error -> {
            log.error ("fetchUserById::Error while fetching data:  {}", error.getMessage ());
            return Mono.empty ();
          });

    });
  }
}
