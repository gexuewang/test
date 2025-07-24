package com.gexw.controller;

import com.gexw.DTO.LoginDTO;
import com.gexw.entity.User;
import com.gexw.result.Result;
import com.gexw.service.LoginService;
import com.gexw.util.JwtHelper;
import com.gexw.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

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
        String token = JwtHelper.createToken(user.getId(), user.getName());
       String userInfo = user.getId()+"_"+user.getName();
       stringRedisTemplate.opsForValue().set("userInfo"+user.getId(),userInfo);

       //暴露自定义请求头
        response.addHeader("Access-Control-Expose-Headers","access_token");
        response.setHeader("access_token",token);
        return Result.success(userInfo);
    }

    @GetMapping("/code/{clientId}")
    public Result getCode(@PathVariable String clientId) {

        String s = RandomUtil.getFourBitRandom();
        stringRedisTemplate.opsForValue().set("code:"+clientId,s,2, TimeUnit.MINUTES);
        return Result.success(s);
    }
}
