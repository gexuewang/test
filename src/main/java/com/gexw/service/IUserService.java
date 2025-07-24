package com.gexw.service;

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
}
