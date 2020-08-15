package com.enticer.usermananagement.service;

import com.enticer.usermananagement.model.User;
import com.enticer.usermananagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }
}
