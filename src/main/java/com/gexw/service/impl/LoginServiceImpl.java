package com.gexw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gexw.DTO.LoginDTO;
import com.gexw.entity.User;
import com.gexw.mapper.UserMapper;
import com.gexw.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(LoginDTO loginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginDTO.getName()).eq("pwd", loginDTO.getPwd());
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
