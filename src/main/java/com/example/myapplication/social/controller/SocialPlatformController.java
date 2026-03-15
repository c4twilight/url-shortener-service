package com.example.myapplication.social.controller;

import com.example.myapplication.social.dto.AddUserRequest;
import com.example.myapplication.social.dto.FollowRequest;
import com.example.myapplication.social.dto.PostRequest;
import com.example.myapplication.social.dto.SocialActionResponse;
import com.example.myapplication.social.service.PlatformService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social")
public class SocialPlatformController {

    private final PlatformService platformService;

    public SocialPlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public SocialActionResponse addUser(@Valid @RequestBody AddUserRequest request) {
        return new SocialActionResponse(platformService.addUser(request.id(), request.name()));
    }

    @PostMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public SocialActionResponse follow(@Valid @RequestBody FollowRequest request) {
        return new SocialActionResponse(platformService.follow(request.followerId(), request.followeeId()));
    }

    @PostMapping("/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public SocialActionResponse unfollow(@Valid @RequestBody FollowRequest request) {
        return new SocialActionResponse(platformService.unfollow(request.followerId(), request.followeeId()));
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public SocialActionResponse post(@Valid @RequestBody PostRequest request) {
        return new SocialActionResponse(platformService.post(request.userId(), request.content()));
    }
}
