package com.jwtsecurity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtsecurity.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String userName);

}
