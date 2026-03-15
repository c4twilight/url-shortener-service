package com.example.myapplication.social.model;

import java.util.NavigableSet;
import java.util.TreeSet;

public class User {
    private final Integer id;
    private final String name;
    private final NavigableSet<User> followers;
    private final NavigableSet<User> following;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.followers = new TreeSet<>((a, b) -> a.id.compareTo(b.id));
        this.following = new TreeSet<>((a, b) -> a.id.compareTo(b.id));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void removeFollower(User user) {
        followers.remove(user);
    }

    public void follow(User user) {
        if (id.equals(user.id)) {
            return;
        }
        following.add(user);
        user.addFollower(this);
    }

    public void unfollow(User user) {
        following.remove(user);
        user.removeFollower(this);
    }

    public NavigableSet<User> getFollowers() {
        return followers;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User other)) {
            return false;
        }
        return id.equals(other.id);
    }
}
