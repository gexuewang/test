package com.gexw.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class LoginDTO  implements Serializable {
    @NotNull(message = "名字不能为空")
    private String name;

    @NotNull(message = "密码不能为空")
    private String pwd;

    @NotNull(message = "验证码不能为空")
    private String code;

    @NotNull(message = "客户端ID不能为空")
    private String clientId;
}
