package com.springfulldemo.api.controller;

import com.springfulldemo.api.controller.interfaces.IAuthenticationController;
import com.springfulldemo.api.infrastructure.converter.Converter;
import com.springfulldemo.api.model.beans.TokenBean;
import com.springfulldemo.api.model.dtos.UserDTO;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.model.records.AuthenticationRecord;
import com.springfulldemo.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequiredArgsConstructor
@RestController
public class AuthenticationController implements IAuthenticationController, Serializable {

    private final AuthenticationService service;

    @Override
    public ResponseEntity<TokenBean> login(AuthenticationRecord authenticationRecord) {
        return service.login(authenticationRecord);
    }

    @Override
    public ResponseEntity<TokenBean> refreshToken(TokenBean tokenBeanRequest) {
        return service.refreshToken(tokenBeanRequest);
    }

    @Override
    public ResponseEntity<UserDTO> updateSessionUser(UserDTO user) {
        return service.updateSessionUser(Converter.convertDTOToEntity(user, User.class));
    }

    @Override
    public ResponseEntity<TokenBean> getSession() {
        return service.getSession();
    }
}
