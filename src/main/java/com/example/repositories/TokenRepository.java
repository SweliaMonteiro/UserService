package com.example.repositories;

import com.example.models.Token;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUserAndIsDeleted(User user, boolean isDeleted);

    @Override
    <S extends Token> S save(S entity);

    Optional<Token> findByValueAndIsDeleted(String value, boolean isDeleted);

    Optional<Token> findByValueAndIsDeletedAndExpiryAtGreaterThan(String value, boolean isDeleted, Date currentDate);

}
