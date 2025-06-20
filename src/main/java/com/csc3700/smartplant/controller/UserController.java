package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.User;
import com.csc3700.smartplant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> all() {
        return service.getAll();
    }

    @GetMapping("/{id}") public User one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
