package com.ResourceSystem.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ResourceSystem.dto.LoginRequest;
import com.ResourceSystem.dto.LoginResponse;
import com.ResourceSystem.entity.User;
import com.ResourceSystem.exception.UnauthorizedException;
import com.ResourceSystem.repository.UserRepository;
import com.ResourceSystem.security.JwtTokenProvider;
import com.ResourceSystem.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
        private final UserRepository userRepository;

        private final JwtTokenProvider jwtTokenProvider;

        private final AuthenticationManager authenticationManager;

        public LoginResponse login(LoginRequest request) {

                try {

                        /*
                         * Authenticate user email and password
                         * Spring Security checks BCrypt password
                         */
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));

                        /*
                         * Fetch user details
                         */
                        User user = userRepository
                                        .findByEmail(request.getEmail())
                                        .orElseThrow(
                                                        () -> new UnauthorizedException(
                                                                        "User not found"));

                        /*
                         * Generate JWT token
                         */
                        String token = jwtTokenProvider.generateToken(
                                        user.getEmail());

                        /*
                         * Return login response
                         */
                        return new LoginResponse(

                                        token,

                                        user.getEmail(),

                                        "Bearer",

                                        user.getRole().getName()

                        );

                } catch (Exception ex) {
                        throw new UnauthorizedException("Invalid email or password");
                }
        }
}
