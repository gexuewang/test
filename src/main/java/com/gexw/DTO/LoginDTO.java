package com.gexw.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginDTO  implements Serializable {
    @NotEmpty(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "密码不能为空")
    private String pwd;

    @NotEmpty(message = "验证码不能为空")
    private String code;

    @NotEmpty(message = "客户端ID不能为空")
    private String clientId;
}
