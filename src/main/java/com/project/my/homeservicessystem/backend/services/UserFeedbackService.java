package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.UserFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {
    private final UserFeedbackRepository repository;
}
