package com.github.lybgeek.mixin.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lybgeek.mixin.user.UserMaxIn;
import com.github.lybgeek.user.controller.UserController;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper(); // 用于JSON序列化

    // 测试数据
    private User testUser = new User(
            "testuser", "测试用户", "test@example.com",
            "13800138000", "男", 25,
            "测试地址", true
    );

    public UserControllerTest() {
        testUser.setId(1);
        testUser.setRegistrationTime(LocalDateTime.of(2025, 11, 20, 10, 0));
    }


    @Test
    public void testUserMaxIn() throws Exception {
        String json = objectMapper.addMixIn(User.class, UserMaxIn.class).writeValueAsString(testUser);
        System.out.println(json);
    }

    // 1. 测试创建用户
    @Test
    public void testCreateUser() throws Exception {
        // 模拟服务层返回
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        // 执行POST请求并验证
        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // 验证服务层方法被调用
        verify(userService, times(1)).createUser(any(User.class));
    }

    // 2. 测试查询单个用户（存在）
    @Test
    public void testGetUserById_Exists() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("测试用户"));
    }

    // 3. 测试查询单个用户（不存在）
    @Test
    public void testGetUserById_NotExists() throws Exception {
        when(userService.getUserById(999)).thenReturn(null);

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    // 4. 测试查询所有用户
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = Arrays.asList(testUser, new User());
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // 验证返回2条数据
    }

    // 5. 测试更新用户（成功）
    @Test
    public void testUpdateUser_Success() throws Exception {
        User updatedUser = new User(
                "updateduser", "更新后的用户", "updated@example.com",
                "13900139000", "女", 26,
                "更新后地址", true
        );
        updatedUser.setId(1);
        updatedUser.setRegistrationTime(testUser.getRegistrationTime());

        when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(post("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.age").value(26));
    }

    // 6. 测试更新用户（失败：用户不存在）
    @Test
    public void testUpdateUser_NotFound() throws Exception {
        User nonExistentUser = new User();
        nonExistentUser.setId(999);

        when(userService.updateUser(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistentUser)))
                .andExpect(status().isNotFound());
    }

    // 7. 测试删除用户（成功）
    @Test
    public void testDeleteUser_Success() throws Exception {
        when(userService.deleteUser(1)).thenReturn(true);

        mockMvc.perform(post("/api/users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")) // 直接传ID
                .andExpect(status().isNoContent());
    }

    // 8. 测试删除用户（失败：用户不存在）
    @Test
    public void testDeleteUser_NotFound() throws Exception {
        when(userService.deleteUser(999)).thenReturn(false);

        mockMvc.perform(post("/api/users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("999"))
                .andExpect(status().isNotFound());
    }
}