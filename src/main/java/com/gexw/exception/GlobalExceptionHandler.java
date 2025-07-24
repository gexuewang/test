package com.gexw.exception;


import com.gexw.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 用于统一处理项目中的各种异常，返回统一的错误响应格式
 *
 * @author zz
 * @date 2025/1/1
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常（@Valid 注解）
     * 当使用 @Valid 注解进行参数校验时，如果校验失败会抛出此异常
     *
     * @param e MethodArgumentNotValidException 参数校验异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 获取所有字段错误信息
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = "参数验证失败: " + String.join(", ", errors);
        log.warn("参数校验失败: {}", errorMessage);

        return Result.error(errorMessage);
    }

    /**
     * 处理约束违反异常（@Validated 注解）
     * 当使用 @Validated 注解进行参数校验时，如果校验失败会抛出此异常
     *
     * @param e ConstraintViolationException 约束违反异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        try {
            // 获取所有约束违反信息
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            List<String> errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            String errorMessage = "参数验证失败: " + String.join(", ", errors);
            log.warn("约束违反: {}", errorMessage);

            return Result.error(errorMessage);
        } catch (Exception ex) {
            log.error("处理约束违反异常时出错: ", ex);
            return Result.error("参数验证失败");
        }
    }

    /**
     * 处理绑定异常
     * 当表单数据绑定到对象时发生错误会抛出此异常
     *
     * @param e BindException 绑定异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleBindException(BindException e) {
        // 获取所有字段错误信息
        List<String> errors = e.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = "数据绑定失败: " + String.join(", ", errors);
        log.warn("数据绑定失败: {}", errorMessage);

        return Result.error(errorMessage);
    }

    /**
     * 处理空指针异常
     * 当访问空对象的属性或方法时会抛出此异常
     *
     * @param e NullPointerException 空指针异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常: ", e);
        return Result.error("系统内部错误，请联系管理员");
    }

    /**
     * 处理数字格式异常
     * 当字符串转换为数字失败时会抛出此异常
     *
     * @param e NumberFormatException 数字格式异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleNumberFormatException(NumberFormatException e) {
        log.warn("数字格式异常: {}", e.getMessage());
        return Result.error("数字格式不正确");
    }

    /**
     * 处理数组越界异常
     * 当访问数组不存在的索引时会抛出此异常
     *
     * @param e ArrayIndexOutOfBoundsException 数组越界异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        log.error("数组越界异常: ", e);
        return Result.error("系统内部错误，请联系管理员");
    }

    /**
     * 处理类型转换异常
     * 当类型转换失败时会抛出此异常
     *
     * @param e ClassCastException 类型转换异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleClassCastException(ClassCastException e) {
        log.error("类型转换异常: ", e);
        return Result.error("系统内部错误，请联系管理员");
    }

    /**
     * 处理运行时异常
     * 捕获所有未明确处理的运行时异常
     *
     * @param e RuntimeException 运行时异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: ", e);
        return Result.error("系统运行异常，请联系管理员");
    }

    /**
     * 处理所有其他异常
     * 这是一个兜底的异常处理方法，捕获所有未被其他方法处理的异常
     *
     * @param e Exception 异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error("系统异常，请联系管理员");
    }

    /**
     * 处理自定义业务异常
     * 当业务逻辑出现问题时抛出此异常
     *
     * @param e BusinessException 业务异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage(), e.getCode());
    }
}