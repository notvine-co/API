package dev.ianrich.kynos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Profile {
    public String username;
    public String password;
    public String avatar;
}
