package com.gexw.VO;

import com.gexw.result.Result;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultServiceVO<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> ResultServiceVO<T> success() {
        ResultServiceVO<T> result = new ResultServiceVO<T>();
        result.code = 1;
        return result;
    }

    public static <T> ResultServiceVO<T> success(T object) {
        ResultServiceVO<T> result = new ResultServiceVO<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> ResultServiceVO<T> success(T object, String msg) {
        ResultServiceVO<T> result = new ResultServiceVO<T>();
        result.data = object;
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> ResultServiceVO<T> error(String msg) {
        ResultServiceVO result = new ResultServiceVO();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public static <T> ResultServiceVO<T> error(String msg,Integer code) {
        ResultServiceVO result = new ResultServiceVO();
        result.msg = msg;
        result.code = code;
        return result;
    }
}
