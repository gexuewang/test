package com.gexw.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gexw.DTO.PageData;
import com.gexw.VO.PageVO;
import com.gexw.VO.ResultServiceVO;
import com.gexw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gexw
 * @since 2025-07-24
 */
public interface IUserService extends IService<User> {


    ResultServiceVO addUseI(User user);

    PageData<User> getUserPage(Integer pageNum, Integer pageSize);


}
