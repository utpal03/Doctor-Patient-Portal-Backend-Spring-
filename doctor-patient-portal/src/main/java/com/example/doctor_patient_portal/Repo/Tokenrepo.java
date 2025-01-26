package com.example.doctor_patient_portal.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.doctor_patient_portal.Model.Token;

@Repository
public interface Tokenrepo extends JpaRepository<Token, Integer> {
    @Query(value = """
            select t from Token t
            inner join Users u on t.users.userId.username = u.userId.username and t.users.userId.id = u.userId.id
            where u.userId.id = :id and u.userId.username = :username and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(@Param("id") Integer id, @Param("username") String username);

    Optional<Token> findByToken(String token);
}
