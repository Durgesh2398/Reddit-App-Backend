package com.upgrad.reddit.api.controller;
import com.upgrad.reddit.api.model.UserDeleteResponse;
import com.upgrad.reddit.service.business.AdminBusinessService;
import com.upgrad.reddit.service.entity.UserEntity;
import com.upgrad.reddit.service.exception.AuthorizationFailedException;
import com.upgrad.reddit.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminBusinessService adminBusinessService;

    @PostMapping("/deleteUser")
    public ResponseEntity<UserDeleteResponse> deleteUser(@RequestBody String userId, @RequestHeader String authorization) throws AuthorizationFailedException,UserNotFoundException {
        UserEntity userEntity = adminBusinessService.deleteUser(authorization,userId);
        return new ResponseEntity<UserDeleteResponse>(new UserDeleteResponse().id(userId),HttpStatus.OK);
    }
}
