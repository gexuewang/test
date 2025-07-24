package com.gexw.service;

import com.gexw.DTO.LoginDTO;
import com.gexw.entity.User;

public interface LoginService {
    User  login(LoginDTO loginDTO);
}
