package com.gexw.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gexw.DTO.PageDTO;
import com.gexw.VO.PageVO;
import com.gexw.VO.ResultServiceVO;
import com.gexw.entity.User;
import com.gexw.result.Result;
import com.gexw.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gexw
 * @since 2025-07-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    public Result getUserPage(@RequestParam(defaultValue = "1") Integer pageNum
                                    , @RequestParam(defaultValue = "5") Integer pageSize ){
        Page<User> page = userService.page(new Page<>(pageNum, pageSize));
        PageVO pageVO = new PageVO<>();
        pageVO.setPageNum(Integer.parseInt(pageNum+""));
        pageVO.setPageSize(Integer.parseInt(pageSize+""));
        pageVO.setAllRow(page.getTotal()); //总记录数
        pageVO.setTotalPage(page.getPages()); //总页数
        pageVO.setData(page.getRecords());
        return Result.success(pageVO);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        ResultServiceVO resultServiceVO= userService.addUseI(user);
        if (resultServiceVO.getCode() == 1){
            return Result.success();
        }else {
            return Result.error(resultServiceVO.getMsg());
        }
    }
}
