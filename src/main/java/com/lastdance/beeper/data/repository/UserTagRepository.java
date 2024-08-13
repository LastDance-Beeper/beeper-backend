package com.lastdance.beeper.data.repository;

import com.lastdance.beeper.data.domain.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
}
