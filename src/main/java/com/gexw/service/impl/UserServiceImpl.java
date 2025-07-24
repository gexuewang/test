package com.gexw.service.impl;

import com.gexw.VO.ResultServiceVO;
import com.gexw.entity.User;
import com.gexw.mapper.UserMapper;
import com.gexw.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gexw.util.ThreadlocalUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gexw
 * @since 2025-07-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultServiceVO addUseI(User user) {
        if (user.getName()==null || user.getName().equals("")){
            return ResultServiceVO.error("用户名不能为空");
        }
        if(user.getEmail()==null || user.getEmail().equals("")){
            return ResultServiceVO.error("邮箱不能为空");
        }
        if(user.getPhone()==null || user.getPhone().equals("")){
            return ResultServiceVO.error("手机号不能为空");
        }

        Long id = ThreadlocalUserId.get();
        user.setCreateId(Integer.parseInt(id+""));
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return ResultServiceVO.success();
    }
}
