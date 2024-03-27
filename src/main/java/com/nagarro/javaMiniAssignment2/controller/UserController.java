package com.nagarro.javaMiniAssignment2.controller;

import com.nagarro.javaMiniAssignment2.entity.User;
import com.nagarro.javaMiniAssignment2.exception.UserCustomException;
import com.nagarro.javaMiniAssignment2.model.UserResponse;
import com.nagarro.javaMiniAssignment2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/app/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public List<User> createUser(
            @RequestParam(name = "size", defaultValue = "1") int size) throws UserCustomException {
        return userService.createRandomUsers(size);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam("sortType") String sortType,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset) throws UserCustomException {
        UserResponse userResponse = userService.getUsers(sortType, sortOrder, limit, offset);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
