package com.springfulldemo.api.controller.interfaces;

import com.springfulldemo.api.model.beans.TokenBean;
import com.springfulldemo.api.model.dtos.UserDTO;
import com.springfulldemo.api.model.records.AuthenticationRecord;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static com.springfulldemo.api.constants.Paths.prefixPath;

@RequestMapping(IAuthenticationController.PATH)
public interface IAuthenticationController {
    String PATH = prefixPath + "/session";

    @PostMapping("/login")
    TokenBean login(@RequestBody @Valid AuthenticationRecord authenticationRecord);

    @PostMapping("/refresh-token")
    TokenBean refreshToken(@RequestBody TokenBean tokenBeanRequest);

    @PutMapping("/user")
    UserDTO updateSessionUser(@RequestBody UserDTO user);

    @GetMapping
    TokenBean getSession();

}