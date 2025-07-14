package com.onlinecourseorganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinecourseorganizer.model.User;
import com.onlinecourseorganizer.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        // You could encode the password here later
        userRepository.save(user);
    }
}
