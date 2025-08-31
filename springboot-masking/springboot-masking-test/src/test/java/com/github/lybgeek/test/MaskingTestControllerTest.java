package com.github.lybgeek.test;


import com.github.lybgeek.masking.context.TenantContext;
import com.github.lybgeek.masking.test.MaskingTestApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MaskingTestController的单元测试，验证不同租户的脱敏效果
 */

@SpringBootTest(classes = MaskingTestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MaskingTestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }


    /**
     * 测试默认租户的用户信息脱敏效果
     */
    @Test
    public void testDefaultTenantUserMasking() throws Exception {

        MvcResult result = mockMvc.perform(get("/test/masking/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertNotNull(jsonResponse);
        
        // 验证默认脱敏规则是否生效
        // 手机号：前3后4，中间4位掩码
        assertTrue(jsonResponse.contains("\"phone\":\"139****4321\""));
        // 姓名：保留姓氏，名字掩码
        assertTrue(jsonResponse.contains("\"name\":\"李*\""));
        // 身份证：前6后4，中间8位掩码
        assertTrue(jsonResponse.contains("\"idCard\":\"310104********7890\""));
    }

    /**
     * 测试tenant1的用户信息脱敏效果
     */
    @Test
    public void testTenant1UserMasking() throws Exception {

        MvcResult result = mockMvc.perform(get("/test/masking/user")
                        .header("X-Tenant-Id", "tenant1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertNotNull(jsonResponse);

        // 验证tenant1的脱敏规则
        // 手机号：前3后4（tenant1规则）
        assertTrue(jsonResponse.contains("\"phone\":\"139****4321\""));
        // 邮箱：前2位掩码（tenant1规则）
        assertTrue(jsonResponse.contains("\"email\":\"li**************\""));

    }

    /**
     * 测试tenant2的用户信息脱敏效果
     */
    @Test
    public void testTenant2UserMasking() throws Exception {


        MvcResult result = mockMvc.perform(get("/test/masking/user")
                        .header("X-Tenant-Id", "tenant2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertNotNull(jsonResponse);

        // 验证tenant2的脱敏规则
        // 手机号：前4后3（tenant2规则）
        assertTrue(jsonResponse.contains("\"phone\":\"1398****321\""));
        // 姓名：保留前2位（tenant2规则）
        assertTrue(jsonResponse.contains("\"name\":\"李四\"")); // 如果姓名是两个字，tenant2规则保留2位则不脱敏
        // 银行卡：前6后3（tenant2规则）
        assertTrue(jsonResponse.contains("\"bankAccount\":\"622848**********567\""));

    }

    /**
     * 测试嵌套对象的脱敏效果
     */
    @Test
    public void testOrderMasking() throws Exception {

        MvcResult result = mockMvc.perform(get("/test/masking/order")
                        .header("X-Tenant-Id", "tenant3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertNotNull(jsonResponse);

        // 验证tenant3的特殊掩码（使用#）
        assertTrue(jsonResponse.contains("\"phone\":\"137####5678\""));
        assertTrue(jsonResponse.contains("\"paymentAccount\":\"6225#########890\""));
        // 验证嵌套对象脱敏
        assertTrue(jsonResponse.contains("\"name\":\"王#\""));

    }

    /**
     * 测试列表数据的脱敏效果
     */
    @Test
    public void testUserListMasking() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/masking/user-list")
                        .header("X-Tenant-Id", "tenant1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertNotNull(jsonResponse);

        // 验证列表中多个对象的脱敏效果
        assertTrue(jsonResponse.contains("\"name\":\"赵*\""));
        assertTrue(jsonResponse.contains("\"name\":\"孙*\""));
        assertTrue(jsonResponse.contains("\"phone\":\"136****4321\""));
        assertTrue(jsonResponse.contains("\"phone\":\"135****5678\""));
    }




}
