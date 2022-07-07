package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    private final static BigDecimal STARTING_BALANCE = BigDecimal.valueOf(1000.00);

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long id) {
        String sql = "INSERT INTO account (user_id, balance) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, STARTING_BALANCE);
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id FROM account";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Account account = mapRowToAccount(results);
                accounts.add(account);
            }
        } catch (DataAccessException e) {
            System.out.println("There are no accounts to display.");
        }
        return accounts;
    }


    @Override
    public Account findAccountById(Long id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        Account account;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        account = mapRowToAccount(results);
        return account;
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        BigDecimal balance = ZERO;
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
        } catch (DataAccessException e) {
            System.out.println("There are no matching accounts.");
        }
        return balance;
    }

    @Override
    public void updateBalance(Long accountId, BigDecimal amount) {
        String sql = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
        try {
            jdbcTemplate.update(sql, amount, accountId);
        } catch (DataAccessException e) {
            System.out.println("Account not found.");
        }
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setId(rs.getLong("user_id"));
        return account;
    }

}



