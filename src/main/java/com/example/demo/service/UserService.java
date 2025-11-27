package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final String PREFIX = "USER:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Save user with TTL 10 minutes and track insertion order
    public void saveUser(User user) {
        redisTemplate.opsForValue().set(PREFIX + user.getId(), user, 1, TimeUnit.MINUTES);
        redisTemplate.opsForList().rightPush("USER_ORDER", user.getId());
    }

    public User getUser(String id) {
        return (User) redisTemplate.opsForValue().get(PREFIX + id);
    }

    public void deleteUser(String id) {
        redisTemplate.delete(PREFIX + id);
        redisTemplate.opsForList().remove("USER_ORDER", 1, id);
    }

    public List<User> getAllUsersInOrder() {
        List<Object> ids = redisTemplate.opsForList().range("USER_ORDER", 0, -1);
        List<User> users = new ArrayList<>();
        if (ids != null) {
            for (Object id : ids) {
                User user = getUser((String) id);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users;
    }
}
