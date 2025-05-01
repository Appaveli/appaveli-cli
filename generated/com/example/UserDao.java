package com.example;

import java.util.List;
import tech.appavelitech.domain.User;

public interface UserDao {
    void save(User entity);
    User findById(int id);
    List<User> findAll();
    void update(User entity);
    void delete(int id);
}