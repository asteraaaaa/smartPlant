package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.User;
import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User save(User user);
    void delete(Long id);
}