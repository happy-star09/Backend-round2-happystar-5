package com.example.backend_5.controller;

import com.example.backend_5.pojo.Result;
import com.example.backend_5.pojo.User;
import com.example.backend_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 在这里返回的数据要自动转成JSON格式返回给前端
@RequestMapping("user/") // 这个类里面所有接口路径前面都要带/user
public class UserController {

    @Autowired // 依赖注入
    private UserService userService;

    @PostMapping("/register")

    /*
     * @Validated触发检查，对参数进行校验，省去if-else
     * @RequestBody用于返回JSON数据
     */
    public Result register(@Validated @RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody User user) {
        userService.login(user);
        return Result.success();
    }
}
