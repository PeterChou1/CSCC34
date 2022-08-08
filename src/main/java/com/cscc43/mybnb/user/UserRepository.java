package com.cscc43.mybnb.user;

import java.util.List;

public interface UserRepository {
    int save(User book);
    int update(User book);
    User findByUsername(String id);
    int deleteById(String id);
    List<User> findAll();
    int deleteAll();
}
