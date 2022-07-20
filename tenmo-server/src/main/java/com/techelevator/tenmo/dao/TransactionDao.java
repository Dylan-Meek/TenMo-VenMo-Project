package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {
    void create(long send_account_id, long receive_account_id, BigDecimal transferAmount,
                Transaction.typeEnum transfer_type, Transaction.statusEnum transfer_status);

    void updateStatus(int transaction_id ,Transaction.statusEnum transfer_status);

    void approveTransaction(int transaction_id);

    void rejectTransaction(int transaction_id);

    Transaction viewTransactionByTransactionID(int transaction_id);

    List<Transaction> viewAllTransactionsForAccountID(int account_id);

    List<Transaction> viewAllPendingTransactionsForAccountID(int account_id);
    }

