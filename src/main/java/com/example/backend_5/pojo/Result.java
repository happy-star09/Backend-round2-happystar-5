package com.example.backend_5.pojo;

import lombok.Data;

@Data
public class Result { // 泛型<T>表示这个类里面可以装任何类型的数据
    private Integer code; // 状态码
    private String msg; // 提示信息

    public static Result success() {
        Result r = new Result();
        r.code = 1;
        r.msg = "Success"; // 成功只需要提示成功
        return r;
    }

    public static Result error(String msg) { // 把错误原因返回给前端，让前端知道错哪儿了
        Result r = new Result();
        r.code = 0;
        r.msg = msg; // 失败要说明为什么失败，所以不返回定死的字符串
        return r;
    }
}