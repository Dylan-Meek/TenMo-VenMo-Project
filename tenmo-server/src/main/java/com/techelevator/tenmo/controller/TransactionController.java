package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping ("/transactions")
public class TransactionController {
    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private UserDao userDao;

//SEND MONEY
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction SendTransfer(@RequestBody @Valid Principal principal, String username, BigDecimal amount) {
    Transaction newTransaction = new Transaction();
    newTransaction.setSend_account_id(userDao.findIdByUsername(principal.getName()));
    newTransaction.setReceive_account_id(userDao.findIdByUsername(username));
    newTransaction.setTransfer_amount(amount);
    newTransaction.setTransfer_status(Transaction.statusEnum.APPROVED);
    newTransaction.setTransfer_type(Transaction.typeEnum.SEND);
    return newTransaction;

    }

//

}