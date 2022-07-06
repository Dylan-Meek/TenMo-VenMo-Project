package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcTransactionDao implements TransactionDao{

    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(long send_account_id, long receive_account_id, BigDecimal transferAmount,
                       Transaction.typeEnum transfer_type, Transaction.statusEnum transfer_status) {
        String sql = "INSERT INTO transaction(send_account_id, receive_account_id, transfer_type, status, transfer_amount)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.queryForObject(sql, Transaction.class, send_account_id, receive_account_id, transfer_type, transfer_status, transferAmount);
    }

    @Override
    public void updateStatus(int transaction_id,Transaction.statusEnum transfer_status) {
        //UPDATE table_name
        //SET column1 = value1, column2 = value2, ...
        //WHERE condition;
        String sql = "UPDATE transaction SET status = ? WHERE transaction_id = ?;";
        jdbcTemplate.update(sql, Transaction.class,transfer_status, transaction_id);
    }
}
