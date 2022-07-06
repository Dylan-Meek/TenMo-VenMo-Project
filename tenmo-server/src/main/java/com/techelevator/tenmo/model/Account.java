package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private Long accountId;
    private BigDecimal balance;
    private Long id;

    public Account() {

    }

    public Account(Long accountId, BigDecimal balance, Long id) {
        this.accountId = accountId;
        this.balance = balance;
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
