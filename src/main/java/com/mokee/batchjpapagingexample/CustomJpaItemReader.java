package com.mokee.batchjpapagingexample;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomJpaItemReader implements ItemReader<UserModel> {
    private int nextUserIndex;
    private List<UserModel> userModelList = null;


    private UserRepository userRepository;

    CustomJpaItemReader(UserRepository userRepository) {
        this.userRepository = userRepository;
        initialize();
        System.out.println("CustomJpaItemReader initialized");
    }

    private void initialize() {
        userModelList = new ArrayList<>(0);
        List<User> users = userRepository.findAll();

        if (Objects.nonNull(users) && !users.isEmpty()) {
            users.forEach(user -> {
                UserModel userModel = new UserModel();
                userModel.setId(1);
                userModel.setUsername("a");
                userModel.setPassword("a");
                userModel.setAge(20);
                userModelList.add(userModel);
            });
        }


        nextUserIndex = 0;
    }


    @Override
    public UserModel read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        UserModel nextUserModel = null;

        if (nextUserIndex < userModelList.size()) {
            nextUserModel = userModelList.get(nextUserIndex);
            nextUserIndex++;
        }

        return nextUserModel;
    }
}
