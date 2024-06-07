package com.tttmars.ss6project.controller;


import com.tttmars.ss6project.dto.AuthRequestDTO;
import com.tttmars.ss6project.dto.JwtResponseDTO;
import com.tttmars.ss6project.dto.RefreshTokenRequestDTO;
import com.tttmars.ss6project.dto.UserResponse;
import com.tttmars.ss6project.model.RefreshToken;
import com.tttmars.ss6project.services.UserService;
import com.tttmars.ss6project.services.RefreshTokenService;
import com.tttmars.ss6project.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RefreshTokenService refreshTokenService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
//            return JwtResponseDTO.builder()
//                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .refreshtoken(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshtoken(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
