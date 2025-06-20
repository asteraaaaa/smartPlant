package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.User;
import com.csc3700.smartplant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repo;

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public User save(User u) {
        return repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
