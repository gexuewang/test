package com.gexw.controller;


import com.gexw.client.FastDFSClient;
import com.gexw.result.Result;
import com.gexw.service.IMembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

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




    @ApiOperation(value = "根据姓名查询会员", notes = "传入会员姓名，返回会员信息列表")
    @GetMapping("/members/{name}")
    public Result members(@ApiParam(value = "会员姓名", required = true) @PathVariable String name) {

        List m= membersService.getmembers(name);
        return Result.success(m);
    }


}
