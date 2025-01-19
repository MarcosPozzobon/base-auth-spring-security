package com.marcos.desenvolvimento.repositories;


import com.marcos.desenvolvimento.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.password FROM User u WHERE u.login = :login")
    String findPasswordByLogin(String login);

    Optional<User> findByLoginIgnoreCase(final String login);

    User findByLogin(String login);

    Boolean existsByLogin(String login);
}
