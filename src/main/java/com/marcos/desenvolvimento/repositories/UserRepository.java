package com.marcos.desenvolvimento.repositories;


import com.marcos.desenvolvimento.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIgnoreCase(final String login);

    Boolean existsByLoginIgnoreCase(final String login);

    Boolean existsByEmailIgnoreCase(final String email);

    Optional<User> findByEmailIgnoreCase(final String email);

    User findByLogin(String login);

    Boolean existsByLogin(String login);
}
