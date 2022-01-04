package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.users.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFeedBackRepository extends JpaRepository<UserFeedback,Long> {
}
