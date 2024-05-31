package io.kazarezau.magallanes.account.output.jpa.repository;

import io.kazarezau.magallanes.account.output.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
}
