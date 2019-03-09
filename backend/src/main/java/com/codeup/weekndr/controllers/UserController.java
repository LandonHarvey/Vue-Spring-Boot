package com.codeup.weekndr.controllers;


import com.codeup.weekndr.models.User;
import com.codeup.weekndr.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    private UserRepository userDao;

    public UserController (UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/userlist.json")
    public @ResponseBody Iterable<User> userList(){
        return userDao.findAll();
    }

    @PostMapping("/userPost")
    public @ResponseBody Iterable<User> userInputed(@RequestBody User user){
        userDao.save(user);
        return userDao.findAll();
    }
}

