package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping ("/transactions")
public class TransactionController {
    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private UserDao userDao;
    private Principal principal;

    public TransactionController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }


    //SEND MONEY
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public void SendTransfer(@ Valid @RequestBody TransactionDTO newTransfer, Principal principal) {
       // transactionDao.create(userDao.findIdByUsername(principal.getName()), newTransfer.getToUserId(), Transaction.typeEnum.SEND);
       // accountDao.updateBalance(newTransfer.getToUserId(), newTransfer.getAmount());
    }

}