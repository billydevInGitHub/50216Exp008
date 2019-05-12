package com.billydev.blib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.billydev.blib.config.JwtTokenUtil;
import com.billydev.blib.model.ApiResponse;
import com.billydev.blib.model.AuthToken;
import com.billydev.blib.model.LoginUser;
import com.billydev.blib.model.User;
import com.billydev.blib.model.WrapOfListDTApplInfo;
import com.billydev.blib.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
       	System.out.println("AuthenticationController, request received123"); 
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
    	System.out.println("AuthenticationController, got the token :"+token);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getUsername()));

    }

}
