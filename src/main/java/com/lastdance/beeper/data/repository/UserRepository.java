package com.lastdance.beeper.data.repository;


import com.lastdance.beeper.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);

}