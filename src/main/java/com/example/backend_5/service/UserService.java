package com.example.backend_5.service;

import com.example.backend_5.pojo.User;
import com.example.backend_5.utils.Md5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service // 告诉 Spring 容器，这个类用来处理业务逻辑。当启动的时候，把它创建成一个Bean对象存起来
public class UserService {
    @Value("${app.user.data-file-path}") // 读取 yml 里面的配置
    private String filePath;

    @Value("${app.user.password.min-length}")
    private int minLength;

    @Value("${app.user.username.pattern}")
    private String usernamePattern;

    /*
     * final 是 Java 关键字，修饰变量,使此变量只能被赋值一次
     * 用 ConcurrentHashMap 而不是普通的 HashMap
     * 因为 ConcurrentHashMap 是并发
     * 如果用普通的 HashMap，当两个用户同时操作，两者的信息就可能会相互冲撞，导致数据丢失或者程序报错
     */
    private static Map<String, User> userMap = new ConcurrentHashMap<>();

    public void login(User user) {
        User existUser = userMap.get(user.getUsername());
        if(existUser == null || !existUser.getPassword().equals(Md5Util.encrypt(user.getPassword()))) {

            /*
             * throw是抛出，会将""里的话抛出，然后被 Controller 抓住返回给前端
             * RuntimeException是JDK官方自带的一个类，见名知意是“运行时异常”
             */
            throw new RuntimeException("用户名或密码错误");
        }
    }
    public void register(User user) {
        User existUser = userMap.get(user.getUsername());
        if(existUser != null) {
            throw new RuntimeException("用户名已存在！");
        }
        if(user.getPassword().length() < minLength) {
            throw new RuntimeException("密码长度不能小于" + minLength + "位");
        }
        if(!user.getUsername().matches(usernamePattern)) { // matches()是拿着字符串和规定比较，不符合就打出去
            throw new RuntimeException("用户名不正确！");
        }
        String mima = Md5Util.encrypt(user.getPassword()); // 加密密码
        user.setPassword(mima); // 把加密后的内容放回去，不然密码还是明文
        userMap.put(user.getUsername(), user); // 储存用户数据到userMap中
        saveToFile(user); // 将数据存到文件里
    }
    private void saveToFile(User user) { // true的意思是在文件末尾接着写
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // 【改动2】这里原来少写了一个右括号，导致整段报错，已补上
            writer.write(user.getUsername() + "，" + user.getPassword());
            writer.newLine(); // 分行，让一行只有一个用户
        }catch(IOException e) { // e代表捕获到的错误对象
            throw new RuntimeException("文件写入失败", e); // 抛出异常并带上错误原因
        }
    }
}
