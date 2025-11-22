package com.github.lybgeek.user.controller;


import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. 创建用户（POST）
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 2. 根据ID查询用户（GET）
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return user != null ?
                new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 3. 查询所有用户（GET）
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // 4. 更新用户（POST）
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 缺少ID则参数错误
        }
        User updatedUser = userService.updateUser(user);
        return updatedUser != null ?
                new ResponseEntity<>(updatedUser, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 5. 删除用户（POST，通过请求体传ID）
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody Integer id) {
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}