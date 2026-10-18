package com.example.backend_5.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data; // 一个插件工具，加上它过后IDEA会在底层帮忙生成getUsername()等方法

@Data // 让下面的 User 类可以自动生成 get/set 方法(好方便不用我敲代码了)
public class User {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
