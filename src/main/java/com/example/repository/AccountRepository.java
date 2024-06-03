package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>
{
    public Account findAccountByUsername(String username);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Account loginUser(@Param("username") String username, @Param("password") String password);

    @Query("SELECT COUNT(username) FROM Account WHERE username = :username")
    Integer userExists(@Param("username") String username);

    @Query("SELECT COUNT(accountId) FROM Account WHERE accountId = :accountId")
    Integer idExists(@Param("accountId") Integer accountId);
}
