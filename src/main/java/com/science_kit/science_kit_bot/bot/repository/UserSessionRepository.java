package com.science_kit.science_kit_bot.bot.repository;

import com.science_kit.science_kit_bot.bot.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession,Long> {
    Optional<UserSession> findByChatId(Long chatId);
}
