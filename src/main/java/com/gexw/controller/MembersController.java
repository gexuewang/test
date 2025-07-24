package com.gexw.controller;


import com.gexw.entity.Members;
import com.gexw.result.Result;
import com.gexw.service.IMembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gexw
 * @since 2025-07-22
 */
@Api(tags = "会员相关接口")
@RestController
@RequestMapping("/members")
@Slf4j
public class MembersController {

    @Autowired
    private IMembersService membersService;
    @Value("${gexw.g1.name}")
    private String name;

    @Value("${gexw.g1.age}")
    private Integer age;

    @ApiOperation(value = "根据姓名查询会员", notes = "传入会员姓名，返回会员信息列表")
    @GetMapping("/members/{name}")
    public Result members(@ApiParam(value = "会员姓名", required = true) @PathVariable String name) {
        log.info("传入查询名字:{}", name);
        log.info("{}......{}",name,age);
        List m= membersService.getmembers(name);
        return Result.success(m);
    }
}
