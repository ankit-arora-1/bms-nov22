package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long aLong);

    Optional<User> findByEmail(String email);

    User save(User user);
}
