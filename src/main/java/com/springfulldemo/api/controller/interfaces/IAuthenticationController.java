package com.springfulldemo.api.controller.interfaces;

import com.springfulldemo.api.constants.Paths;
import com.springfulldemo.api.model.beans.TokenBean;
import com.springfulldemo.api.model.dtos.UserDTO;
import com.springfulldemo.api.model.records.AuthenticationRecord;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(IAuthenticationController.PATH)
public interface IAuthenticationController {
    String PATH = Paths.prefixPath + "/session";

    @PostMapping("/login")
    ResponseEntity<TokenBean> login(@RequestBody @Valid AuthenticationRecord authenticationRecord);

    @PostMapping("/refresh-token")
    ResponseEntity<TokenBean> refreshToken(@RequestBody TokenBean tokenBeanRequest);

    @PutMapping("/user")
    ResponseEntity<UserDTO> updateSessionUser(@RequestBody UserDTO user);

    @GetMapping
    ResponseEntity<TokenBean> getSession();
}