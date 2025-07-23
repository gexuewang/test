package com.gexw.service;

import com.gexw.entity.Members;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gexw
 * @since 2025-07-22
 */
public interface IMembersService extends IService<Members> {

    List getmembers(String name);
}
