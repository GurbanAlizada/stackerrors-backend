package com.stackerrors.repository;

import com.stackerrors.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findById(int id);

    User findByUsername(String username);

    Optional<User> findByEmailVerificationCode(Integer verificationCode);

    Optional<User> findByForgotPasswordToken(String token);

    Optional<User> findByEmail(String email);


}
