package com.nagarro.javaMiniAssignment2.service;

import com.nagarro.javaMiniAssignment2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.TO_BE_VERIFIED;
import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.VERIFIED;
@Slf4j
@Component
public class UserInputVerification {
    @Autowired
    private UserApiProcessor userApiProcessor;


    CompletableFuture<User> verifyAndSetStatus(User user) {
        CompletableFuture<Boolean> nationalityCheckFuture = userApiProcessor.checkNationalityAsync(user);
        CompletableFuture<Boolean> genderCheckFuture = userApiProcessor.checkGenderAsync(user);

        return CompletableFuture.allOf(nationalityCheckFuture, genderCheckFuture)
                .thenApplyAsync((ignore) -> {
                    boolean nationalityMatch = nationalityCheckFuture.join();
                    boolean genderMatch = genderCheckFuture.join();

                    user.setVerificationStatus(nationalityMatch && genderMatch ? VERIFIED : TO_BE_VERIFIED);

                    log.info("User verification status updated: {}", user.getVerificationStatus());

                    return user;
                })
                .exceptionally(ex -> {
                    log.error("Error in verifyAndSetStatus: {}", ex.getMessage());
                    return user; // Return the user even in case of an exception
                });
    }
}
