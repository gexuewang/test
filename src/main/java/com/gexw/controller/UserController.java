package com.gexw.controller;


import com.gexw.DTO.PageData;
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

        PageData<User> userPage = userService.getUserPage(pageNum, pageSize);
        return Result.success(userPage);
    }

    @PostMapping("/add1111")
    public Result addUser(@RequestBody User user){
        System.out.println(user);
        ResultServiceVO resultServiceVO= userService.addUseI(user);
        if (resultServiceVO.getCode() == 1){
            return Result.success();
        }else {
            return Result.error(resultServiceVO.getMsg());
        }
    }


    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable Integer id){
      User user=  userService.getById(id);
        System.out.println(user);
      return Result.success(user);
    }


    @PostMapping("/addorupdate")
    public Result add(@RequestBody User user){
        System.out.println(user);
        userService.saveOrUpdate(user);
        return Result.success();
    }

    @DeleteMapping("del/{id}")
    public Result del(@PathVariable Integer id){
        userService.removeById(id);
        return Result.success();
    }
}
