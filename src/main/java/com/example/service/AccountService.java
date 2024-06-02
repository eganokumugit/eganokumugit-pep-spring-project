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

    public HashMap<Account,Integer> registerAccount(Account acc)
    {
        HashMap<Account,Integer> resultMap = new HashMap<>();
        if(accRepo.findAccountByUsername(acc.getUsername()) == null && acc.getPassword().length() > 4 && acc.getUsername().length() > 0)
        {
            accRepo.save(acc);
            Account registeredAcc = accRepo.findAccountByUsername(acc.getUsername());
            resultMap.put(registeredAcc, 200);
            return resultMap;
        }
        else if(accRepo.findAccountByUsername(acc.getUsername()) != null)
        {
            resultMap.put(null, 409);
            return resultMap;
        }
        else
        {
            return null;
        }
    }
    public Account loginUser(Account acc)
    {
        return accRepo.findAccountByUsernameAndPassword(acc.getUsername(),acc.getPassword());   
    }
}
