package com.gexw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gexw.entity.Members;
import com.gexw.mapper.MembersMapper;
import com.gexw.service.IMembersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gexw
 * @since 2025-07-22
 */
@Service
public class MembersServiceImpl extends ServiceImpl<MembersMapper, Members> implements IMembersService {

    @Autowired
    private MembersMapper membersMapper;

    @Override
    public List getmembers(String name) {
        QueryWrapper<Members> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return membersMapper.selectList(queryWrapper);
    }
}
