package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Objects;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(path = "/{account_id}")
    public BigDecimal getBalance(@PathVariable long account_id, Principal principal) {
        long principalUserId = userDao.findIdByUsername(principal.getName());
        long principalAccountId = accountDao.findAccountById(principalUserId).getAccountId();

        if (Objects.equals(account_id, principalAccountId)) {
            return accountDao.getBalance(account_id);
        } else {
            return null;
        }
    }

/*    @ResponseStatus
    @GetMapping(path = "")
    public List<Account> viewAllAccounts() {
        return accountDao.findAll();
    }*/
}
