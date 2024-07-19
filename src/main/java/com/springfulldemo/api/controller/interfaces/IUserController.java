package com.springfulldemo.api.controller.interfaces;

import com.springfulldemo.api.model.dtos.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.springfulldemo.api.constants.Paths.prefixPath;

@RequestMapping(IUserController.PATH)
public interface IUserController extends IAbstractAllController<UserDTO> {

    String PATH = prefixPath + "/user";

}