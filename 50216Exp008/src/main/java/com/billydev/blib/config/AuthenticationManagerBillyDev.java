package com.billydev.blib.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerBillyDev implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("Customized authentication manager"); 
		if(authentication.getName().equals("test")) {
			throw new BillyDevAuthenticationException("login error");
		}else {
		return null;
		}
	}

}
