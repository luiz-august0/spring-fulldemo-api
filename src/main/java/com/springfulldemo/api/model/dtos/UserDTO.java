package com.springfulldemo.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springfulldemo.api.model.beans.MultipartBean;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.model.enums.EnumUserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDTO extends AbstractDTO<User> {

    private Integer id;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private EnumUserRole role;

    private Boolean active;

    private String photo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartBean photoMultipart;

}