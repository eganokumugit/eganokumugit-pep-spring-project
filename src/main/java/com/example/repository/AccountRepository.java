package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long>
{
    public Account findAccountByUsername(Account acc);

    public Account findAccountByUsernameAndPassword(Account acc);
}
