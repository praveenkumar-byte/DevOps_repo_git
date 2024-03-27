package com.nagarro.javaMiniAssignment2.service;

import com.nagarro.javaMiniAssignment2.entity.User;
import com.nagarro.javaMiniAssignment2.exception.UserCustomException;

import com.nagarro.javaMiniAssignment2.model.UserResponse;

import java.util.List;

public interface UserService {

    List<User> createRandomUsers(int size) throws UserCustomException;

    UserResponse getUsers(String sortType, String sortOrder, int limit, int offset) throws UserCustomException;

}
