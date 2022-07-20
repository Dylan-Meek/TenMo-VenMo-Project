package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long send_account_id, long receive_account_id, BigDecimal transferAmount,
                       Transaction.typeEnum transfer_type, Transaction.statusEnum transfer_status) {
        String sql = "INSERT INTO transaction(send_account_id, receive_account_id, transfer_type, status, transfer_amount)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, send_account_id, receive_account_id, transfer_type.toString(), transfer_status.toString(), transferAmount);
    }

    @Override
    public void updateStatus(int transaction_id,Transaction.statusEnum transfer_status) {
        //UPDATE table_name
        //SET column1 = value1, column2 = value2, ...
        //WHERE condition;
        String sql = "UPDATE transaction SET status = ? WHERE transaction_id = ?;";
        jdbcTemplate.update(sql, transfer_status, transaction_id);
    }

    @Override
    public void approveTransaction(int transaction_id) {
        String sql = "UPDATE transaction SET status = 'APPROVE' WHERE transaction_id = ?;";
        jdbcTemplate.update(sql, transaction_id);
    }

    @Override
    public void rejectTransaction(int transaction_id) {
        String sql = "UPDATE transaction SET status = 'REJECT' WHERE transaction_id = ?;";
        jdbcTemplate.update(sql, transaction_id);
    }

    @Override
    public Transaction viewTransactionByTransactionID(int transaction_id) {
        Transaction lineTransaction = new Transaction();
        String sql = "SELECT transaction_id, send_account_id, receive_account_id," +
                " transfer_type, transfer_amount, status FROM transaction" +
                " WHERE transaction_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, transaction_id);
        if (rs.next()) {
            lineTransaction = mapRowToTransaction(rs);
        }
        return lineTransaction;
    }

    @Override
    public List<Transaction> viewAllTransactionsForAccountID(int account_id) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT transaction_id, send_account_id, receive_account_id, transfer_type, transfer_amount, status FROM transaction WHERE receive_account_id = ? OR send_account_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, account_id, account_id);
        while(rs.next()){
            transactionList.add(mapRowToTransaction(rs));
        }
        return transactionList;
    }

    public List<Transaction> viewAllPendingTransactionsForAccountID(int account_id) {
        Transaction.statusEnum pending = Transaction.statusEnum.PENDING;
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT transaction_id, send_account_id, receive_account_id, transfer_type, transfer_amount, status FROM transaction WHERE status = ? AND (receive_account_id = ? OR send_account_id = ?);";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, "PENDING", account_id, account_id);
        while(rs.next()){
            transactionList.add(mapRowToTransaction(rs));
        }
        return transactionList;
    }


    public Transaction mapRowToTransaction(SqlRowSet rs){
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rs.getInt("transaction_id"));
        transaction.setSend_account_id(rs.getLong("send_account_id"));
        transaction.setReceive_account_id(rs.getLong("receive_account_id"));
        transaction.setTransfer_type(Transaction.typeEnum.valueOf(rs.getString("transfer_type")));
        transaction.setTransfer_amount(rs.getBigDecimal("transfer_amount"));
        transaction.setTransfer_status(Transaction.statusEnum.valueOf(rs.getString("status")));
        return transaction;
        }
//
    }

