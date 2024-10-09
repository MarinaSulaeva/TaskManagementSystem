package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Интерфейс для работы с сущностью рефреш-токена в базе данных
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
}
