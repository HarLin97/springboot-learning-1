package com.github.lybgeek.user.service;


import com.github.lybgeek.user.dao.UserRepository;
import com.github.lybgeek.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 创建用户（自动填充注册时间）
    public User createUser(User user) {
        // 新增用户时，默认设置注册时间为当前时间
        if (user.getRegistrationTime() == null) {
            user.setRegistrationTime(LocalDateTime.now());
        }
        // 默认激活状态为true
        if (!user.isActive()) {
            user.setActive(true);
        }
        return userRepository.save(user);
    }

    // 根据ID查询
    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // 查询所有
    public List<User> getAllUsers() {
        Map<Integer, User> userMap = userRepository.findAll();
        if(CollectionUtils.isEmpty(userMap)){
            User user = new User();
            user.setId(1);
            user.setUsername("test");
            user.setFullName("测试用户");
            user.setEmail("test@example.com");
            user.setPhone("13800138000");
            user.setGender("男");
            user.setAge(18);
            user.setAddress("中国");
            user.setRegistrationTime(LocalDateTime.now());
            return Arrays.asList( user);

        }
        return userMap.values().stream().collect(Collectors.toList());
    }

    // 更新用户（注意：更新时不会覆盖注册时间，除非手动传）
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId());
        if (existingUser == null) {
            return null;
        }
        // 保留原注册时间（避免更新时被覆盖）
        if (user.getRegistrationTime() == null) {
            user.setRegistrationTime(existingUser.getRegistrationTime());
        }
        return userRepository.save(user);
    }

    // 删除用户
    public boolean deleteUser(Integer id) {
        if (userRepository.findById(id) != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}