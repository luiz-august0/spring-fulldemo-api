package com.springfulldemo.api.service;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.springfulldemo.api.repository.UserRepository;
import com.springfulldemo.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            UserDetails userDetails = repository.findByLogin(username);

            if (Utils.isEmpty(userDetails))
                throw new ApplicationGenericsException(EnumUnauthorizedException.WRONG_CREDENTIALS);

            return userDetails;
        } catch (UsernameNotFoundException e) {
            throw new ApplicationGenericsException(EnumResourceNotFoundException.RESOURCE_NOT_FOUND);
        }
    }
}
