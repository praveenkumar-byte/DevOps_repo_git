package com.nagarro.javaMiniAssignment2.service;
import com.nagarro.javaMiniAssignment2.entity.User;
import com.nagarro.javaMiniAssignment2.exception.UserCustomException;
import com.nagarro.javaMiniAssignment2.factory.ValidatorFactory;
import com.nagarro.javaMiniAssignment2.model.PageInfo;
import com.nagarro.javaMiniAssignment2.model.UserResponse;
import com.nagarro.javaMiniAssignment2.repository.UserRepository;
import com.nagarro.javaMiniAssignment2.strategy.SortingStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserApiProcessor userApiProcessor;
    private final UserRepository userRepository;
    private final SortingStrategyFactory sortingStrategyFactory; // Add this field
    private final UserInputVerification userInputVerification;

    @Autowired
    public UserServiceImpl(
            UserApiProcessor userApiProcessor,
            UserRepository userRepository,
            UserInputVerification userInputVerification,
            SortingStrategyFactory sortingStrategyFactory) {
        this.userApiProcessor = userApiProcessor;
        this.userRepository = userRepository;
        this.sortingStrategyFactory = sortingStrategyFactory;
        this.userInputVerification=userInputVerification;
    }

    @Override
    public List<User> createRandomUsers(int size) throws UserCustomException {
        ValidatorFactory.getValidator(size).validateNumericLimit(size);

        List<User> savedUsers = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            List<User> randomUsers = userApiProcessor.fetchAndSaveRandomUsers().join();
            if (randomUsers.isEmpty()) {
                throw new UserCustomException(API_FAILURE);
            }

            randomUsers.forEach(user -> {
                userInputVerification.verifyAndSetStatus(user).join();
                LocalDateTime now = LocalDateTime.now();
                user.setDateCreated(now);
                user.setDateModified(now);
            });

            userRepository.saveAll(randomUsers);
            savedUsers.addAll(randomUsers);
        }
        return savedUsers;
    }






    @Override
    public UserResponse getUsers(String sortType, String sortOrder, int limit, int offset) throws UserCustomException {
        userApiProcessor.validateParametre(sortType, sortOrder, limit, offset);

        List<User> userFromDatabase = userRepository.findAll();
        List<User> sortedUser = sortingStrategyFactory.getSortingStrategy(sortType, sortOrder).sort(userFromDatabase);

        // Paginate the sorted user list using streams
        List<User> paginatedUsers = sortedUser.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

        PageInfo pageInfo = PageInfo.builder()
                .hasNextPage(offset + limit < sortedUser.size())
                .hasPreviousPage(offset > 0)
                .total((long) sortedUser.size())
                .build();

        return UserResponse.builder()
                .data(paginatedUsers)
                .pageInfo(pageInfo)
                .build();
    }

}

