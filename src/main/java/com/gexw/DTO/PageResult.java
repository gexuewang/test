package com.gexw.DTO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 统一分页响应对象
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> {
    private Integer code;       // 状态码
    private String msg;    // 消息
    private PageData<T> data; // 分页数据

    // 成功响应
    public static <T> PageResult<T> success(PageData<T> data) {
        PageResult<T> result = new PageResult<>();
        result.setCode(1);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    // 失败响应
    public static <T> PageResult<T> error(int code, String message) {
        PageResult<T> result = new PageResult<>();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

}

