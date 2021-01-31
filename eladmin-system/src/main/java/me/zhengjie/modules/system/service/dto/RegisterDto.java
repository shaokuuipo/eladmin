package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {
    private String account;
    private String password;
    private Long authId;
    private String authState;
    private String authSource;
}
