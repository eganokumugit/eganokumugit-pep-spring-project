package com.example.service;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService 
{
    AccountRepository accRepo;
    @Autowired
    public AccountService(AccountRepository accRepo)
    {
        this.accRepo = accRepo;
    }

    public HashMap<Integer,Account> registerAccount(Account acc)
    {
        HashMap<Integer,Account> resultMap = new HashMap<>();
        if(accRepo.userExists(acc.getUsername()) == 0 && acc.getPassword().length() > 4 && acc.getUsername().length() > 0)
        {
            accRepo.save(acc);
            Account registeredAcc = accRepo.findAccountByUsername(acc.getUsername());
            resultMap.put(200,registeredAcc);
            return resultMap;
        }
        else if(accRepo.userExists(acc.getUsername()) > 0)
        {
            resultMap.put(409,null);
            return resultMap;
        }
        else
        {
            return null;
        }
    }
    public Account loginUser(Account acc)
    {
        if(acc.getUsername().length() > 0 && acc.getPassword().length() > 4)
        {
            return accRepo.loginUser(acc.getUsername(),acc.getPassword());   
        }
        else
        {
            return null;
        }
    }
}
