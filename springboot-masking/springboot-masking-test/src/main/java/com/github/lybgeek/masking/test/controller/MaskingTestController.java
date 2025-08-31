package com.github.lybgeek.masking.test.controller;


import com.github.lybgeek.masking.context.TenantContext;
import com.github.lybgeek.masking.test.model.BankCard;
import com.github.lybgeek.masking.test.model.Order;
import com.github.lybgeek.masking.test.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 脱敏规则测试控制器，用于验证不同租户的脱敏效果
 */
@RestController
@RequestMapping("/test/masking")
public class MaskingTestController {

    /**
     * 测试用户信息脱敏
     */
    @GetMapping("/user")
    public User testUserMasking() {
        return new User(
            1001L,
            "李四",
            "13987654321",
            "lisi@company.com",
            "310104198506127890",
            "6228480402561234567",
            "上海市浦东新区张江高科技园区博云路2号"
        );
    }

    /**
     * 测试银行卡信息脱敏
     */
    @GetMapping("/bank-card")
    public BankCard testBankCardMasking() {
        return new BankCard(
            "6222031202001234567",
            "李四",
            "ICBC",
            "储蓄卡",
            "2028-12"
        );
    }

    /**
     * 测试订单信息脱敏（包含嵌套对象）
     */
    @GetMapping("/order")
    public Order testOrderMasking() {
        User buyer = new User(
            1002L,
            "王五",
            "13712345678",
            "wangwu@example.com",
            "440106199203214567",
            "6226090210012345678",
            "广州市天河区珠江新城冼村路5号"
        );
        
        Order order = new Order();
        order.setOrderId("ORD20230515001");
        order.setBuyer(buyer);
        order.setAmount(9999.99);
        order.setPaymentMethod("credit_card");
        order.setPaymentAccount("6225881234567890");
        order.setCreateTime(new Date());
        order.setShippingAddress("深圳市南山区科技园科苑路8号");
        
        return order;
    }

    /**
     * 测试列表数据脱敏
     */
    @GetMapping("/user-list")
    public List<User> testUserListMasking() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(
            1003L,
            "赵六",
            "13687654321",
            "zhaoliu@test.com",
            "330102198809234567",
            "6217001234567890123",
            "杭州市西湖区文三路9号"
        ));
        userList.add(new User(
            1004L,
            "孙七",
            "13512345678",
            "sunqi@demo.com",
            "510104199504156789",
            "621483123456789012",
            "成都市锦江区红星路3段1号"
        ));
        return userList;
    }

    /**
     * 手动切换租户（用于测试）
     */
    @PostMapping("/switch-tenant")
    public String switchTenant(@RequestParam String tenantId) {
        // 实际环境中一般通过拦截器从请求头自动获取，这里提供手动切换接口方便测试
        TenantContext.setTenantId(tenantId);
        return "已切换到租户: " + tenantId;
    }

    /**
     * 获取当前租户信息
     */
    @GetMapping("/current-tenant")
    public String getCurrentTenant() {
        return "当前租户: " + TenantContext.getTenantId();
    }
}
