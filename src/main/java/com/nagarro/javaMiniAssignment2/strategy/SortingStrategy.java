package com.nagarro.javaMiniAssignment2.strategy;

import com.nagarro.javaMiniAssignment2.entity.User;

import java.util.List;

public interface SortingStrategy {
    List<User> sort(List<User> users);
}