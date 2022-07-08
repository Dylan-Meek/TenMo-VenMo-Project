package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/users")
public class UserController {
        private AccountDao accountDao;
        private UserDao userDao;

        public UserController(UserDao userDao, AccountDao accountDao) {
            this.userDao = userDao;
            this.accountDao = accountDao;
        }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(path = "")
    public List<String> viewAllUsers(Principal principal) {
        List<String> userList = userDao.findAll();
        String principalName = principal.getName();
        userList.remove(principalName);
        return userList;
    }
}