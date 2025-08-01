package com.gexw.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gexw.DTO.PageData;
import com.gexw.VO.PageVO;
import com.gexw.VO.ResultServiceVO;
import com.gexw.entity.User;
import com.gexw.mapper.UserMapper;
import com.gexw.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gexw.util.ThreadlocalUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



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
        userMapper.insertUser(user);
        return ResultServiceVO.success();
    }

    @Override
    public PageData<User> getUserPage(Integer pageNum, Integer pageSize) {
        Page<User> page=userMapper.selectPage(new Page<>(pageNum,pageSize),null);

//        PageVO pageVO = new PageVO<>();
//        pageVO.setPageNum(Integer.parseInt(pageNum+""));
//        pageVO.setPageSize(Integer.parseInt(pageSize+""));
//        pageVO.setAllRow(page.getTotal()); //总记录数
//        pageVO.setTotalPage(page.getPages()); //总页数
//        pageVO.setData(page.getRecords());

        PageData<User> from = PageData.from(page);
        return from;
    }



}
