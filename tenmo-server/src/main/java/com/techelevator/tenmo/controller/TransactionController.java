package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.RequestDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.SendDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private UserDao userDao;
    private SendDTO transactionDTO;

    public TransactionController(TransactionDao transactionDao, UserDao userDao, SendDTO transactionDTO, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.transactionDTO = transactionDTO;
        this.accountDao = accountDao;
    }


    //SEND MONEY
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/send")
    public boolean SendTransfer(@Valid @RequestBody SendDTO newTransfer, Principal principal) {
        BigDecimal transferAmount = newTransfer.getAmount();

        long senderUserID = (long) userDao.findIdByUsername(principal.getName());
        long senderAccountId = accountDao.findAccountById(senderUserID).getAccountId();

        long receiveUserId = userDao.findIdByUsername(newTransfer.getReceiverUserName());
        long receiveAccountId = accountDao.findAccountById(receiveUserId).getAccountId();

        if (accountDao.getBalance(senderAccountId).compareTo(transferAmount) >= 0
                && senderAccountId != receiveAccountId
                && transferAmount.compareTo(BigDecimal.ZERO) >= 0) {
            transactionDao.create(senderAccountId, receiveAccountId, newTransfer.getAmount(),
                    Transaction.typeEnum.SEND, Transaction.statusEnum.APPROVED);

            accountDao.subtractFromBalance(senderAccountId, transferAmount);
            accountDao.addToBalance(receiveAccountId, transferAmount);
            return true;
        } else
            System.out.println("Cannot transfer funds.");
        return false;
    }
    // TODO: ADD INSUFFICIENT FUNDS EXCEPTION

    //SEND MONEY
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/request")
    public boolean requestTransfer(@Valid @RequestBody SendDTO newTransfer, Principal principal) {
        BigDecimal transferAmount = newTransfer.getAmount();

        long senderUserID = userDao.findIdByUsername(newTransfer.getReceiverUserName());
        long senderAccountId = accountDao.findAccountById(senderUserID).getAccountId();

        long receiveUserId = (long) userDao.findIdByUsername(principal.getName());
        long receiveAccountId = accountDao.findAccountById(receiveUserId).getAccountId();

        if (senderAccountId != receiveAccountId && transferAmount.compareTo(BigDecimal.ZERO) >= 0) {
            transactionDao.create(senderAccountId, receiveAccountId, newTransfer.getAmount(),
                    Transaction.typeEnum.REQUEST, Transaction.statusEnum.PENDING);
            return true;
        } else {
            System.out.println("");
            return false;
        }
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @PostMapping(path = "/request/{transaction_id}")
    public boolean requestDecision(@Valid @RequestBody Transaction transaction, Principal principal) {
        BigDecimal transferAmount = transaction.getTransfer_amount();

        long senderUserID = transaction.getSend_account_id();
        long senderAccountId = accountDao.findAccountById(senderUserID).getAccountId();

        long receiveUserId = (long) userDao.findIdByUsername(principal.getName());
        long receiveAccountId = accountDao.findAccountById(receiveUserId).getAccountId();


        if (accountDao.getBalance(senderAccountId).compareTo(transferAmount) >= 0) {

        }
        return true;
    }
    // VIEW TRANSACTION BY TRANSACTION ID.
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(path = "/{transaction_id}")
    public Transaction ViewTransactionsByTransactionId(@PathVariable int transaction_id) {
        return transactionDao.viewTransactionByTransactionID(transaction_id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(path = "/all/{username}")
    public List<Transaction> viewAllTransactionsForAccountID(@PathVariable String username, Principal principal) {
        List<Transaction> transactionList = new ArrayList<>();
        if (Objects.equals(username, principal.getName())) {
            long principalUserId = userDao.findIdByUsername(principal.getName());
            int principalAccountId = Math.toIntExact(accountDao.findAccountById(principalUserId).getAccountId());
            transactionList = transactionDao.viewAllTransactionsForAccountID(principalAccountId);
        }
        return transactionList;
    }
}