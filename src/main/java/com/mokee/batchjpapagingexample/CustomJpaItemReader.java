package com.mokee.batchjpapagingexample;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomJpaItemReader implements ItemReader<User> {
    private int nextUserIndex;
    private List<User> userList = null;

    CustomJpaItemReader() {
        initialize();
    }

    private void initialize() {
        userList = new ArrayList<>(0);
        User a = new User();
        a.setId(1);
        a.setUsername("a");
        a.setPassword("a");
        a.setAge(20);

        User b = new User();
        b.setId(1);
        b.setUsername("b");
        b.setPassword("b");
        b.setAge(25);


        userList = Collections.unmodifiableList(Arrays.asList(a, b));
        nextUserIndex = 0;
    }


    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        User nextUser = null;

        if (nextUserIndex < userList.size()) {
            nextUser = userList.get(nextUserIndex);
            nextUserIndex++;
        }

        return nextUser;
    }
}
