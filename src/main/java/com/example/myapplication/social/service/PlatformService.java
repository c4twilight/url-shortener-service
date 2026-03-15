package com.example.myapplication.social.service;

import com.example.myapplication.covid.exception.BadRequestException;
import com.example.myapplication.covid.exception.ElementNotFoundException;
import com.example.myapplication.social.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlatformService {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public List<String> addUser(Integer id, String name) {
        if (users.containsKey(id)) {
            throw new BadRequestException("User with id " + id + " already exists");
        }

        users.put(id, new User(id, name));
        return List.of(name + " added successfully.");
    }

    public List<String> follow(Integer followerId, Integer followeeId) {
        User follower = getOrThrow(followerId);
        User followee = getOrThrow(followeeId);
        follower.follow(followee);
        return List.of(follower.getName() + " is now following " + followee.getName() + ".");
    }

    public List<String> unfollow(Integer followerId, Integer followeeId) {
        User follower = getOrThrow(followerId);
        User followee = getOrThrow(followeeId);
        follower.unfollow(followee);
        return List.of(follower.getName() + " has unfollowed " + followee.getName() + ".");
    }

    public List<String> post(Integer userId, String content) {
        User user = getOrThrow(userId);
        List<String> events = new ArrayList<>();
        events.add(user.getName() + " posted: \"" + content + "\".");

        for (User follower : user.getFollowers()) {
            events.add(follower.getName() + " received notification: " + user.getName() + " posted: \"" + content + "\".");
        }

        return events;
    }

    public void clearAll() {
        users.clear();
    }

    private User getOrThrow(Integer id) {
        User user = users.get(id);
        if (user == null) {
            throw new ElementNotFoundException("User not found for id=" + id);
        }
        return user;
    }
}
