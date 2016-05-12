package com.example.qq_2012.avtivity;

import java.util.ArrayList;

import messageEntity.MyMessage;
import messageEntity.User;

/**
 * Created by Jason on 2016/4/6.
 */
public class Person {
    public Person(User user) {
        this.user = user;
    }

    public User user;
    ArrayList<MyMessage> allMessage = new ArrayList<>();


}
