package com.vermeg.aws.cost.optimization.dashboard.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
