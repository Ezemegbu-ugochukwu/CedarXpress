//package com.example.cedarxpressliveprojectjava010.repository;
//
//import com.example.cedarxpressliveprojectjava010.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findUserByEmail(String email);
//    Optional<User> findUserByUsernameOrEmail(String username, String email);
//    Optional<User> findUserByUsername(String username);
//    Boolean existsByEmail(String email);
//    Boolean existsByUsername(String username);
//
//}
