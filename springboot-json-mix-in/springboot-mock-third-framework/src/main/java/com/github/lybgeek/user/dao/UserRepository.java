package com.github.lybgeek.user.dao;

import com.github.lybgeek.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private static final Map<Integer, User> users = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    // 保存（新增/更新）
    public User save(User user) {
        if (user.getId() == null) {
            int newId = idGenerator.getAndIncrement();
            user.setId(newId);
        }
        users.put(user.getId(), user);
        return user;
    }

    // 根据ID查询
    public User findById(Integer id) {
        return users.get(id);
    }

    // 查询所有
    public Map<Integer, User> findAll() {
        return new HashMap<>(users); // 返回副本，避免外部修改原Map
    }

    // 删除
    public void deleteById(Integer id) {
        users.remove(id);
    }
}