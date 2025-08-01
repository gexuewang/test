package com.gexw.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gexw.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gexw
 * @since 2025-07-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into user(name, email, phone, create_time,create_id) values " +
            "(#{name},#{email},#{phone},#{createTime},#{createId})")
    void insertUser(User user);

    @Select("select * from user")
    Page<User> selectAll();
}
