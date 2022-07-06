package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    boolean create(Long accountId, Long id, BigDecimal balance);

    List<Account> findAll();

    Long findAccountById(Long id);

    BigDecimal getBalance(Long accountId);

    BigDecimal updateBalance(Long accountId, BigDecimal amount);

}
