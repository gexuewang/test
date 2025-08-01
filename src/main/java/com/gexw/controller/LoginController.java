package com.gexw.controller;

import cn.hutool.json.JSONUtil;
import com.gexw.DTO.LoginDTO;
import com.gexw.entity.User;
import com.gexw.result.Result;
import com.gexw.service.LoginService;
import com.gexw.util.CaptUtils;
import com.gexw.util.JwtHelper;
import com.gexw.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LoginService loginService;


    @PostMapping("user")
    public Result login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        String s = stringRedisTemplate.opsForValue().get("code:" + loginDTO.getClientId());

        if (s == null) {
            return Result.error("客户端id有误");
        }
        if(!s.equals(loginDTO.getCode())){
            return Result.error("验证码错误");
        }

        User user=  loginService.login(loginDTO);
       if(user==null){
           return Result.error("用户或密码错误");
       }
        String token = JwtHelper.createToken(Math.toIntExact(user.getId()), user.getName());
       String userInfo = user.getId()+"_"+user.getName();
       stringRedisTemplate.opsForValue().set("userInfo"+user.getId(),userInfo);

       //暴露自定义请求头
        response.addHeader("Access-Control-Expose-Headers","access_token");
        response.setHeader("access_token",token);
        return Result.success(userInfo);
    }

    @GetMapping("/code/{clientId}")
    public Result getCode(@PathVariable String clientId) throws IOException {
        Map<String, String> captchaMap = CaptUtils.createCaptString();
        String captCodeValue = captchaMap.get("captCodeValue");
        String base64Value = captchaMap.get("base64Value");


        stringRedisTemplate.opsForValue().set("code:"+clientId,captCodeValue,2, TimeUnit.MINUTES);
        // 返回结果，包含验证码文本和 Base64 编码的图片字符串
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("captCodeValue", captCodeValue);
        resultMap.put("base64Value", base64Value);

        return Result.success(resultMap);
    }
}
