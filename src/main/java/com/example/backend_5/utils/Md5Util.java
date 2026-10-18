package com.example.backend_5.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class Md5Util {
    public static String encrypt(String password) {
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8); // 这一步是在定义一个变量，把用户输入的字符串密码按照 UTF-8 编码，拆解成计算机认识的底层字节数据

        /*
         *因为密码不能用明文存，需要加密，因此我们要把原本的密码进行转化
         *这时候就使用DigestUtils.md5DigestAsHex方法，将 bytes 里原来的一串有规律的数据转变成一串“乱码”
         * MD5 算法算出来的底层数据是一堆乱码字节，然后再用 AsHex 把它转化成 16 进制的字母和数字（Hex 是 16 进制数的缩写）
         */
        return DigestUtils.md5DigestAsHex(bytes);
    }

}
