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
    public Account findAccountByAccountId(Integer accountId);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Account findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
