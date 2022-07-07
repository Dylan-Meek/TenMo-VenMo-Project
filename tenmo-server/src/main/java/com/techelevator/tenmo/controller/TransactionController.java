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
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private UserDao userDao;
    private TransactionDTO transactionDTO;

    public TransactionController(TransactionDao transactionDao, UserDao userDao, TransactionDTO transactionDTO) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.transactionDTO = transactionDTO;
    }


    //SEND MONEY
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public void SendTransfer(@Valid @RequestBody TransactionDTO newTransfer, Principal principal) {
        transactionDao.create(accountDao.findAccountById((userDao.findIdByUsername(principal.getName())), newTransfer.getToUserId(),
                newTransfer.getAmount(), Transaction.typeEnum.SEND, Transaction.statusEnum.APPROVED);
        accountDao.updateBalance(newTransfer.getToUserId(), (accountDao.getBalance(newTransfer.getToUserId())).add(newTransfer.getAmount()));
        accountDao.updateBalance((long) userDao.findIdByUsername(principal.getName()),
                (accountDao.getBalance((long) userDao.findIdByUsername(principal.getName())).subtract(newTransfer.getAmount())));
    }

// MAKE METHOD TO FIND ACCOUNT ID BY USERNAME

}