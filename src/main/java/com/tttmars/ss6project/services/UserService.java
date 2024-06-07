package com.tttmars.ss6project.services;

import com.tttmars.ss6project.dto.UserRequest;
import com.tttmars.ss6project.dto.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();


}
