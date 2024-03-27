package com.nagarro.javaMiniAssignment2.strategy.sortingStartegyImpl;

import com.nagarro.javaMiniAssignment2.entity.User;
import com.nagarro.javaMiniAssignment2.strategy.SortingStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgeSortingStrategy implements SortingStrategy {

        private final boolean sortOrder;

        public AgeSortingStrategy(boolean sortOrder) {
            this.sortOrder = sortOrder;
        }

        @Override
        public List<User> sort(List<User> users) {
            return users.stream()
                    .filter(user -> (user.getAge() % 2 == 0) == sortOrder)
                    .sorted(Comparator.comparingInt(User::getAge))
                    .collect(Collectors.toList());
        }
}