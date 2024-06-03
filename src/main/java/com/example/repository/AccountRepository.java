package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>
{
    public Account findAccountsByUsername(String username);
    public Account findAccountsByAccountId(Integer accountId);

    public Account findAccountsByUsernameAndPassword(String username, String password);
}
