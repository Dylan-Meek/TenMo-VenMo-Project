package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
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

    public TransactionController(TransactionDao transactionDao, UserDao userDao, TransactionDTO transactionDTO, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.transactionDTO = transactionDTO;
        this.accountDao = accountDao;
    }


    //SEND MONEY
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public void SendTransfer(@Valid @RequestBody TransactionDTO newTransfer, Principal principal) {
        BigDecimal transferAmount = newTransfer.getAmount();

        long senderUserID = (long) userDao.findIdByUsername(principal.getName());
        long senderAccountId = accountDao.findAccountById(senderUserID).getAccountId();

        long receiveUserId = userDao.findIdByUsername(newTransfer.getReceiverUserName());
        long receiveAccountId = accountDao.findAccountById(receiveUserId).getAccountId();

        transactionDao.create(senderAccountId, receiveAccountId, newTransfer.getAmount(),
                Transaction.typeEnum.SEND, Transaction.statusEnum.APPROVED);

        accountDao.subtractFromBalance(senderAccountId, transferAmount);
        accountDao.addToBalance(receiveAccountId, transferAmount);
    }

// MAKE METHOD TO FIND ACCOUNT ID BY USERNAME

}