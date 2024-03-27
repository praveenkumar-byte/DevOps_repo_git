package com.nagarro.javaMiniAssignment2.strategy;

import com.nagarro.javaMiniAssignment2.exception.UserCustomException;
import com.nagarro.javaMiniAssignment2.strategy.sortingStartegyImpl.AgeSortingStrategy;
import com.nagarro.javaMiniAssignment2.strategy.sortingStartegyImpl.NameSortingStrategy;

import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.*;

public class SortingStrategyFactory {
    public SortingStrategy getSortingStrategy(String sortType, String sortOrder) throws UserCustomException {
        boolean sortingOrder = sortOrder.equalsIgnoreCase(SORT_ORDER_EVEN);
        if (SORT_BY_NAME.equalsIgnoreCase(sortType)) {
            return new NameSortingStrategy(sortingOrder);
        } else if (SORT_BY_AGE.equalsIgnoreCase(sortType)) {
            return new AgeSortingStrategy(sortingOrder);
        }
        throw new UserCustomException(INVALID_SORT_TYPE_ORDER);
    }
}