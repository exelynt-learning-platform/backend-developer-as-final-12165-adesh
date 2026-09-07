package com.ResourceSystem.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("admin1234 : " + encoder.encode("admin1234"));
        System.out.println("user1234  : " + encoder.encode("user1234"));

        System.out.println("Test12345  : " + encoder.encode("Test12345"));
    }

}
